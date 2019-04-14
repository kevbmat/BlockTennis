import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import java.util.ArrayList;

public class BlockTennis extends JFrame implements KeyListener, ActionListener{
	private int paddleWidth = 20; // X dimension for paddles
	private int paddleHeight = 110; // Y dimension for paddles
	private int paddleSpeed = 5;
	private int width = 1800;    // X dimension for window
	private int height = 750;   // Y dimension for window
	private int paddleOnePos = 0; // X dimension for paddles
	private int paddleTwoPos = 0; // X dimension for paddles
	private Timer timer;
	private int delay = 15;
	ArrayList<Integer> activeKeys = new ArrayList<Integer>();
    //private StatPanel stats;
    //private GamePanel court;
    public BlockTennis() {
        // displaying the title
        setTitle("Block Tennis");
        // adding a border layout for different panels
        setLayout(new BorderLayout());
        timer = new Timer(delay, this);
        timer.start();
        addKeyListener(this);
        pack();
        setSize(width, height);
        setVisible(true);
    }
    public void paint(Graphics g)
    {
       super.paint(g);
       //g.drawRect(paddleSizeX, paddleSizeY, (int) (width*.40), height/2);
       g.drawRect((int) (width*.30), ((int) (height/2))-(paddleHeight/2)+paddleOnePos, paddleWidth, paddleHeight);
       g.drawRect((int) (width*.70)-paddleWidth, ((int) (height/2))-(paddleHeight/2)+paddleTwoPos, paddleWidth, paddleHeight);
    }
    public static void main(String[] args) {
        new BlockTennis();
    }
	@Override
	public void keyPressed(KeyEvent e) {
		boolean foundKey = false;
		/*if (e.getKeyCode() == KeyEvent.VK_P){
			paddleTwoPos = paddleTwoPos - paddleSpeed;
			repaint();
		}else if (e.getKeyCode() == KeyEvent.VK_L) {
			paddleTwoPos = paddleTwoPos + paddleSpeed;
			repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			paddleOnePos = paddleOnePos - paddleSpeed;
			repaint();
		}else if (e.getKeyCode() == KeyEvent.VK_A) {
			paddleOnePos = paddleOnePos + paddleSpeed;
			repaint();
		}*/
		for(int i = 0; i < activeKeys.size(); i++) {
			if(activeKeys.get(i) == e.getKeyCode()) {
				foundKey = true;
			}
		}
		if (foundKey == false) {
			activeKeys.add(e.getKeyCode());
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		for(int i = 0; i < activeKeys.size(); i++) {
			if(activeKeys.get(i) == e.getKeyCode()) {
				activeKeys.remove(i);
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		//System.out.print(e.getKeyCode());
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Search through arraylist for active keys and move paddles
		for(int i = 0; i < activeKeys.size(); i++) {
			if (activeKeys.get(i) == KeyEvent.VK_P) {
				paddleTwoPos = paddleTwoPos - paddleSpeed;
			}else if (activeKeys.get(i) == KeyEvent.VK_L) {
				paddleTwoPos = paddleTwoPos + paddleSpeed;
			}else if (activeKeys.get(i) == KeyEvent.VK_Q) {
				paddleOnePos = paddleOnePos - paddleSpeed;
			}else if (activeKeys.get(i) == KeyEvent.VK_A) {
				paddleOnePos = paddleOnePos + paddleSpeed;
			}
		}
		repaint();
	}
}
