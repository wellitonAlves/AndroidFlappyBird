package com.cursoandroid.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Jogo extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture passaros [];
	private Texture fundo;
	private int movimentox = 0;
	private int movimentoy = 0;


	private float larguraDispositivo;
	private float alturaDispositivo;
	private float variacao;

	@Override
	public void create () {
		batch = new SpriteBatch();
		passaros =  new Texture[3];
		passaros [0] = new Texture("passaro1.png");
		passaros [1] = new Texture("passaro2.png");
		passaros [2] = new Texture("passaro3.png");
		fundo  =  new Texture("fundo.png");
		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();
		variacao = 0;
	}

	@Override
	public void render () {
		//Gdx.gl.glClearColor(1, 0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(fundo, 0, 0,larguraDispositivo, alturaDispositivo);
		batch.draw(passaros[(int)variacao], movimentox++, movimentoy++);
		batch.end();

		variacao += Gdx.graphics.getDeltaTime() * 10;


		if(variacao >= 3){
			variacao = 0.0f;
		}
	}
	
	@Override
	public void dispose () {

	}
}
