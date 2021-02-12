import java.awt.Color;

/**
 *
 * <p>There are a number of defined gradient types (look at the static fields),
 * but you can create any gradient you like by using either of the following functions:
 * <ul>
 *   <li>public static Color[] createMultiGradient(Color[] colors, int numSteps)</li>
 *   <li>public static Color[] createGradient(Color one, Color two, int numSteps)</li>
 * </ul>
 * You can then assign an arbitrary Color[] object to the HeatMap as follows:
 * <pre>myHeatMap.updateGradient(Gradient.createMultiGradient(new Color[] {Color.red, Color.white, Color.blue}, 256));</pre>
 * </p>
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

public class Gradient
{
    /**
     * Produces a gradient using the University of Minnesota's school colors, from maroon (low) to gold (high)
     */
    public final static Color[] GRADIENT_MAROON_TO_GOLD = createGradient(new Color(0xFF, 0xFF, 0x00), new Color(0xA0, 0x00, 0x00), 500);

    /**
     * Produces a gradient from blue (low) to red (high)
     */
    public final static Color[] GRADIENT_BLUE_TO_RED = createGradient(Color.BLUE, Color.RED, 500);

    /**
     * Produces a gradient from black (low) to white (high)
     */
    public final static Color[] GRADIENT_WHITE_TO_BLACK = createGradient(Color.WHITE, Color.BLACK, 500);
    
    /**
     *Produces a gradient from red (low) to green (high)
     */
    public final static Color[] GRADIENT_GREEN_TO_RED = createGradient(Color.green, Color.red, 500);

    /**
     *Produces a gradient through green, yellow, orange, red
     */
    public final static Color[] GRADIENT_GREEN_YELLOW_ORANGE_RED = createMultiGradient(new Color[]{Color.green, Color.yellow, Color.orange, Color.red}, 500);

    /**
     *Produces a gradient through the rainbow: violet, blue, green, yellow, orange, red
     */
    public final static Color[] GRADIENT_RAINBOW = createMultiGradient(new Color[]{new Color(181, 32, 255), Color.blue, Color.green, Color.yellow, Color.orange, Color.red}, 500);
    
    /**
     *Produces a gradient for hot things (black, red, orange, yellow, white)
     */
    public final static Color[] GRADIENT_HOT = createMultiGradient(new Color[]{ Color.white,Color.yellow, Color.orange,Color.red, new Color(87, 0, 0), Color.black}, 500);

    /**
     *Produces a different gradient for hot things (black, brown, orange, white)
     */
    public final static Color[] GRADIENT_HEAT = createMultiGradient(new Color[]{Color.white, new Color(255, 150, 38), new Color(192, 23, 0), new Color(105, 0, 0), Color.black}, 500);

    /**
     *Produces a gradient through red, orange, yellow
     */
    public final static Color[] GRADIENT_YOR = createMultiGradient(new Color[]{Color.yellow, Color.orange, Color.red}, 500);
    
    
    public final static Color[] GRADIENT_WHITE_TO_BLUE = createGradient(Color.WHITE, Color.BLUE, 500);

    public final static Color[] GRADIENT_PLANET_HEART = createMultiGradient(new Color[]{Color.blue, Color.white, Color.gray, Color.green, Color.yellow , Color.orange, Color.black}, 500);
    
    public final static Color[] GRADIENT_HEATED_METAL = createMultiGradient(new Color[]{ Color.white, Color.yellow, Color.red, new Color(128,0,128), Color.black}, 500);
    
    public final static Color[] GRADIENT_DEEP_SEA = createMultiGradient(new Color[]{ new Color(0, 250, 250), new Color(23, 173, 203), new Color(46, 100, 158), new Color(24, 53, 103), Color.black}, 500);

    public final static Color[] GRADIENT_BU_GN_YI = createMultiGradient(new Color[]{ new Color(254, 255, 221), new Color(100, 180, 194), new Color(14, 27, 84)}, 500);
    
    public final static Color[] GRADIENT_RED = createMultiGradient(new Color[]{new Color(253, 246, 240), new Color(234, 115, 84), new Color(94, 14, 18)}, 500);
    
    public final static Color[] GRADIENT_BLUE = createMultiGradient(new Color[]{new Color(248, 251, 254) , new Color(122, 173, 211), new Color(20, 46, 104)}, 500);
    
    public final static Color[] GRADIENT_DIVERGING = createMultiGradient(new Color[]{new Color(79, 116, 176), new Color(181, 215, 231), new Color(254, 254, 198), new Color(242, 117, 110), new Color(198, 64, 49)}, 500);

    public final static Color[] GRADIENT_DIVERGING2 = createMultiGradient(new Color[]{ new Color(185, 99, 167), new Color(240, 240, 240),  new Color(77, 169, 173),}, 500);
    
    public final static Color[] GRADIENT_PINK_GREEN = createMultiGradient(new Color[]{new Color(185, 99, 167), new Color(249, 252, 229), new Color(122, 130, 63)}, 500);
    
    public final static Color[] GRADIENT_YELLOW_TO_PURPLE = createMultiGradient(new Color[]{new Color(241, 233, 127), new Color(68, 36, 115)}, 500);

    public final static Color[] GRADIENT_ORANGE_PINK_GREY = createMultiGradient(new Color[]{ new Color(220, 218, 217), new Color(202, 111, 188), new Color(254, 253, 115),  new Color(245, 192, 106)}, 500);

    public final static Color[] GRADIENT_BLUE_YELLOW_RED = createMultiGradient(new Color[]{new Color(87, 160, 229), new Color(239, 234, 122), new Color(225, 105, 101)}, 500);

    public final static Color[] GRADIENT_RED_YELLOW_BLUE = createMultiGradient(new Color[]{new Color(24, 64, 126) , new Color(251, 254, 173),  new Color(236, 89, 107)}, 500);

    public final static Color[] GRADIENT_BLUE_BROWN_RED = createMultiGradient(new Color[]{  new Color(73, 141, 176), new Color(45, 83, 108), new Color(237, 222, 183), new Color(230, 74, 41)}, 500);

    public final static Color[] GRADIENT_GREEN_TO_ORANGE = createMultiGradient(new Color[]{Color.green, Color.orange}, 500);
   
    public final static Color[] GRADIENT_PAIRD = createMultiGradient(new Color[]{new Color(172, 204, 225), new Color(59, 118, 175), new Color(187, 222, 146), new Color(85, 157, 62), new Color(238, 158, 156), new Color(209, 56, 44), new Color(244, 194, 122), new Color(239, 134, 51), new Color(198, 178, 211), new Color(100, 63, 150), new Color(165, 93, 52)}, 500);

    public final static Color[] GRADIENT_ACCENT = createMultiGradient(new Color[]{new Color(144, 199, 134), new Color(187, 174, 209), new Color(243, 194, 142), new Color(254, 252, 116), new Color(69, 107, 169), new Color(220, 64, 127), new Color(178, 98, 42), new Color(102, 103, 101)}, 500);

    public final static Color[] GRADIENT_ROCKET = createMultiGradient(new Color[]{new Color(224, 215, 74), new Color(187, 54, 36), new Color(40, 48, 68), new Color(29, 30, 29)}, 500);

    
    /**
     * Creates an array of Color objects for use as a gradient, using a linear 
     * interpolation between the two specified colors.
     * @param one Color used for the bottom of the gradient
     * @param two Color used for the top of the gradient
     * @param numSteps The number of steps in the gradient. 250 is a good number.
     */
    public static Color[] createGradient(final Color one, final Color two, final int numSteps)
    {
        int r1 = one.getRed();
        int g1 = one.getGreen();
        int b1 = one.getBlue();
        int a1 = one.getAlpha();

        int r2 = two.getRed();
        int g2 = two.getGreen();
        int b2 = two.getBlue();
        int a2 = two.getAlpha();

        int newR = 0;
        int newG = 0;
        int newB = 0;
        int newA = 0;

        Color[] gradient = new Color[numSteps];
        double iNorm;
        for (int i = 0; i < numSteps; i++)
        {
            iNorm = i / (double)numSteps; //a normalized [0:1] variable
            newR = (int) (r1 + iNorm * (r2 - r1));
            newG = (int) (g1 + iNorm * (g2 - g1));
            newB = (int) (b1 + iNorm * (b2 - b1));
            newA = (int) (a1 + iNorm * (a2 - a1));
            gradient[i] = new Color(newR, newG, newB, newA);
        }

        return gradient;
    }

    /**
     * Creates an array of Color objects for use as a gradient, using an array of Color objects. It uses a linear interpolation between each pair of points. The parameter numSteps defines the total number of colors in the returned array, not the number of colors per segment.
     * @param colors An array of Color objects used for the gradient. The Color at index 0 will be the lowest color.
     * @param numSteps The number of steps in the gradient. 250 is a good number.
     */
    public static Color[] createMultiGradient(Color[] colors, int numSteps)
    {
        //we assume a linear gradient, with equal spacing between colors
        //The final gradient will be made up of n 'sections', where n = colors.length - 1
        int numSections = colors.length - 1;
        int gradientIndex = 0; //points to the next open spot in the final gradient
        Color[] gradient = new Color[numSteps];
        Color[] temp;

        if (numSections <= 0)
        {
            throw new IllegalArgumentException("You must pass in at least 2 colors in the array!");
        }

        for (int section = 0; section < numSections; section++)
        {
            //we divide the gradient into (n - 1) sections, and do a regular gradient for each
            temp = createGradient(colors[section], colors[section+1], numSteps / numSections);
            for (int i = 0; i < temp.length; i++)
            {
                //copy the sub-gradient into the overall gradient
                gradient[gradientIndex++] = temp[i];
            }
        }

        if (gradientIndex < numSteps)
        {
            //The rounding didn't work out in our favor, and there is at least
            // one unfilled slot in the gradient[] array.
            //We can just copy the final color there
            for (/* nothing to initialize */; gradientIndex < numSteps; gradientIndex++)
            {
                gradient[gradientIndex] = colors[colors.length - 1];
            }
        }

        return gradient;
    }
}
