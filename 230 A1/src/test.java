import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class test extends JPanel {

  public void paint(Graphics g) {
    g.setColor (Color.red);
    g.drawRect (0,0,100,100);
    g.clipRect (25, 25, 50, 50);
    g.drawLine (0,100,100,0);
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.add(new test());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(20,20, 500,500);
    frame.setVisible(true);
  }
}
