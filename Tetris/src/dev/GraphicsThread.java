package dev;

//import dev.Tetris;

public class GraphicsThread extends Thread {

	public void run() {
		
		while(true) {
			
			Game.sleep(1000/Game.REFRESH);
			Game.panel.repaint();
		}
		
	}
	
}
