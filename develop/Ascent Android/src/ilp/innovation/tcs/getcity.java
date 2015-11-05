package ilp.innovation.tcs;
/*
 * Author:Lokesh Deshmukh
 * Year:2015
 * Date:25-Sep-2015
 */



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.R.layout;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class getcity  extends AsyncTask<String,Void,String> {
	
	   private Context context;
	   private int byGetOrPost = 0; 
	   //flag 0 means get and 1 means post.(By default it is get.)
	   Context con;
	   public getcity (Context context) {
	      
	    con=context;
	   }


	protected void onPreExecute(){

	   }
	int fromservice=0;
	   @Override
	   protected String doInBackground(String... arg0) {
		   
	   
	    
	         try{
	          
	            String link="http://theinspirer.in/ascent/?action=getRegion";

	            Log.d("myTag", link);	            
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
	            Log.d("myTag", sb.toString());	
	            return sb.toString();
	         }catch(Exception e){
	            return new String(e.getMessage()+"Exception: null");
	         }
	      }
	   
	   @Override
	   protected void onPostExecute(String result){
      MainActivity.eventappointmentbook1.setText(result);
			 
		//backgroundservice.eventappointmentbook.setText(result);
	   }
	   
	  
}
