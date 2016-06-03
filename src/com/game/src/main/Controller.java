package com.game.src.main;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.text.html.parser.Entity;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;

public class Controller {
	
	private LinkedList<EntityA> ea = new LinkedList<EntityA>();	
	private LinkedList<EntityB> eb = new LinkedList<EntityB>();
//made two new linkedList
	EntityA enta;
	EntityB entb;
	private Textures tex;
	Random r = new Random();
	private Game game;
	
	public Controller(Textures tex, Game game){
		this.tex = tex;
		this.game = game;
	}
	
	public void createEnemy(int enemy_count){
		for(int i = 0; i < enemy_count; i++){
			addEntity(new Enemy(r.nextInt(640), -10, tex, this, game));
		}
		
	}
	
	public void update(){
		//A CLASS
		for(int i = 0; i < ea.size(); i++){
			enta = ea.get(i);
			
			enta.update();
		}
		
		//B CLASS
		for(int i = 0; i < eb.size(); i++){
			entb = eb.get(i);
					
			entb.update();
		}
	
	}
	public void render(Graphics g){
		
		for(int i = 0; i < ea.size(); i++){
			enta = ea.get(i);
			
			enta.render(g);
		}
		
		for(int i = 0; i < eb.size(); i++){
			entb = eb.get(i);
			
			entb.render(g);
		}
		
	}
	public void addEntity(EntityA block){
		ea.add(block);
		
	}
	
	public void removeEntity(EntityA block){
		ea.remove(block);
		
	}
	//we can use the same name for the methods because of different parameters
	public void addEntity(EntityB block){
		eb.add(block);
		
	}
	
	public void removeEntity(EntityB block){
		eb.remove(block);
		
	}
	
	public LinkedList<EntityA> getEntityA(){
		return ea;
	}
	
	public LinkedList<EntityB> getEntityB(){
		return eb;
	}
	


}
