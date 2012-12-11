/*
 * Copyright (C) 2011 The Code Bakers
 * Authors: Cleuton Sampaio e Francisco Rodrigues
 * e-mail: thecodebakers@gmail.com
 * Project: https://code.google.com/p/open-source-android-blackjack/
 * Site: http://thecodebakers.blogspot.com
 *
 * Licensed under the GNU GPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://gplv3.fsf.org/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author Cleuton Sampaio e Francisco Rogrigues - thecodebakers@gmail.com
 */

package br.com.thecodebakers.blackjack.business;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;
import br.com.thecodebakers.blackjack.R;
import br.com.thecodebakers.blackjack.activities.BlackJack;
import br.com.thecodebakers.blackjack.model.Message;

/**
 * Classe com as regras de negócio do game.<br/>
 * 
 * Class with the business rules of the game.
 * 
 * @author Cleuton Sampaio e Francisco Rogrigues - thecodebakers@gmail.com
 *
 */
public class BlackBO {
	
	private Message message = null;
	private static BlackBO instance;
	private static final String TAG = "BlackBO";
	private Context context;
	
	private int somabanca = 0;
	private int somaplayer = 0;
	private int indicecarta = 0;
	private int indicebanca = 0;
	
	private boolean fim = false;
	private boolean inicializado = false;
	
	private ArrayList <Integer> cartasSorteadasPlayer ;
	private ArrayList <Integer> cartasSorteadasBanca ;
	
	private Integer cartaBanca ;
	private Integer cartaPlayer;
	
	//lista para armazenar os dados do stand
	private ArrayList <Integer> cartasSorteadasStand;
	
	//array de strings para contar os pontos das cartas
	private String [] cartasStr = {
			"as_copas.png", 	      "nove_copas.png", 	  "rei_ouros.png", 
			"as_espadas.png", 	      "nove_espadas.png", 	  "rei_paus.png", 
			"as_ouros.png", 	      "nove_ouros.png", 	  "seis_copas.png", 
			"as_paus.png", 	      "nove_paus.png", 	  "seis_espadas.png", 
			"cinco_copas.png",        "oito_copas.png", 	  "seis_ouros.png", 
			"cinco_espadas.png",      "oito_espadas.png",   "seis_paus.png", 
			"cinco_ouros.png",        "oito_ouros.png", 	  "sete_copas.png", 
			"cinco_paus.png", 	      "oito_paus.png", 	  "sete_espadas.png", 
			"curinga_verde.png",      "quatro_copas.png",   "sete_ouros.png", 
			"curinga_vermelho.png",   "quatro_espadas.png",   "sete_paus.png", 
			"dez_copas.png", 	      "quatro_ouros.png", 	  "tres_copas.png", 
			"dez_espadas.png",        "quatro_paus.png", 	  "tres_espadas.png", 
			"dez_ouros.png", 	      "raina_ouros.png", 	  "tres_ouros.png", 
			"dez_paus.png", 	      "rainha_copas.png", 	  "tres_paus.png", 
			"dois_copas.png", 	      "rainha_espadas.png",   "valete_copas.png", 
			"dois_espadas.png",       "rainha_paus.png", 	  "valete_espadas.png", 
			"dois_ouros.png", 	      "rei_copas.png", 	  "valete_paus.png", 
			"dois_paus.png", 	      "rei_espadas.png" 	 
			};
	
	//array de inteiros com as imagens das cartas para a tela
	private Integer[]  cartas = {
    		 R.drawable.as_copas, 		  R.drawable.nove_copas,     R.drawable.rei_ouros, 
    		 R.drawable.as_espadas, 	  R.drawable.nove_espadas,   R.drawable.rei_paus, 
    		 R.drawable.as_ouros, 	      R.drawable.nove_ouros, 	 R.drawable.seis_copas, 
    		 R.drawable.as_paus, 	      R.drawable.nove_paus,      R.drawable.seis_espadas, 
    		 R.drawable.cinco_copas,      R.drawable.oito_copas,     R.drawable.seis_ouros, 
    		 R.drawable.cinco_espadas,    R.drawable.oito_espadas,   R.drawable.seis_paus, 
    		 R.drawable.cinco_ouros,      R.drawable.oito_ouros,     R.drawable.sete_copas, 
    		 R.drawable.cinco_paus, 	  R.drawable.oito_paus,      R.drawable.sete_espadas, 
    		 R.drawable.curinga_verde,    R.drawable.quatro_copas,   R.drawable.sete_ouros, 
    		 R.drawable.curinga_vermelho, R.drawable.quatro_espadas, R.drawable.sete_paus, 
    		 R.drawable.dez_copas, 	      R.drawable.quatro_ouros,   R.drawable.tres_copas, 
    		 R.drawable.dez_espadas,      R.drawable.quatro_paus,    R.drawable.tres_espadas, 
    		 R.drawable.dez_ouros, 	      R.drawable.raina_ouros, 	 R.drawable.tres_ouros, 
    		 R.drawable.dez_paus, 	      R.drawable.rainha_copas,   R.drawable.tres_paus, 
    		 R.drawable.dois_copas, 	  R.drawable.rainha_espadas, R.drawable.valete_copas, 
    		 R.drawable.dois_espadas,     R.drawable.rainha_paus,    R.drawable.valete_espadas, 
    		 R.drawable.dois_ouros, 	  R.drawable.rei_copas, 	 R.drawable.valete_paus, 
    		 R.drawable.dois_paus,  	  R.drawable.rei_espadas};

	
	protected BlackBO() {
		super();
		Log.d(TAG, "Instanciou o BO sem contexto.");
	}
	
	public static BlackBO getInstance(Context ctx) {
		if (instance == null) {
			Log.d(TAG, "Cria nova instância do BO.");
			instance = new BlackBO();
			instance.context = ctx;
		}
		
		Log.d(TAG, "Retorna uma referência para o BO.");
		return instance;
		
	}
	
	private void inicializar() {
		cartaBanca=null ;
		cartaPlayer=null ;
		message = null;
    	cartasSorteadasPlayer = new ArrayList<Integer>(); 
    	cartasSorteadasBanca = new ArrayList<Integer>();
    	cartasSorteadasStand = new ArrayList<Integer>();
    }
   
	private int aleatorio(int inferior, int superior){
        int numPossibilidades = superior - inferior;
        double aleat = Math.random() * (numPossibilidades + 1);
        aleat = Math.floor(aleat);
        return (int) (inferior + aleat);
    } 
    
    public void stand() {
    	Resources res = this.context.getResources();
    	String text = "";
    	cartaBanca=null ;
    	cartasSorteadasStand = new ArrayList<Integer>();
    	
    	if (fim) {
    		text = res.getString(R.string.msg_jogo_acabou);
    		//BlackJack.sr.stopListening(); 
    		BlackJack.gameover =1;
    		message = new Message(text);
    		return;
    		
    	}
    	if (!inicializado) {
    		novo();
    		Toast.makeText(context, R.string.msg_jogo_antes_desistir , Toast.LENGTH_LONG).show();
    		return;
    	}
    	bancajoga(true);
    }
    
    public void novo() {
    	inicializar();
    	inicializado = true;
    	
    	//limpa os pontos
    	somabanca = 0;
    	somaplayer = 0;
    	indicecarta = 0;
    	indicebanca = 0;
    	fim = false;
    }
    
    public void hitme() {
    	Resources res = this.context.getResources();
    	String text = "";
    	if (fim) {
    		text = res.getString(R.string.msg_jogo_acabou);
    		message = new Message(text);
    		return ;
    	}
    	
    	if (!inicializado) {
    		novo();
    	}
    	
    	if (somaplayer >= 21) {
    		Toast.makeText(context, R.string.msg_naotem_cartas, Toast.LENGTH_LONG).show();
    	}else {
    		
    		indicecarta++;
    		int carta = sorteio();
    		cartaPlayer = cartas[carta];
            
    		if (somaplayer == 21) {
    			text = res.getString(R.string.msg_vc_venceu);
    			//BlackJack.sr.stopListening(); 
        		BlackJack.gameover =1;
        		message = new Message(text);
    			fim = true;
    		}else if (somaplayer > 21) {
    			text = res.getString(R.string.msg_vc_perdeu);
    			BlackJack.sr.stopListening(); 
        		BlackJack.gameover =1;

    			message = new Message(text);
        		this.cartaBanca = null;
    			fim = true;
    		}
    	}
    	
    	if (somaplayer <= 21) {
    		bancajoga(false);
    	}
    	
    	
    }
    
    private int  sorteio() {
    	boolean igual = true;
    	int carta=0;int pontos=0;
    	while (igual) {
    		igual = false;
    		carta = aleatorio(1, cartas.length);
    		carta = carta - 1;
    		
    		for (Iterator<Integer> iterator = cartasSorteadasPlayer.iterator(); iterator.hasNext();) {
				Integer cartaSorteada = (Integer) iterator.next();
				if (carta == cartaSorteada.intValue()) {
    				igual = true;
    				break;
    			}
			}
    		
    	}
    	
    	pontos = verpontos(carta);
    	somaplayer = somaplayer + pontos;
    	cartasSorteadasPlayer.add(carta);
    	
    	return carta;	
    }

    private int sorteiobanca() {
    	boolean igual=true;
    	int carta=0;int pontos=0;
    	
    	while (igual) {
    		igual = false;
    		carta = aleatorio(1, cartas.length);
    		carta = carta - 1;
    		for (Iterator<Integer> iterator = cartasSorteadasBanca.iterator(); iterator.hasNext();) {
				Integer cartaSorteada = (Integer) iterator.next();
				if (carta == cartaSorteada.intValue()) {
	    			igual = true;
	    			break;
	    		}
			}
    		
    	}
    	
    	pontos = verpontos(carta);
    	somabanca = somabanca +  pontos;
    	cartasSorteadasBanca.add(carta);
    	
    	return carta;	
    }
    
    private void bancajoga(boolean direto) {
    	
    	Resources res = this.context.getResources();
    	String text = "";
    	
    	// true significa que o jogador já perdeu, logo, 
    	// a banca deve jogar direto até ganhar ou parar
    	do {
    		if (fim) {
    			return;
    		}
    		//A banca ainda está no páreo! Tem que decidir se pede ou não...

    		if (somabanca < somaplayer) {
    			//Se o jogador fez 21 ou mais, a banca não joga mais...
    			if ((21 - somabanca) < 3) {
    				//vai jogar dados para ver se pede mais cartas...
    				if (aleatorio(1,10) <= 5) {
    					// vai jogar
    					jogar();
    				}
    			}
    			else {
    				//Vai pedir mais cartas com certeza
    				jogar();
    			}
    		}
    		else {
    			// Se ele parou e nós temos mais pontos, então ganhamos
    			if ((somabanca > somaplayer) && direto) {
    				String msgBanca = res.getString(R.string.msg_banca_ganha_21);
    				String pontos = res.getString(R.string.msg_21_pontos);
    				text = msgBanca + " " + somabanca + " " + pontos;
    				BlackJack.sr.stopListening();
    	    		BlackJack.gameover =1;

            		message = new Message(text);
    				fim = true;
    				return;
    			}
    			// Se estamos iguais, vamos jogar
    			jogar();
    		}
    		
    		// Se jogamos, temos que saber o resultado
    		if (somabanca == 21) {
    			text = res.getString(R.string.msg_banca_ganhou);
    			BlackJack.sr.stopListening(); 
        		BlackJack.gameover =1;

        		message = new Message(text);
    			fim = true;
    			return;
    		}
    		else {
    			if (somabanca > 21) {
    			   
    			   text = res.getString(R.string.msg_banca_perdeu);
    			   BlackJack.sr.stopListening();
    	    		BlackJack.gameover =1;

           		   message = new Message(text);
       			   
    			   fim = true;
    			   return;
    			}
    		}
    	}
    	while (direto);
    }
    
    private void jogar() {

    	indicebanca++;
    	int carta = sorteiobanca();
    	cartaBanca = cartas[carta];
    	cartasSorteadasStand.add(cartaBanca);
    	Resources res = this.context.getResources();
    	
    	if (somabanca == 21) {
    	   String text = res.getString(R.string.msg_banca_venceu);
    	   BlackJack.sr.stopListening(); 
   		BlackJack.gameover =1;

    	   message = new Message(text);
			   
    	}
    	
    }
    
    private int verpontos(int carta) {
    	
    	String c = cartasStr[carta];
    	System.out.println(c);
    	int pontos = 10;
    	
    	if (c.indexOf("dois",0) >=0) {
    		pontos = 2;
    	}else if (c.indexOf("tres",0) >=0) {
    	     pontos = 3;
    	}else if (c.indexOf("quatro",0) >=0) {
    	     pontos = 4;
    	}else if (c.indexOf("cinco",0) >=0) {
    	     pontos = 5;
    	}else if (c.indexOf("seis",0) >=0) {
    	     pontos = 6;
    	}else if (c.indexOf("sete",0) >=0) {
    	     pontos = 7;
    	}else if (c.indexOf("oito",0) >=0) {
    		pontos = 8;
    	}else if (c.indexOf("nove",0) >=0) {
    	    pontos = 9;
    	}else if (c.indexOf("as_",0) >=0) {
    	    pontos = 1;
    	}
    	
    	return pontos;
    }

	public Integer getCartaBanca() {
		return cartaBanca;
	}

	public void setCartaBanca(Integer cartaBanca) {
		this.cartaBanca = cartaBanca;
	}

	public Integer getCartaPlayer() {
		return cartaPlayer;
	}

	public void setCartaPlayer(Integer cartaPlayer) {
		this.cartaPlayer = cartaPlayer;
	}

	public int getSomabanca() {
		return somabanca;
	}

	public void setSomabanca(int somabanca) {
		this.somabanca = somabanca;
	}

	public int getSomaplayer() {
		return somaplayer;
	}

	public void setSomaplayer(int somaplayer) {
		this.somaplayer = somaplayer;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public ArrayList<Integer> getCartasSorteadasStand() {
		return cartasSorteadasStand;
	}

	public void setCartasSorteadasStand(ArrayList<Integer> cartasSorteadasStand) {
		this.cartasSorteadasStand = cartasSorteadasStand;
	}
	
}
