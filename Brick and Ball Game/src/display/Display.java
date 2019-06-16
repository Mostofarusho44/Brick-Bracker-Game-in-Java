
package display;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import oracle.jrockit.jfr.JFR;


public class Display {
    private String title;
    private int width;
    private int height;
    public JFrame frame;
    public Canvas canvas;
    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        createDisplay();
    }
    public void createDisplay()
    {
        frame=new JFrame(title);
        frame.setSize(width,height);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        canvas=new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setBackground(Color.PINK);
        canvas.setFocusable(false);
        frame.add(canvas);
        frame.pack();
    }
    
    
}
