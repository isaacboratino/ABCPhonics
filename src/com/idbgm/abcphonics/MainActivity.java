package com.idbgm.abcphonics;

import java.util.HashMap;
import java.util.Map;

import com.idbgm.abcphonics.R;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends Activity {	 
	
	SoundPool sound = null;
	MediaPlayer player = null;
	VideoView mVideoView = null;
	Map<String, Integer> aMap = new HashMap<String, Integer>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	

		LoadSounds();

		// Inicializa video
		if (mVideoView == null) 
		{
        	mVideoView = (VideoView)findViewById(R.id.videoView);
        	mVideoView.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}		
	
	public void onClickSelectLanguage(View v) {		
		final int id = v.getId();
		SelectLanguage(id);
	}
	
	public void LoadSounds() {	
		
		try {										 
			
		    aMap.clear();
		    
	    	sound = null;
	    	sound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);		    
		    
	    	// Load all letters
		    for (int i = 65; i <= 90; i++ ) {
		    	String letter = ((char)i)+"";
		    	String file = "sounds/phonics/" + letter + ".mp3";
    			AssetFileDescriptor soundFile = getAssets().openFd(file);	    		    	    		    	    
    			aMap.put(letter, sound.load(soundFile , 1));
    			soundFile.close();
    		}		    		   
		    
		} catch(Exception e) {			
		}
	}
	
	public void SelectLanguage(int id) {	
		
		try {
			
		    switch(id) {
		    	case R.id.btPTBR :
		    		playMusic("pho1");
		    		break;
		    	case R.id.btENEN :
		    		playVideo(R.raw.phovidheetee);
		    		break;
		    	case R.id.btESES :
		    		playVideo(R.raw.phovidpancake);
		    		break;
		    	default :

		    }		   
		    
		} catch(Exception e) {			
		}
	}
	
	public void onClick(View v) {		
	    final int id = v.getId();
	    Button b = (Button)v;
	    	    
	    playBeep(b.getText().toString());
	} 
	
	public void playMusic(String soundName) {
			
	    try {
	    	
	    	this.stopAll();
	    	
	    	String file = "sounds/musics/" + soundName + ".mp3";
	    	AssetFileDescriptor afd = getAssets().openFd(file);
	    	player = new MediaPlayer();
		    player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
		    player.prepare();
		    player.start();
		    
	        
	    } catch (Exception e) {}
	}
	
	public void playBeep(String soundName) {
		
	    try {
	    	
	    	if (player != null)
	    		if (player.isPlaying())
	    			player.stop();
	    	
	    	if (sound == null)
	    		sound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

	    	sound.play(aMap.get(soundName), 1f, 1f, 0, 0, 1f);
	    		        	       
	    } catch (Exception e) {}
	}
	
	public void playVideo(int soundName) {
		
	    try {
	    	
	    	stopAll();
	    	
		    //	Displays a video file.
	        //String uriPath = "android.resource://com.idbgm.abcphonics/"+R.raw.phovid;	        
	    	String uriPath = "android.resource://com.idbgm.abcphonics/"+soundName;
	        Uri uri = Uri.parse(uriPath);
	        
	        if (mVideoView == null) 
	        	mVideoView = (VideoView)findViewById(R.id.videoView);
	        	
	        mVideoView.setVideoURI(uri);
	        
	        MediaController mc = new MediaController(this);
	        mc.setAnchorView(mVideoView);
	        mc.setMediaPlayer(mVideoView);
	        
	        mVideoView.setMediaController(mc);
	        mVideoView.setVisibility(View.VISIBLE);
	        mVideoView.start();
	        
	    } catch (Exception e) {}
	}
	
	private void stopAll()
	{
		// stop audio
		if (player == null)
    		player = new MediaPlayer();
    	
    	if (player.isPlaying())
    		player.stop();
    	
    	// stop video
    	if (mVideoView != null)
    	{
    		if (mVideoView.isPlaying())
    		{    			
    			mVideoView.stopPlayback();
    			mVideoView.setVisibility(View.INVISIBLE);
    		}
    	}
	}	
}
