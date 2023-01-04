package dev;

import java.awt.Color;
import java.awt.Graphics;

public class Block {

	//class variables
	static int size = 40;
	
	//instance variables
	int x;
	int y;
	Color color;

	
	Block(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	//to draw the block
	void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, size,size);
	}
	
}
