import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MenuController implements ActionListener{
	
	MenuView view;
	Model model;
	JFileChooser fileChooser;
	DBViewFrame db;
	
	MenuController(MenuView view, Model model) {
		this.view = view;
		this.model = model;
	}
	
	void initController() {
		view.getsaveItem().addActionListener(this);
		view.getCloseItem().addActionListener(this);
		view.getHelpItem().addActionListener(this);
		view.getSaveJPEG().addActionListener(this);
		view.getSaveCSV().addActionListener(this);
		view.getAbout().addActionListener(this);
		view.getConnectItem().addActionListener(this);
	}
	
	public BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}  
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "SavePNG":
				JFrame parentFrame = new JFrame();
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save"); 
				int userSelection = fileChooser.showSaveDialog(parentFrame);
				
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    File fileToSave = fileChooser.getSelectedFile();
				    
				    if(!fileToSave.getPath().endsWith(".png")) {
				    	fileToSave = new File(fileToSave.getPath() + ".png");
				    }
				    
				    try {
						ImageIO.write(model.getImg(), "png", fileToSave);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				System.out.println("Done saving the file.");
				break;
			case "SaveJPEG":
				JFrame parentFrame2 = new JFrame();
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save"); 
				userSelection = fileChooser.showSaveDialog(parentFrame2);
				
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    File fileToSave = fileChooser.getSelectedFile();
				    
				    if(!fileToSave.getPath().endsWith(".tiff")) {
				    	fileToSave = new File(fileToSave.getPath() + ".tiff");
				    }
				    
				    try {
						ImageIO.write(model.getImg(), "tiff", fileToSave);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				break;
			case "Close":
				System.exit(0);
				break;
			case "Contact":
				JOptionPane.showMessageDialog(null, new ContactView(), "Contact Us", JOptionPane.PLAIN_MESSAGE);
				break;
			case "AboutVersion":
				JOptionPane.showMessageDialog(null, "GVViZ Version 1.0.0", "About", JOptionPane.PLAIN_MESSAGE);
				break;
			case "SaveCSV":
				JFrame csvFrame = new JFrame();
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save"); 
				int userSelectionCSV = fileChooser.showSaveDialog(csvFrame);
				
				if (userSelectionCSV == JFileChooser.APPROVE_OPTION) {
				    File fileToSave = fileChooser.getSelectedFile();
				    String filePath = fileToSave.getAbsolutePath();

				    try {
				    	FileWriter fw;
				    	BufferedWriter bw;
				    	
					    if(!filePath.endsWith(".csv")) {
					    	 fw = new FileWriter(new File(filePath + ".csv"));
					    	 bw = new BufferedWriter(fw);
					    }else  {
					    	fw = new FileWriter(fileToSave);
					    	bw = new BufferedWriter(fw);
					    }
					    
					    bw.write(",");
					    
					    for (int i =0; i < model.getSID().length; i++) {
					    	if( i == model.getSID().length - 1)
					    		bw.write(model.getSID()[i].toString());
					    	else
					    	 bw.write(model.getSID()[i].toString() + "," );
					    }
					    
					    bw.write("\n");
						
						for (int j =0; j < model.getGene().length; j++) {
							for (int i =0; i < model.getSID().length; i++) {
								int counter = model.getH().get(model.getSID()[i] + " " + model.getGene()[j]);
								
								if(i == 0)
									bw.write(model.getData()[j][0].toString() + "," + model.getData()[counter][2].toString() + ",");
								else if(i == model.getSID().length - 1)
									bw.write(model.getData()[counter][2].toString());
								else
									bw.write(model.getData()[counter][2].toString() + ",");
							}
								bw.write("\n");
						}

				        
				        bw.close();
				        
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				System.out.println("Done saving the CSV file.");

				break;
			case "Connect":
				db = model.getFrame();
				db.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				db.setVisible(true);
			    db.setSize(500, 300);      
			    db.setLocationRelativeTo(null); 
				break; 
		}
	}
}
