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
	private int paddleSpeed = 5;
	private int paddleOnePos = 0; // X dimension for paddles
	private int paddleTwoPos = 0; // X dimension for paddles
	
	/* BALL VARS */
	private int x = width / 2;		// x position
	private int y = height / 2;		// y position
	private int radius = 10;	// ball radius

	private int dx = 6;		// increment amount (x coord)
	private int dy = 6;		// increment amount (y coord)
	
	/* BRICK VARS */
	ArrayList<Block> playerOneBricks = new ArrayList<>();
	ArrayList<Block> playerTwoBricks = new ArrayList<>();
	
	/* TIMER VARS */
	private Timer timer;
	private int delay = 5;

	/* KEY VARS */
	ArrayList<Integer> activeKeys = new ArrayList<Integer>();

	public TennisPanel() {
		JOptionPane.showMessageDialog(null, "Welcome to Block Tennis! \nPlayer 1 moves with Q and A\nPlayer 2 moves with P and L\nFirst to break all the other player's blocks wins!");
		levelSelect();
		timer = new Timer(delay, this);
		timer.start();		// start the timer
	}

	public void levelSelect() {
		int option = Integer.parseInt(JOptionPane.showInputDialog("Select Level (1) (2) (3)"));
		if (option < 1) {
			option = 1;
		} else if (option > 3) {
			option = 3;
		}
		switch (option) {
			case 1:
				for (int i = 200; i <= 500; i += 150) {
					playerOneBricks.add(new Block(200, i, 20, 100));
					playerTwoBricks.add(new Block(width - 200, i, 20, 100));
				}
				break;
			case 2:
				break;
			case 3:
				break;
			default:
				System.out.println("Dang we should never have executed this");
		}
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
		y >= getBlockY(b.height, b.centerY, "top") && y <= getBlockY(b.height, b.centerY, "bottom"); 
	}

	// changes the motion of the ball (due to a collision)
	public void ballCollision() {
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

	public void paintComponent( Graphics g ) {
		super.paintComponent(g);
		g.setColor(new Color(60,179,113));
		g.fillRect(0, 0, width, height);
		// drawing all bricks out onto the screen
		g.setColor(Color.BLACK);
		for (int i = 0; i < playerOneBricks.size(); i++) {
			g.fillRect(playerOneBricks.get(i).topLeftX, playerOneBricks.get(i).topLeftY, playerOneBricks.get(i).width, playerOneBricks.get(i).height);
		}
		for (int i = 0; i < playerTwoBricks.size(); i++) {
			g.fillRect(playerTwoBricks.get(i).topLeftX, playerTwoBricks.get(i).topLeftY, playerTwoBricks.get(i).width, playerTwoBricks.get(i).height);
		}

		//g.drawRect(paddleSizeX, paddleSizeY, (int) (width*.40), height/2);
		g.setColor(new Color(205,133,63));
		g.fillRect((int) (width*.30), ((int) (height/2))-(paddleHeight/2)+paddleOnePos, paddleWidth, paddleHeight);
		g.fillRect((int) (width*.70)-paddleWidth, ((int) (height/2))-(paddleHeight/2)+paddleTwoPos, paddleWidth, paddleHeight);
		g.setColor(Color.GREEN);
		
		// checking for if the ball is hitting the game boundaries
		if (x < radius)
			dx = Math.abs(dx);
		if (x > getWidth() - radius)	
			dx = -Math.abs(dx);
		if (y < radius)			
			dy = Math.abs(dy);
		if (y > getHeight() - radius)
			dy = -Math.abs(dy);

		//Player 1 Paddle collision
		if ((x >= getBlockX(paddleWidth, (int) (width*.30)-paddleWidth, "left")) && (x <= getBlockX(paddleWidth, (int) (width*.30)+paddleWidth, "right")) && 
		(y >= getBlockY(paddleHeight, ((height/2)+paddleOnePos), "top")) && (y <= getBlockY(paddleHeight, ((height/2)+paddleOnePos), "bottom"))) {
			ballCollision();
		}

		//Player 2 Paddle collision
		if ((x >= getBlockX(paddleWidth, (int) (width*.70)-paddleWidth, "left")) && (x <= getBlockX(paddleWidth, (int) (width*.70), "right")) && 
		(y >= getBlockY(paddleHeight, ((height/2)+paddleTwoPos), "top")) && (y <= getBlockY(paddleHeight, ((height/2)+paddleTwoPos), "bottom"))) {
			ballCollision();
		}

		// check block colision
		for (int i = 0; i < playerOneBricks.size(); i++) {
			if (hitBlock(playerOneBricks.get(i))) {
				g.clearRect(playerOneBricks.get(i).topLeftX, playerOneBricks.get(i).topLeftY, playerOneBricks.get(i).width, playerOneBricks.get(i).height);
				System.out.println("Player 2 hit a brick!");
				playerOneBricks.remove(i);
				ballCollision();
			}
		}

		for (int i = 0; i < playerTwoBricks.size(); i++) {
			if (hitBlock(playerTwoBricks.get(i))) {
				g.clearRect(playerTwoBricks.get(i).topLeftX, playerTwoBricks.get(i).topLeftY, playerTwoBricks.get(i).width, playerTwoBricks.get(i).height);
				System.out.println("Player 1 hit a brick!");
				playerTwoBricks.remove(i);
				ballCollision();
			}
		}

		// check if either player has won
		if (playerOneBricks.isEmpty()) {
			playAgain("2");
		} else if (playerTwoBricks.isEmpty()) {
			playAgain("1");
		}

		// adjust ball position
		x += dx;
		y += dy;
		g.fillOval(x - radius, y - radius, radius*2, radius*2);
	}
	
	public void playAgain(String winner) {
		timer.stop();
		JOptionPane.showMessageDialog(null, "Player " + winner + "has won!" );
		int playAgain = JOptionPane.showConfirmDialog(null, "Would you like to play Again?", "Game Over", JOptionPane.YES_NO_OPTION);
		switch (playAgain) {
			case JOptionPane.YES_OPTION:
				playerOneBricks.clear();
				playerTwoBricks.clear();
				levelSelect();
				timer.start();
				break;
			default:
				JOptionPane.showMessageDialog(null, "Thanks for Playing!");
				System.exit(0);
		}
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