import argparse
import os
import pandas as pd
import getpass

getpass.getuser()


def parse_args():
    # DEFAULT PATHS TO CHANGE
    parser = argparse.ArgumentParser(description="Datasets Processing.")
    parser.add_argument('--dataset', type=int, default=4,
                        help='Choose a dataset:\n 1)supermarket1  2)supermarket2 3)weather 4)Students 5)Tumor.')
    parser.add_argument('--TID', type=int, default=0,
                        help='Choose a dataset:\n VAL(0)=DEFAULT VAL(1)=TID.')
    u = getpass.getuser()
    parser.add_argument('--path_save', nargs='?', default='C:/users/' + u + '/Desktop/',
                        help='Path to save the processed dataset.')
    return parser.parse_args()


def main(users_path, dataset, save, TID):
    if dataset == 1:
        store_data = pd.read_csv(users_path)
        store_data = store_data.drop(columns=['total'])
        store_data = store_data.replace('?', 0)
        store_data = store_data.replace('t', 1)
        for j in store_data.columns:
            occ = store_data[j].value_counts()[0]
            if occ == len(store_data):
                store_data = store_data.drop(columns=[j])
        if TID == 0:
            store_data.to_csv(save + 'store_mode_1.csv', index=False, header=True)
        else:
            store_data_f = pd.DataFrame(columns=['item', 'transact_num', 'TID'])
            for i in store_data.columns:
                res = store_data.index[store_data[i] == 1].tolist()
                store_data_f.loc[len(store_data_f)] = [i, len(store_data), res]
            print(store_data_f)
            store_data_f.to_csv(save + 'store_mode_1.csv', index=False, header=True)

    if dataset == 2:
        store_data = pd.read_csv(users_path, header=None)
        products = []
        # extracting unique values
        for i in range(0, len(store_data)):
            for j in range(len(store_data.columns)):
                if store_data.values[i, j] not in products:
                    products.append(store_data.values[i, j])

        # clear the nan values
        products = [x for x in products if str(x) != 'nan']

        # creating the new data frame (empty)
        store_data_f = pd.DataFrame(columns=products)
        for i in range(0, len(store_data)):
            store_data_f.loc[len(store_data_f)] = 0

        # filling
        for i in range(0, len(store_data)):
            for j in range(len(store_data.columns)):
                product = store_data.values[i, j]
                if str(product) != 'nan':
                    store_data_f.loc[i, product] = 1
        if TID == 0:
            store_data_f.to_csv(save + 'store_mode_2.csv', index=False, header=True)
        else:
            store_data_f2 = pd.DataFrame(columns=['item', 'transact_num', 'TID'])
            for i in store_data_f.columns:
                res = store_data_f.index[store_data_f[i] == 1].tolist()
                store_data_f2.loc[len(store_data_f2)] = [i, len(store_data), res]
            store_data_f2.to_csv(save + 'store_mode_2.csv', index=False, header=True)

    if dataset == 3:
        weather_data = pd.read_csv(users_path)
        weather_data_f = pd.DataFrame(columns=['outlook=sunny', 'outlook=overcast', 'outlook=rainy',
                                               'temperature=hot', 'temperature=mild', 'temperature=cool',
                                               'humidity=high',
                                               'humidity=normal', 'windy=True', 'windy=False', 'play=no',
                                               'play=yes'])
        # empty data frame
        for i in range(0, len(weather_data)):
            weather_data_f.loc[len(weather_data_f)] = 0

        # filling
        for i in weather_data.index:
            for j in weather_data.columns:
                val = weather_data[j][i]
                weather_data_f[str(j) + '=' + str(val)][i] = 1
        if TID == 0:
            weather_data_f.to_csv(save + 'weather_mode.csv', index=False, header=True)
        else:
            weather_data_f2 = pd.DataFrame(columns=['item', 'transact_num', 'TID'])
            for i in weather_data_f.columns:
                res = weather_data_f.index[weather_data_f[i] == 1].tolist()
                weather_data_f2.loc[len(weather_data_f2)] = [i, len(weather_data_f), res]
            weather_data_f2.to_csv(save + 'weather_mode.csv', index=False, header=True)

    if dataset == 4:
        students_data = pd.read_csv(users_path)
        col = []
        for i in students_data.columns:
            if i in ['raisedhands', 'VisITedResources', 'AnnouncementsView', 'Discussion']:
                for j in range(len(students_data)):
                    val = students_data[i][j]
                    students_data[i][j] = val[3:len(val) - 3]
            uniq_vals = students_data[i].unique().tolist()
            for k in uniq_vals:
                res = i + '=' + k
                col.append(res)

        students_data_f = pd.DataFrame(columns=col)

        # empty data frame
        for i in range(0, len(students_data)):
            students_data_f.loc[len(students_data_f)] = 0

        # filling
        for i in students_data.index:
            for j in students_data.columns:
                val = students_data[j][i]
                students_data_f[str(j) + '=' + str(val)][i] = 1
        if TID == 0:
            students_data_f.to_csv(save + 'Students_mode.csv', index=False, header=True)

        else:
            students_data_f2 = pd.DataFrame(columns=['item', 'transact_num', 'TID'])
            for i in students_data_f.columns:
                res = students_data_f.index[students_data_f[i] == 1].tolist()
                students_data_f2.loc[len(students_data_f2)] = [i, len(students_data_f), res]
            students_data_f2.to_csv(save + 'Students_mode.csv', index=False, header=True)

    if dataset == 5:
        tumor_data = pd.read_csv(users_path)
        tumor_data = tumor_data.fillna("unknown")
        col = []
        for i in tumor_data.columns:
            uniq_vals = tumor_data[i].unique().tolist()
            for k in uniq_vals:
                res = i + '=' + str(k)
                col.append(res)

        tumor_data_f = pd.DataFrame(columns=col)
        # empty data frame
        for i in range(0, len(tumor_data)):
            tumor_data_f.loc[len(tumor_data_f)] = 0
        # filling
        for i in tumor_data.index:
            for j in tumor_data.columns:
                val = tumor_data[j][i]
                tumor_data_f[str(j) + '=' + str(val)][i] = 1

        if TID == 0:
            tumor_data_f.to_csv(save + 'Tumor_mode.csv', index=False, header=True)
        else:
            tumor_data_f2 = pd.DataFrame(columns=['item', 'transact_num', 'TID'])
            for i in tumor_data_f.columns:
                res = tumor_data_f.index[tumor_data_f[i] == 1].tolist()
                tumor_data_f2.loc[len(tumor_data_f2)] = [i, len(tumor_data_f), res]
            tumor_data_f2.to_csv(save + 'Tumor_mode.csv', index=False, header=True)


if __name__ == '__main__':
    args = parse_args()
    saving_path = args.path_save
    dataset = args.dataset
    TID = args.TID
    script_dir = os.path.dirname(__file__)  # <-- absolute dir the script is in

    if dataset == 1:
        # super market 2
        path_users = os.path.join(script_dir, 'store_data_1.csv')  # supermarket
    elif dataset == 2:
        # super market 1
        path_users = os.path.join(script_dir, "store_data_2.csv")
    elif dataset == 3:
        path_users = os.path.join(script_dir, "weather nominal.csv")
    elif dataset == 4:
        path_users = os.path.join(script_dir, "Students_nominal.csv")
    else:
        path_users = os.path.join(script_dir, "primary-tumor.csv")

    main(path_users, dataset, saving_path, TID)
