package com.cursoandroid.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Jogo extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture passaros [];
	private Texture fundo;
	private Texture canobaixo;
	private Texture canotopo;
	private Texture gameOver;

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
	private int estadoJogo = 0;

	private BitmapFont textoPontuacao;

    private ShapeRenderer shapeRenderer;
	private Circle circuloPassaro;
	private Rectangle retanguloCanoCima;
	private Rectangle retanguloCanoBaixo;

	BitmapFont textoReiniciar;
	BitmapFont textoMelhorPontuacao;

	@Override
	public void create () {

		iniciarTexturas();
		iniciarObjetos();


	}

	@Override
	public void render () {

		verificaEstadoJogo();
		desenharTexturas();
		detectarColisoes();
		validarPontos();

	}
	
	@Override
	public void dispose () {

	}


	private void verificaEstadoJogo(){

		variacao += Gdx.graphics.getDeltaTime() * 10;
		if(variacao >= 3){
			variacao = 0.0f;
		}

		if(estadoJogo == 0){
			if(Gdx.input.justTouched()){
				estadoJogo = 1;
			}
			return;
		}else if(estadoJogo == 1){

				//aplicando evento de click
				if(Gdx.input.justTouched()){
					posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro + 150;
				}else if(posicaoInicialVerticalPassaro > 0){
					posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro - (int) gravidade;
				}

				posicaoCanoHorizontal-= 5;

				if(posicaoCanoHorizontal < -canobaixo.getWidth()){
					posicaoCanoHorizontal = larguraDispositivo;
					posicaoCanoVertical = random.nextInt(800) - 400;
					passouCano = false;
				}

		}else if(estadoJogo == 2){

		}



	}


	private void desenharTexturas(){

		batch.begin();
		batch.draw(fundo, 0, 0,larguraDispositivo, alturaDispositivo);
		batch.draw(passaros[(int)variacao], movimentox, posicaoInicialVerticalPassaro);
		batch.draw(canobaixo,posicaoCanoHorizontal ,-espacoEntreCanos/2 + posicaoCanoVertical);
		batch.draw(canotopo,posicaoCanoHorizontal,alturaDispositivo/2 + espacoEntreCanos/2 + posicaoCanoVertical);
		textoPontuacao.draw(batch,String.valueOf(pontos),larguraDispositivo/2,alturaDispositivo - 100);

		if(estadoJogo == 2){
			batch.draw(gameOver, larguraDispositivo/2 - (gameOver.getWidth()/2), alturaDispositivo/2);
			textoReiniciar.draw(batch,"Toque para reiniciar!", larguraDispositivo/2 - 140,alturaDispositivo/2 - gameOver.getHeight()/2);
			textoMelhorPontuacao.draw(batch,"Seu recorde é: 0 pontos", larguraDispositivo/2 - 140,alturaDispositivo/2  - gameOver.getHeight());
		}

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
		circuloPassaro =  new Circle();
		retanguloCanoBaixo = new Rectangle();
		retanguloCanoCima = new Rectangle();
		shapeRenderer = new ShapeRenderer();

		gameOver =  new Texture("game_over.png");

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

		textoReiniciar =  new BitmapFont();
		textoReiniciar.setColor(Color.GREEN);
		textoReiniciar.getData().setScale(2);

		textoMelhorPontuacao =  new BitmapFont();
		textoMelhorPontuacao.setColor(Color.RED);
		textoMelhorPontuacao.getData().setScale(2);


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

	public void detectarColisoes(){

		circuloPassaro.set(50 + passaros[0].getWidth()/2, posicaoInicialVerticalPassaro + passaros[0].getHeight()/2, passaros[0].getWidth()/2);
		retanguloCanoCima.set(posicaoCanoHorizontal,alturaDispositivo/2 + espacoEntreCanos/2 + posicaoCanoVertical,canotopo.getWidth(),canotopo.getHeight());
		retanguloCanoBaixo.set(posicaoCanoHorizontal ,-espacoEntreCanos/2 + posicaoCanoVertical,canobaixo.getWidth(),canobaixo.getHeight());


		if(Intersector.overlaps(circuloPassaro,retanguloCanoCima) || Intersector.overlaps(circuloPassaro,retanguloCanoBaixo)){
			Gdx.app.log("Log", "Colidiu cano");
			estadoJogo = 2;
		}

		//codigo importante para visualizar as formas
		/*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.circle(50 + passaros[0].getWidth()/2, posicaoInicialVerticalPassaro + passaros[0].getHeight()/2, passaros[0].getWidth()/2);
		shapeRenderer.rect(posicaoCanoHorizontal,alturaDispositivo/2 + espacoEntreCanos/2 + posicaoCanoVertical,canotopo.getWidth(),canotopo.getHeight());
		shapeRenderer.rect(posicaoCanoHorizontal ,-espacoEntreCanos/2 + posicaoCanoVertical,canobaixo.getWidth(),canobaixo.getHeight());
		shapeRenderer.end();
		*/


	}
}
