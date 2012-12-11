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
package br.com.thecodebakers.blackjack.activities;

import java.util.ArrayList;
import java.util.List;

//import speech.recog.SpeechActivity;


//import com.google.ads.AdRequest;
//import com.google.ads.AdView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.thecodebakers.blackjack.R;
import br.com.thecodebakers.blackjack.business.BlackBO;
import android.widget.AdapterView.OnItemClickListener;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;

public class BlackJack extends Activity implements OnClickListener{
	
	private BlackBO blackBO;
	private TextView mText;
	public static SpeechRecognizer sr;
	private static final int VR_REQUEST = 999;
	private static final String TAG = "BlackJack";
	
	private int posicaoCartaBanca=0;
	private int posicaoCartaPlayer=0;
	private int somabanca =0;
	private int somaplayer=0;
	private Integer cartaBanca ;
	private Integer cartaPlayer;
	private boolean som=true;
	private MediaPlayer media = null;
	public static Intent intent1 ;
	public static int gameover =0;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
//        try{
//        	media = MediaPlayer.create(this.getApplicationContext(), R.raw.audio);
//        	media.start();
//        }catch  (Exception e){
//        	e.printStackTrace();
//        }
        
        blackBO = BlackBO.getInstance(this.getApplicationContext());
        //AdView adView = (AdView)this.findViewById(R.id.adView);
        //adView.loadAd(new AdRequest());
        PackageManager packManager = getPackageManager();
        List<ResolveInfo> intActivities = packManager.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (intActivities.size() != 0) {
            //speech recognition is supported - detect user button clicks
            //speechBtn.setOnClickListener(this);
        	startrecog();
//            sr = SpeechRecognizer.createSpeechRecognizer(this);       
//            sr.setRecognitionListener(new listener());  
        }
        else
        {
            //speech recognition not supported, disable button and output message
            
            Toast.makeText(this, "Oops - Speech recognition not supported!", Toast.LENGTH_LONG).show();
        }

               
    }
	
	private void startrecog() {
		// TODO Auto-generated method stub
		sr = SpeechRecognizer.createSpeechRecognizer(BlackJack.this.getApplicationContext());       
        sr.setRecognitionListener(new listener());
	}

	class listener implements RecognitionListener          
    {
             public void onReadyForSpeech(Bundle params)
             {
                      Log.d(TAG, "onReadyForSpeech");
             }
             public void onBeginningOfSpeech()
             {
                      Log.d(TAG, "onBeginningOfSpeech");
             }
             public void onRmsChanged(float rmsdB)
             {
                   //   Log.d(TAG, "onRmsChanged");
             }
             public void onBufferReceived(byte[] buffer)
             {
                     // Log.d(TAG, "onBufferReceived");
             }
             public void onEndOfSpeech()
             {
                      Log.d(TAG, "onEndofSpeech");
             }
             public void onError(int error)
             {		//  sr.stopListening();
            	 if(error ==8){
            		 Log.d(TAG,  "error 8 8 8 8 " +  error);
            		 sr.destroy();
            		 startrecog();
            		 
            	 }
                      Log.d(TAG,  "error " +  error);
                      listenToSpeech();
                      //wordList.setText("error " + error);
             }
             public void onResults(Bundle results)                   
             {
            	 sr.stopListening(); 
                      String str = new String();
                      Log.d(TAG, "onResults " + results);
                      ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                      View myview;
                      int flag =0;
                      for (int i = 0; i < data.size(); i++)
                      {
                                Log.d(TAG, "result " + data.get(i));
                                str += data.get(i);
                                if(data.get(i).contains("hit") && data.get(i).contains("stand")){
                                	Log.d(TAG, "listening1");
                                flag =1;
                                Toast.makeText( BlackJack.this, "You cannot say both hit and miss. Say one", Toast.LENGTH_LONG).show();
                                break;
                                }
                                else if(data.get(i).contains("hit")){
                                	Log.d(TAG, "listening2");
                                	flag=0;
                               myview = (View) BlackJack.this.findViewById(R.id.btnHitMe);
                                 hitMe(myview);
                                 break;
                                 }
                                else if (data.get(i).contains("stand")){
                                	Log.d(TAG, "listening3");
                                	flag=0;
                                	myview = (View) BlackJack.this.findViewById(R.id.btnStand);
                                	stand(myview);
                                	break;
                                }
                                else {
                                	Log.d(TAG, "listening4");
                                	flag =1;
                                }
                                
                      }
                      if(flag ==1){
                    	  Log.d(TAG, "listening5");
                    	  Toast.makeText( BlackJack.this, "Couldnt recognize command. Say hit or stand", Toast.LENGTH_LONG).show();
                    	  listenToSpeech();
                      }
                      //mText.setText("results: "+String.valueOf(data.size()));
                      
                      //wordList.setAdapter(new ArrayAdapter<String> (SpeechActivity.this, R.layout.word, data));
                      
                      
             }
             public void onPartialResults(Bundle partialResults)
             {
                      Log.d(TAG, "onPartialResults");
             }
             public void onEvent(int eventType, Bundle params)
             {
                      Log.d(TAG, "onEvent " + eventType);
             }
    }
    
	@Override
	protected void onStart() {
    	super.onStart();
    	Log.d(TAG, "Very first");
    	listenToSpeech();
//    	intent1 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);        
//        intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent1.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");
//
//        intent1.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,10); 
//         sr.startListening(intent1);
	}
    private void listenToSpeech() {
    	Log.d(TAG, "listening -- speak now");
    	try {
			Thread.sleep(500);
			Toast.makeText( BlackJack.this, "Talk now", Toast.LENGTH_LONG).show();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	intent1 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);        
        intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent1.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");

        intent1.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,10); 
         sr.startListening(intent1);
    }
    @Override
	public void onBackPressed() {
		moveTaskToBack(true);
	}
    /**
     * button hitme
     * @param view
     */
    public void hitMe (View view) {
    	Log.d(TAG, "hit Me if u are the player..."); 
    	sr.stopListening(); 
    	blackBO.hitme();
    	cartaBanca = blackBO.getCartaBanca();
    	cartaPlayer = blackBO.getCartaPlayer() ; 
    	    	
    	Log.d(TAG, "imageView cartaPlayer ID" + cartaPlayer);
    	Log.d(TAG, "imageView cartaBanca ID" + cartaBanca);
    	
    	String pontos = null;
    	
    	//Cartas da Banca
    	if (cartaBanca  != null){
    		
    		ImageView imageView = (ImageView) this. findViewById(posicaoCartasBanca[posicaoCartaBanca]);
	    	imageView.setImageResource(cartaBanca);
	    	Animation cartaAnim1 = AnimationUtils.loadAnimation(BlackJack.this, R.anim.carta_anim);
			imageView.setAnimation(cartaAnim1);
			cartaAnim1.startNow();
			posicaoCartaBanca++;
			
			//seta o total de pontos da banca
			TextView pontosBanca = (TextView) this.findViewById(R.id.textViewPntBanca); 
			this.somabanca = blackBO.getSomabanca();
			pontos = String.valueOf( somabanca );
			pontosBanca.setText(pontos);
			
    	}
    	
		//Suas cartas
    	if (cartaPlayer != null){
    		
			ImageView imageView = (ImageView) this. findViewById(posicaoCartasPlayer[posicaoCartaPlayer]);//R.id.img1Grid2
    		imageView.setImageResource(cartaPlayer);
    		Animation cartaAnim2 = AnimationUtils.loadAnimation(BlackJack.this, R.anim.carta_anim2);
			imageView.setAnimation(cartaAnim2);
			cartaAnim2.startNow();
			posicaoCartaPlayer++;
			
			//seta o total de pontos do jogador
			TextView pontosPlayer = (TextView) findViewById(R.id.textViewPntSuasCartas); 
			this.somaplayer = blackBO.getSomaplayer();
			pontos = String.valueOf( somaplayer );
			pontosPlayer.setText(pontos);
    	}
    	
		if (blackBO.getMessage() != null){
			this.mensagem (blackBO.getMessage().getText());
		}
    	Log.d(TAG, "End of hitMe ");
    	if(gameover !=1){gameover =0;listenToSpeech();}
    	//   	listenToSpeech();
  //  	sr.startListening(intent1);
    	
	}
    /**
     * Button stand
     * @param view
     */
    public void stand (View view) {
   // 	sr.stopListening(); 
    	Log.d(TAG, "stand the game..."); 
    	blackBO.stand();
    	String pontos = null;
    	TextView pontosBanca = null;
    	ArrayList<Integer> cartaBancaStand = blackBO.getCartasSorteadasStand();
    	
    	//Cartas da Banca
    	for (Integer cartaStand : cartaBancaStand) {
    		
    		if (cartaStand  != null){
        		ImageView imageView = (ImageView) this. findViewById(posicaoCartasBanca[posicaoCartaBanca]);
    	    	imageView.setImageResource(cartaStand);
    	    	Animation cartaAnim1 = AnimationUtils.loadAnimation(BlackJack.this, R.anim.carta_anim);
    			imageView.setAnimation(cartaAnim1);
    			cartaAnim1.startNow();
    			posicaoCartaBanca++;
    			//seta o total de pontos da banca
    			pontosBanca = (TextView) this.findViewById(R.id.textViewPntBanca); 
    			this.somabanca = blackBO.getSomabanca();
    			pontos = String.valueOf( somabanca );
    			pontosBanca.setText(pontos);
    			
        	}
        	
		}
    	    	
    	if (blackBO.getMessage() != null){
			this.mensagem (blackBO.getMessage().getText());
		}
    	if(gameover !=1){gameover =0;listenToSpeech();}
    	//sr.startListening(intent1);
    	//listenToSpeech();
	}
    protected void onStop(){
    	sr.stopListening();
    	//sr.destroy();
    	super.onStop();
    	    }
    protected void onDestroy(){
    	sr.stopListening();
    	//sr.destroy();
    	super.onDestroy();

    	    }
    
    /**
     * button new game
     * @param view
     */
    public void novoJogo (View view) {
    	Log.d(TAG, "starting new game...");
    	reloadActivity();
    	Log.d(TAG, "game started."); 
	}
    
    private void reloadActivity(){
    	sr.cancel();
    	startrecog();
    	Log.d(TAG, "reloading activity...");
    	posicaoCartaBanca=0;
    	posicaoCartaPlayer=0;
    	somabanca =0;
    	somaplayer=0;
    	this.cartaBanca=0;
    	this.cartaPlayer=0;
    	
    	blackBO.novo();
    	Intent i = new Intent(this, BlackJack.class);
    	startActivity(i);
		this.finish();
    }
    
    //array de inteiros com as imagens das cartas da banca
    private Integer[]  posicaoCartasBanca = {
	   		 R.id.img1Grid1, R.id.img2Grid1, R.id.img3Grid1, 
	   		 R.id.img4Grid1, R.id.img5Grid1, R.id.img6Grid1, 
	   		 R.id.img7Grid1, R.id.img8Grid1, R.id.img9Grid1, 
	   		 R.id.img10Grid1
    };

	//array de inteiros com as imagens das cartas do jogador
	private Integer[]  posicaoCartasPlayer = {
			 R.id.img1Grid2, R.id.img2Grid2, R.id.img3Grid2, 
	   		 R.id.img4Grid2, R.id.img5Grid2, R.id.img6Grid2, 
	   		 R.id.img7Grid2, R.id.img8Grid2, R.id.img9Grid2, 
	   		 R.id.img10Grid2
	};

	private void mensagem (String texto) {
    	new AlertDialog.Builder(this).setMessage(texto)
        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	reloadActivity();
            	return;
            } 
         }).show();     	
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_main, menu);
	    return true;
	}

    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mnuAjuda:
			Intent i = new Intent (this.getApplicationContext(), Ajuda.class);
			this.startActivity(i);
			return true;
		default: return super.onOptionsItemSelected(item); 
		}
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		super.onDestroy();
    	sr.stopListening();
//		if (media != null) {
//			if (media.isPlaying()) {
//				media.pause();
//			}
//		}
		Log.d(TAG, "pause the sound.");
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		if (media != null) {
//			if (!media.isPlaying()) {
//				media.start();
//			}
//		}	
		Log.d(TAG, "play the sound.");
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}