package com.game.src.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	//whenever we press a key, it's going to call this class
	
	Game game;
	
	public KeyInput(Game game){
		this.game = game;
	}
	
	public void keyPressed(KeyEvent e){
		game.keyPressed(e);
	}
	//whenever we press a key, the KeyAdapter is going to run these two methods
	public void keyReleased(KeyEvent e){
		game.keyReleased(e);
	}

}
