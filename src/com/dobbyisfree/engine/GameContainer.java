package com.dobbyisfree.engine;

public class GameContainer implements Runnable {
	
	private Thread thread;
	private Window window;
	private Renderer renderer;
	private Input input;
	private AbstractGame game;
	
	// basic variables to run engine
	private boolean running = false;
	private final double UPDATE_CAP = 1.0 / 60.0;
	// variables for window
	private int width = 320, height = 240;
	private float scale = 4f;
	private String title = "Engine v0.1";
	
	public GameContainer(AbstractGame game) {
		// initalise game
		this.game = game;
	}
	
	public void start() {
		// initialise window
		window = new Window(this);
		
		// initialise renderer
		renderer = new Renderer(this);
		
		// initialise input
		input = new Input(this);
		
		// start main thread to run game on
		thread = new Thread(this);
		thread.run();
	}
	
	public void stop() {
		
	}
	
	public void run() {
		running = true;
		
		// initialise variables to use to maintain 60 updates per second
		boolean render = false;
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = 0;
		double unprocessedTime = 0;
		
		// used to track frame-rate
		double frameTime = 0;
		int frames = 0;
		int fps = 0;
		
		while(running) {
			// turn render off so it only updates when told to
			render = false;
			
			// calculate time passed
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			
			// turn this into time passed since last update
			unprocessedTime += passedTime;
			frameTime += passedTime;
			
			// if time is amount of time to be updated per second, update
			while(unprocessedTime >= UPDATE_CAP) {
				// reset unprocessed time, and allow render to run once
				unprocessedTime -= UPDATE_CAP;
				render = true;
				
				game.update(this, (float)UPDATE_CAP);
				input.update();
				
				// if it's been a second, calculate frames passed in the last second to get FPS
				if(frameTime >= 1.0) {
					frameTime = 0;
					fps = frames;
					frames = 0;
					System.out.println("FPS: " + fps);
				}
			}
			
			// if render is allowed, render the game once, if not, sleep thread to save CPU
			if(render) { 
				renderer.clear();
				game.render(this, renderer);
				window.update();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		dispose();
	}
	
	private void dispose() {
		
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Window getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}
	
}
