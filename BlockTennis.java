import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class BlockTennis{
	public static void main(String[] args) {
        JFrame frame = new JFrame( "Block Tennis" );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        TennisPanel tp = new TennisPanel();
        JLabel backgroundImage = new JLabel(new ImageIcon("cyberpunk.jpg"));

        tp.setLayout(new BorderLayout());
        frame.add(tp);
        frame.setSize(1800, 750);
        tp.addKeyListener(tp);
        ArrayList<Integer> activeKeys = new ArrayList<Integer>();
        tp.setFocusable(true);
        tp.requestFocusInWindow();
        //frame.add(backgroundImage);
        frame.setVisible(true);
    }
}
class TennisPanel extends JPanel implements KeyListener, ActionListener{
	/* WINDOW VARS */
	private int width = 1800;    // X dimension for window
	private int height = 750;   // Y dimension for window
	
	/* PADDLE VARS */
	private int paddleWidth = 20; // X dimension for paddles
	private int paddleHeight = 110; // Y dimension for paddles
	private int paddleSpeed = 3;
	private int paddleOnePos = 0; // X dimension for paddles
	private int paddleTwoPos = 0; // X dimension for paddles
	
	/* BALL VARS */
	private int x = 0;		// x position
	private int y = 0;		// y position
	private int radius = 15;	// ball radius

	private int dx = 2;		// increment amount (x coord)
	private int dy = 2;		// increment amount (y coord)
	
	/* BRICK VARS */
	ArrayList<Integer> playerOneBricks = new ArrayList<Integer>();
	ArrayList<Integer> playerTwoBricks = new ArrayList<Integer>();
	
	/* TIMER VARS */
	protected Timer timer;
	private int delay = 5;
	ArrayList<Integer> activeKeys = new ArrayList<Integer>();
    //private StatPanel stats;
    //private GamePanel court;
	public TennisPanel(){
		timer = new Timer(delay, this);
		timer.start();		// start the timer
	}
	public void actionPerformed(ActionEvent arg) {
		// Search through arraylist for active keys and move paddles
		for(int i = 0; i < activeKeys.size(); i++) {
			if (activeKeys.get(i) == KeyEvent.VK_P) {
				if (paddleTwoPos > (-height/2) + (paddleHeight/2)) {
					paddleTwoPos = paddleTwoPos - paddleSpeed;
				}
			}else if (activeKeys.get(i) == KeyEvent.VK_L) {
				if (paddleTwoPos < (height/2) - (paddleHeight - 5)) {
					paddleTwoPos = paddleTwoPos + paddleSpeed;
				}
			}else if (activeKeys.get(i) == KeyEvent.VK_Q) {
				if (paddleOnePos > (-height/2) + (paddleHeight/2)) {
					paddleOnePos = paddleOnePos - paddleSpeed;
				}
			}else if (activeKeys.get(i) == KeyEvent.VK_A) {
				if (paddleOnePos < (height/2) - (paddleHeight - 5)) {
					paddleOnePos = paddleOnePos + paddleSpeed;
				}
			}
		}
		repaint();
	}
	public int getBlockX(int blockWidth, int blockPosition, String loc){
		if (loc.equals("left")) {
			return blockPosition-(blockWidth/2);
		}else if(loc.equals("right")) {
			return blockPosition+(blockWidth/2);
		}
		return 0;
	}
	public int getBlockY(int blockHeight, int blockPosition, String loc) {
		if (loc.equals("top")) {
			return blockPosition-(blockHeight/2);
		}else if(loc.equals("bottom")) {
			return blockPosition+(blockHeight/2);
		}
		return 0;
	}
	public void paintComponent( Graphics g )
    {
		
		super.paintComponent( g );
		//g.drawRect(paddleSizeX, paddleSizeY, (int) (width*.40), height/2);
		g.drawRect((int) (width*.30), ((int) (height/2))-(paddleHeight/2)+paddleOnePos, paddleWidth, paddleHeight);
		g.drawRect((int) (width*.70)-paddleWidth, ((int) (height/2))-(paddleHeight/2)+paddleTwoPos, paddleWidth, paddleHeight);
		g.setColor(Color.blue);
		
		if (x < radius)
			dx = Math.abs(dx);
		if (x > getWidth() - radius)	
			dx = -Math.abs(dx);
		if (y < radius)			
			dy = Math.abs(dy);
		if (y > getHeight() - radius)
			dy = -Math.abs(dy);
		
		//Player 1 Paddle collision
		if((x >= getBlockX(paddleWidth, (int) (width*.30)-paddleWidth, "left")) && (x <= getBlockX(paddleWidth, (int) (width*.30)+paddleWidth, "right")) && (y >= getBlockY(paddleHeight, ((height/2)+paddleOnePos), "top")) && (y <= getBlockY(paddleHeight, ((height/2)+paddleOnePos), "bottom"))) {
			if (dx >= 0) {
				dx = -Math.abs(dx);
			}else {
				dx = Math.abs(dx);
			}
			if (dy >= 0) {
				dy = Math.abs(dx);
			}else {
				dy = -Math.abs(dx);
			}
		}
		//Player 2 Paddle collision
		if((x >= getBlockX(paddleWidth, (int) (width*.70)-paddleWidth, "left")) && (x <= getBlockX(paddleWidth, (int) (width*.70), "right")) && (y >= getBlockY(paddleHeight, ((height/2)+paddleTwoPos), "top")) && (y <= getBlockY(paddleHeight, ((height/2)+paddleTwoPos), "bottom"))) {
			if (dx >= 0) {
				dx = -Math.abs(dx);
			}else {
				dx = Math.abs(dx);
			}
			if (dy >= 0) {
				dy = Math.abs(dx);
			}else {
				dy = -Math.abs(dx);
			}
		}
		// adjust ball position
		x += dx;
		y += dy;
		g.fillOval(x - radius, y - radius, radius*2, radius*2);
    }
	public void keyPressed(KeyEvent e) {
		boolean foundKey = false;
		for(int i = 0; i < activeKeys.size(); i++) {
			if(activeKeys.get(i) == e.getKeyCode()) {
				foundKey = true;
			}
		}
		if (foundKey == false) {
			activeKeys.add(e.getKeyCode());
		}
	}
	public void keyReleased(KeyEvent e) {
		for(int i = 0; i < activeKeys.size(); i++) {
			if(activeKeys.get(i) == e.getKeyCode()) {
				activeKeys.remove(i);
			}
		}
	}
	public void keyTyped(KeyEvent e) {
		//System.out.print(e.getKeyCode());
	}
}
