import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * <p>This class shows the various options of the HeatMap.</p>
 *
 * <hr />
 * <p><strong>Copyright:</strong> Copyright (c) 2007, 2008</p>
 *
 * <p>HeatMap is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.</p>
 *
 * <p>HeatMap is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.</p>
 *
 * <p>You should have received a copy of the GNU General Public License
 * along with HeatMap; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA</p>
 *
 * @author Matthew Beckler (matthew@mbeckler.org)
 * @author Josh Hayes-Sheen (grey@grevian.org), Converted to use BufferedImage.
 * @author J. Keller (jpaulkeller@gmail.com), Added transparency (alpha) support, data ordering bug fix.
 * @version 1.6
 */

class GVViZ extends JFrame implements ItemListener, FocusListener, ActionListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HeatMap panel;
    SqlView sql;
    BeginerView beg;
    BeginerView2 dataPane;
    DBViewFrame dbpanel;
    MenuView menu;
    JCheckBox drawLegend;
    JCheckBox drawTitle;
    JCheckBox drawXTitle;
    JCheckBox drawXTicks;
    JCheckBox drawYTitleLeft;
    JCheckBox drawYTitleRight;
    JCheckBox drawYTicks;
    JTextField textTitle;
    JTextField textXTitle;
    JTextField textYTitleLeft;
    JTextField textYTitleRight;
    JComboBox XValue;
    JComboBox LeftYValue;
    JComboBox RightYValue;
    JComboBox ZValue;
    JTextField textFGColor;
    JTextField textBGColor;
    JComboBox gradientComboBox;
    SQLController controller;
    Model model;
    MenuController mController;
    Table<String, String, Double> table;
    JButton submit;
    
    JLabel status;
    JLabel processing;
    JLabel minlabel;
    JLabel maxlabel;
    JLabel records;
    
    static GVViZ frame;
    
    ImageIcon[] icons;
    String[] names = {"GRADIENT_RAINBOW",
                      "GRADIENT_BLUE_TO_RED",
                      "GRADIENT_GREEN_YELLOW_ORANGE_RED",
                      "GRADIENT_HEAT",
                      "GRADIENT_HOT",
                      "GRADIENT_MAROON_TO_GOLD",
                      "GRADIENT_WHITE_TO_BLACK",
                      "GRADIENT_GREEN_TO_RED",
                      "GRADIENT_YOR",
                      "GRADIENT_WHITE_TO_BLUE",
                      "GRADIENT_PLANET_HEART",
                      "GRADIENT_HEATED_METAL",
                      "GRADIENT_DEEP_SEA",
                      "GRADIENT_BU_GN_YI",
                      "GRADIENT_RED",
                      "GRADIENT_BLUE",
                      "GRADIENT_DIVERGING",
                      "GRADIENT_DIVERGING2",
                      "GRADIENT_PINK_GREEN",
                      "GRADIENT_YELLOW_TO_PURPLE",
                      "GRADIENT_ORANGE_PINK_GREY",
                      "GRADIENT_BLUE_YELLOW_RED",
    				  "GRADIENT_RED_YELLOW_BLUE",
    				  "GRADIENT_BLUE_BROWN_RED",
    				  "GRADIENT_GREEN_TO_ORANGE",
    				  "GRADIENT_PAIRD",
    				  "GRADIENT_ACCENT",
    				  "GRADIENT_ROCKET"};
    
    Color[][] gradients = {Gradient.GRADIENT_RAINBOW,
                           Gradient.GRADIENT_BLUE_TO_RED,
                           Gradient.GRADIENT_GREEN_YELLOW_ORANGE_RED,
                           Gradient.GRADIENT_HEAT,
                           Gradient.GRADIENT_HOT,
                           Gradient.GRADIENT_MAROON_TO_GOLD,
                           Gradient.GRADIENT_WHITE_TO_BLACK,
                           Gradient.GRADIENT_GREEN_TO_RED, 
                           Gradient.GRADIENT_YOR,
                           Gradient.GRADIENT_WHITE_TO_BLUE,
                           Gradient.GRADIENT_PLANET_HEART,
                           Gradient.GRADIENT_HEATED_METAL,
                           Gradient.GRADIENT_DEEP_SEA,
                           Gradient.GRADIENT_BU_GN_YI,
                           Gradient.GRADIENT_RED,
                           Gradient.GRADIENT_BLUE,
                           Gradient.GRADIENT_DIVERGING,
                           Gradient.GRADIENT_DIVERGING2,
                           Gradient.GRADIENT_PINK_GREEN, 
                           Gradient.GRADIENT_YELLOW_TO_PURPLE,
                           Gradient.GRADIENT_ORANGE_PINK_GREY,
                           Gradient.GRADIENT_BLUE_YELLOW_RED,
                           Gradient.GRADIENT_RED_YELLOW_BLUE,
                           Gradient.GRADIENT_BLUE_BROWN_RED,
                           Gradient.GRADIENT_GREEN_TO_ORANGE,
                           Gradient.GRADIENT_PAIRD,
                           Gradient.GRADIENT_ACCENT,
                           Gradient.GRADIENT_ROCKET};
    
    public GVViZ() throws Exception
    {
        super("GVViZ");
        
        // gui stuff to demonstrate options
        JPanel listPane = new JPanel();
        listPane.setLayout(new GridBagLayout());
        listPane.setBorder(BorderFactory.createTitledBorder("GVViZ Heat Map Settings"));

        GridBagConstraints gbc;        
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(2, 1, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_START;
        
        status = new JLabel("Not connected to the DB");
        status.setFont(status.getFont().deriveFont(15f));
        status.setForeground(Color.red);
        listPane.add(status, gbc);
        
        gbc.gridy = GridBagConstraints.RELATIVE;
        
        processing = new JLabel("Available...");
        processing.setFont(processing.getFont().deriveFont(15f));
        processing.setForeground(Color.green);
        listPane.add(processing, gbc);
        
        listPane.add(Box.createVerticalStrut(20), gbc);
        
        
        minlabel = new JLabel("Min Value: ");
        minlabel.setFont(minlabel.getFont().deriveFont(15f));
        listPane.add(minlabel, gbc);
        
        gbc.gridy = GridBagConstraints.RELATIVE;
        
        maxlabel = new JLabel("Max Value: ");
        maxlabel.setFont(maxlabel.getFont().deriveFont(15f));
        listPane.add(maxlabel, gbc);
        
        gbc.gridy = GridBagConstraints.RELATIVE;
        
        records = new JLabel("Number of Records Found: ");
        records.setFont(records.getFont().deriveFont(15f));
        listPane.add(records, gbc);
        
        
        listPane.add(Box.createVerticalStrut(20), gbc);
        
        drawTitle = new JCheckBox("Draw Title");
        drawTitle.setSelected(true);
        drawTitle.addItemListener(this);
        listPane.add(drawTitle, gbc);
        gbc.gridy = GridBagConstraints.RELATIVE;
        drawLegend = new JCheckBox("Draw Legend");
        drawLegend.setSelected(true);
        drawLegend.addItemListener(this);
        listPane.add(drawLegend, gbc);
        
        drawXTitle = new JCheckBox("Draw X-Axis Title");
        drawXTitle.setSelected(true);
        drawXTitle.addItemListener(this);
        listPane.add(drawXTitle, gbc);
        
        drawYTitleLeft = new JCheckBox("Draw Y-Axis Left Title");
        drawYTitleLeft.setSelected(true);
        drawYTitleLeft.addItemListener(this);
        listPane.add(drawYTitleLeft, gbc);
        
        drawYTitleRight = new JCheckBox("Draw Y-Axis Right Title");
        drawYTitleRight.setSelected(true);
        drawYTitleRight.addItemListener(this);
        listPane.add(drawYTitleRight, gbc);
        
        listPane.add(Box.createVerticalStrut(20), gbc);
        
        JLabel label = new JLabel("Title:");
        listPane.add(label, gbc);
        
        textTitle = new JTextField();
        textTitle.addFocusListener(this);
        listPane.add(textTitle, gbc);
        
        label = new JLabel("X-Axis Title:");
        listPane.add(label, gbc);
        
        textXTitle = new JTextField();
        textXTitle.addFocusListener(this);
        listPane.add(textXTitle, gbc);

        label = new JLabel("Y-Axis Left Title:");
        listPane.add(label, gbc);
        
        textYTitleLeft = new JTextField();
        textYTitleLeft.addFocusListener(this);
        listPane.add(textYTitleLeft, gbc);
        
        label = new JLabel("Y-Axis Right Title:");
        listPane.add(label, gbc);
        
        textYTitleRight = new JTextField();
        textYTitleRight.addFocusListener(this);
        listPane.add(textYTitleRight, gbc);
        
        //label = new JLabel("X-Axis Data Source:");
        //listPane.add(label, gbc);
        
        String[] Strings = { "Ensembl Id", "Gene Name", "Disease"};
        String[] Strings2 = { "none", "Ensembl Id", "Gene Name", "Disease"};

        //XValue = new JComboBox(Strings);
        //XValue.setSelectedIndex(0);
        //listPane.add(XValue, gbc);
        
        label = new JLabel(" Y-Axis Left Title Source:");
        listPane.add(label, gbc);

        LeftYValue = new JComboBox(Strings);
        LeftYValue.setSelectedIndex(0);
        listPane.add(LeftYValue, gbc);
        
        label = new JLabel("Y-Axis Right Title Source:");
        listPane.add(label, gbc);

        RightYValue = new JComboBox(Strings2);
        RightYValue.setSelectedIndex(0);
        listPane.add(RightYValue, gbc);
        
        listPane.add(Box.createVerticalStrut(20), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 29;//GridBagConstraints.RELATIVE;
        gbc.weightx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        listPane.add(Box.createVerticalStrut(20), gbc);
        
        //----------------------------------------------------------------------
        
        label = new JLabel("Gradient:");
        listPane.add(label, gbc);
        
        icons = new ImageIcon[names.length];
        Integer[] intArray = new Integer[names.length];
        for (int i = 0; i < names.length; i++)
        {
            intArray[i] = new Integer(i);
            icons[i] = createImageIcon("images/" + names[i] + ".gif");
        }
        
        gbc.gridx = 0;
        gbc.gridy = 30;//GridBagConstraints.RELATIVE;
        gbc.weightx = 0;
        
        gradientComboBox = new JComboBox(intArray);
        ComboBoxRenderer renderer = new ComboBoxRenderer();
        gradientComboBox.setRenderer(renderer);
        gradientComboBox.addItemListener(this);
        
        listPane.add(gradientComboBox, gbc);
        
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        
        submit = new JButton("Render Heat Map");
        submit.addActionListener(this);
        gbc.gridx = 10;
        gbc.gridy = 55;
        listPane.add(submit, gbc);
        

        
        dataPane = new BeginerView2();
        dataPane.setBorder(BorderFactory.createTitledBorder("GVViZ Data Settings"));
        
        //----------------------------------------------------------------------
        
        //double[][] data = HeatMap.generateSinCosData(200);
        double[][] data = new double[][]{{3,2,3,4,5,6},
            {2,3,4,5,6,7},
            {3,4,5,6,7,6},
            {4,5,6,7,6,5}};
            
        boolean useGraphicsYAxis = true;
        
        model = new Model();
        beg = new BeginerView();

        // you can use a pre-defined gradient:
        panel = new HeatMap(data, useGraphicsYAxis, Gradient.GRADIENT_RAINBOW, model, beg, dataPane);
        gradientComboBox.setSelectedIndex(0);
        
        // set miscelaneous settings
        panel.setDrawLegend(true);

        panel.setTitle("Heat Map");
        textTitle.setText("Heat Map");
        panel.setDrawTitle(true);

        panel.setXAxisTitle("SID");
        textXTitle.setText("SID");
        panel.setDrawXAxisTitle(true);

        panel.setYAxisTitleLeft("Gene");
        textYTitleLeft.setText("Gene");
        
        panel.setYAxisTitleRight("");
        textYTitleRight.setText("");
        panel.setDrawYAxisTitleLeft(true);

        panel.setCoordinateBounds(0, 6.28, 0, 6.28);
        panel.setDrawXTicks(true);
        panel.setDrawYTicks(true);
        
        dbpanel = new DBViewFrame();
        model.setFrame(dbpanel);
        menu = new MenuView();
        this.setJMenuBar(menu.menuBar);
        
        mController = new MenuController(menu, model);
        mController.initController();
        
        sql = new SqlView();

        JScrollPane scrollPane = new JScrollPane(beg);
        
        controller = new SQLController(model, sql, dbpanel, beg, dataPane, status, processing, records);
        controller.initController();
        
        //controller.Connect();
        
        //this.getContentPane().add(dbpanel, BorderLayout.PAGE_START);
	    this.getContentPane().add(listPane, BorderLayout.EAST);
	    this.getContentPane().add(dataPane, BorderLayout.WEST);
	    
	    JScrollPane panelPane = new JScrollPane(panel);
	    
	    JTabbedPane tp=new JTabbedPane(); 
	    tp.add("Search",scrollPane);
	    tp.add("Visualize", panelPane); 
	    tp.add("SQL",sql);  
	   
	    
	    //System.out.println(tp.getBoundsAt(1).width);

	    this.getContentPane().add(tp);
	    
	    table = HashBasedTable.create();
 
        //this.getContentPane().add(panel, BorderLayout.CENTER);
    }
    
    Object[] getColumn(Object[][] matrix, int column) {
    	
    	Object[] tmp = new Object[matrix.length];
    	
    	for(int i =0; i < matrix.length; i++) {
    		tmp[i] = matrix[i][column];
    	}
    	
    	return tmp;
    }
    
    Object[] getDistinct(Object [] array) {
    	return Arrays.stream(array).distinct().toArray(Object[]::new);
    }
    
    boolean isBlankString(String string) {
        return string == null || string.trim().isEmpty();
    }
    
    public void focusGained(FocusEvent e)
    {
    	Object source = e.getSource();
    }
    
    public void focusLost(FocusEvent e)
    {
        Object source = e.getSource();
        
        //System.out.println(e.getID());
        
        if (source == textTitle)
        {
            panel.setTitle(textTitle.getText());
        }
        else if (source == textXTitle)
        {
            panel.setXAxisTitle(textXTitle.getText());
        }
        else if (source == textYTitleLeft)
        {
            panel.setYAxisTitleLeft(textYTitleLeft.getText());
        }
        else if (source == textYTitleRight)
        {
            panel.setYAxisTitleRight(textYTitleRight.getText());
        }
        else if (source == textFGColor)
        {
            String c = textFGColor.getText();
            if (c.length() != 6)
                return;
            
            Color color = colorFromHex(c);
            if (color == null)
                return;

            panel.setColorForeground(color);
        }
        else if (source == textBGColor)
        {
            String c = textBGColor.getText();
            if (c.length() != 6)
                return;

            Color color = colorFromHex(c);
            if (color == null)
                return;

            panel.setColorBackground(color);
        }
    }
    
    private void RenderData() {
    	if(model.getHscolums() != null) {   
    		model.setUpdated(true);
			int p = model.getHscolums().indexOf("SID");
			model.setXcolumns(getDistinct(getColumn(model.getData(), p)));   			
			p = model.getHscolums().indexOf("gene_id");
			model.setYcolumns(getDistinct(getColumn(model.getData(), p)));
			
			if(dataPane.getAll2().isSelected() == false) {
				
				if(LeftYValue.getSelectedIndex() == 0)
					model.setYcolumnLeft(model.getYcolumns());
				else if (LeftYValue.getSelectedIndex() == 1) 
					model.setYcolumnLeft(model.getGeneName());
				else
					model.setYcolumnLeft(model.getDiseaseName());
				
				if(RightYValue.getSelectedIndex() == 0)
					model.setYcolumnRight(null);
				else if (RightYValue.getSelectedIndex() == 1) 
					model.setYcolumnRight(model.getYcolumns());
				else if(RightYValue.getSelectedIndex() == 2)
					model.setYcolumnRight(model.getGeneName());
				else
					model.setYcolumnRight(model.getDiseaseName());
				
			}else {
				model.setYcolumnLeft(model.getYcolumns());
				model.setYcolumnRight(null);
			}
						
			p = model.getHscolums().indexOf("TPM");
			model.setZcolumns(getColumn(model.getData(), p));   
			
			for(int i = 0; i < model.getData().length; i++) {
				table.put(model.getData()[i][0].toString(), model.getData()[i][1].toString(), Double.valueOf(model.getData()[i][2].toString()));
			}
			
			double[][] data = new double[model.getXcolumns().length][model.getYcolumns().length];
			
			for(int i=0; i < model.getXcolumns().length; i++) {
				for(int j=0; j < model.getYcolumns().length; j++) {
					data[i][j] = table.get(model.getYcolumns()[j].toString(), model.getXcolumns()[i].toString());
				}
			}
						
			model.setRenderData(data);
			panel.updateData(data, true);
			maxlabel.setText("Max Value: " + model.getMax());
			minlabel.setText("Min Value: " + model.getMin());
    	}else {
    		JOptionPane.showMessageDialog(null, "The sample id and the gene or disease name can't be empty.", "Error", JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    private Color colorFromHex(String c)
    {
        try
        {
            int r = Integer.parseInt(c.substring(0, 2), 16);
            int g = Integer.parseInt(c.substring(2, 4), 16);
            int b = Integer.parseInt(c.substring(4, 6), 16);
            
            float rd = r / 255.0f;
            float gd = g / 255.0f;
            float bd = b / 255.0f;
            
            return new Color(rd, gd, bd);
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    public void itemStateChanged(ItemEvent e)
    {
    	Object source = e.getItemSelectable();

        if (source == drawLegend)
        {
            panel.setDrawLegend(e.getStateChange() == ItemEvent.SELECTED);
        }
        else if (source == drawTitle)
        {
            panel.setDrawTitle(e.getStateChange() == ItemEvent.SELECTED);
        }
        else if (source == drawXTitle)
        {
            panel.setDrawXAxisTitle(e.getStateChange() == ItemEvent.SELECTED);
        }
        else if (source == drawXTicks)
        {
            panel.setDrawXTicks(e.getStateChange() == ItemEvent.SELECTED);
        }
        else if (source == drawYTitleLeft)
        {
            panel.setDrawYAxisTitleLeft(e.getStateChange() == ItemEvent.SELECTED);
        }
        else if (source == drawYTitleRight)
        {
            panel.setDrawYAxisTitleRight(e.getStateChange() == ItemEvent.SELECTED);
        }
        else if (source == drawYTicks)
        {
            panel.setDrawYTicks(e.getStateChange() == ItemEvent.SELECTED);
        }else if(source == LeftYValue) 
        {
        	panel.setYAxis();
        }
        else
        {
            // must be from the combo box
            Integer ix = (Integer) e.getItem();
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                panel.updateGradient(gradients[ix]);
            }
        }
    }
    
    // this function will be run from the EDT
    private static void createAndShowGUI() throws Exception
    {
        GVViZ hmd = new GVViZ();
        hmd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hmd.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        hmd.setVisible(true);
        frame = hmd;
        //hmd.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    createAndShowGUI();
                }
                catch (Exception e)
                {
                    System.err.println(e);
                    e.printStackTrace();
                }
            }
        });
    }
    
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected ImageIcon createImageIcon(String path)
    {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null)
        {
            return new ImageIcon(imgURL);
        }
        else
        {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    
    class ComboBoxRenderer extends JLabel implements ListCellRenderer
    {
        public ComboBoxRenderer()
        {
            setOpaque(true);
            setHorizontalAlignment(LEFT);
            setVerticalAlignment(CENTER);
        }
        
        public Component getListCellRendererComponent(
                                                JList list,
                                                Object value,
                                                int index,
                                                boolean isSelected,
                                                boolean cellHasFocus)
        {
            int selectedIndex = ((Integer)value).intValue();
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            ImageIcon icon = icons[selectedIndex];
            setIcon(icon);
            setText(names[selectedIndex].substring(9));
            return this;
        }
    }


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
    	if(controller.getConnection() == false){
			JOptionPane.showMessageDialog(this, "You need to connect to the data base before rendering the data.", "Rendering Error", JOptionPane.ERROR_MESSAGE);
    	}else if(model.getData() == null) {
			JOptionPane.showMessageDialog(this, "You need to execute a query before rendering the data.", "Rendering Error", JOptionPane.ERROR_MESSAGE);
    	}else {
    		RenderData();
    	}
	}
}
