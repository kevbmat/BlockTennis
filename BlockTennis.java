import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.*;

public class BlockTennis extends JFrame {
    private StatPanel stats;
    private GamePanel court;
    public BlockTennis() {
        // displaying the title
        setTitle("Block Tennis");
        // adding a border layout for different panels
        setLayout(new BorderLayout());
        pack();
        setSize(500, 1000);
        setVisible(true);
    }

    public static void main(String[] args) {
        new BlockTennis();
    }
}