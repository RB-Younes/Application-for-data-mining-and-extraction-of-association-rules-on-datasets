import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

// Method for reading CSV file
    public class CSVFile {
        private final ArrayList<String[]> Rs = new ArrayList<String[]>();
        private String[] OneRow;

        public ArrayList<String[]> ReadCSVfile(File DataFile) {
            try {
                @SuppressWarnings("resource")
				BufferedReader brd = new BufferedReader(new FileReader(DataFile));
                while (brd.ready()) {
                	
                		String st = brd.readLine();

                        OneRow = st.split(";");
                        Rs.add(OneRow);

                    
                } // end of while
                if (brd != null) {
                	brd.close(); 
                	brd = null;
                }
            } // end of try
            catch (Exception e) {
                String errmsg = e.getMessage();
				JOptionPane.showMessageDialog(null,  errmsg ,"Warning" ,JOptionPane.PLAIN_MESSAGE, new ImageIcon(Datasets.class.getResource("/state/error.png")));	

            } // end of Catch
            
            return Rs;
        }// end of ReadFile method
    }// end of CSVFile class