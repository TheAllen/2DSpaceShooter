package com.game.src.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFrame;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;

public class Game extends Canvas implements Runnable{
		
	private static final long serialVersionUID = 1L;
	public static final int width = 320;
	public static final int height = width / 12 * 9;
	public static final int scale = 2;
   

	public final String title = "Space Game";
    
    private boolean running = false;
    private Thread thread;
   
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private BufferedImage spriteSheet = null;
    private BufferedImage background = null;
    
    private boolean is_shooting = false;
    
    private int enemy_count = 12;
    private int enemy_killed = 0;
    
    private Player p;
    private Controller c;
    private Textures tex;
    private Menu menu;
    
    public LinkedList<EntityA> ea;
    public LinkedList<EntityB> eb;
    
    public static enum STATE{
    	MENU, 
    	GAME
    	
    };
    
    public static STATE state = STATE.MENU;
    
    
    
    public void init(){
    	requestFocus();
    	//gives it initial focus
    	BufferedImageLoader loader = new BufferedImageLoader();
    	//used to actually load the SpriteSheet
    	try{
    		spriteSheet = loader.loadImage("/SpriteSheet.png");
    		background = loader.loadImage("/backGround.png");
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	
    	
    	tex = new Textures(this);
    	
    	p = new Player(200, 200, tex);
    	//this refers to this class
    	c = new Controller(tex, this);
    	menu = new Menu();
    	
    	c.createEnemy(enemy_count);
    	
    	ea = c.getEntityA();
    	eb = c.getEntityB();
    	
    	addKeyListener(new KeyInput(this));
    	//adding an instance of the KeyInput class
    	this.addMouseListener(new MouseInput());
    }
    
    private synchronized void start(){
    	if(running) return;
      	running = true;
    	thread = new Thread(this);//which we are referring to this run method
    	thread.start();
    	
    }
    
    private synchronized void stop(){
    	if(!running) return;
    	running = false;
    	try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    public void run(){   
    	init();
    	long lastTime = System.nanoTime();
    	final double amountOfTicks = 60.0;
    	double ns = 1000000000 / amountOfTicks;
    	double delta = 0.0;
    	//delta is so it catches itself up
    	int updates = 0;
    	int frames = 0;
    	long timer = System.currentTimeMillis();
    	
    	while(running){
    		long now = System.nanoTime();
    		delta += (now - lastTime) / ns;
    		lastTime = now;
    		if(delta >= 1){
    			update();
    			//updating the game at 60 times per second
    			updates++;
    			delta--;
    		}
    		render();
    		frames++;
    		
    		if(System.currentTimeMillis() - timer > 1000){
    			timer += 1000;
     			System.out.println(updates + " updates, Fps " + frames);
     			
    			updates = 0;
    			frames = 0;
     		}
    	
    	
    		
    	}
    	stop();
    	//after it gets out of the running loop the method is going to stop;
    }
    
    private void update(){
    	if(state == STATE.GAME){
    		p.update();
        	c.update();
        	
    	}
    	 	
    	if(enemy_killed >= enemy_count){
    		enemy_count += 2;
    		enemy_killed = 0;
    		c.createEnemy(enemy_count);
    	}
    }
    
    private void render(){
    	BufferStrategy bs = this.getBufferStrategy();
    	//this is accessing the Canvas
    	if(bs == null){
    		createBufferStrategy(3);
    		return;
    	}
    	
    	Graphics g = bs.getDrawGraphics();
    	//this method creates a link doing graphics and the buffer
    	////////////
    	
    	g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    	
    	g.drawImage(background, 0, 0, null);
    			
    	if(state == STATE.GAME){
    		p.render(g);      	
        	c.render(g);
    	}else if(state == STATE.MENU){
    		menu.render(g);
    	}


    	g.dispose();
    	bs.show();
    }
    
    public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT){
			p.setVelX(5);
		}else if(key == KeyEvent.VK_LEFT){
			p.setVelX(-5);
		}else if(key == KeyEvent.VK_DOWN){
			p.setVelY(5);
		}else if(key == KeyEvent.VK_UP){
			p.setVelY(-5);
		}if(key == KeyEvent.VK_SPACE && !is_shooting){
			c.addEntity(new Bullet(p.getX(), p.getY(), tex, this));
			is_shooting = true;
		}
		
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(state == STATE.GAME){
			
			if(key == KeyEvent.VK_RIGHT){
				p.setVelX(0);
			}else if(key == KeyEvent.VK_LEFT){
				p.setVelX(0);
			}else if(key == KeyEvent.VK_DOWN){
				p.setVelY(0);
			}else if(key == KeyEvent.VK_UP){
				p.setVelY(0);
			}else if(key == KeyEvent.VK_SPACE){
				is_shooting = false;
			}
			//copied the KeyPressed method and set the parameter to 0
		}
		}
   
    public static void main(String[] args){
    	Game game = new Game();
    	
    	game.setPreferredSize(new Dimension(width*scale , height*scale));
    	game.setMaximumSize(new Dimension(width*scale , height*scale));
    	game.setMinimumSize(new Dimension(width*scale , height*scale));
    	
    	JFrame frame = new JFrame(game.title);
    	frame.add(game);
    	frame.pack();
    	//sets the size of the frame according to the component.
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setResizable(false);
    	frame.setLocationRelativeTo(null);
    	//not going to set the location
    	frame.setVisible(true);
    	
    	game.start();
    }
    
    public BufferedImage getSpriteSheet(){
    	return spriteSheet;
    }
    
    public int getEnemy_count() {
		return enemy_count;
	}

	public void setEnemy_count(int enemy_count) {
		this.enemy_count = enemy_count;
	}

	public int getEnemy_killed() {
		return enemy_killed;
	}

	public void setEnemy_killed(int enemy_killed) {
		this.enemy_killed = enemy_killed;
	}
    	
   
}

