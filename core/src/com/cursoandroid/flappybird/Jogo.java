package com.cursoandroid.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Jogo extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture passaros [];
	private Texture fundo;
	private Texture canobaixo;
	private Texture canotopo;
	private int movimentox = 50;
	private int posicaoInicialVerticalPassaro;
	private float gravidade = 3;


	private float larguraDispositivo;
	private float alturaDispositivo;
	private float variacao;
	private float posicaoCanoHorizontal;
	private float posicaoCanoVertical;
	private float espacoEntreCanos;
	private Random random;
	private int pontos;
	private boolean passouCano;

	private BitmapFont textoPontuacao;

	@Override
	public void create () {

		iniciarTexturas();
		iniciarObjetos();


	}

	@Override
	public void render () {

		verificaEstadoJogo();
		desenharTexturas();
		validarPontos();

	}
	
	@Override
	public void dispose () {

	}


	private void verificaEstadoJogo(){


		//aplicando evento de click
		if(Gdx.input.justTouched()){
			posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro + 150;
		}else if(posicaoInicialVerticalPassaro > 0){
			posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro - (int) gravidade;
		}

		variacao += Gdx.graphics.getDeltaTime() * 10;
		if(variacao >= 3){
			variacao = 0.0f;
		}


		posicaoCanoHorizontal-= 5;

		if(posicaoCanoHorizontal < -canobaixo.getWidth()){
			posicaoCanoHorizontal = larguraDispositivo;
			posicaoCanoVertical = random.nextInt(800) - 400;
			passouCano = false;
		}


	}


	private void desenharTexturas(){

		batch.begin();
		batch.draw(fundo, 0, 0,larguraDispositivo, alturaDispositivo);
		batch.draw(passaros[(int)variacao], movimentox, posicaoInicialVerticalPassaro);
		batch.draw(canobaixo,posicaoCanoHorizontal ,-espacoEntreCanos/2 + posicaoCanoVertical);
		batch.draw(canotopo,posicaoCanoHorizontal,alturaDispositivo/2 + espacoEntreCanos/2 + posicaoCanoVertical);
		textoPontuacao.draw(batch,String.valueOf(pontos),larguraDispositivo/2,alturaDispositivo - 100);
		batch.end();

	}

	private void iniciarTexturas(){

		batch = new SpriteBatch();
		passaros =  new Texture[3];
		passaros [0] = new Texture("passaro1.png");
		passaros [1] = new Texture("passaro2.png");
		passaros [2] = new Texture("passaro3.png");
		fundo  =  new Texture("fundo.png");
		canobaixo  =  new Texture("cano_baixo_maior.png");
		canotopo  =  new Texture("cano_topo_maior.png");

	}

	private void iniciarObjetos(){

		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();
		posicaoInicialVerticalPassaro = (int) alturaDispositivo / 2;
		posicaoCanoHorizontal = larguraDispositivo;
		posicaoCanoVertical = alturaDispositivo;
		variacao = 0;
		espacoEntreCanos = 400;
		random =  new Random();
		textoPontuacao =  new BitmapFont();
		textoPontuacao.setColor(Color.WHITE);
		textoPontuacao.getData().setScale(10);
		pontos = 0;
	}

	private void validarPontos(){

		if(posicaoCanoHorizontal < 40){
			if(!passouCano){
				pontos++;
				passouCano = true;
			}
		}

	}
}
