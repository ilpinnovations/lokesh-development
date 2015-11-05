package com.ilp.innovations.myapplication.Fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class reserveslot extends AsyncTask<String,Void,String> {

	   private Context context;
	   private int byGetOrPost = 0;
	   //flag 0 means get and 1 means post.(By default it is get.)
	   public reserveslot(Context context) {
	      
	    
	   }


	protected void onPreExecute(){

	   }
	   @Override
	   protected String doInBackground(String... arg0) {
		   
	
	         try{
	            String name = arg0[0];
	            
	            String link="http://www.theinspirer.in/mlcpapp/reserveslot.php?slotId="+name;
	            Log.d("myTag", link);	            
	            URL url = new URL(link.trim().replace(" ", "xyzzyspoonshift1"));
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

		   SlotViewFragment.result.setText(result);
	   }

	   
	  
}
