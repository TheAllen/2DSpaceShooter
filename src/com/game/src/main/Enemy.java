package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.game.src.libs.Animation;
import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;

public class Enemy extends GameObject implements EntityB{
	
	//private double x, y;
	Random r = new Random();
	
	private Textures tex;
	
	private Game game;
	private Controller c;
	
	private int speed = r.nextInt(3) + 1;
	
	private Animation anim;
	
	public Enemy(double x, double y, Textures tex, Controller c, Game game){

		super(x, y);
		this.tex = tex;
		this.c = c;
		this.game = game;
		
		anim = new Animation(5, tex.enemy[0], tex.enemy[1], tex.enemy[2]);
	}
	
	public void update(){
		
		y += speed;
		
		if(y > (Game.height * Game.scale)){
			y = 0;
			x = r.nextInt(Game.width * Game.scale);
			//used to give Re-spawning planes a different location
		}
		
		for(int i = 0; i < game.ea.size(); i++){
			EntityA tempEnt = game.ea.get(i);
			
			if(Physics.Collision(this, tempEnt)){
				c.removeEntity(this);
				game.setEnemy_killed(game.getEnemy_killed()+1);
				
			}
			
		}
		
	
		anim.runAnimation();
	}
	
	public void render(Graphics g){
		anim.drawAnimation(g, x, y, 0);
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, 32, 32);
	}
	
	public double getX() {
		return x;
	}
	
    public double getY(){
		return y;
	}
    
    public void setY(double y){
    	this.y = y;
    }

	

}
