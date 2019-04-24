import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TennisPanel extends JPanel implements KeyListener, ActionListener {
    /* WINDOW VARS */
	private int width = 1800;    // X dimension for window
	private int height = 750;   // Y dimension for window
	
	/* PADDLE VARS */
	private int paddleWidth = 20; // X dimension for paddles
	private int paddleHeight = 110; // Y dimension for paddles
	private int paddleSpeed = 2;
	private int paddleOnePos = 0; // X dimension for paddles
	private int paddleTwoPos = 0; // X dimension for paddles
	
	/* BALL VARS */
	private int x = 0;		// x position
	private int y = 0;		// y position
	private int radius = 10;	// ball radius

	private int dx = 2;		// increment amount (x coord)
	private int dy = 2;		// increment amount (y coord)
	
	/* BRICK VARS */
	ArrayList<Block> playerOneBricks = new ArrayList<>();
	ArrayList<Block> playerTwoBricks = new ArrayList<>();

	/* BRICK SIZE */
	private int brickSize = 40;
	
	/* TIMER VARS */
	private Timer timer;
	private int delay = 5;
	ArrayList<Integer> activeKeys = new ArrayList<Integer>();

	public TennisPanel() {
		timer = new Timer(delay, this);
		timer.start();		// start the timer

		// adding blocks to the each players block arraylist
		playerOneBricks.add(new Block(200, 350, 20, 300));
	}

	public void actionPerformed(ActionEvent arg) {
		// Search through arraylist for active keys and move paddles
		for (int i = 0; i < activeKeys.size(); i++) {
			if (activeKeys.get(i) == KeyEvent.VK_P) {
				if (paddleTwoPos > (-height/2) + (paddleHeight/2)) {
					paddleTwoPos = paddleTwoPos - paddleSpeed;
				}
			} else if (activeKeys.get(i) == KeyEvent.VK_L) {
				if (paddleTwoPos < (height/2) - (paddleHeight - 5)) {
					paddleTwoPos = paddleTwoPos + paddleSpeed;
				}
			} else if (activeKeys.get(i) == KeyEvent.VK_Q) {
				if (paddleOnePos > (-height/2) + (paddleHeight/2)) {
					paddleOnePos = paddleOnePos - paddleSpeed;
				}
			} else if (activeKeys.get(i) == KeyEvent.VK_A) {
				if (paddleOnePos < (height/2) - (paddleHeight - 5)) {
					paddleOnePos = paddleOnePos + paddleSpeed;
				}
			}
		}
		repaint();
	}
	
	// check for if the ball is within a certain x range
	public int getBlockX(int blockWidth, int blockPosition, String loc){
		if (loc.equals("left")) {
			return blockPosition-(blockWidth/2);
		} else if(loc.equals("right")) {
			return blockPosition+(blockWidth/2);
		}
		return 0;
	}

	//check for if the ball is within a certan y range
	public int getBlockY(int blockHeight, int blockPosition, String loc) {
		if (loc.equals("top")) {
			return blockPosition-(blockHeight/2);
		} else if (loc.equals("bottom")) {
			return blockPosition+(blockHeight/2);
		}
		return 0;
	}

	// checks if a certain block has been hit
	public boolean hitBlock(Block b) {
		return x >= getBlockX(b.width, b.centerX, "left") && x <= getBlockX(b.width, b.centerX, "right") && 
		y >= getBlockY(b.height, b.centerY, "top") && y <= getBlockY(b.height, b.centerY, "bottom") &&
		!b.isHit;
	}

	public void paintComponent( Graphics g ) {
		super.paintComponent( g );

		// drawing all bricks out onto the screen
		g.setColor(Color.BLACK);
		for (int i = 0; i < playerOneBricks.size(); i++) {
			if (!(playerOneBricks.get(i).isHit)) {
				g.fillRect(playerOneBricks.get(i).topLeftX, playerOneBricks.get(i).topLeftY, playerOneBricks.get(i).width, playerOneBricks.get(i).height);
			}
		}

		//g.drawRect(paddleSizeX, paddleSizeY, (int) (width*.40), height/2);
		g.drawRect((int) (width*.30), ((int) (height/2))-(paddleHeight/2)+paddleOnePos, paddleWidth, paddleHeight);
		g.drawRect((int) (width*.70)-paddleWidth, ((int) (height/2))-(paddleHeight/2)+paddleTwoPos, paddleWidth, paddleHeight);
		g.setColor(Color.GREEN);
		
		if (x < radius)
			dx = Math.abs(dx);
		if (x > getWidth() - radius)	
			dx = -Math.abs(dx);
		if (y < radius)			
			dy = Math.abs(dy);
		if (y > getHeight() - radius)
			dy = -Math.abs(dy);

		// messing around with block collisions for my own learning
		/*
		if (x >= getBlockX(100, 850, "left") && x <= getBlockX(100, 850, "right") && 
		y >= getBlockY(500, 350, "top") && y <= getBlockY(500, 350, "bottom")) {
			if (dx >= 0) {
				dx = -Math.abs(dx);
			} else {
				dx = Math.abs(dx);
			}
			if (dy >= 0) {
				dy = Math.abs(dx);
			} else {
				dy = -Math.abs(dx);
			}
			g.clearRect(800, 100, 100, 500);
		}
		*/

		//Player 1 Paddle collision
		if ((x >= getBlockX(paddleWidth, (int) (width*.30)-paddleWidth, "left")) && (x <= getBlockX(paddleWidth, (int) (width*.30)+paddleWidth, "right")) && 
		(y >= getBlockY(paddleHeight, ((height/2)+paddleOnePos), "top")) && (y <= getBlockY(paddleHeight, ((height/2)+paddleOnePos), "bottom"))) {
			if (dx >= 0) {
				dx = -Math.abs(dx);
			} else {
				dx = Math.abs(dx);
			}
			if (dy >= 0) {
				dy = Math.abs(dx);
			} else {
				dy = -Math.abs(dx);
			}
		}

		//Player 2 Paddle collision
		if ((x >= getBlockX(paddleWidth, (int) (width*.70)-paddleWidth, "left")) && (x <= getBlockX(paddleWidth, (int) (width*.70), "right")) && 
		(y >= getBlockY(paddleHeight, ((height/2)+paddleTwoPos), "top")) && (y <= getBlockY(paddleHeight, ((height/2)+paddleTwoPos), "bottom"))) {
			if (dx >= 0) {
				dx = -Math.abs(dx);
			} else {
				dx = Math.abs(dx);
			}
			if (dy >= 0) {
				dy = Math.abs(dx);
			} else {
				dy = -Math.abs(dx);
			}
		}

		// check block colision
		for (int i = 0; i < playerOneBricks.size(); i++) {
			if (hitBlock(playerOneBricks.get(i))) {
				g.clearRect(playerOneBricks.get(i).topLeftX, playerOneBricks.get(i).topLeftY, playerOneBricks.get(i).width, playerOneBricks.get(i).height);
				playerOneBricks.get(i).isHit = true;
			}
		}

		// adjust ball position
		x += dx;
		y += dy;
		g.fillOval(x - radius, y - radius, radius*2, radius*2);
    }

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

	public void keyReleased(KeyEvent e) {
		for (int i = 0; i < activeKeys.size(); i++) {
			if (activeKeys.get(i) == e.getKeyCode()) {
				activeKeys.remove(i);
			}
		}
	}
    
	public void keyTyped(KeyEvent e) {
		//System.out.print(e.getKeyCode());
	}
}