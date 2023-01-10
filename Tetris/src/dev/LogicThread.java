package dev;

//import dev.Tetris;

public class LogicThread extends Thread {
	public void run() {
		while (true) {
			
			if (Game.isRunning) {
				Game.sleep(1000);
				
				Game.moveBlockDown();
				
				Game.checkBlock();
				Game.checkRows();
				Game.checkGameStatus();
			}
			
		}
	}
}
