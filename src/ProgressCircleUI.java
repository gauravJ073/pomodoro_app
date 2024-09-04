import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicProgressBarUI;

/*BasicProgressBarUI is responsible for the visual representation of a progress bar in Java Swing*/
class ProgressCircleUI extends BasicProgressBarUI {

    /*
    * Overriding getPreferredSize to ensure that the progress bar is rendered as a square,
    * regardless of its original dimensions.
    * This is because a circular progress bar should have equal width and height.
    */
    @Override
    public Dimension getPreferredSize(JComponent c) {
        Dimension d = super.getPreferredSize(c); //Retrieves the default preferred size of the component.
        int v = Math.max(d.width, d.height); // Ensures that the component is a square by taking the larger of the width or height.
        d.setSize(v, v);//Sets both the width and height to the larger value.
        return d;
    }

    @Override public void paint(Graphics g, JComponent c) {
        // retrieves the Insets object that defines the space between the progress bar's border and its content area.
        Insets b = progressBar.getInsets();
        /*
        * Insets typically contains four values:
        * top, left, bottom, and right,
        * which represent the size of the border or padding on each side of the component
        * */

        // This gives you the width of the area inside the borders where the progress can be drawn.
        int barRectWidth  = progressBar.getWidth()  - b.right - b.left;

        //This gives you the height of the drawable area inside the borders.
        int barRectHeight = progressBar.getHeight() - b.top - b.bottom;

        // ensures that the calculated barRectWidth and barRectHeight are positive values.
        if (barRectWidth <= 0 || barRectHeight <= 0) {
            return;
        }
        /*
        * If the progress bar is too small (e.g., its size is set to zero or it's collapsed),
        * trying to draw it would be pointless and might lead to errors or unexpected behavior.
        * Therefore, the method exits early if there's no valid space to draw.
        * */

        // draw the cells
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(progressBar.getForeground()); //Sets the color for the progress indicator.

        /*
        degree: Represents the angle corresponding to the completion percentage.
        sz: Determines the size of the square that will contain the circle.
        cx and cy: Calculate the center of the circle.
        or and ir: Represent the outer and inner radii of the circular progress bar.
        */
        double degree = 360 * progressBar.getPercentComplete();
        double sz = Math.min(barRectWidth, barRectHeight);
        double cx = b.left + barRectWidth  * .5;
        double cy = b.top  + barRectHeight * .5;
        double or = sz * .5;
        double ir = or * .5; //or - 20;

        /*
        * inner: This is a small circle that will be subtracted from the outer arc to create the progress indicator.
        * outer: This is an arc that represents the progress.
        * */
        Shape inner = new Ellipse2D.Double(cx - ir, cy - ir, ir * 2, ir * 2);
        Shape outer = new Arc2D.Double(
                cx - or, cy - or, sz, sz, 90 - degree, degree, Arc2D.PIE);

        /*The shapes are combined using the Area class,
        and the inner shape is subtracted from the outer shape to create the final progress arc.*/
        Area area = new Area(outer);
        area.subtract(new Area(inner));
        g2.fill(area);
        g2.dispose();

        // Deal with possible text painting
        if (progressBar.isStringPainted()) {
            paintString(g, b.left, b.top, barRectWidth, barRectHeight, 0, b);
        }
    }
}
