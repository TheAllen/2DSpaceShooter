package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.src.libs.Animation;
import com.game.src.main.classes.EntityA;

public class Bullet extends GameObject implements EntityA{
	//private double x;
	//private double y;
	
	
	private Textures tex;
	private Game game;
	
	private Animation anim;
		
	public Bullet(double x, double y, Textures tex, Game game){
		super(x, y);
		this.tex = tex;
		this.game = game;
		
		anim = new Animation(5, tex.missile[0], tex.missile[1], tex.missile[2]);
		
	}
	
	public void update(){
		y -= 10;
		//speed in which the bullet travels.
		
	
		anim.runAnimation();
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, 32, 32);	
	}
	
	public void render(Graphics g){
		anim.drawAnimation(g, x, y, 0);
		//used in every draw image method to draw the image
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}

	
	

}
