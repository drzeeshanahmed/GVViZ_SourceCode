import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Arrays;

import javax.swing.*;

/**
 *
 * <p><strong>Title:</strong> HeatMap</p>
 *
 * <p>Description: HeatMap is a JPanel that displays a 2-dimensional array of
 * data using a selected color gradient scheme.</p>
 * <p>For specifying data, the first index into the double[][] array is the x-
 * coordinate, and the second index is the y-coordinate. In the constructor and
 * updateData method, the 'useGraphicsYAxis' parameter is used to control 
 * whether the row y=0 is displayed at the top or bottom. Since the usual
 * graphics coordinate system has y=0 at the top, setting this parameter to
 * true will draw the y=0 row at the top, and setting the parameter to false
 * will draw the y=0 row at the bottom, like in a regular, mathematical
 * coordinate system. This parameter was added as a solution to the problem of
 * "Which coordinate system should we use? Graphics, or mathematical?", and
 * allows the user to choose either coordinate system. Because the HeatMap will
 * be plotting the data in a graphical manner, using the Java Swing framework
 * that uses the standard computer graphics coordinate system, the user's data
 * is stored internally with the y=0 row at the top.</p>
 * <p>There are a number of defined gradient types (look at the static fields),
 * but you can create any gradient you like by using either of the following 
 * functions in the Gradient class:
 * <ul>
 *   <li>public static Color[] createMultiGradient(Color[] colors, int numSteps)</li>
 *   <li>public static Color[] createGradient(Color one, Color two, int numSteps)</li>
 * </ul>
 * You can then assign an arbitrary Color[] object to the HeatMap as follows:
 * <pre>myHeatMap.updateGradient(Gradient.createMultiGradient(new Color[] {Color.red, Color.white, Color.blue}, 256));</pre>
 * </p>
 *
 * <p>By default, the graph title, axis titles, and axis tick marks are not
 * displayed. Be sure to set the appropriate title before enabling them.</p>
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

public class HeatMap extends JPanel
{
    private double[][] data;
    private int[][] dataColorIndices;
    private double[] dataRanges;
    private double[] dataSmall;

    // these four variables are used to print the axis labels
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;

    private String title;
    private String xAxis;
    private String yAxisLeft;
    private String yAxisRight;

    private boolean drawTitle = false;
    private boolean drawXTitle = false;
    private boolean drawYTitleLeft = false;
    private boolean drawYTitleRight = true;
    private boolean drawLegend = true;
    private boolean drawXTicks = false;
    private boolean drawYTicks = false;

    private Color[] colors;
    private Color bg = Color.white;
    private Color fg = Color.black;

    private BufferedImage bufferedImage;
    private Graphics2D bufferedGraphics;
    
    private Model model;
    private BeginerView view;
    private BeginerView2 view2;
    
	private boolean xValuesHorizontal;
	private boolean yValuesHorizontal;
    
	// General chart settings.
	private Dimension cellSize;
	private Dimension chartSize;
	private int margin;
	private Color backgroundColour;
    
    // Title settings.
    private Font titleFont;
	private Color titleColour;
	private Dimension titleSize;
	private int titleAscent;
	
	// Axis settings.
	private int axisThickness;
	private Color axisColour;
	private Font axisLabelsFont;
	private Color axisLabelColour;
	private String xAxisLabel;
	private String yAxisLabel;
	private Color axisValuesColour;
	private Font axisValuesFont; // The font size will be considered the maximum font size - it may be smaller if needed to fit in.
	private int xAxisValuesFrequency;
	private int yAxisValuesFrequency;
	private boolean showXAxisValues;
	private boolean showYAxisValues;
	
	// Generated axis properties.
	private int xAxisValuesHeight;
	private int xAxisValuesWidthMax;
	
	private int yAxisValuesHeightLeft;
	private int yAxisValuesAscentLeft;
	private int yAxisValuesWidthMaxLeft;
	
	private int yAxisValuesHeightRight;
	private int yAxisValuesAscentRight;
	private int yAxisValuesWidthMaxRight;
	
	private Dimension xAxisLabelSize;
	private int xAxisLabelDescent;
	
	private Dimension yAxisLabelSizeLeft;
	private Dimension yAxisLabelSizeRight;
	private Dimension yAxisLabelSize;
	private int yAxisLabelAscentLeft;
	private int yAxisLabelAscentRight;
	
	// Key co-ordinate positions.
	private Point heatMapTL;
	private Point heatMapBR;
	private Point heatMapC;
	
	// Heat map dimensions.
	private Dimension heatMapSize;
	
    /**
     * @param data The data to display, must be a complete array (non-ragged)
     * @param useGraphicsYAxis If true, the data will be displayed with the y=0 row at the top of the screen. If false, the data will be displayed with they=0 row at the bottom of the screen.
     * @param colors A variable of the type Color[]. See also {@link #createMultiGradient} and {@link #createGradient}.
     */
    public HeatMap(double[][] data, boolean useGraphicsYAxis, Color[] colors, Model model, BeginerView view, BeginerView2 view2)
    {
        super();

        this.model = model;
        this.view = view;
        this.view2 = view2;
        
        updateGradient(colors);
        updateData(data, useGraphicsYAxis);

        this.setPreferredSize(new Dimension(60+data.length, 60+data[0].length));
        this.setDoubleBuffered(true);

        this.bg = Color.white;
        this.fg = Color.black;
        
		// Default chart settings.
		
		this.margin = 20;
		this.backgroundColour = Color.WHITE;
		
		// Default title settings.
		this.title = null;
		this.titleFont = new Font("Sans-Serif", Font.BOLD, 16);
		this.titleColour = Color.BLACK;
		
		// Default axis settings.
		this.xAxisLabel = null;
		this.yAxisLabel = null;
		this.axisThickness = 2;
		this.axisColour = Color.BLACK;
		this.axisLabelColour = Color.BLACK;
		this.axisValuesColour = Color.BLACK;
		this.xAxisValuesFrequency = 1;
		this.xAxisValuesHeight = 0;
		this.xValuesHorizontal = false;
		this.showXAxisValues = true;
		this.showYAxisValues = true;
		this.yAxisValuesFrequency = 1;
		this.yAxisValuesHeightLeft = 0;
		this.yValuesHorizontal = true;
        
        // this is the expensive function that draws the data plot into a 
        // BufferedImage. The data plot is then cheaply drawn to the screen when
        // needed, saving us a lot of time in the end.
        drawData();
    }

    /**
     * Specify the coordinate bounds for the map. Only used for the axis labels, which must be enabled seperately. Calls repaint() when finished.
     * @param xMin The lower bound of x-values, used for axis labels
     * @param xMax The upper bound of x-values, used for axis labels
     */
    public void setCoordinateBounds(double xMin, double xMax, double yMin, double yMax)
    {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        
        repaint();
    }

    /**
     * Specify the coordinate bounds for the X-range. Only used for the axis labels, which must be enabled seperately. Calls repaint() when finished.
     * @param xMin The lower bound of x-values, used for axis labels
     * @param xMax The upper bound of x-values, used for axis labels
     */
    public void setXCoordinateBounds(double xMin, double xMax)
    {
        this.xMin = xMin;
        this.xMax = xMax;
        
        repaint();
    }
    
    /**
     * Specify the coordinate bounds for the X Min. Only used for the axis labels, which must be enabled seperately. Calls repaint() when finished.
     * @param xMin The lower bound of x-values, used for axis labels
     */
    public void setXMinCoordinateBounds(double xMin)
    {
        this.xMin = xMin;
        
        repaint();
    }
    
    /**
     * Specify the coordinate bounds for the X Max. Only used for the axis labels, which must be enabled seperately. Calls repaint() when finished.
     * @param xMax The upper bound of x-values, used for axis labels
     */
    public void setXMaxCoordinateBounds(double xMax)
    {
        this.xMax = xMax;
        
        repaint();
    }

    /**
     * Specify the coordinate bounds for the Y-range. Only used for the axis labels, which must be enabled seperately. Calls repaint() when finished.
     * @param yMin The lower bound of y-values, used for axis labels
     * @param yMax The upper bound of y-values, used for axis labels
     */
    public void setYCoordinateBounds(double yMin, double yMax)
    {
        this.yMin = yMin;
        this.yMax = yMax;
        
        repaint();
    }
    
    /**
     * Specify the coordinate bounds for the Y Min. Only used for the axis labels, which must be enabled seperately. Calls repaint() when finished.
     * @param yMin The lower bound of Y-values, used for axis labels
     */
    public void setYMinCoordinateBounds(double yMin)
    {
        this.yMin = yMin;
        
        repaint();
    }
    
    /**
     * Specify the coordinate bounds for the Y Max. Only used for the axis labels, which must be enabled seperately. Calls repaint() when finished.
     * @param yMax The upper bound of y-values, used for axis labels
     */
    public void setYMaxCoordinateBounds(double yMax)
    {
        this.yMax = yMax;
        
        repaint();
    }

    /**
     * Updates the title. Calls repaint() when finished.
     * @param title The new title
     */
    public void setTitle(String title)
    {
        this.title = title;
        
        repaint();
    }

    /**
     * Updates the state of the title. Calls repaint() when finished.
     * @param drawTitle Specifies if the title should be drawn
     */
    public void setDrawTitle(boolean drawTitle)
    {
        this.drawTitle = drawTitle;
        
        repaint();
    }

    /**
     * Updates the X-Axis title. Calls repaint() when finished.
     * @param xAxisTitle The new X-Axis title
     */
    public void setXAxisTitle(String xAxisTitle)
    {
        this.xAxis = xAxisTitle;
        
        repaint();
    }

    /**
     * Updates the state of the X-Axis Title. Calls repaint() when finished.
     * @param drawXAxisTitle Specifies if the X-Axis title should be drawn
     */
    public void setDrawXAxisTitle(boolean drawXAxisTitle)
    {
        this.drawXTitle = drawXAxisTitle;
        
        repaint();
    }

    /**
     * Updates the Y-Axis title. Calls repaint() when finished.
     * @param yAxisTitle The new Y-Axis title
     */
    public void setYAxisTitleLeft(String yAxisTitle)
    {
        this.yAxisLeft = yAxisTitle;
        
        repaint();
    }
    
    public void setYAxisTitleRight(String yAxisTitle)
    {
        this.yAxisRight = yAxisTitle;
        
        repaint();
    }

    /**
     * Updates the state of the Y-Axis Title. Calls repaint() when finished.
     * @param drawYAxisTitle Specifies if the Y-Axis title should be drawn
     */
    public void setDrawYAxisTitleLeft(boolean drawYAxisTitle)
    {
        this.drawYTitleLeft = drawYAxisTitle;
        
        repaint();
    }
    
    public void setDrawYAxisTitleRight(boolean drawYAxisTitle)
    {
    	//System.out.print("Right");
        this.drawYTitleRight = drawYAxisTitle;
        repaint();
    }

    /**
     * Updates the state of the legend. Calls repaint() when finished.
     * @param drawLegend Specifies if the legend should be drawn
     */
    public void setDrawLegend(boolean drawLegend)
    {
    	//System.out.print("Legend");
        this.drawLegend = drawLegend;
        
        repaint();
    }

    /**
     * Updates the state of the X-Axis ticks. Calls repaint() when finished.
     * @param drawXTicks Specifies if the X-Axis ticks should be drawn
     */
    public void setDrawXTicks(boolean drawXTicks)
    {
        this.drawXTicks = drawXTicks;
        
        repaint();
    }

    /**
     * Updates the state of the Y-Axis ticks. Calls repaint() when finished.
     * @param drawYTicks Specifies if the Y-Axis ticks should be drawn
     */
    public void setDrawYTicks(boolean drawYTicks)
    {
        this.drawYTicks = drawYTicks;
        
        repaint();
    }

    /**
     * Updates the foreground color. Calls repaint() when finished.
     * @param fg Specifies the desired foreground color
     */
    public void setColorForeground(Color fg)
    {
        this.fg = fg;

        repaint();
    }

    /**
     * Updates the background color. Calls repaint() when finished.
     * @param bg Specifies the desired background color
     */
    public void setColorBackground(Color bg)
    {
        this.bg = bg;

        repaint();
    }
    
    public void setYAxis() 
    {
    	repaint();
    }
    

    /**
     * Updates the gradient used to display the data. Calls drawData() and 
     * repaint() when finished.
     * @param colors A variable of type Color[]
     */
    public void updateGradient(Color[] colors)
    {
        this.colors = (Color[]) colors.clone();

        if (data != null)
        {
            updateDataColors();

            drawData();

            repaint();
        }
    }
    
    private String normalizeByRow() {
    	
        double largest = Double.MIN_VALUE;
        double smallest = Double.MAX_VALUE;
        
        double largestOverall = Double.MIN_VALUE;
        double smallestOverall = Double.MAX_VALUE;
        
        dataRanges = new double[data[0].length];
        dataSmall = new double[data[0].length];

        for (int y = 0; y < data[0].length; y++)
        {
            for (int x = 0; x < data.length; x++)
            {
            	largest = Math.max(data[x][y], largest);
            	smallest = Math.min(data[x][y], smallest);
            	
            	largestOverall = Math.max(data[x][y], largestOverall);
            	smallestOverall = Math.min(data[x][y], smallestOverall);
            }
            double range = largest - smallest;            
            dataRanges[y] = range;
            dataSmall[y] = smallest;
            
            largest = Double.MIN_VALUE;
            smallest = Double.MAX_VALUE;
        }
        
        return Double.toString(largestOverall) + ":" + Double.toString(smallestOverall);
    }
    
    private void normalizeByColum() {
    	
        double largest = Double.MIN_VALUE;
        double smallest = Double.MAX_VALUE;
        
        dataRanges = new double[data.length];
        dataSmall = new double[data.length];

        for (int x = 0; x < data.length; x++)
        {
	        for (int y = 0; y < data[0].length; y++)
	        {
	        	largest = Math.max(data[x][y], largest);
	           	smallest = Math.min(data[x][y], smallest);
	        }
            double range = largest - smallest;            
            dataRanges[x] = range;
            dataSmall[x] = smallest;
	        
            largest = Double.MIN_VALUE;
            smallest = Double.MAX_VALUE;
        }
    }

    /**
     * This uses the current array of colors that make up the gradient, and 
     * assigns a color index to each data point, stored in the dataColorIndices
     * array, which is used by the drawData() method to plot the points.
     */
    private void updateDataColors()
    {
        double largest = Double.MIN_VALUE;
        double smallest = Double.MAX_VALUE;
        
        String val = normalizeByRow();
        
        String[] values = val.split(":");
        
        model.setMin(Double.parseDouble(values[1]));
        model.setMax(Double.parseDouble(values[0]));

        // dataColorIndices is the same size as the data array
        // It stores an int index into the color array
        dataColorIndices = new int[data.length][data[0].length];    

        //System.out.println("Colors: " + colors.length);
        
        //assign a Color to each data point
        for (int x = 0; x < data.length; x++)
        {
            for (int y = 0; y < data[0].length; y++)
            {
                double norm = (data[x][y] - dataSmall[y]) / dataRanges[y]; // 0 < norm < 1
                int colorIndex =  (int) Math.floor(norm * (colors.length - 1));
                dataColorIndices[x][y] = colorIndex;
            }
        }
    }

    /**
     * This function generates data that is not vertically-symmetric, which
     * makes it very useful for testing which type of vertical axis is being
     * used to plot the data. If the graphics Y-axis is used, then the lowest
     * values should be displayed at the top of the frame. If the non-graphics
     * (mathematical coordinate-system) Y-axis is used, then the lowest values
     * should be displayed at the bottom of the frame.
     * @return double[][] data values of a simple vertical ramp
     */
    public static double[][] generateRampTestData()
    {
        double[][] data = new double[10][10];
        for (int x = 0; x < 10; x++)
        {
            for (int y = 0; y < 10; y++)
            {
                data[x][y] = y;
            }
        }

        return data;
    }
    
    /**
     * This function generates an appropriate data array for display. It uses
     * the function: z = sin(x)*cos(y). The parameter specifies the number
     * of data points in each direction, producing a square matrix.
     * @param dimension Size of each side of the returned array
     * @return double[][] calculated values of z = sin(x)*cos(y)
     */
    public static double[][] generateSinCosData(int dimension)
    {
        if (dimension % 2 == 0)
        {
            dimension++; //make it better
        }

        double[][] data = new double[dimension][dimension];
        double sX, sY; //s for 'Scaled'

        for (int x = 0; x < dimension; x++)
        {
            for (int y = 0; y < dimension; y++)
            {
                sX = 2 * Math.PI * (x / (double) dimension); // 0 < sX < 2 * Pi
                sY = 2 * Math.PI * (y / (double) dimension); // 0 < sY < 2 * Pi
                data[x][y] = Math.sin(sX) * Math.cos(sY);
            }
        }

        return data;
    }

    /**
     * This function generates an appropriate data array for display. It uses
     * the function: z = Math.cos(Math.abs(sX) + Math.abs(sY)). The parameter 
     * specifies the number of data points in each direction, producing a 
     * square matrix.
     * @param dimension Size of each side of the returned array
     * @return double[][] calculated values of z = Math.cos(Math.abs(sX) + Math.abs(sY));
     */
    public static double[][] generatePyramidData(int dimension)
    {
        if (dimension % 2 == 0)
        {
            dimension++; //make it better
        }

        double[][] data = new double[dimension][dimension];
        double sX, sY; //s for 'Scaled'

        for (int x = 0; x < dimension; x++)
        {
            for (int y = 0; y < dimension; y++)
            {
                sX = 6 * (x / (double) dimension); // 0 < sX < 6
                sY = 6 * (y / (double) dimension); // 0 < sY < 6
                sX = sX - 3; // -3 < sX < 3
                sY = sY - 3; // -3 < sY < 3
                data[x][y] = Math.cos(Math.abs(sX) + Math.abs(sY));
            }
        }

        return data;
    }

    /**
     * Updates the data display, calls drawData() to do the expensive re-drawing
     * of the data plot, and then calls repaint().
     * @param data The data to display, must be a complete array (non-ragged)
     * @param useGraphicsYAxis If true, the data will be displayed with the y=0 row at the top of the screen. If false, the data will be displayed with the y=0 row at the bottom of the screen.
     */
    public void updateData(double[][] data, boolean useGraphicsYAxis)
    {
        this.data = new double[data.length][data[0].length];
        for (int ix = 0; ix < data.length; ix++)
        {
            for (int iy = 0; iy < data[0].length; iy++)
            {
                // we use the graphics Y-axis internally
                if (useGraphicsYAxis)
                {
                    this.data[ix][iy] = data[ix][iy];
                }
                else
                {
                    this.data[ix][iy] = data[ix][data[0].length - iy - 1];
                }
            }
        }

        updateDataColors();
        
        drawData();

        repaint();
    }
    
    /**
     * Creates a BufferedImage of the actual data plot.
     *
     * After doing some profiling, it was discovered that 90% of the drawing
     * time was spend drawing the actual data (not on the axes or tick marks).
     * Since the Graphics2D has a drawImage method that can do scaling, we are
     * using that instead of scaling it ourselves. We only need to draw the 
     * data into the bufferedImage on startup, or if the data or gradient
     * changes. This saves us an enormous amount of time. Thanks to 
     * Josh Hayes-Sheen (grey@grevian.org) for the suggestion and initial code
     * to use the BufferedImage technique.
     * 
     * Since the scaling of the data plot will be handled by the drawImage in
     * paintComponent, we take the easy way out and draw our bufferedImage with
     * 1 pixel per data point. Too bad there isn't a setPixel method in the 
     * Graphics2D class, it seems a bit silly to fill a rectangle just to set a
     * single pixel...
     *
     * This function should be called whenever the data or the gradient changes.
     */
    private void drawData()
    {    	
        bufferedImage = new BufferedImage(data.length,data[0].length, BufferedImage.TYPE_INT_ARGB);
        bufferedGraphics = bufferedImage.createGraphics();
        
        for (int x = 0; x < data.length; x++)
        {
            for (int y = 0; y < data[0].length; y++)
            {
                bufferedGraphics.setColor(colors[dataColorIndices[x][y]]);
                bufferedGraphics.fillRect(x, y, 1, 1);
            }
        }
    }
    
	private void measureComponents() {
		//TODO This would be a good place to check that all settings have sensible values or throw illegal state exception.
		
		//TODO Put this somewhere so it only gets created once.
		BufferedImage chartImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D tempGraphics = chartImage.createGraphics();
		
		// Calculate title dimensions.
		if (title != null) {
			tempGraphics.setFont(titleFont);
			FontMetrics metrics = tempGraphics.getFontMetrics();
			titleSize = new Dimension(metrics.stringWidth(title), metrics.getHeight());
			titleAscent = metrics.getAscent();
		} else {
			titleSize = new Dimension(0, 0);
		}
		
		//System.out.println(titleSize.toString());
		
		// Calculate x-axis label dimensions.
		if (drawXTitle && xAxis != null) {
			tempGraphics.setFont(axisLabelsFont);
			FontMetrics metrics = tempGraphics.getFontMetrics();
			xAxisLabelSize = new Dimension(metrics.stringWidth(xAxis), metrics.getHeight());
			xAxisLabelDescent = metrics.getDescent();
		} else {
			xAxisLabelSize = new Dimension(0, 0);
		}
		
		//System.out.println(xAxisLabelSize.toString());
		
		// Calculate y-axis label dimensions Left.
		if (drawYTitleLeft && yAxisLeft != null) {
			tempGraphics.setFont(axisLabelsFont);
			FontMetrics metrics = tempGraphics.getFontMetrics();
			yAxisLabelSizeLeft = new Dimension(metrics.stringWidth(yAxisLeft), metrics.getHeight());
			yAxisLabelAscentLeft = metrics.getAscent();
		} else {
			yAxisLabelSizeLeft = new Dimension(0, 0);
		}
		
		if (drawYTitleRight && yAxisRight != null) {
			tempGraphics.setFont(axisLabelsFont);
			FontMetrics metrics = tempGraphics.getFontMetrics();
			yAxisLabelSizeRight = new Dimension(metrics.stringWidth(yAxisRight), metrics.getHeight());
			yAxisLabelAscentRight = metrics.getAscent();
		} else {
			yAxisLabelSizeRight = new Dimension(0, 0);
		}
		
		yAxisLabelSize = new Dimension(yAxisLabelSizeLeft.width + yAxisLabelSizeRight.width, yAxisLabelSizeLeft.height + yAxisLabelSizeRight.height);
		
		//System.out.println(yAxisLabelSize.toString());
		
		// Calculate x-axis value dimensions.
		if (drawXTicks && model.getXcolumns() != null) {
			tempGraphics.setFont(axisValuesFont);
			FontMetrics metrics = tempGraphics.getFontMetrics();
			xAxisValuesHeight = metrics.getHeight();
			xAxisValuesWidthMax = 0;			
			for (Object o: model.getXcolumns()) {
				int w = metrics.stringWidth(o.toString());
				if (w > xAxisValuesWidthMax) {
					xAxisValuesWidthMax = w;
				}
			}
		} else {
			xAxisValuesHeight = 0;
		}
		
		// Calculate y-axis value dimensions.
		if (drawYTicks && model.getYcolumnLeft() != null) {
			tempGraphics.setFont(axisValuesFont);
			FontMetrics metrics = tempGraphics.getFontMetrics();
			yAxisValuesHeightLeft = metrics.getHeight();
			yAxisValuesAscentLeft = metrics.getAscent();
			yAxisValuesWidthMaxLeft = 0;
			for (Object o: model.getYcolumnLeft()) {
				int w = metrics.stringWidth(o.toString());
				if (w > yAxisValuesWidthMaxLeft) {
					yAxisValuesWidthMaxLeft = w;
				}
			}
		} else {
			yAxisValuesHeightLeft = 0;
		}
		
		if (drawYTicks && model.getYcolumnRight() != null) {
			tempGraphics.setFont(axisValuesFont);
			FontMetrics metrics = tempGraphics.getFontMetrics();
			yAxisValuesHeightRight = metrics.getHeight();
			yAxisValuesAscentRight = metrics.getAscent();
			yAxisValuesWidthMaxRight = 0;
			for (Object o: model.getYcolumnRight()) {
				int w = metrics.stringWidth(o.toString());
				if (w > yAxisValuesWidthMaxRight) {
					yAxisValuesWidthMaxRight = w;
				}
			}
		} else {
			yAxisValuesHeightRight = 0;
		}
		
		// Calculate heatmap dimensions.
		int heatMapWidth = (model.getxValue() * cellSize.width);
		int heatMapHeight = (model.getyValue() * cellSize.height);
				
		//System.out.println("Size: " + model.getxValue() + " " + model.getyValue());
		
		heatMapSize = new Dimension(heatMapWidth, heatMapHeight);
		
		int yValuesHorizontalSize = 0;
		if (yValuesHorizontal) {
			yValuesHorizontalSize = yAxisValuesWidthMaxLeft + yAxisValuesWidthMaxRight;
		} else {
			yValuesHorizontalSize = yAxisValuesHeightLeft + yAxisValuesHeightRight;
		}
		
		int xValuesVerticalSize = 0;
		if (xValuesHorizontal) {
			xValuesVerticalSize = xAxisValuesHeight;
		} else {
			xValuesVerticalSize = xAxisValuesWidthMax;
		}
		
		// Calculate chart dimensions.
		int chartWidth = heatMapWidth + (2 * margin) + yAxisLabelSize.height + yValuesHorizontalSize + axisThickness;
		
		if(chartWidth < titleSize.width)
			chartWidth = titleSize.width;
		
		int chartHeight = heatMapHeight + (2 * margin) + xAxisLabelSize.height + xValuesVerticalSize + titleSize.height + axisThickness;
		chartSize = new Dimension(chartWidth, chartHeight);
		
		//System.out.println("Not Model: " + heatMapWidth + " " + yAxisLabelSize.height + " " + yValuesHorizontalSize + " " + axisThickness);
		//System.out.println("Not Model: " + heatMapHeight + " " + xAxisLabelSize.height + " " + xValuesVerticalSize + " " + titleSize.height + " " + axisThickness);
	}

	private void updateCoordinates() {
		// Top-left of heat map.
		int x = margin + axisThickness + yAxisLabelSizeLeft.height;
		x += (yValuesHorizontal ? yAxisValuesWidthMaxLeft : yAxisValuesHeightLeft);
		int y = titleSize.height + margin;
		heatMapTL = new Point(x, y);

		// Top-right of heat map.
		x = heatMapTL.x + heatMapSize.width;
		y = heatMapTL.y + heatMapSize.height;
		heatMapBR = new Point(x, y);
		
		// Centre of heat map.
		x = heatMapTL.x + (heatMapSize.width / 2);
		y = heatMapTL.y + (heatMapSize.height / 2);
		heatMapC = new Point(x, y);
	}
    
    private void drawData(Graphics2D chartGraphics)
    {    	
    	if(heatMapSize.width != 0 && heatMapSize.height != 0 ) {
			BufferedImage heatMapImage = new BufferedImage(heatMapSize.width, heatMapSize.height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D heatMapGraphics = heatMapImage.createGraphics();
	    	
	        for (int x = 0; x < data.length; x++)
	        {
	            for (int y = 0; y < data[0].length; y++)
	            {
	            	heatMapGraphics.setColor(colors[dataColorIndices[x][y]]);
	                heatMapGraphics.fillRect(x*cellSize.width, y*cellSize.height, cellSize.width, cellSize.height);
	                //System.out.println(x + " " + y);
	            }
	        }
	        
			chartGraphics.drawImage(heatMapImage, heatMapTL.x, heatMapTL.y, heatMapSize.width, heatMapSize.height, null);
    	}
    }
    
	private void drawTitle(Graphics2D chartGraphics) {
		if (drawTitle && title != null) {			
			// Strings are drawn from the baseline position of the leftmost char.
			int yTitle = (margin/2) + titleAscent;
			int xTitle = (chartSize.width/2) - (titleSize.width/2);

			chartGraphics.setFont(titleFont);
			chartGraphics.setColor(titleColour);
			chartGraphics.drawString(title, xTitle, yTitle);
		}
	}
    
	private void drawXLabel(Graphics2D chartGraphics) {
		if (drawXTitle && xAxis != null) {
			// Strings are drawn from the baseline position of the leftmost char.
			int yPosXAxisLabel = chartSize.height - (margin / 2) - xAxisLabelDescent;
			//TODO This will need to be updated if the y-axis values/label can be moved to the right.
			int xPosXAxisLabel = heatMapC.x - (xAxisLabelSize.width / 2);
			
			chartGraphics.setFont(axisLabelsFont);
			chartGraphics.setColor(axisLabelColour);
			chartGraphics.drawString(xAxis, xPosXAxisLabel, yPosXAxisLabel);
		}
	}
	
	private void drawYLabelLeft(Graphics2D chartGraphics) {
		if (drawYTitleLeft && yAxisLeft != null) {
			// Strings are drawn from the baseline position of the leftmost char.
			int yPosYAxisLabel = heatMapC.y + (yAxisLabelSizeLeft.width / 2);
			int xPosYAxisLabel = (margin / 2) + yAxisLabelAscentLeft;
			
			chartGraphics.setFont(axisLabelsFont);
			chartGraphics.setColor(axisLabelColour);
			
			// Create 270 degree rotated transform.
			AffineTransform transform = chartGraphics.getTransform();
			AffineTransform originalTransform = (AffineTransform) transform.clone();
			transform.rotate(Math.toRadians(270), xPosYAxisLabel, yPosYAxisLabel);
			chartGraphics.setTransform(transform);
			
			// Draw string.
			chartGraphics.drawString(yAxisLeft, xPosYAxisLabel, yPosYAxisLabel);
			
			// Revert to original transform before rotation.
			chartGraphics.setTransform(originalTransform);
		}
	}
	
	private void drawYLabelRight(Graphics2D chartGraphics) {
		if (drawYTitleRight && yAxisRight != null) {
			// Strings are drawn from the baseline position of the leftmost char.
			
            if(model.getYcolumnRight() == null)
            	yAxisValuesWidthMaxRight = 0;
			
			int yPosYAxisLabel = heatMapC.y + (yAxisLabelSizeLeft.width / 2);
			int xPosYAxisLabel = (margin / 2) + heatMapBR.x + yAxisValuesWidthMaxRight;
			
			chartGraphics.setFont(axisLabelsFont);  
			chartGraphics.setColor(axisLabelColour);
			
			// Create -270 degree rotated transform.
			AffineTransform transform = chartGraphics.getTransform();
			AffineTransform originalTransform = (AffineTransform) transform.clone();
			transform.rotate(Math.toRadians(-270), xPosYAxisLabel, yPosYAxisLabel);
			chartGraphics.setTransform(transform);
			
			// Draw string.
			chartGraphics.drawString(yAxisRight, xPosYAxisLabel - 25, yPosYAxisLabel);
			
			// Revert to original transform before rotation.
			chartGraphics.setTransform(originalTransform);
		}
	}
	
	private void drawAxisBars(Graphics2D chartGraphics) {
		if (axisThickness > 0) {
			chartGraphics.setColor(axisColour);
			
			// Draw x-axis.
			int x = heatMapTL.x - axisThickness;
			int y = heatMapBR.y;
			
			//System.out.println("X: " + x + " Y: " + y);
			int width = heatMapSize.width + axisThickness + 1;
			int height = axisThickness;
			chartGraphics.fillRect(x, y, width, height);
			
			//System.out.println("width: " + width + " height: " + height);
			
			// Draw y-axis left.
			x = heatMapTL.x - axisThickness;
			y = heatMapTL.y;
			width = axisThickness;
			height = heatMapSize.height;
			chartGraphics.fillRect(x, y, width, height);
			
			if(model.getYcolumnRight() != null) {
				// Draw y-axis right.
				x = heatMapTL.x + heatMapSize.width;
				y = heatMapTL.y;
				width = axisThickness;
				height = heatMapSize.height + 2;
				chartGraphics.fillRect(x, y, width, height);
			}
		}
	}

	private void drawXValues(Graphics2D chartGraphics) {
		if (drawXTicks && model.getXcolumns() == null) {
			return;
		}
		
		chartGraphics.setColor(axisValuesColour);
		
		for (int i=0; i < model.getXcolumns().length; i++) {
			if (i % xAxisValuesFrequency != 0) {
				continue;
			}
			
			String xValueStr = model.getXcolumns()[i].toString();
			
			chartGraphics.setFont(axisValuesFont);
			FontMetrics metrics = chartGraphics.getFontMetrics();
			
			int valueWidth = metrics.stringWidth(xValueStr);
			
			if (xValuesHorizontal) {
				// Draw the value with whatever font is now set.
				int valueXPos = (i * cellSize.width) + ((cellSize.width / 2) - (valueWidth / 2));
				valueXPos += heatMapTL.x;
				int valueYPos = heatMapBR.y + metrics.getAscent() + 1;
				
				chartGraphics.drawString(xValueStr, valueXPos, valueYPos);
			} else {
				int valueXPos = heatMapTL.x + (i * cellSize.width) + ((cellSize.width / 2) + (xAxisValuesHeight / 2));
				int valueYPos = heatMapBR.y + axisThickness + valueWidth;
				
				// Create 270 degree rotated transform.
				AffineTransform transform = chartGraphics.getTransform();
				AffineTransform originalTransform = (AffineTransform) transform.clone();
				transform.rotate(Math.toRadians(270), valueXPos, valueYPos);
				chartGraphics.setTransform(transform);
				
				// Draw the string.
				chartGraphics.drawString(xValueStr, valueXPos, valueYPos);
				
				// Revert to original transform before rotation.
				chartGraphics.setTransform(originalTransform);
			}
		}
	}
	
	private void drawYValues(Graphics2D chartGraphics) {
		if (drawYTicks && model.getYcolumnLeft() == null) {
			return;
		}
		
		chartGraphics.setColor(axisValuesColour);
		
		for (int i=0; i<model.getYcolumnLeft().length; i++) {
			if (i % yAxisValuesFrequency != 0) {
				continue;
			}
			
			String yValueStrLeft = model.getYcolumnLeft()[i].toString();
			
			chartGraphics.setFont(axisValuesFont);
			FontMetrics metrics = chartGraphics.getFontMetrics();
			
			int valueWidth = metrics.stringWidth(yValueStrLeft);
			
			// Draw the value with whatever font is now set.
			int valueXPos = margin + yAxisLabelSizeLeft.height + (yAxisValuesWidthMaxLeft - valueWidth);
			int valueYPos = heatMapTL.y + (i * cellSize.height) + (cellSize.height/2) + (yAxisValuesAscentLeft/2);
			
			chartGraphics.drawString(yValueStrLeft, valueXPos, valueYPos);
			

			if(model.getYcolumnRight() != null) {
				String yValueStrRight = model.getYcolumnRight()[i].toString();			
				chartGraphics.drawString(yValueStrRight, heatMapTL.x + heatMapSize.width + axisThickness , valueYPos); 
			}
		}
	}
	
	private void drawLegend(Graphics2D chartGraphics) {
        if (drawLegend)
        {
        	
            for (int y = 0; y < chartSize.height - 61; y++)
            {
                int yStart = chartSize.height - 31 - (int) Math.ceil(y * ((chartSize.height - 60) / (colors.length * 1.0)));
                yStart = chartSize.height - 31 - y;
                chartGraphics.setColor(colors[(int) ((y / (double) (chartSize.height - 60)) * (colors.length * 1.0))]);
                
                if(model.getYcolumnRight() == null)
                	yAxisValuesWidthMaxRight = 0;
                
                if(drawYTitleRight)     
                	chartGraphics.fillRect(heatMapTL.x + heatMapSize.width + yAxisValuesWidthMaxRight + 23, yStart, 9, 1);
                else
                	chartGraphics.fillRect(heatMapTL.x + heatMapSize.width + yAxisValuesWidthMaxRight + 10, yStart, 9, 1);
            }
        }
	}
	
    /**
     * The overridden painting method, now optimized to simply draw the data
     * plot to the screen, letting the drawImage method do the resizing. This
     * saves an extreme amount of time.
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        int width = this.getWidth();
        int height = this.getHeight();
                
        this.setOpaque(true);

        // clear the panel
        g2d.setColor(bg);
        g2d.fillRect(0, 0, width, height);
        
        if(view2.getAll2().isSelected() == true) {
        	this.cellSize = new Dimension(10, 10);
        	this.axisLabelsFont = new Font("Arial", Font.PLAIN, 10);
			this.axisValuesFont = new Font("Arial", Font.PLAIN, 10);
        }else {
        	this.cellSize = new Dimension(15, 15);
			this.axisLabelsFont = new Font("Arial", Font.PLAIN, 12);
			this.axisValuesFont = new Font("Arial", Font.PLAIN, 13);
        }
        
        BufferedImage chartImage = null;
        
        if( model.isUpdated() == true) {
			measureComponents();
			updateCoordinates();
	        
			chartImage = new BufferedImage(chartSize.width, chartSize.height, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D chartGraphics = chartImage.createGraphics();
			
			// Use anti-aliasing where ever possible.
			chartGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			chartGraphics.setColor(backgroundColour);
			chartGraphics.fillRect(0, 0, chartSize.width, chartSize.height);
			
			drawTitle(chartGraphics);
			drawData(chartGraphics);
			drawXLabel(chartGraphics);
			drawYLabelLeft(chartGraphics);
			drawYLabelRight(chartGraphics);
			drawAxisBars(chartGraphics);
			drawXValues(chartGraphics);
			drawYValues(chartGraphics);
			
			drawLegend(chartGraphics);
			        
	        model.setImg(chartImage);
	        model.setUpdated(false);
        }else {
        	chartImage = model.getImg();
        }
        
        // The data plot itself is drawn with 1 pixel per data point, and the
        // drawImage method scales that up to fit our current window size. This
        // is very fast, and is much faster than the previous version, which 
        // redrew the data plot each time we had to repaint the screen.
        g2d.drawImage(chartImage,
        			  width/2 - chartImage.getWidth()/2,
        			  height/2 - chartImage.getHeight()/2,
                      chartImage.getWidth(), chartImage.getHeight(),
                      null);
        
        this.setPreferredSize(new Dimension(chartImage.getWidth(), chartImage.getHeight()));
        this.revalidate();
    }
}
