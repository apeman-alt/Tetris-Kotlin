package dev;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tetris {
	
	//GAME
	ArrayList<Block> blockList = new ArrayList<Block>();
	
	//DISPLAY
	JFrame window;
	GFXPanel panel;
	int panW = Block.size*10;
	int panH = Block.size*20;
	
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

		panel.repaint();
		
		
		while (true) {
			spawnBlock();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) { e.printStackTrace(); }
			panel.repaint();
		}
	}
	
	public void spawnBlock() {
		
		//get random position
		int x = (int)(Math.random()*10)*Block.size;
		int y = (int)(Math.random()*20)*Block.size;
		System.out.println(x + " " + y);
		
		//get random color
		String colors[] = {"#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF", "#00FFFF"};
		Color color = Color.decode( colors[(int)(Math.random()*6)] );
		
		//initialize block object and add it to arraylist
		Block block = new Block(x,y,color, (int)(Math.random()*7));
		blockList.add(block);
	}
	
	@SuppressWarnings("serial")
	class GFXPanel extends JPanel {
		
		GFXPanel() {
			this.setPreferredSize(new Dimension(panW,panH));
		}
		
		//to draw stuff
		public void paintComponent (Graphics g) {
			super.paintComponent(g);
			//g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON); //antialiasing
			
			//to draw a square:
			
			//Block block = new Block(100,100, Color.decode("#ff0000"));
			//g.fillRect(block.x, block.y, Block.width, Block.height);
			for (Block b : blockList) {
				b.draw(g);
			}
		}
		
	}
	
}
