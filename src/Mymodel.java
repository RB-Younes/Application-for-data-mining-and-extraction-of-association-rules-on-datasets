import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

class Mymodel extends AbstractTableModel {
        /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private final String[] columnNames = { "itemset", "support", "Ferm"};
        private ArrayList<String[]> Data = new ArrayList<String[]>();

        public void AddCSVData(ArrayList<String[]> DataIn) {
            this.Data = DataIn;
            this.fireTableDataChanged();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;// length;
        }

        @Override
        public int getRowCount() {
            return Data.size();
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            return Data.get(row)[col];
        }
    }