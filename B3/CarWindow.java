package concurrent_assignment4.B3;





import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class CarWindow extends JFrame {

    CarWorld display;
    JButton addLeft;
    JButton addRight;

    public CarWindow() {
	
	Container c = getContentPane();
	
        c.setLayout(new BorderLayout());
        display = new CarWorld();


        c.add("Center",display);
        addLeft = new JButton("Add pedestrian");
        addRight = new JButton("Add car");
	
        addLeft.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    display.addPeaton();
		}
	    }
				  );

        addRight.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    display.addCar();
		}
	    }
				   );

       
        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout());
        p1.add(addLeft);
        p1.add(addRight);
        c.add("South",p1);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
}
