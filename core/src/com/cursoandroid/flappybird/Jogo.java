package com.cursoandroid.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Jogo extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private Texture fundo;
	private int movimentox = 0;
	private int movimentoy = 0;
	private float larguraDispositivo;
	private float alturaDispositivo;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("passaro1.png");
		fundo  =  new Texture("fundo.png");
		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();
	}

	@Override
	public void render () {
		//Gdx.gl.glClearColor(1, 0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(fundo, 0, 0,larguraDispositivo, alturaDispositivo);
		batch.draw(img, movimentox++, movimentoy++);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
