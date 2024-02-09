import argparse
import numpy as np
from collections import OrderedDict
import os
import pandas as pd
import getpass
import shutil
from operator import itemgetter

getpass.getuser()


def parse_args():
    # DEFAULT PATHS TO CHANGE
    script_dir = os.path.dirname(__file__)  # <-- absolute dir the script is in
    parser = argparse.ArgumentParser(description="Datasets Processing.")
    parser.add_argument('--dataset', type=int, default=4,
                        help='Choose a dataset:\n 1)supermarket1  2)supermarket2 3)weather  4)Students 5)Tumor.')
    parser.add_argument('--TID', type=int, default=0,
                        help='Choose a dataset:\n VAL(0)=DEFAULT VAL(1)=TID.')
    u = getpass.getuser()
    parser.add_argument('--path_it_et', nargs='?', default='C:/users/' + u + '/Desktop/',
                        help='Path to save the results.')
    parser.add_argument('--path_saved', nargs='?', default=os.path.join(script_dir),
                        help='Path of the processed dataset.')
    parser.add_argument('--Minsup', nargs='?', default="0.25",
                        help='MINSUP.')
    parser.add_argument('--k', nargs='?', default="20000000000",
                        help='Stop in K iest itemset.')
    parser.add_argument('--Pres', nargs='?', default="2",
                        help='Precision.')
    parser.add_argument('--num', nargs='?', default="10",
                        help='number of rules.')

    return parser.parse_args()


def generer_item_set_1(data_frame_init, TID):
    if TID == 0:
        return data_frame_init.columns
    elif TID == 1:
        return data_frame_init['item'].tolist()


def comp_prefix(item1, item2):
    # cette fonction permet de vérifier si deux liste ont le meme préfixe
    nbr1 = len(item1)
    for i in range(0, nbr1 - 1):
        if item1[i] != item2[i]:
            return False
    return True


def in_ferm(final_list, fem_item1, fem_item2):
    # cette fonction permet de vérifier l'existence d'une sous-liste dans une liste
    if not set(final_list).issubset(fem_item1):
        if not set(final_list).issubset(fem_item2):
            return True
    return False


def generer_item_set(data_frame_fermetures):
    item_set = []
    for i in range(len(data_frame_fermetures)):
        # on prend l'item et sa fermeture
        item = data_frame_fermetures.values[i, 0]
        fem_item = data_frame_fermetures.values[i, 2]
        # on parcous notre itemset-n afin de génerer un itemset-n+1 en concaténant nos items en cas d'existence de préfixe en commun
        for j in range(i + 1, len(data_frame_fermetures)):
            if comp_prefix(item, data_frame_fermetures.values[j, 0]):
                joinedlist = item + data_frame_fermetures.values[j, 0]
                final_list = list(OrderedDict.fromkeys(joinedlist))
                # on vérifie avant d'ajouter l'item qui'il n'est pas contenu dans la fermuture de ses generateurs
                if in_ferm(final_list, fem_item, data_frame_fermetures.values[j, 2]):
                    item_set.append(final_list)
    return item_set


def supp_calc(item_set, data_frame, min_supp, TID, P):
    trans_numb = len(data_frame)
    supports = []
    res_prod = []
    if TID == 0:
        # on a séparer le cas de itemset-1 des autres
        if isinstance(item_set[0], str):
            for product in item_set:
                occ = data_frame[product].value_counts()[1]
                support = round(occ / trans_numb, P)
                if support >= min_supp:
                    supports.append(support)
                    res_prod.append(product)
        else:
            # on multiplie nos vecteurs d'items afin d'obtenir un seul vecteur pour compter le nombre de 1
            for l in item_set:
                res = np.ones(shape=len(data_frame))
                for k in l:
                    res = data_frame[k].to_numpy() * res
                occ = np.count_nonzero(res == 1)
                support = round(occ / trans_numb, P)
                if support >= min_supp:
                    supports.append(support)
                    res_prod.append(l)
    elif TID == 1:
        num = data_frame['transact_num'].iloc[0]
        trans_numb = int(num)
        if isinstance(item_set[0], str):
            for product in item_set:
                idx = data_frame.index[data_frame['item'] == product]
                li = data_frame['TID'].iloc[idx]
                res = li.values[0]
                res = res[1:len(res) - 1]
                list_ = res.split(", ")
                support = round(len(list_) / trans_numb, P)
                if support >= min_supp:
                    supports.append(support)
                    res_prod.append(product)
        else:
            for l in item_set:
                idx = data_frame.index[data_frame['item'] == l[0]]
                li = data_frame['TID'].iloc[idx]
                res = li.values[0]
                res = res[1:len(res) - 1]
                list_ = res.split(", ")
                set_1 = set(list_)
                for k in l:
                    idx = data_frame.index[data_frame['item'] == k]
                    li = data_frame['TID'].iloc[idx]
                    res = li.values[0]
                    res = res[1:len(res) - 1]
                    list_ = res.split(", ")
                    set_2 = set(list_)
                    set_1 = set_1.intersection(set_2)
                support = round(len(list(set_1)) / trans_numb, P)
                if support >= min_supp:
                    supports.append(support)
                    res_prod.append(l)
    return supports, res_prod


def fermetures(product_list, supports, data_frame, TID):
    df_res = pd.DataFrame(columns=['itemset', 'support', 'Ferm'])
    if TID == 0:
        # on calcule la fermeture des items fréquent en utilisant aussi la multiplication des vecteurs
        i = 0
        for product_elagué in product_list:
            f_list = []
            if isinstance(product_list[0], str):  # item set 1
                for product in data_frame.columns:
                    res = data_frame[product].to_numpy() * data_frame[product_elagué].to_numpy()
                    if np.array_equiv(data_frame[product_elagué].to_numpy(), res):
                        f_list.append(product)
                df_res.loc[len(df_res)] = [[product_elagué], supports[i], f_list]
                i += 1
            else:  # item set >1
                f_list = []
                res_join = np.ones(shape=len(data_frame))
                for k in product_elagué:
                    res_join = data_frame[k].to_numpy() * res_join
                for product in data_frame.columns:
                    res = data_frame[product].to_numpy() * res_join
                    if np.array_equiv(res_join, res):
                        f_list.append(product)
                df_res.loc[len(df_res)] = [product_elagué, supports[i], f_list]
                i += 1
    elif TID == 1:
        i = 0
        for product_elagué in product_list:
            f_list = []
            if isinstance(product_list[0], str):  # item set 1
                idx = data_frame.index[data_frame['item'] == product_elagué]
                li = data_frame['TID'].iloc[idx]
                res = li.values[0]
                res = res[1:len(res) - 1]
                list_1 = res.split(", ")
                for j in range(len(data_frame)):
                    li = data_frame['TID'].iloc[j]
                    res = li
                    res = res[1:len(res) - 1]
                    list_2 = res.split(", ")
                    if set(list_1).issubset(set(list_2)):
                        f_list.append(data_frame['item'].iloc[j])
                df_res.loc[len(df_res)] = [[product_elagué], supports[i], f_list]
                i += 1
            else:
                f_list = []
                m = 0
                for k in product_elagué:
                    if m == 0:
                        idx = data_frame.index[data_frame['item'] == k]
                        li = data_frame['TID'].iloc[idx]
                        res = li.values[0]
                        res = res[1:len(res) - 1]
                        list_ = res.split(", ")
                        inter = set(list_)
                    else:
                        idx = data_frame.index[data_frame['item'] == k]
                        li = data_frame['TID'].iloc[idx]
                        res = li.values[0]
                        res = res[1:len(res) - 1]
                        list_ = res.split(", ")
                        set_1 = set(list_)
                        inter = inter.intersection(set_1)
                    m += 1
                for j in range(len(data_frame)):
                    li = data_frame['TID'].iloc[j]
                    res = li
                    res = res[1:len(res) - 1]
                    list_2 = res.split(", ")
                    if inter.issubset(set(list_2)):
                        f_list.append(data_frame['item'].iloc[j])
                df_res.loc[len(df_res)] = [product_elagué, supports[i], f_list]
                i += 1
    return df_res


def close(data_frame_init, Min_s, k_stop, path_to_save, TID, P):
    if not os.path.exists(path_to_save + "/item sets"):  # le dossier n'existe pas
        os.mkdir(path_to_save + "/item sets")
    else:
        shutil.rmtree(path_to_save + "/item sets", ignore_errors=True)  # si il exist we delete
        os.mkdir(path_to_save + "/item sets")

    item_set = generer_item_set_1(data_frame_init, TID)
    df_res = pd.DataFrame(columns=['itemset', 'support', 'Ferm'])
    fin = True
    i = 1
    nbr = len(item_set)
    # les condition d'arret sont : il existe plus d'items fréquents, on a atteint le nombre maximal
    # d'itemset à génerer ou bien on ne peut plus génerer d'itemset
    while fin and len(item_set) > 0:

        supports, res_prod = supp_calc(item_set, data_frame_init, Min_s, TID, P)
        if len(res_prod) == 0 or i == nbr:
            break
        df = fermetures(res_prod, supports, data_frame_init, TID)
        print('--------------------item_set' + str(i) + "--------------------",
              file=open(path_to_save + "output.txt", "a"))
        print(df, file=open(path_to_save + "output.txt", "a"))
        np.savetxt(path_to_save + "/item sets/" + 'DF-' + str(i) + '.csv', df, fmt='%s', delimiter=';')
        df_res = pd.concat([df_res, df], axis=0, ignore_index=True)
        if k_stop == i:
            break
        i += 1
        item_set = generer_item_set(df)
    return df_res


def generer_regles_lift(data_frame_ferm, data_frame_init, TID, P, ARR):
    print("--------------LES REGLES D'ASSOCIATION-----------------", file=open(path_to_save + "output.txt", "a"))
    print("--------------LES REGLES D'ASSOCIATION-----------------",
          file=open(path_to_save + "association rules.txt", "w"))
    result = []
    for i in range(len(data_frame_ferm)):
        item = data_frame_ferm.values[i, 0]
        fermeture = data_frame_ferm.values[i, 2]
        fermeture_item = [x for x in fermeture if x not in item]
        if len(fermeture_item) > 0:
            if isinstance(item, str):
                item = [item]
            all = item + fermeture_item
            if TID == 0:

                res = np.ones(shape=len(data_frame_init))
                for k in all:
                    res = data_frame_init[k].to_numpy() * res
                occ = np.count_nonzero(res == 1)
                support_all = round(occ / len(data_frame_init), P)
                # lift
                res = np.ones(shape=len(data_frame_init))
                for k in fermeture_item:
                    res = data_frame_init[k].to_numpy() * res
                occ = np.count_nonzero(res == 1)
                support_ferm = occ / len(data_frame_init)
                lift = round(support_all / (data_frame_ferm.values[i, 1] * support_ferm), P)

                result.append([item, fermeture_item, lift])
            if TID == 1:
                idx = data_frame_init.index[data_frame_init['item'] == all[0]]
                li = data_frame_init['TID'].iloc[idx]
                res = li.values[0]
                res = res[1:len(res) - 1]
                list_ = res.split(", ")
                set_1 = set(list_)
                for k in all:
                    idx = data_frame_init.index[data_frame_init['item'] == k]
                    li = data_frame_init['TID'].iloc[idx]
                    res = li.values[0]
                    res = res[1:len(res) - 1]
                    list_ = res.split(", ")
                    set_2 = set(list_)
                    set_1 = set_1.intersection(set_2)
                occ = len(set_1)
                support_all = round(occ / int(data_frame_init['transact_num'].iloc[0]), P)
                # lift
                idx = data_frame_init.index[data_frame_init['item'] == fermeture_item[0]]
                li = data_frame_init['TID'].iloc[idx]
                res = li.values[0]
                res = res[1:len(res) - 1]
                list_ = res.split(", ")
                set_1 = set(list_)
                for k in fermeture_item:
                    idx = data_frame_init.index[data_frame_init['item'] == k]
                    li = data_frame_init['TID'].iloc[idx]
                    res = li.values[0]
                    res = res[1:len(res) - 1]
                    list_ = res.split(", ")
                    set_2 = set(list_)
                    set_1 = set_1.intersection(set_2)
                occ = len(set_1)
                support_ferm = occ / int(data_frame_init['transact_num'].iloc[0])
                lift = round(support_all / (data_frame_ferm.values[i, 1] * support_ferm), P)

                result.append([item, fermeture_item, lift])

    result = sorted(result, key=itemgetter(1))
    if len(result)>ARR:
        for i in range(ARR):
            print(str(result[i][0]) + " -----> " + str(result[i][1]) + " avec un "
                                                                       "lift de"
                                                                       " " +
                  str(result[i][2]) + "."
                  , file=open(path_to_save + "output.txt", "a"))
            print(str(result[i][0]) + " -----> " + str(result[i][1]) + " avec un "
                                                                       "lift de"
                                                                       " " +
                  str(result[i][2]) + "."
                  , file=open(path_to_save + "association rules.txt", "a"))
    else:
        for i in result:
            print(str(i[0]) + " -----> " + str(i[1]) + " avec un "
                                                                       "lift de"
                                                                       " " +
                  str(i[2]) + "."
                  , file=open(path_to_save + "output.txt", "a"))
            print(str(i[0]) + " -----> " + str(i[1]) + " avec un "
                                                                       "lift de"
                                                                       " " +
                  str(i[2]) + "."
                  , file=open(path_to_save + "association rules.txt", "a"))



if __name__ == "__main__":
    args = parse_args()
    saving_path = args.path_saved
    dataset = args.dataset
    path_to_save = args.path_it_et
    Min_supp = float(args.Minsup)
    K_set = int(args.k)
    Pres = int(args.Pres)
    TID = args.TID
    num = int(args.num)

    if dataset == 1:
        store_data = pd.read_csv(os.path.join(saving_path, 'store_mode_1.csv'))
    elif dataset == 2:
        store_data = pd.read_csv(os.path.join(saving_path, 'store_mode_2.csv'))
    elif dataset == 3:
        store_data = pd.read_csv(os.path.join(saving_path, 'weather_mode.csv'))
    elif dataset == 4:
        store_data = pd.read_csv(os.path.join(saving_path, 'Students_mode.csv'))
    else:
        store_data = pd.read_csv(os.path.join(saving_path, 'Tumor_mode.csv'))

    print("", file=open(path_to_save + "output.txt", "w"))
    df = close(store_data, Min_supp, K_set, path_to_save, TID, Pres)
    generer_regles_lift(df, store_data, TID, Pres, num)
