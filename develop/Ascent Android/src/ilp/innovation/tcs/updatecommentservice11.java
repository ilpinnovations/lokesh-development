package ilp.innovation.tcs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class updatecommentservice11  extends AsyncTask<String,Void,String> {
	
	   private Context context;
	   private int byGetOrPost = 0; 
	   //flag 0 means get and 1 means post.(By default it is get.)
	   Context con;
	   public updatecommentservice11 (Context context) {
	      
	    con=context;
	   }


	protected void onPreExecute(){

	   }
	int fromservice=0;
	   @Override
	   protected String doInBackground(String... arg0) {
		   
	   
	    
	         try{
	        	 
	  				
	  			
	            String link="http://theinspirer.in/ascent?action=setFeedback&schedId="+arg0[0]+"&empId="+arg0[1]+"&rating="+arg0[2]+"&comments="+arg0[3].replace(" ","%20");

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
		   
		rating.eventappointmentbook.setText(result);
		Log.d("done---->","executed");
	   }
	   
	  
}