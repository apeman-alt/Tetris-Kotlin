package dev;

import java.awt.Color;
import java.awt.Graphics;

public class Block {

	//class variables
	static int size = 40;
	
	//instance variables
	int x;
	int y;
	int vectors[][];
	Color color;

	
	Block(int x, int y, Color color, int blockType) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.vectors = getVectors(blockType);
		
	}
	
	public int[][] getVectors(int type) {
		
		//block
		if (type == 0) {
			int[][]shapeVectors = {{0,0}, {1,0}, {1,-1}, {0,-1}};
			return shapeVectors;
		}
		//T shaped
		else if (type == 1) {
			int [][]shapeVectors = {{0,0}, {1,0}, {-1,0}, {0,-1}};
			return shapeVectors;
		}
		//L shaped
		else if (type == 2) {
			int [][]shapeVectors = {{0,0}, {0,1}, {0,2}, {1,0}};
			return shapeVectors;
		}
		//Reverse L shaped
		else if (type == 3) {
			int [][]shapeVectors = {{0,0}, {-1,0}, {0,1}, {0,2}};
			return shapeVectors;
		}
		//Straight
		else if (type == 4) {
			int [][]shapeVectors = {{0,0}, {1,0}, {2,0}, {0,-1}};
			return shapeVectors;
		}
		//Left Zig Zag
		else if (type == 5) {
			int [][]shapeVectors = {{0,0}, {-1,0}, {0,-1}, {1,-1}};
			return shapeVectors;
		}
		//Right Zig Zag
		else if (type == 6) {
			int [][]shapeVectors = {{0,0}, {1,0}, {0,-1}, {-1,-1}};
			return shapeVectors;
		}
		
		return null;
		
	}
	
	//TODO: methods to get the extremum of the block
	
	//to draw the block
	void draw(Graphics g) {
		g.setColor(color);
		
		for (int i = 0; i < vectors.length; i++) {
			
			g.fillRect(x + (size*vectors[i][0]), y + (size*vectors[i][1]), size,size);
			
		}
	}
	
}