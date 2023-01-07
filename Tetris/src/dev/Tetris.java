package dev;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

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

			sleep(1000/REFRESH);
			checkBlock();
			panel.repaint();
		}
	}
	
	public void setup() {
		block = spawnBlock();
	}
	
	public void sleep(double sleepTime) {
		try {
			Thread.sleep((int)sleepTime);
		} catch (InterruptedException e) { e.printStackTrace(); }
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
		
		//to make sure the block doesn't go beyond border when spawning
		while (block.getHighestPoint() < 0) {
			block.y += Block.size;
		}
		
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

			boolean canMoveLeft = true;
			
			//check if block is directly to the left of border
			if (block.getLeftmostPoint() <= 0) canMoveLeft = false;
			
			//check if block is directly to the left of a placed block
			for (int[] v : block.vectors) {
				
				for (Block p : placedBlocks) {
					
					for (int[] pv : p.vectors) {
						
						//if they're at the same height
						if (block.y + v[1]*Block.size == p.y + pv[1]*Block.size) {
							
							//if the block vector is one space left of the placed block vector
							if (block.x + v[0]*Block.size <= p.x + pv[0]*Block.size + Block.size) {
								canMoveLeft = false;
							}	
						}
					}	
				}
			}
			
			if (canMoveLeft) block.x -= Block.size;
			
		}
		else if (direction.equals("RIGHT")) {
			
			boolean canMoveRight = true;
			
			//check if block is directly to the right of border
			if (block.getRightmostPoint() >= panW-Block.size) canMoveRight = false;
			
			//check if block is directly to the left of a placed block
			for (int[] v : block.vectors) {
				
				for (Block p : placedBlocks) {
					
					for (int[] pv : p.vectors) {
						
						//if they're at the same height
						if (block.y + v[1]*Block.size == p.y + pv[1]*Block.size) {
							
							//if the block vector is one space left of the placed block vector
							if (block.x + v[0]*Block.size >= p.x + pv[0]*Block.size - Block.size) {
								canMoveRight = false;
							}	
						}
					}	
				}
			}
			
			if (canMoveRight) block.x += Block.size;
		}
	}
	
	public void rotateBlock(String direction) {
		//All blocks are represented by a set of "direction vectors", where [0,0] is the anchor block, and [1,0] is a block 1 unit to the right of the anchor, etc.
		//Based on Linear Algebra principles, vectors can undergo "linear transformations" such as stretching, compressing, and in our case--rotating
		//Any linear transformation of a vector can be represented by product of a transformation matrix and the vector
		//To rotate the vectors, we need 2 matrices, one to increase the vector's angle by 90 degrees (counter clockwise),
		//and one to decrease the angle by 90 degrees (clockwise)
		
		//Therefore, to rotate the block (remember, the block is made up of direction vectors), then we need to multiply
		//each direction vector by the appropriate rotation matrix such that A*v0 = v1, where A is the matrix, v0 the original vector, and v1 the rotated vector  
		
		
		//to learn how these matrices were derived, read about "2x2 rotation matrix"
		int[][] clockwiseMatrix = {
				{ 0 , 1 },
				{-1 , 0 }
		};
		int[][] counterClockwiseMatrix = {
				{ 0 ,-1 },
				{ 1 , 0 }
		};
		
		int[][] rotationMatrix = null;
		
		
		if (direction.equals("CLOCKWISE")) {
			rotationMatrix = clockwiseMatrix;
		}
		else if (direction.equals("COUNTERCLOCKWISE")) {
			rotationMatrix = counterClockwiseMatrix;
		}
		
		
		//multiply direction vector by rotation matrix
		for (int i = 0; i < block.vectors.size(); i++) {
			
			/*
			 * FOR MATRIX MULTIPLICATION IN THE FORM OF
			 * 
			 *  _    _  _ _     _  _
			 * | a  b || x |   | x1 |
			 * | c  d || y | = | y1 |
			 * 
			 */
			
			int x = block.vectors.get(i)[0];
			int y = block.vectors.get(i)[1];
			
			int a = rotationMatrix[0][0];
			int b = rotationMatrix[0][1];
			int c = rotationMatrix[1][0];
			int d = rotationMatrix[1][1];
			
			int x1 = a*x + b*y;
			int y1 = c*x + d*y;
			
			block.vectors.get(i)[0] = x1;
			block.vectors.get(i)[1] = y1;
			
		}
		
		//adjust if block went over border after rotation
		
		while (block.getLeftmostPoint() < 0) {
			block.x += Block.size;
		}
		
		while (block.getRightmostPoint() > panW-Block.size) {
			block.x -= Block.size;
		}
		
		while (block.getLowestPoint() > panH-Block.size) {
			block.x -= Block.size;
		}
		
	}
	
	public void checkBlock() {
		
		//check if block has reached the ground
		if (block.getLowestPoint() >= panH - Block.size) {
			placedBlocks.add(block);
			block = spawnBlock();
			return;
		}
		
		//check if block is on top of another block
		for (int[] v : block.vectors) {
			for (Block p : placedBlocks) {
				for (int[] pv : p.vectors) {
					//Essentially, check if one of the block's vectors is directly on top of one of the vectors of a placed block
			
					if ( block.x + Block.size*v[0] == p.x + Block.size*pv[0]) {
						
						if ( block.y + Block.size*v[1] == p.y + Block.size*pv[1] - Block.size) {
							
							placedBlocks.add(block);
							block = spawnBlock();
							return;
							
						}
					}
				}		
			}
		}
		
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
			
			//draw grid
			g.setColor(Color.decode("#e0e0e0"));
			for (int i = 0; i < panW/Block.size; i++) 
				g.drawLine(i*Block.size, 0, i*Block.size, panH);
			for (int i = 0; i < panH/Block.size; i++)
				g.drawLine(0, i*Block.size, panW, i*Block.size);
			
		}
		
	}
	
	class Keyboard implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			if (e.getKeyChar() == 'z') {
				rotateBlock("CLOCKWISE");
			}
			else if (e.getKeyChar() == 'x') {
				rotateBlock("COUNTERCLOCKWISE");
			}
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

		@Override public void keyReleased(KeyEvent e) {}
		
	}
	
}
