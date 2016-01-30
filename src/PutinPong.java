import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class PutinPong extends Applet implements Runnable, KeyListener {
	Image putin;
	int ballX = 500, ballY = 250;
	int ballSpeedX = 1, ballSpeedY = -1;
	int paddleY = 250;
	int paddleSpeedY = 0;
	int lives = 10;
	int score = 0;
	public void init() {
		setSize(1000,500);
		setBackground(Color.WHITE);
		addKeyListener(this);
		try {
			URL url = new URL(getDocumentBase(), "putin.png");
			putin = ImageIO.read(url);
		}
		catch (IOException e) {}
	}
	public void start() {
		Thread th = new Thread(this);
		th.start();
	}
	public void stop() {}
	public void paint(Graphics g) {
		g.setColor(new Color(213, 132, 12));
		g.drawImage(putin, ballX, ballY, this);
		g.drawRect(0, 0, 1009, 499);
		g.fillRect(900, paddleY, 25, 200);
		g.setFont(new Font("Press Start K", Font.PLAIN, 24)); 
		g.setColor(new Color(234, 193, 133));
		g.drawString("Lives: " + lives, 30, 50);
		g.drawString("Score: " + score, 30, 80);
	}
	public void run() {
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		while(true) {
			ballX = ballX + ballSpeedX;
			ballY = ballY + ballSpeedY;
			paddleY = paddleY + paddleSpeedY;
			if (ballX == 0 || ballX == 800 && Math.abs(ballY - (paddleY + 100)) <= 100) {
				if (ballX <= 0) {
					ballSpeedY = ballSpeedY + (int)(Math.random()*2-1);
				}
				ballSpeedX = -ballSpeedX;
			}
			if (ballY == 0 || ballY == 400) {ballSpeedY = -ballSpeedY;}
			if (ballX == 1050) {
				ballX = 75;
				ballY = (int)(Math.random() * 300 + 100);
				lives--;
			}
			score++;
			repaint();
			try {
				Thread.sleep(2);
			}
			catch(InterruptedException Ex) {}
		}
	}
	public void keyTyped(KeyEvent e) {
	}
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 38) { // Up arrow
			if (paddleY >= 100) {
				paddleSpeedY -= 1;
			}
		}
		if (e.getKeyCode() == 40) { // Down arrow
			if (paddleY <= 400) {
				paddleSpeedY += 1;
			}
		}
	}
	public void keyReleased(KeyEvent e) {
		paddleSpeedY = 0;
	}
}
