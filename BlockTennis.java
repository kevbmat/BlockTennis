import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class BlockTennis {
	public static void main(String[] args) {
        JFrame frame = new JFrame( "Block Tennis" );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        TennisPanel tp = new TennisPanel();
        tp.setLayout(new BorderLayout());
        frame.add(tp);
        frame.setSize(1800, 750);
        tp.addKeyListener(tp);
        tp.setFocusable(true);
        tp.requestFocusInWindow();
        //frame.add(backgroundImage);
        frame.setVisible(true);
    }
}