package com.game.src.main;

import java.util.LinkedList;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;

public class Physics {
	//going to handle if we have collisions or not
	
	
	public static boolean Collision(EntityA enta, EntityB entb){
		
			
		if(enta.getBounds().intersects(entb.getBounds())){
			  return true;
		}				
		return false;
	}	
		public static boolean Collision(EntityB entb, EntityA enta){
										
				if(entb.getBounds().intersects(enta.getBounds())){										
					return true;
				}
		        return false;
	}

}
