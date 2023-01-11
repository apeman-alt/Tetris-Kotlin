package dev;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Block {

	//class variables
	static int size = 40;
	
	//instance variables
	int x;
	int y;
	//int vectors[][];
	ArrayList<int[]> vectors = new ArrayList<>();
	Color color;

	//to make a filled block
	Block(int x, int y, Color color, int blockType) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.vectors = getVectors(blockType);
		
	}
	
	
	//to make a filled block
	public ArrayList<int[]> getVectors(int type) {
		
		ArrayList<int[]> vectors = new ArrayList<>();
		
		//block
		if (type == 0) {
			//int[][]shapeVectors = {{0,0}, {1,0}, {1,-1}, {0,-1}};
			//return shapeVectors;
			int[] vector1 = {0,0};
			vectors.add(vector1);
			int[] vector2 = {1,0};
			vectors.add(vector2);
			int[] vector3 = {1,-1};
			vectors.add(vector3);
			int[] vector4 = {0,-1};
			vectors.add(vector4);
			
		}
		//T shaped
		else if (type == 1) {
			//int [][]shapeVectors = {{0,0}, {1,0}, {-1,0}, {0,-1}};
			//return shapeVectors;
			int[] vector1 = {0,0};
			vectors.add(vector1);
			int[] vector2 = {1,0};
			vectors.add(vector2);
			int[] vector3 = {-1,0};
			vectors.add(vector3);
			int[] vector4 = {0,-1};
			vectors.add(vector4);
		}
		//L shaped
		else if (type == 2) {
			//int [][]shapeVectors = {{0,0}, {0,1}, {0,2}, {1,0}};
			//return shapeVectors;
			int[] vector1 = {0,0};
			vectors.add(vector1);
			int[] vector2 = {0,1};
			vectors.add(vector2);
			int[] vector3 = {0,2};
			vectors.add(vector3);
			int[] vector4 = {1,0};
			vectors.add(vector4);
		}
		//Reverse L shaped
		else if (type == 3) {
			//int [][]shapeVectors = {{0,0}, {-1,0}, {0,1}, {0,2}};
			//return shapeVectors;
			int[] vector1 = {0,0};
			vectors.add(vector1);
			int[] vector2 = {-1,0};
			vectors.add(vector2);
			int[] vector3 = {0,1};
			vectors.add(vector3);
			int[] vector4 = {0,2};
			vectors.add(vector4);
		}
		//Straight
		else if (type == 4) {
			//int [][]shapeVectors = {{0,0}, {1,0}, {2,0}, {-1,0}};
			//return shapeVectors;
			int[] vector1 = {0,0};
			vectors.add(vector1);
			int[] vector2 = {1,0};
			vectors.add(vector2);
			int[] vector3 = {2,0};
			vectors.add(vector3);
			int[] vector4 = {-1,0};
			vectors.add(vector4);
		}
		//Left Zig Zag
		else if (type == 5) {
			//int [][]shapeVectors = {{0,0}, {-1,0}, {0,-1}, {1,-1}};
			//return shapeVectors;
			int[] vector1 = {0,0};
			vectors.add(vector1);
			int[] vector2 = {-1,0};
			vectors.add(vector2);
			int[] vector3 = {0,-1};
			vectors.add(vector3);
			int[] vector4 = {1,-1};
			vectors.add(vector4);
		}
		//Right Zig Zag
		else if (type == 6) {
			//int [][]shapeVectors = {{0,0}, {1,0}, {0,-1}, {-1,-1}};
			//return shapeVectors;
			int[] vector1 = {0,0};
			vectors.add(vector1);
			int[] vector2 = {1,0};
			vectors.add(vector2);
			int[] vector3 = {0,-1};
			vectors.add(vector3);
			int[] vector4 = {-1,-1};
			vectors.add(vector4);
		}
		
		return vectors;
		
	}
	
	
	public int getLeftmostPoint() {
		
		int leftmostVector = 0;
		
		for (int i = 0; i < vectors.size(); i++) {
			if (vectors.get(i)[0] < leftmostVector) {
				leftmostVector = vectors.get(i)[0];	
			}
		}
		
		int leftmostPoint = x + leftmostVector*size;
		
		return leftmostPoint;
		
	}
	
	public int getRightmostPoint() {
		
		int rightmostVector = 0;
		
		for (int i = 0; i < vectors.size(); i++) {
			if (vectors.get(i)[0] > rightmostVector) {
				rightmostVector = vectors.get(i)[0];	
			}
		}
		
		int rightmostPoint = x + rightmostVector*size;
		
		return rightmostPoint;
		
	}
	
	public int getLowestPoint() {
		
		int lowestVector = 0;
		
		for (int i = 0; i < vectors.size(); i++) {
			if (vectors.get(i)[1] > lowestVector) {
				lowestVector = vectors.get(i)[1];	
			}
		}
		
		int lowestPoint = y + lowestVector*size;
		
		return lowestPoint;
		
	}
	
	public int getHighestPoint() {
		
		int highestVector = 0;
		
		for (int i = 0; i < vectors.size(); i++) {
			if (vectors.get(i)[1] < highestVector) {
				highestVector = vectors.get(i)[1];	
			}
		}
		
		int highestPoint = y + highestVector*size;
		
		return highestPoint;
		
	}
	
	//to draw the block
	public void draw(Graphics g) {
		g.setColor(color);
		
		for (int i = 0; i < vectors.size(); i++) {
			
			g.fillRect(x + (size*vectors.get(i)[0]), y + (size*vectors.get(i)[1]), size,size);
			
		}
	}
	
	public void drawShadow(Graphics g) {
		
		//BROKEN
		
		int shadowX = x;
		int shadowY = y;
		
		while (Game.checkBlock()) {
			shadowY += size;
		}
		
		
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		if (red + 170 <= 255) red+=170;
		if (green + 170 <= 255) green+=170;
		if (blue + 170 <= 255) blue+=170;
		
		Color shadCol = new Color(red,green,blue);
		g.setColor(shadCol);
		
		for (int i = 0; i < vectors.size(); i++) {
			
			g.fillRect(shadowX + (size*vectors.get(i)[0]), shadowY + (size*vectors.get(i)[1]), size,size);
			
		}
		
		
	}
	
}