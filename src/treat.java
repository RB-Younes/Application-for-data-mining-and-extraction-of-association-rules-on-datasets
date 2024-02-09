import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarculaLaf;


////////////////////////////////////////////////////////////////////////////////-----------Fenetre TRINING------------///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class treat extends JFrame {
	

	private static final long serialVersionUID = 1L;
	
	private int posX = 0;   //Position X de la souris au clic
    private int posY = 0;   //Position Y de la souris au clic
    

	private JPanel contentPane;
	
	private JButton btn1;
	private JButton btn3;
	private JTextField textFieldMS;
	private JTextField textFieldK;
	private JTextField textFieldPath;
	private JFileChooser fileChooser;
	private String Folder_Selected;
	private JButton btnchooser;
	private JTextArea testAreaDesc;
	private JScrollPane scrollpane; 
	private String Path_save;
	private int i=0;
	private String path_open;
	private int p=0;
	private JCheckBox chckbxNewCheckBox;
	private JTable table;
	private int fileCount=-1 ;
	private JButton nextitemset ;
	private JButton previousitemset ;
	private int nbr;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JTextField textFieldP;
	private JTextField textFieldN;
	
	
	/**
	 * Launch the application.
	 * @throws UnsupportedLookAndFeelException 
	 */
	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		FlatDarculaLaf.install();	
		UIManager.setLookAndFeel(new FlatDarculaLaf() );
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					treat frame = new treat("2","C:/Users/name/Desktop/...",true,"0");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the frame.
	 */
	public treat(String dataset,String path_data,Boolean DataProcessed,String TID) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(treat.class.getResource("/Menu_img/train.png")));
		//cnx

		setUndecorated(true);	
		setResizable(false);

		setTitle("Trainig");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1100, 650);
		setShape(new RoundRectangle2D.Double(0d, 0d, 1100d, 650d, 25d, 25d));
		setLocationRelativeTo(null);
		//vu que la frame est Undecorated on a besoin de ces MouseListeners pour la faire bouger(frame)
		  addMouseListener(new MouseAdapter() {
	            @Override
	            //on recupere les coordonnées de la souris
	            public void mousePressed(MouseEvent e) {
	                posX = e.getX();    //Position X de la souris au clic
	                posY = e.getY();    //Position Y de la souris au clic
	            }
	        });
	        addMouseMotionListener(new MouseMotionAdapter() {
	            // A chaque deplacement on recalcul le positionnement de la fenetre
	            @Override
	            public void mouseDragged(MouseEvent e) {
	                int depX = e.getX() - posX;
	                int depY = e.getY() - posY;
	                setLocation(getX()+depX, getY()+depY);
	            }
	        });
	        
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel BGDatasets = new JLabel("");
		BGDatasets.setIcon(new ImageIcon(treat.class.getResource("/treatement_img/1.png")));
       
// Bouton Reduire ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JButton Minimise_BTN = new JButton("");
		Minimise_BTN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Minimise_BTN.setIcon(new ImageIcon(treat.class.getResource("/Menu_img/Minimize ML selected.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				Minimise_BTN.setIcon(new ImageIcon(treat.class.getResource("/Menu_img/Minimize ML .png")));
			}
		});
		Minimise_BTN.setToolTipText("Minimize");
		ButtonStyle(Minimise_BTN);
		Minimise_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setState(ICONIFIED);
				
			}
		});
		Minimise_BTN.setIcon(new ImageIcon(treat.class.getResource("/Menu_img/Minimize ML .png")));
		Minimise_BTN.setBounds(932, 11, 32, 32);
		Minimise_BTN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(Minimise_BTN);
//Boutton home//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				JButton btnHome = new JButton("");
				btnHome.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						if (btnHome.isEnabled()) {
							btnHome.setIcon(new ImageIcon(treat.class.getResource("/Models_img/home selected.png")));//changer les couleurs button
						}
					}
					@Override
					public void mouseExited(MouseEvent e) {
						if (btnHome.isEnabled()) {
							btnHome.setIcon(new ImageIcon(treat.class.getResource("/Models_img/home.png")));//remetre le bouton de base
						}
					}
				});
				btnHome.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String Path_save =textFieldPath.getText();
						Menu frame = new Menu(String.valueOf(dataset),Path_save,DataProcessed,TID);// retourner au menu medecin
							frame.setLocationRelativeTo(null);
							frame.setVisible(true);
							dispose();
					}
				});
				btnHome.setIcon(new ImageIcon(treat.class.getResource("/Models_img/home.png")));
				btnHome.setToolTipText("Menu");
				btnHome.setBounds(974, 11, 32, 32);
				btnHome.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				ButtonStyle(btnHome);
				contentPane.add(btnHome);
// Exit bouton//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		JButton Exit_BTN = new JButton("");
		Exit_BTN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				Exit_BTN.setIcon(new ImageIcon(treat.class.getResource("/Menu_img/Exit ML selected.png")));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				Exit_BTN.setIcon(new ImageIcon(treat.class.getResource("/Menu_img/Exit ML.png")));
			}
		});
		Exit_BTN.setToolTipText("Exit");
		ButtonStyle(Exit_BTN);
		Exit_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
					
					int ClickedButton	=JOptionPane.showConfirmDialog(null, "Do you really want to leave?", "Close", JOptionPane.YES_NO_OPTION);
					if(ClickedButton==JOptionPane.YES_OPTION)
					{					
						dispose();
					}
					
					
			}
			
		});
		Exit_BTN.setIcon(new ImageIcon(treat.class.getResource("/Menu_img/Exit ML.png")));
		Exit_BTN.setBounds(1016, 11, 32, 32);
		Exit_BTN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(Exit_BTN);
//btns ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		btn1= new JButton("");
		btn1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
						BGDatasets.setIcon(new ImageIcon(treat.class.getResource("/treatement_img/2.png")));

			}
			@Override
			public void mouseExited(MouseEvent e) {
						BGDatasets.setIcon(new ImageIcon(treat.class.getResource("/treatement_img/1.png")));
			}
		});
		btn1.setToolTipText("Start");
		btn1.setForeground(Color.WHITE);
		btn1.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 16));
		btn1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn1.addActionListener(new ActionListener() {
			Process process;
			Boolean stopped = false;
			public void actionPerformed(ActionEvent arg0) {
				Float t=(float) 2;
				if(textFieldMS.getText().equals("1 , 2 ...")) {
					 t=(float) 0.002;
				}
				else {
				 t=Float.parseFloat(textFieldMS.getText().toString());
				}
				if(t>=0 && t<=1 )
				{
				
				  JFrame f = new JFrame("Stepping Progress");
				    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				    
				    f.setUndecorated(true);	
				    f.setResizable(false);
				    f.setSize(600, 200);
				    f.setShape(new RoundRectangle2D.Double(0d, 0d, 600, 200, 25d, 25d));
				    
				    f.setLocationRelativeTo(null);
				    
				    
				    final JProgressBar aJProgressBar = new JProgressBar();
				    aJProgressBar.setBounds(110,92,400, 16);;
				    aJProgressBar.setIndeterminate(true);
				    f.getContentPane().add(aJProgressBar);
				 
					final CyclicBarrier gate = new CyclicBarrier(3);
					
					Thread t1 = new Thread(){
					    public void run(){
					        try {
								gate.await();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (BrokenBarrierException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	
					        //do stuff  
					        i=1;
				
										String K=textFieldK.getText();
										String Minsup=textFieldMS.getText();
										String Pres = textFieldP.getText();
										String NumberR = textFieldN.getText();
										String dataset_= dataset;
										String path_data_ = path_data;
										String Str_tid=TID;
										
										if (K.equals("1 , 2 ...")) {
											K="1000000";
										}
										if (Minsup.equals("1 , 2 ...")) {
											Minsup="0.2";
										}
										
										if (Pres.equals("1 , 2 ...")) {
											Pres="2";
										}
										if (NumberR.equals("1 , 2 ...")) {
											NumberR="10";
										}
										
										Path_save=textFieldPath.getText();
										if (dataset.equals("0")) {
											dataset_="2";
										}
										if (path_data.equals("C:/Users/name/Desktop/...")) {
											path_data_="C:/users/" + System.getProperty("user.name") + "/Desktop/";
										}
										else {
											path_data_ =path_data+'\\';
										}
										if (Path_save.equals("C:/Users/name/Desktop/...")) {
											Path_save="C:/users/" + System.getProperty("user.name") + "/Desktop/";
											path_open=Path_save;
										}
										else {
											 Path_save =textFieldPath.getText()+'\\';
											 path_open=Path_save;
											 p=1;
										}
										
						
											String path_script ="";
											path_script = test.class.getResource("/scripts_py/Close.py").getPath(); // get the path of the script (a revoir)
											path_script = path_script.substring(1, path_script.length());
											
											
											ProcessBuilder pb = new ProcessBuilder("python",path_script,"--path_it_et",Path_save,"--path_saved",path_data_,"--dataset",dataset_,
													
													"--Minsup",Minsup,"--k",K,"--TID",Str_tid,"--Pres",Pres,"--num",NumberR).inheritIO();
											try {
											 process=pb.start();
												try {
													 process.waitFor();
												} catch (InterruptedException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
											} catch (IOException e) {
												// TODO Auto-generated catch block
												JOptionPane.showMessageDialog(null,e);
											}	
						
										
										
										
										if(!stopped) {
											f.dispose();
											JOptionPane.showMessageDialog(null,  "Completed treatment !","state" ,JOptionPane.PLAIN_MESSAGE, new ImageIcon(Datasets.class.getResource("/state/stamp.png")));	
											
											CSVFile Rd = new CSVFile();
									        Mymodel NewModel = new Mymodel();
									        table.setModel(NewModel);
									        File DataFile = new File(path_open+"/item sets/DF-1.csv");
									        ArrayList<String[]> Rs2 = Rd.ReadCSVfile(DataFile);
									        NewModel.AddCSVData(Rs2);
									        lblNewLabel.setText("Item set 1");
									        
									        File directory=new File(path_open+"/item sets/");
									        fileCount=directory.list().length;
									        if (fileCount>0) {
												nbr = 1;
											}
									        else
									        	nbr = fileCount;
									        previousitemset.setEnabled(true);
									        nextitemset.setEnabled(true);
									        
									        String FolderName="C:/users/"+System.getProperty("user.name")+"/Desktop/";
											
												if (p==1) {
													p=0;
													FolderName=path_open;
												}
									        
									        try {

												FileReader reader = new FileReader( FolderName+"association rules.txt");
							                    BufferedReader br = new BufferedReader(reader);
							                    testAreaDesc.read( br, null );
							                    br.close();
							                    testAreaDesc.requestFocus();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
									       
											
											
										}
						                else {
						                	f.dispose();
											JOptionPane.showMessageDialog(null,  "Stopped treatment.","state" ,JOptionPane.PLAIN_MESSAGE, new ImageIcon(Datasets.class.getResource("/state/error.png")));	
											stopped=false;
											i=0;
						                }
										
				
					    }};
					    
				Thread t2 = new Thread(){
				    public void run(){
				        try {
							gate.await();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (BrokenBarrierException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				        
				        JButton btnStop = new JButton("Stop");
				        btnStop.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								stopped=true;
								process.destroy();
							}
						});
				        btnStop.setToolTipText("Stop processing");
				        btnStop.setBounds(250, 130, 100, 25);
						f.getContentPane().add(btnStop);
						
						JLabel BG = new JLabel("");
						BG.setIcon(new ImageIcon(Datasets.class.getResource("/treatement_img/process Train.png")));
						BG.setBounds(0, 0, 600, 200);
						f.getContentPane().add(BG);

					    f.setVisible(true);  
				    }};

				t1.start();
				t2.start();

				// At this point, t1 and t2 are blocking on the gate. 
				// Since we gave "3" as the argument, gate is not opened yet.
				// Now if we block on the gate from the main thread, it will open
				// and all threads will start to do stuff!

				try {
					gate.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}    
			    
			 }
				else {
					JOptionPane.showMessageDialog(null,  "Please make sure that the value of the minsup is between 0 and 1 !","Warning" ,JOptionPane.PLAIN_MESSAGE, new ImageIcon(Datasets.class.getResource("/state/error.png")));	

				}
			}
		});
		btn1.setBounds(443, 535, 196, 52);
		ButtonStyle(btn1);
		contentPane.add(btn1);
		
		
		btn3= new JButton("");
		btn3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
					BGDatasets.setIcon(new ImageIcon(Datasets.class.getResource("/treatement_img/3.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				BGDatasets.setIcon(new ImageIcon(Datasets.class.getResource("/treatement_img/1.png")));
			}
		});
		btn3.setToolTipText("Show data");
		btn3.setForeground(Color.WHITE);
		btn3.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 16));
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String FolderName="C:/users/"+System.getProperty("user.name")+"/Desktop/";
				if (i==1) {
					if (p==1) {
						p=0;
						FolderName=path_open;
					}
					try {
						Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + FolderName);
					} catch (IOException ex) {
			             Logger.getLogger(Datasets.class.getName()).log(Level.SEVERE, null, ex);
					}
					
				}
				else {
					JOptionPane.showMessageDialog(null,  "Please process a dataset !","Warning" ,JOptionPane.PLAIN_MESSAGE, new ImageIcon(Datasets.class.getResource("/state/error.png")));	
				}
				
			}
		});
		btn3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn3.setBounds(799, 534, 196, 52);
		ButtonStyle(btn3);
		contentPane.add(btn3);
		
	
		
		textFieldMS = new JTextField();
		textFieldMS.setToolTipText("Minsup");
		textFieldMS.setText("1 , 2 ...");
		textFieldMS.setForeground(Color.GRAY);
		textFieldMS.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 12));
		textFieldMS.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		      char c = e.getKeyChar();
		    	 
		      if (!((c >= '0') && (c <= '9') ||
		    	(c == '.')||	  
		         (c == KeyEvent.VK_BACK_SPACE) ||
		         (c == KeyEvent.VK_DELETE))) {
		        getToolkit().beep();
		        e.consume();
		      }
		    }
		  });
		textFieldMS.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textFieldMS.getText().toString().equals("1 , 2 ...")) {
					textFieldMS.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 16));
					textFieldMS.setForeground(Color.LIGHT_GRAY);
					textFieldMS.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (textFieldMS.getText().toString().equals("")) {
					textFieldMS.setFont(new Font("Microsoft PhagsPa",Font.CENTER_BASELINE,12));
					textFieldMS.setForeground(Color.GRAY);
					textFieldMS.setText("1 , 2 ...");
				}
			}
		});
		textFieldMS.setColumns(10);
		textFieldMS.setBounds(140, 209, 184, 32);
		contentPane.add(textFieldMS);
		
		textFieldK = new JTextField();
		textFieldK.setEnabled(false);
		textFieldK.setEditable(false);
		textFieldK.setToolTipText("K");
		textFieldK.setText("1 , 2 ...");
		textFieldK.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		     
		      char c = e.getKeyChar();
		      if (!((c >= '0') && (c <= '9') ||
		         (c == KeyEvent.VK_BACK_SPACE) ||
		         (c == KeyEvent.VK_DELETE))) {
		        getToolkit().beep();
		        e.consume();
		      }
		    }
		  });
		textFieldK.setForeground(Color.GRAY);
		textFieldK.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textFieldK.getText().toString().equals("1 , 2 ...")) {
					textFieldK.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 16));
					textFieldK.setForeground(Color.LIGHT_GRAY);
					textFieldK.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (textFieldK.getText().toString().equals("")) {
					textFieldK.setFont(new Font("Microsoft PhagsPa",Font.CENTER_BASELINE,12));
					textFieldK.setForeground(Color.GRAY);
					textFieldK.setText("1 , 2 ...");
				}
			}
		});
		textFieldK.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 12));
		textFieldK.setColumns(10);
		textFieldK.setBounds(140, 273, 184, 32);
		contentPane.add(textFieldK);
//PATH TO SAVE MODEL/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		textFieldPath = new JTextField();
		textFieldPath.setEnabled(false);
		textFieldPath.setEditable(false);
		textFieldPath.setToolTipText("Path to save train & test files");
		textFieldPath.setText("C:/Users/name/Desktop/...");
		textFieldPath.setFont(new Font("Microsoft PhagsPa",Font.CENTER_BASELINE,12));
		textFieldPath.setForeground(Color.GRAY);
		textFieldPath.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textFieldPath.getText().toString().equals("C:/Users/name/Desktop/...") && textFieldPath.isEditable()) {
					textFieldPath.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 16));
					textFieldPath.setForeground(Color.LIGHT_GRAY);
					textFieldPath.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (textFieldPath.getText().toString().equals("")) {
					textFieldPath.setFont(new Font("Microsoft PhagsPa",Font.CENTER_BASELINE,12));
					textFieldPath.setForeground(Color.GRAY);
					textFieldPath.setText("C:/Users/name/Desktop/...");
				}
			}
		});
		textFieldPath.setColumns(10);
		textFieldPath.setBounds(79, 463, 234, 34);
		contentPane.add(textFieldPath);
		
		btnchooser = new JButton("");
		btnchooser.setIcon(new ImageIcon(Datasets.class.getResource("/Datasets_img/folder.png")));
		btnchooser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnchooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileChooser = new JFileChooser(); 
				fileChooser.setDialogTitle("Choose Directory");
	            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	            int option = fileChooser.showOpenDialog(btnchooser);
	            if(option == JFileChooser.APPROVE_OPTION){
	            	Folder_Selected = fileChooser.getSelectedFile().getAbsolutePath();
	            	textFieldPath.setText(Folder_Selected);
	            	textFieldPath.setForeground(Color.LIGHT_GRAY);
	            }else{
	            	Folder_Selected= "C:/Users/name/Desktop/...";
	            }
			}
		});
		btnchooser.setToolTipText("Path chooser");
		btnchooser.setBounds(320, 463, 34, 34);
		contentPane.add(btnchooser);
		
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setToolTipText("GO to datasets");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnNewButton.setIcon(new ImageIcon(Datasets.class.getResource("/arrows/previous selected.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton.setIcon(new ImageIcon(Datasets.class.getResource("/arrows/previous.png")));
			}
		});
		btnNewButton.setIcon(new ImageIcon(treat.class.getResource("/arrows/previous.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Datasets frame = new Datasets(dataset,path_data,DataProcessed,TID);
				frame.setLocationRelativeTo(contentPane);
				frame.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(295, 87, 32, 32);
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		ButtonStyle(btnNewButton);
		contentPane.add(btnNewButton);
		
		scrollpane = new JScrollPane();
		scrollpane.setBounds(415, 374, 591, 150);
		scrollpane.setBorder(BorderFactory.createEmptyBorder());
		scrollpane.getViewport().setOpaque(false);
		contentPane.add(scrollpane);
		
		testAreaDesc = new JTextArea();
		scrollpane.setViewportView(testAreaDesc);
		testAreaDesc.setForeground(Color.WHITE);
		testAreaDesc.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 14));
		testAreaDesc.setEditable(false);
		testAreaDesc.setSelectionStart(0);// le xceollpane affiche de haut en bas
		testAreaDesc.setSelectionEnd(0);
		
		
		chckbxNewCheckBox = new JCheckBox("");
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textFieldK.isEnabled()) {
					textFieldK.setEnabled(true);
					textFieldK.setEditable(true);
				}
				else {
					textFieldK.setEnabled(false);
					textFieldK.setEditable(false);
					textFieldK.setText("1 , 2 ...");
				}
				
			}
		});
		chckbxNewCheckBox.setToolTipText("K-stop");
		chckbxNewCheckBox.setBounds(94, 245, 21, 23);
		chckbxNewCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(chckbxNewCheckBox);
		
		
		JScrollPane scrollPaneT = new JScrollPane();
		scrollPaneT.setBounds(415, 141, 591, 194);
		contentPane.add(scrollPaneT);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 13));
		scrollPaneT.setViewportView(table);
		
		previousitemset = new JButton("");
		previousitemset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (previousitemset.isEnabled()) {
					previousitemset.setIcon(new ImageIcon(treat.class.getResource("/treatement_img/prev item selected.png")));
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (previousitemset.isEnabled()) 
					previousitemset.setIcon(new ImageIcon(treat.class.getResource("/treatement_img/prev item.png")));
			}
		});
		previousitemset.setIcon(new ImageIcon(treat.class.getResource("/treatement_img/prev item.png")));
		previousitemset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		previousitemset.setEnabled(false);
		previousitemset.setToolTipText("previous");
		previousitemset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (nbr>1) {
					nbr--;
					CSVFile Rd = new CSVFile();
			        Mymodel NewModel = new Mymodel();
			        table.setModel(NewModel);
			        File DataFile = new File(path_open+"/item sets/DF-"+nbr+".csv");
			        ArrayList<String[]> Rs2 = Rd.ReadCSVfile(DataFile);
			        NewModel.AddCSVData(Rs2);
				}
				else {
					nbr=fileCount;
					CSVFile Rd = new CSVFile();
			        Mymodel NewModel = new Mymodel();
			        table.setModel(NewModel);
			        File DataFile = new File(path_open+"/item sets/DF-"+nbr+".csv");
			        ArrayList<String[]> Rs2 = Rd.ReadCSVfile(DataFile);
			        NewModel.AddCSVData(Rs2);
					
				}
				 lblNewLabel.setText("Item set "+nbr);
			}
		});
		previousitemset.setBounds(461, 96, 32, 32);
		ButtonStyle(previousitemset);
		contentPane.add(previousitemset);
		
		nextitemset = new JButton("");
		nextitemset.setIcon(new ImageIcon(treat.class.getResource("/treatement_img/next item.png")));
		nextitemset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (nextitemset.isEnabled()) {
					nextitemset.setIcon(new ImageIcon(treat.class.getResource("/treatement_img/next item selected.png")));
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (nextitemset.isEnabled()) 
					nextitemset.setIcon(new ImageIcon(treat.class.getResource("/treatement_img/next item.png")));
			}
		});
		nextitemset.setEnabled(false);
		nextitemset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (nbr<fileCount) {
					nbr++;
					CSVFile Rd = new CSVFile();
			        Mymodel NewModel = new Mymodel();
			        table.setModel(NewModel);
			        File DataFile = new File(path_open+"/item sets/DF-"+nbr+".csv");
			        ArrayList<String[]> Rs2 = Rd.ReadCSVfile(DataFile);
			        NewModel.AddCSVData(Rs2);
				}
				else {
					nbr=1;
					CSVFile Rd = new CSVFile();
			        Mymodel NewModel = new Mymodel();
			        table.setModel(NewModel);
			        File DataFile = new File(path_open+"/item sets/DF-"+nbr+".csv");
			        ArrayList<String[]> Rs2 = Rd.ReadCSVfile(DataFile);
			        NewModel.AddCSVData(Rs2);
					
				}
				  lblNewLabel.setText("Item set "+nbr);
				
			}
		});
		nextitemset.setToolTipText("next");
		ButtonStyle(nextitemset);
		nextitemset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		nextitemset.setBounds(932, 96, 32, 32);
		contentPane.add(nextitemset);
		
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(676, 96, 81, 23);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 16));
		lblNewLabel.setText("item sets");
		lblNewLabel.setOpaque(false);

		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Association rules");
		lblNewLabel_1.setOpaque(false);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 16));
		lblNewLabel_1.setBounds(640, 343, 151, 23);
		contentPane.add(lblNewLabel_1);
		
		textFieldP = new JTextField();
		textFieldP.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		     
		      char c = e.getKeyChar();
		      if (!((c >= '0') && (c <= '9') ||
		         (c == KeyEvent.VK_BACK_SPACE) ||
		         (c == KeyEvent.VK_DELETE))) {
		        getToolkit().beep();
		        e.consume();
		      }
		    }
		  });
		textFieldP.setToolTipText("Precision");
		textFieldP.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textFieldP.getText().toString().equals("1 , 2 ...")) {
					textFieldP.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 16));
					textFieldP.setForeground(Color.LIGHT_GRAY);
					textFieldP.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (textFieldP.getText().toString().equals("")) {
					textFieldP.setFont(new Font("Microsoft PhagsPa",Font.CENTER_BASELINE,12));
					textFieldP.setForeground(Color.GRAY);
					textFieldP.setText("1 , 2 ...");
				}
			}
		});
		textFieldP.setText("1 , 2 ...");
		textFieldP.setForeground(Color.GRAY);
		textFieldP.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 12));
		textFieldP.setColumns(10);
		textFieldP.setBounds(140, 340, 184, 32);
		contentPane.add(textFieldP);
		
		textFieldN = new JTextField();
		textFieldN.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		     
		      char c = e.getKeyChar();
		      if (!((c >= '0') && (c <= '9') ||
		         (c == KeyEvent.VK_BACK_SPACE) ||
		         (c == KeyEvent.VK_DELETE))) {
		        getToolkit().beep();
		        e.consume();
		      }
		    }
		  });
		textFieldN.setToolTipText("N of rules");
		textFieldN.setToolTipText("Precision");
		textFieldN.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textFieldN.getText().toString().equals("1 , 2 ...")) {
					textFieldN.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 16));
					textFieldN.setForeground(Color.LIGHT_GRAY);
					textFieldN.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (textFieldN.getText().toString().equals("")) {
					textFieldN.setFont(new Font("Microsoft PhagsPa",Font.CENTER_BASELINE,12));
					textFieldN.setForeground(Color.GRAY);
					textFieldN.setText("1 , 2 ...");
				}
			}
		});
		textFieldN.setText("1 , 2 ...");
		textFieldN.setForeground(Color.GRAY);
		textFieldN.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 12));
		textFieldN.setColumns(10);
		textFieldN.setBounds(140, 401, 184, 32);
		contentPane.add(textFieldN);
		
		
//le background ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		BGDatasets.setBounds(0, 0, 1100, 650);
		contentPane.add(BGDatasets);
		
	}
//methode du style des buttons/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 private void ButtonStyle(JButton btn) {
	//enlecer les bordures des btn
	 btn.setOpaque(false);
	 btn.setFocusPainted(false);
	 btn.setBorderPainted(false);
	 btn.setContentAreaFilled(false);
	
}
}
