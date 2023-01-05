package dev;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tetris {
	
	//GAME
	ArrayList<Block> placedBlocks = new ArrayList<Block>();
	Block block;
	long prev = System.currentTimeMillis();
	long current = prev;
	
	//DISPLAY
	JFrame window;
	GFXPanel panel;
	int panW = Block.size*10;
	int panH = Block.size*20;
	int REFRESH = 144; //refresh rate
	
	public static void main (String[] args) { new Tetris(); }

	Tetris() {
		//config display window
		window = new JFrame("Tetris");
		panel = new GFXPanel();
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(panel);
		window.pack();
		window.setVisible(true);
		
		setup();
		
		while (true) {

			//MOVE BLOCK DOWN EVERY SECOND
			current = System.currentTimeMillis();
			if (current-prev >= 1000)  {
				moveBlockDown(); //move block down every second
				prev = System.currentTimeMillis();
			}
			
			//CHECK IF BLOCK HAS HIT THE GROUND OR ANOTHER PLACED BLOCK
			checkBlock();
			
			
			try {
				Thread.sleep((int)1000/REFRESH);
			} catch (InterruptedException e) { e.printStackTrace(); }
			
			panel.repaint();
		}
	}
	
	public void setup() {
		block = spawnBlock();
	}
	
	public Block spawnBlock() {
		
		//get position
		int x = panW/2;
		int y = 0;
		
		//get random color
		String colors[] = {"#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF", "#00FFFF"};
		Color color = Color.decode( colors[(int)(Math.random()*6)] );
		
		//initialize block object and add it to arraylist
		Block block = new Block(x,y,color, (int)(Math.random()*7));
		return block;
		
	}
	
	public void moveBlockDown() {
		block.y += Block.size;
	}
	
	public void adjustBlockPosition(String direction) {
		if (direction.equals("DOWN")) {
			block.y += Block.size;
		}
		else if (direction.equals("LEFT")) {
			block.x -= Block.size;
		}
		else if (direction.equals("RIGHT")) {
			block.x += Block.size;
		}
	}
	
	public void rotateBlock(String direction) {
	}
	
	public void checkBlock() {
		
		if (block.y >= panH-Block.size) {
			placedBlocks.add(block);
			block = spawnBlock();
		}
		
		//TODO: check if block is above a placed block
		
	}
	
	@SuppressWarnings("serial")
	class GFXPanel extends JPanel {
		
		GFXPanel() {
			this.setPreferredSize(new Dimension(panW,panH));
			this.addKeyListener(new Keyboard());
			this.setFocusable(true);
		}
		
		//to draw stuff
		public void paintComponent (Graphics g) {
			super.paintComponent(g);
			
			//draw current block
			block.draw(g);
			
			//draw placed blocks
			for (Block b : placedBlocks) {
				b.draw(g);
			}
		}
		
	}
	
	class Keyboard implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			final int LEFT = 37;
			final int RIGHT = 39;
			final int DOWN = 40;
			
			String direction = "";
			if (e.getKeyCode() == LEFT) direction = "LEFT";
			else if (e.getKeyCode() == RIGHT) direction = "RIGHT";
			else if (e.getKeyCode() == DOWN) direction = "DOWN";
			
			adjustBlockPosition(direction);
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
