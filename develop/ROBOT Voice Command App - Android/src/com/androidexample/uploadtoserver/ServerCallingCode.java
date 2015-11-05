package com.androidexample.uploadtoserver;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;



import android.R.layout;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class ServerCallingCode  extends AsyncTask<String,Void,String> {
	
	   private Context context;
	   private int byGetOrPost = 0; 
	   //flag 0 means get and 1 means post.(By default it is get.)
	   public ServerCallingCode (Context context) {
	      
	    
	   }


	protected void onPreExecute(){

	   }
	int decider=0;
	   @Override
	   protected String doInBackground(String... arg0) {
		   
	
	         try{
	     
	            String link= "http://192.168.1.101/setcondition.php?condition="+arg0[0];
	    	            	            
	            URL url = new URL(link);
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
	        	 e.printStackTrace();
	  	       Log.d("Exception",e.getMessage());
	            return new String(e.getMessage()+"Exception: null");
	         }
	      }
	   
	   @Override
	   protected void onPostExecute(String result){


	   }
	   
	  
}
