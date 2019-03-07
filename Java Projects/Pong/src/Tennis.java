import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tennis extends Applet implements Runnable, KeyListener{
	
	final int WIDTH = 700, HEIGHT = 500;
	static int score;
	Thread thread;
	HumanPaddle p1;
	Ball b1;
	AIPaddle p2;
	boolean gameStarted;
	
	public void init() {
		this.resize(WIDTH, HEIGHT);
		score = 0;
		gameStarted = false;
		this.addKeyListener(this);
		p1 = new HumanPaddle(1);
		b1 = new Ball();
		p2 = new AIPaddle(2, b1);
		thread = new Thread(this);
		thread.start(); 
		
		
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.RED);
		g.drawString("Score: " + score,  322, 40);
		//if ball go out of x line = game over
		if (b1.getX() < -10 || b1.getX() > 710) {
			g.setColor(Color.RED);
			g.drawString("Game Over", 310, 200);
		}
		else {
			p1.draw(g);
			b1.draw(g);
			p2.draw(g);
		}
		
		if (!gameStarted) {
			g.setColor(Color.WHITE);
			g.drawString("Tennis", 322, 100);
			g.drawString("Press Enter to Begin", 290, 130);
		}
	}
	
	public void update(Graphics g) {
		paint(g);
	}

	public void run() {
		for(;;) {
			
			if (gameStarted) {
				
				p1.move();
				p2.move();
				b1.move();
				b1.checkPaddleCollision(p1, p2);
			}
			repaint();
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			p1.setUpAccel(true);
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			p1.setDownAccel(true);
		}
		else if (e.getKeyCode() == KeyEvent.VK_ENTER){
			gameStarted = true;
		}
		
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			p1.setUpAccel(false);
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			p1.setDownAccel(false);
		}
		
	}

	public void keyTyped(KeyEvent e) {
		
		
	}
	
}
