package com.dobbyisfree.game;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.dobbyisfree.engine.AbstractGame;
import com.dobbyisfree.engine.GameContainer;
import com.dobbyisfree.engine.Renderer;
import com.dobbyisfree.engine.gfx.Image;

public class GameManager extends AbstractGame {

	private Image image;
	
	public GameManager() {
		image = new Image("/cute frog.png");
	}
	
	@Override
	public void update(GameContainer gc, float dt) {
		
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		r.drawImage(image,  gc.getInput().getMouseX() - image.getW() / 2, gc.getInput().getMouseY() - image.getH() / 2);
	}
	
	public static void main(String args[]) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}

}
