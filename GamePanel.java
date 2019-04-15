import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
    private int width = 1800;    // X dimension for window
	private int height = 750;   // Y dimension for window
    private int paddleWidth = 20; // X dimension for paddles
	private int paddleHeight = 110; // Y dimension for paddles
    private int paddleSpeed = 5;
    private int paddleOnePos = 0; // X dimension for paddles
    private int paddleTwoPos = 0; // X dimension for paddles
    ArrayList<Integer> activeKeys = new ArrayList<Integer>();
    private Timer timer;
    private int delay = 15;
    
    public GamePanel(int width, int height) {
        this.width = width;
        this.height = height;
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawRect(paddleSizeX, paddleSizeY, (int) (width*.40), height/2);
        g.drawRect((int) (width*.30), ((int) (height/2))-(paddleHeight/2)+paddleOnePos, paddleWidth, paddleHeight);
        g.drawRect((int) (width*.70)-paddleWidth, ((int) (height/2))-(paddleHeight/2)+paddleTwoPos, paddleWidth, paddleHeight);
    }

    @Override
	public void keyPressed(KeyEvent e) {
		boolean foundKey = false;
		for (int i = 0; i < activeKeys.size(); i++) {
			if (activeKeys.get(i) == e.getKeyCode()) {
				foundKey = true;
			}
		}
		if (foundKey == false) {
			activeKeys.add(e.getKeyCode());
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		for (int i = 0; i < activeKeys.size(); i++) {
			if (activeKeys.get(i) == e.getKeyCode()) {
				activeKeys.remove(i);
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		//System.out.print(e.getKeyCode());
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		// Search through arraylist for active keys and move paddles
		for (int i = 0; i < activeKeys.size(); i++) {
			if (activeKeys.get(i) == KeyEvent.VK_P) {
				paddleTwoPos = paddleTwoPos - paddleSpeed;
			} else if (activeKeys.get(i) == KeyEvent.VK_L) {
				paddleTwoPos = paddleTwoPos + paddleSpeed;
			} else if (activeKeys.get(i) == KeyEvent.VK_Q) {
				paddleOnePos = paddleOnePos - paddleSpeed;
			} else if (activeKeys.get(i) == KeyEvent.VK_A) {
				paddleOnePos = paddleOnePos + paddleSpeed;
			}
		}
		repaint();
	}
}

