import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarculaLaf;



////////////////////////////////////////////////////////////////////////////////-----------Fenetre DATASET------------///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class Datasets extends JFrame {
	

	private static final long serialVersionUID = 1L;

	
	private int posX = 0;   //Position X de la souris au clic
    private int posY = 0;   //Position Y de la souris au clic
    

	private JPanel contentPane;
	private JTextField textFieldPath;
	
	private JButton btn1;
	private JButton btn3;
	private JRadioButton RadiobtnSPM1;
	private JRadioButton RadiobtnSPM2;
	private JFileChooser fileChooser;
	private String Folder_Selected;
	private int i=0;
	private String path_open;
	private int p=0;
	private int dataset=0; 
	private int tid=0;
	private boolean DataProcessed = false;
	private boolean selectedRDBTN = false;
	private JTextArea testAreaDesc;
	private JScrollPane scrollpane; 
	private JCheckBox chckbxNewCheckBox;
	private JRadioButton RadiobtnSPM3;
	private JRadioButton RadiobtnSPM4;
	private JRadioButton RadiobtnSPM5;
	/**
	 * Launch the application.
	 * @throws UnsupportedLookAndFeelException 
	 */
	public static void main(String[] args) throws UnsupportedLookAndFeelException , IOException, InterruptedException  {
		FlatDarculaLaf.install();	
		UIManager.setLookAndFeel(new FlatDarculaLaf() );
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Datasets frame = new Datasets("-1","NONE",false,"0");
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
	public Datasets(String dataset_,String path_data,Boolean DataProcessed_,String TID ) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Datasets.class.getResource("/Menu_img/data.png")));
		//cnx

		setUndecorated(true);	
		setResizable(false);

		setTitle("Datasets");
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
		BGDatasets.setIcon(new ImageIcon(Datasets.class.getResource("/Datasets_img/1.png")));
		//BGDatasets.setIcon(new ImageIcon(Models.class.getResource("/Menu_img/1.png")));	// Back ground de base	
       
// Bouton Reduire ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JButton Minimise_BTN = new JButton("");
		Minimise_BTN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Minimise_BTN.setIcon(new ImageIcon(Datasets.class.getResource("/Menu_img/Minimize ML selected.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				Minimise_BTN.setIcon(new ImageIcon(Datasets.class.getResource("/Menu_img/Minimize ML .png")));
			}
		});
		Minimise_BTN.setToolTipText("Minimize");
		ButtonStyle(Minimise_BTN);
		Minimise_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setState(ICONIFIED);
				
			}
		});
		Minimise_BTN.setIcon(new ImageIcon(Datasets.class.getResource("/Menu_img/Minimize ML .png")));
		Minimise_BTN.setBounds(932, 11, 32, 32);
		Minimise_BTN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(Minimise_BTN);
//Boutton home//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				JButton btnHome = new JButton("");
				btnHome.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						if (btnHome.isEnabled()) {
							btnHome.setIcon(new ImageIcon(Datasets.class.getResource("/Models_img/home selected.png")));//changer les couleurs button
						}
					}
					@Override
					public void mouseExited(MouseEvent e) {
						if (btnHome.isEnabled()) {
							btnHome.setIcon(new ImageIcon(Datasets.class.getResource("/Models_img/home.png")));//remetre le bouton de base
						}
					}
				});
				btnHome.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (DataProcessed_==true) {
							Menu frame = new Menu(dataset_,path_data,DataProcessed_,TID);
							frame.setLocationRelativeTo(contentPane);
							frame.setVisible(true);
							dispose();
						}
						else {
							String Path_save =textFieldPath.getText();
							Menu frame = new Menu(String.valueOf(dataset),Path_save,DataProcessed,TID);// retourner au menu medecin
							frame.setLocationRelativeTo(null);
							frame.setVisible(true);
							dispose();
						}
						
					}
				});
				btnHome.setIcon(new ImageIcon(Datasets.class.getResource("/Models_img/home.png")));
				btnHome.setToolTipText("Menu");
				btnHome.setBounds(974, 11, 32, 32);
				ButtonStyle(btnHome);
				btnHome.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				contentPane.add(btnHome);
// Exit bouton//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		JButton Exit_BTN = new JButton("");
		Exit_BTN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				Exit_BTN.setIcon(new ImageIcon(Datasets.class.getResource("/Menu_img/Exit ML selected.png")));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				Exit_BTN.setIcon(new ImageIcon(Datasets.class.getResource("/Menu_img/Exit ML.png")));
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
		Exit_BTN.setIcon(new ImageIcon(Datasets.class.getResource("/Menu_img/Exit ML.png")));
		Exit_BTN.setBounds(1016, 11, 32, 32);
		Exit_BTN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(Exit_BTN);
//radio btns//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		RadiobtnSPM1 = new JRadioButton("");
		RadiobtnSPM1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dataset=1;
				selectedRDBTN=true;
				testAreaDesc.setText("- Rows number : 4627.\n"
						+ "- Columns number : 217.\n"
						+ "- Columns Type : Nominal.\n"
						);
			}
		});
				
				RadiobtnSPM1.setToolTipText("supermarket1");
				RadiobtnSPM1.setBounds(100, 243, 21, 23);
				RadiobtnSPM1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				contentPane.add(RadiobtnSPM1);
				
				RadiobtnSPM2 = new JRadioButton("");
				RadiobtnSPM2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dataset=2;
						selectedRDBTN=true;
						testAreaDesc.setText("- Rows number : 7501.\n"
								+ "- Rows contains the transaction \n articles."
								);
					}
				});
				RadiobtnSPM2.setToolTipText("supermarket2");
				RadiobtnSPM2.setBounds(100, 315, 21, 23);
				RadiobtnSPM2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				contentPane.add(RadiobtnSPM2);
				
				
				RadiobtnSPM3 = new JRadioButton("");
				RadiobtnSPM3.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dataset=3;
						selectedRDBTN=true;
						testAreaDesc.setText("- Rows number : 14.\n"
								+ "- Columns number : 12.\n"
								+ "- Columns Type : Nominal.\n"
								);
					}
				});
				RadiobtnSPM3.setToolTipText("weather");
				RadiobtnSPM3.setBounds(100, 170, 21, 23);
				RadiobtnSPM3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				contentPane.add(RadiobtnSPM3);
				
				
				RadiobtnSPM4 = new JRadioButton("");
				RadiobtnSPM4.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dataset=4;
						selectedRDBTN=true;
						testAreaDesc.setText("- Rows number : 480.\n"
								+ "- Columns number : 17.\n"
								+ " ( Before treatement )\n"
								);
					}
				});
				RadiobtnSPM4.setToolTipText("Students");
				RadiobtnSPM4.setBounds(100, 383, 21, 23);
				RadiobtnSPM4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				contentPane.add(RadiobtnSPM4);
				
				RadiobtnSPM5 = new JRadioButton("");
				RadiobtnSPM5.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dataset=5;
						selectedRDBTN=true;
						testAreaDesc.setText("- Rows number : 309.\n"
								+ "- Columns number : 18.\n"
								+ " ( Before treatement )\n"
								);
					}
				});
				RadiobtnSPM5.setToolTipText("Tumor");
				RadiobtnSPM5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				RadiobtnSPM5.setBounds(100, 450, 21, 23);
				contentPane.add(RadiobtnSPM5);
				
				   //Group the radio buttons.
				ButtonGroup group = new ButtonGroup();
				group.add(RadiobtnSPM1);
				group.add(RadiobtnSPM2);
				group.add(RadiobtnSPM3);
				group.add(RadiobtnSPM4);
				group.add(RadiobtnSPM5);
//btns ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		btn1= new JButton("");
		btn1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
					BGDatasets.setIcon(new ImageIcon(Datasets.class.getResource("/Datasets_img/2.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				BGDatasets.setIcon(new ImageIcon(Datasets.class.getResource("/Datasets_img/1.png")));
			}
			
		});
		btn1.setToolTipText("Proceed data");
		btn1.setForeground(Color.WHITE);
		btn1.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 16));
		btn1.addActionListener(new ActionListener() {
			Process process;
			Boolean stopped = false;
			public void actionPerformed(ActionEvent arg0) {
			if (selectedRDBTN) {
				
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
		                DataProcessed=true;
		                
		                if (chckbxNewCheckBox.isSelected()) tid=1;
		                else tid=0;
		                String Str_tid=String.valueOf(tid);
		                
		                
		                String Str_dataset=String.valueOf(dataset);
		                
		                String Path_save =textFieldPath.getText();
		                
		                if (!Path_save.equals("C:/Users/name/Desktop/...")) {
		                     Path_save =textFieldPath.getText()+'\\';
		                     path_open=Path_save;
		                     p=1;
		                }
		                 
		                
		                String path_script ="";
		                path_script = Datasets.class.getResource("/scripts_py/main.py").getPath(); // get the path of the script (a revoir)
		                path_script = path_script.substring(1, path_script.length());
		                ProcessBuilder pb = null;
		                if (!Path_save.equals("C:/Users/name/Desktop/..."))
		                {
		               
		                	pb = new ProcessBuilder("python",path_script,"--path_save",Path_save,"--dataset", Str_dataset,"--TID",Str_tid ).inheritIO();
		                }
		                else
		                {	
		                	pb = new ProcessBuilder("python",path_script,"--dataset", Str_dataset ,"--TID",Str_tid ).inheritIO();
		                }
		                
		                try {
		                    
		                    process=pb.start();
		                    try {
		                         process.waitFor();
		                    } catch (InterruptedException e1) {
		                        // TODO Auto-generated catch block
		                        e1.printStackTrace();
		                    }
		                    if(!stopped) {
		                    	f.dispose();
							JOptionPane.showMessageDialog(null,  "Data loaded and processed !","state" ,JOptionPane.PLAIN_MESSAGE, new ImageIcon(Datasets.class.getResource("/state/stamp.png")));	
		                    }
		                    else {
		                    	f.dispose();
							JOptionPane.showMessageDialog(null,  "Stopped treatment.","state" ,JOptionPane.PLAIN_MESSAGE, new ImageIcon(Datasets.class.getResource("/state/error.png")));	
							i=0;
			                DataProcessed=false;
			                stopped=false;
							}
							
		                } catch (IOException e) {
		                    // TODO Auto-generated catch block
		                    JOptionPane.showMessageDialog(null,e);
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
						BG.setIcon(new ImageIcon(Datasets.class.getResource("/Datasets_img/process.png")));
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
				JOptionPane.showMessageDialog(null,  "Please select a dataset !","Warning" ,JOptionPane.PLAIN_MESSAGE, new ImageIcon(Datasets.class.getResource("/state/error.png")));	

			  }
		  }
		});
		btn1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn1.setBounds(376, 480, 185, 44);
		ButtonStyle(btn1);
		contentPane.add(btn1);
		
		btn3= new JButton("");
		btn3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
					BGDatasets.setIcon(new ImageIcon(Datasets.class.getResource("/Datasets_img/3.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				BGDatasets.setIcon(new ImageIcon(Datasets.class.getResource("/Datasets_img/1.png")));
			}
		});
		btn3.setToolTipText("Show data");
		btn3.setForeground(Color.WHITE);
		btn3.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 16));
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String FolderName="C:/users/"+System.getProperty("user.name")+"/Desktop/";
				if(dataset==1)
					  FolderName="C:/users/"+System.getProperty("user.name")+"/Desktop/"+"store_mode_1.csv";
				else {
              	  if (dataset==2) 
              		FolderName="C:/users/"+System.getProperty("user.name")+"/Desktop/"+"store_mode_2.csv";
              	  	else
		           	  {
              	  		if (dataset==3) 
              	  			FolderName="C:/users/"+System.getProperty("user.name")+"/Desktop/"+"weather_mode.csv";
              	  		else {
              	  			if(dataset==4)
              	  				FolderName="C:/users/"+System.getProperty("user.name")+"/Desktop/"+"Students_mode.csv";
              	  			else
              	  				FolderName="C:/users/"+System.getProperty("user.name")+"/Desktop/"+"Tumor_mode.csv";
              	  		}
              	  			
		           	  }
           		  
				}
				if (i==1) {
					if (p==1) {
						p=0;
						 if(dataset==1)
							  FolderName=path_open+"store_mode_1.csv";
		                  else {
		                	  if (dataset==2) 
		                		  FolderName=path_open+"store_mode_2.csv";
							
		                	  else
		                	  {
		                		  if (dataset==3) 
			                		  FolderName=path_open+"weather_mode.csv";
		                		  else {
		                			  if(dataset==4)
		                				  FolderName=path_open+"Students_mode.csv"; 
		                			  else
		                				  FolderName=path_open+"Tumor_mode.csv"; 
		                				  
		                		  }
		                			 
		                	  }
		                		  
		                  }
		                	  
						
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
		btn3.setBounds(700, 480, 185, 44);
		ButtonStyle(btn3);
		contentPane.add(btn3);

		
		textFieldPath = new JTextField();
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
		textFieldPath.setBounds(711, 308, 240, 34);
		contentPane.add(textFieldPath);
		
		testAreaDesc = new JTextArea();
		testAreaDesc.setText("No dataset selected");
		testAreaDesc.setBackground(new Color(255, 51, 51));
		testAreaDesc.setForeground(Color.WHITE);
		testAreaDesc.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 16));
		testAreaDesc.setEditable(false);
		testAreaDesc.setSelectionStart(0);// le xceollpane affiche de haut en bas
		testAreaDesc.setSelectionEnd(0);
		testAreaDesc.setOpaque(false);
		testAreaDesc.setColumns(10);
		
		scrollpane = new JScrollPane(testAreaDesc);
		scrollpane.setBounds(414, 281, 265, 114);
		scrollpane.setBorder(BorderFactory.createEmptyBorder());
		scrollpane.getViewport().setOpaque(false);
		contentPane.add(scrollpane);
	  
		
		JButton btnchooser = new JButton("");
		btnchooser.setIcon(new ImageIcon(Datasets.class.getResource("/Datasets_img/folder.png")));
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
		btnchooser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnchooser.setBounds(961, 308, 34, 34);
		contentPane.add(btnchooser);
		
//Progress Bar ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		

		JButton btnNewButton = new JButton("");
		btnNewButton.setToolTipText("GO to treatement");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnNewButton.setIcon(new ImageIcon(Datasets.class.getResource("/arrows/next selected.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton.setIcon(new ImageIcon(Datasets.class.getResource("/arrows/next.png")));
			}
		});
		btnNewButton.setIcon(new ImageIcon(Datasets.class.getResource("/arrows/next.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			if (DataProcessed_==true && i!=1) {
				treat frame = new treat(dataset_,path_data,DataProcessed_,TID);
				frame.setLocationRelativeTo(contentPane);
				frame.setVisible(true);
				dispose();
			}
			else {
				if (i==1) {
				String Path_save =textFieldPath.getText();
				treat frame = new treat(String.valueOf(dataset),Path_save,DataProcessed,String.valueOf(tid));
				frame.setLocationRelativeTo(contentPane);
				frame.setVisible(true);
				dispose();
				}
				else {
					JOptionPane.showMessageDialog(null,  "Please process a dataset !","Warning" ,JOptionPane.PLAIN_MESSAGE, new ImageIcon(Datasets.class.getResource("/state/error.png")));	
				}
				
			}
			}
		});
		btnNewButton.setBounds(296, 86, 32, 32);
		ButtonStyle(btnNewButton);
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(btnNewButton);
		
		chckbxNewCheckBox = new JCheckBox("");
		chckbxNewCheckBox.setToolTipText("Tid");
		chckbxNewCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxNewCheckBox.setBounds(84, 501, 21, 23);
		contentPane.add(chckbxNewCheckBox);
		
		
		
		
		
	   
		
		
//le background////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
