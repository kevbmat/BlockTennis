import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import java.util.ArrayList;

public class BlockTennis extends JFrame {
	private int width = 1800;    // X dimension for window
	private int height = 750;   // Y dimension for window
    private GamePanel field;
    public BlockTennis() {
        // displaying the title
		setTitle("Block Tennis");
		// initialization of the field
		field = new GamePanel(width, height);
        // adding a border layout for different panels
		setLayout(new BorderLayout());
		add(field, BorderLayout.CENTER);
        pack();
        setSize(width, height);
        setVisible(true);
	}

    public static void main(String[] args) {
        new BlockTennis();
	}
}
