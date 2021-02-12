import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ContactView extends JPanel implements MouseListener{
	
	private ImageIcon imageIcon;
	private JLabel logo;
	private JLabel text;
	private JLabel github;
	private JLabel egrEmail;
	private JLabel zaEmail;
	private JLabel promis;
	private JLabel programmed;
	private JLabel supervised;
	private JLabel lab;

	
	ContactView()  {
        java.net.URL imgURL = getClass().getResource("images/Ahmed_lab_logo.png");
		imageIcon = new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
		logo = new JLabel(imageIcon);
		
	    GridBagConstraints c = new GridBagConstraints();
	    this.setLayout(new GridBagLayout());
		text = new JLabel();
		text.setText("<html><center><b>GVViZ. ver.1.0.0</b></center><br/>"
				+ "Gene Variant Visualization<br/>");
		
		github = new JLabel();
		github.setText( "<html><a href=\"https://github.com/drzeeshanahmed/GVViZ\">https://github.com/drzeeshanahmed/GVViZ</a><br/></html>");
		
		programmed = new JLabel();
		programmed.setText("<html><center><br/>Programmed by:<center><br/>"
				+ "<center><b>Dr. Eduard Gibert Renart</b><center>"
				+ "<center>Postdoctoral Research Associate<center></html>");
		
		egrEmail = new JLabel();
		egrEmail.setText("<html><center><a href=\"erenart@ifh.rutgers.edu\">erenart@ifh.rutgers.edu</a><center><br/></html>");
		
		supervised = new JLabel();
		supervised.setText("<html></br><center>Supervised by:<center><br/>"
				+ "<center><b>Dr. Zeeshan Ahmed</b><center>"
				+ "<center>Assistant Professor of Medicine<center></html>");
		
		zaEmail = new JLabel();
		zaEmail.setText("<html><center><a href=\"zahmed@ifh.rutgers.edu\">zahmed@ifh.rutgers.edu</a><center><br/><br/></html>");
		
		lab = new JLabel();
		lab.setText("<html><center>Ahmed Lab!<center><br/>"
				+ "<center>Rutgers, Institute for Health, Health Care Policy and Aging Research (IFH).<center><br/>"
				+ "<center>Rutgers, Robert Wood Johnson Medical School (RWJMS).<center><br/>"
				+ "<center>Rutgers, Biomedical and Health Sciences (RBHS).<center><br/>"
				+ "<center>Rutgers, The State University of New Jersey.<center><br/>"
				+ "<center>New Brunswick, NJ, USA.<center><br/></html>");
		
		promis = new JLabel();
		promis.setText("<html><center>Lab URL: <a href=\"https://promis.rutgers.edu\">https://promis.rutgers.edu</a><center><br/></html>");
		
		egrEmail.addMouseListener(this);
		github.addMouseListener(this);
		zaEmail.addMouseListener(this);
		promis.addMouseListener(this);
		
		c.gridx = 0;
		c.gridy = 0;
		c.anchor=GridBagConstraints.WEST;
		this.add(logo);
		c.anchor=GridBagConstraints.CENTER;
		c.gridy = 1;
		this.add(text,c);
		c.gridy = 2;
		this.add(github,c);
		c.gridy = 3;
		this.add(programmed,c);
		c.gridy = 4;
		this.add(egrEmail, c);
		c.gridy = 5;
		this.add(supervised,c);
		c.gridy = 6;
		this.add(zaEmail,c);
		c.gridy = 7;
		this.add(lab,c);
		c.gridy = 8;
		this.add(promis,c);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == egrEmail)
			try {
				Desktop.getDesktop().mail(new URI("mailto:erenart@ifh.rutgers.edu?subject=GVViZ"));
			} catch (IOException | URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		else if(e.getSource() == github)
			try {
				Desktop.getDesktop().browse(new URI("https://github.com/drzeeshanahmed/GVViZ"));
			} catch (IOException | URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		else if(e.getSource() == promis)
			try {
				Desktop.getDesktop().browse(new URI("https://promis.rutgers.edu"));
			} catch (IOException | URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		else if(e.getSource() == zaEmail)
			try {
				Desktop.getDesktop().mail(new URI("mailto:zahmed@ifh.rutgers.edu?subject=GVViZ"));
			} catch (IOException | URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
