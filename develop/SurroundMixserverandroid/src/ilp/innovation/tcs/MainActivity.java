package ilp.innovation.tcs;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
 
public class MainActivity extends Activity implements OnClickListener,
OnPreparedListener, OnErrorListener, OnCompletionListener {
	
	
	AudioManager audio;
    MediaPlayer mp;
    ProgressDialog pd;
    public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.register);
//    Button bt = (Button)findViewById(R.id.buttonplay);
 //   bt.setOnClickListener(this);
	
    
    audio=(AudioManager)getSystemService(Context.AUDIO_SERVICE);

 
    
// new socket(this).execute("");
    
     new updatecommentservice12(this).execute("http://192.168.1.103/startsong.php?playmusic=1");
 new  volume(this).execute("http://192.168.1.103/volume.php");
}
 
   @Override
   public void onPrepared(MediaPlayer mp) {
       Log.i("StreamAudioDemo", "prepare finished");
       pd.setMessage("Playing.....");
       pd.cancel();
       mp.start();
       mp.pause();
       new updatecommentservice12(this).execute("http://192.168.1.103/startsong.php?playmusic=1");
      // mp.start();
  }
 
 
  public void buffer() {
       try
        {
            pd = new ProgressDialog(this);
            pd.setMessage("Buffering.....");
            pd.show();
            mp = new MediaPlayer();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setOnPreparedListener(this);
            mp.setOnErrorListener(this);
            mp.setDataSource("http://192.168.1.103/song.mp3");
            mp.prepareAsync();
            mp.setOnCompletionListener(this);
        }
        catch(Exception e)
        {
            Log.e("StreamAudioDemo", e.getMessage());
        }
    }
 
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
      pd.dismiss();
      return false;
    }
 
    @Override
    public void onCompletion(MediaPlayer mp) {
        pd.dismiss();
        Toast.makeText(getApplicationContext(), "Completed", Toast.LENGTH_LONG).show();     
    }
    int temp=0;
    int decider=0;
    int volumedecider=0;
    int timescalled=0;
    public void gotresult(String result)
    {
    	if(result.trim().equals("stop"))
    	{
    		
    		temp=0;
    		if(mp!=null)
    		{	
    			if(mp.isPlaying()==true)
    				mp.stop();
    		mp=null;
    		}
    	}
    	else
    	{
    	
    	if(result.trim().equals("wait")&&temp==0)
    	{
            mp=null;
    		buffer();
    		temp=1;
    	}
    	
      if(result.trim().equals("three")||result.trim().equals("all"))
       {

    	  if(mp!=null)
    	  if(mp.isPlaying()==true)
    	  {
    		  volumedecider=1;
         	// audio.setStreamVolume(AudioManager.STREAM_MUSIC, 50, 0);
         	//int media_length=mp.getCurrentPosition(); 
             // mp.seekTo(media_length); 
  		     // Toast.makeText(getApplicationContext(), "Volume Up", Toast.LENGTH_SHORT).show();
        // mp.setVolume(1,1);
    	  }
    	  else
    	  {
    		     Toast.makeText(getApplicationContext(), "Start Called="+timescalled, Toast.LENGTH_SHORT).show();
    	        	  timescalled++;
    		  mp.start();
    	  }

       }
      else if(result.trim().equals("one")||result.trim().equals("four")||result.trim().equals("two"))
      {

    	  if(mp!=null)
    	  {
    		  
            if(mp.isPlaying()!=false)
            {
volumedecider=0;
                  mp.setVolume(0, 0);
        //    	  mp.pause();
                      	 
             }
             else
   	         {
   		      Toast.makeText(getApplicationContext(), "started", Toast.LENGTH_SHORT).show();
   		      mp.start();
   	         }
    	  }
    	  
      }
      else
      {
    	  if(mp!=null)
    	  {
         if(mp.isPlaying()!=false)
         {


    	  mp.pause();
    	 
         }
    	  }
      }
    	}
      new  updatecommentservice12(this).execute("http://192.168.1.103/startsong.php?playmusic=1");
    }
    public void volume( String result)
    {
    if(volumedecider==1)
    {
    	try
    	{
    	mp.setVolume(Float.parseFloat(result.trim()),Float.parseFloat(result.trim()));
    	}
    	catch(Exception e)
    	{
    		
    	}
    }
    	
        new  volume(this).execute("http://192.168.1.103/volume.php");
    }

    public class updatecommentservice12  extends AsyncTask<String,Void,String> {
    	
 	   private Context context;
 	   private int byGetOrPost = 0; 
 	   //flag 0 means get and 1 means post.(By default it is get.)
 	   Context con;
 	   public updatecommentservice12 (Context context) {
 	      
 	    con=context;
 	   }


 	protected void onPreExecute(String data){
 		
 		
 	   }
 	int fromservice=0;
 	   @Override
 	   protected String doInBackground(String... arg0) {
 		   
 	   
 	    
 	         try{
 	        	 
 	  				
 	  			
 	            String link=arg0[0];

 	            Log.d("myTag", link);	            
 	            URL url = new URL(link.trim().replace(" ", "%20"));
 	            URLConnection conn = url.openConnection(); 
 	            conn.setDoOutput(true); 
 	            OutputStreamWriter wr = new OutputStreamWriter
 	            (conn.getOutputStream()); 
 	           
 	            wr.flush(); 
 	            BufferedReader reader = new BufferedReader
 	            (new InputStreamReader(conn.getInputStream()));
 	            StringBuilder sb = new StringBuilder();
 	            String line = null;
 	            // Read Server Response
 	            while((line = reader.readLine()) != null)
 	            {
 	               sb.append(line);
 	               break;
 	            }
 	            return sb.toString();
 	         }catch(Exception e){
 	            return new String(e.getMessage()+"Exception: null");
 	         }
 	      }
 	   
 	   @Override
 	   protected void onPostExecute(String result){
 		   
 		gotresult(result);
 		Log.d("done---->","executed");
 	   }
 	   
 	  
 }
    public class volume  extends AsyncTask<String,Void,String> {
    	
  	   private Context context;
  	   private int byGetOrPost = 0; 
  	   //flag 0 means get and 1 means post.(By default it is get.)
  	   Context con;
  	   public volume (Context context) {
  	      
  	    con=context;
  	   }


  	protected void onPreExecute(String data){
  		
  	
  	   }
  	int fromservice=0;
  	   @Override
  	   protected String doInBackground(String... arg0) {
  		   
  	   
  	    
  	         try{
  	        	 
  	  				
  	  			
  	            String link=arg0[0];

  	            Log.d("myTag", link);	            
  	            URL url = new URL(link.trim().replace(" ", "%20"));
  	            URLConnection conn = url.openConnection(); 
  	            conn.setDoOutput(true); 
  	            OutputStreamWriter wr = new OutputStreamWriter
  	            (conn.getOutputStream()); 
  	           
  	            wr.flush(); 
  	            BufferedReader reader = new BufferedReader
  	            (new InputStreamReader(conn.getInputStream()));
  	            StringBuilder sb = new StringBuilder();
  	            String line = null;
  	            // Read Server Response
  	            while((line = reader.readLine()) != null)
  	            {
  	               sb.append(line);
  	               break;
  	            }
  	            return sb.toString();
  	         }catch(Exception e){
  	            return new String(e.getMessage()+"Exception: null");
  	         }
  	      }
  	   
  	   @Override
  	   protected void onPostExecute(String result){
  		   
  		volume(result);
  		Log.d("done---->","executed");
  	   }
  	   
  	  
  }
    public class socket  extends AsyncTask<String,Void,String> {
    	
   	   private Context context;
   	   private int byGetOrPost = 0; 
   	   //flag 0 means get and 1 means post.(By default it is get.)
   	   Context con;
   	   public socket (Context context) {
   	      
   	    con=context;
   	   }


   	protected void onPreExecute(String data){
   		
   	   Toast.makeText(getApplicationContext(),"hii", Toast.LENGTH_SHORT).show();
   	   }
   	int fromservice=0;
   	   @Override
   	   protected String doInBackground(String... arg0) {
   		   
   	   String data="";
   		 data="1";
   		   String serverName = "192.168.1.103";
   	    int port = Integer.parseInt("10011");
   	    try
   	    {
   	    while(true)
   	    {
   	       System.out.println("Connecting to " + serverName +
   			 " on port " + port);
   	   
   	       Socket client = new Socket(serverName, port);
   	       System.out.println("Just connected to " 
   			 + client.getRemoteSocketAddress());
   	       OutputStream outToServer = client.getOutputStream();
   	       DataOutputStream out = new DataOutputStream(outToServer);
   	       out.writeUTF("Hello from "
   	                    + client.getLocalSocketAddress());
   	       InputStream inFromServer = client.getInputStream();
   	       DataInputStream in =
   	                      new DataInputStream(inFromServer);
   	      // System.out.println("Server says " + in.readUTF());
   	       data=in.readUTF();
   	       client.close();
   	       if(data.equals("1"))
   	    	   break;
   	    }
   	    }catch(IOException e)
   	    {
   	      return e.getMessage();
   	    }
   	    return data;
   	      }
   	   
   	   @Override
   	   protected void onPostExecute(String result){
   		   
Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
   		//Log.d("done---->",result);
   	   }
   	   
   	  
   }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}