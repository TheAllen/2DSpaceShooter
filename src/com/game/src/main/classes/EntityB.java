package com.game.src.main.classes;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface EntityB {
	
	public void update();
    public void render(Graphics g);
    public Rectangle getBounds();
    
    public double getX();
    public double getY();
    
	

}
