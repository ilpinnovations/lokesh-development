package ilp.innovation.tcs;
/*
 * Author:Lokesh Deshmukh
 * Year:2015
 * Date:25-Sep-2015
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import android.app.Dialog;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class backgroundservice extends Service {

	
	
    
    @Override
    public void onCreate() {
       super.onCreate(); // if you override onCreate(), make sure to call super().
       // If a Context object is needed, call getApplicationContext() here.
     
    }

  
	
	Thread main;
	static String empid="";
	

	
	public static String current="";
	public String read()
	{
try
{
        FileInputStream fIn = openFileInput("comment.txt");
        InputStreamReader isr = new InputStreamReader(fIn);

        /* Prepare a char-Array that will
         * hold the chars we read back in. */
        char[] inputBuffer = new char[2024];
        

        // Fill the Buffer with data from the file
       
        	isr.read(inputBuffer);
        
        // Transform the chars to a String

        // Check if we read back the same chars that we had written out
    String readString=new String(inputBuffer);

        Log.i("File Reading stuff", "success = " + readString.trim());
        return readString.replaceAll("[^ -~]", "").trim();
}
catch(Exception e)
{

}
return null;
	}
	public void write(String data)
	{
		try { 
		       // catches IOException below
		       

		       /* We have to use the openFileOutput()-method
		       * the ActivityContext provides, to
		       * protect your file from others and
		       * This is done for security-reasons.
		       * We chose MODE_WORLD_READABLE, because
		       *  we have nothing to hide in our file */             
		       FileOutputStream fOut = openFileOutput("comment.txt",
		                                                            MODE_WORLD_READABLE);
		       OutputStreamWriter osw = new OutputStreamWriter(fOut); 

		       // Write the string to the file
		       osw.write(data);

		       /* ensure that everything is
		        * really written out and close */
		       osw.flush();
		       osw.close();

		//Reading the file back...

		       /* We have to use the openFileInput()-method
		        * the ActivityContext provides.
		        * Again for security reasons with
		        * openFileInput(...) */


		    } catch (IOException ioe) 
		      {ioe.printStackTrace();}
	}

	

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		
		return null;
	}
	int mStartMode;

	   /** The service is starting, due to a call to startService() */
	   @Override
	   public int onStartCommand(Intent intent, int flags, int startId) {
		  // write("");
		   try
		   {
		   
			   if(read()!=null&&read().length()>10)
				{
		          String[] temp=read().split("xyzzyspoonshift2");
		          
		        	if(temp.length>0)  
		        	{
                     if(temp[0].length()>10)
                     {
		        	  String[] temp1=temp[0].split("xyzzyspoonshift1");
		        	  current=temp[0];
				       new updatecommentservice1(backgroundservice.this).execute(temp1[0],temp1[1],temp1[2],temp1[3]);
				  synchronized (main) {
					     main.wait();						
				                      }

                     }
		        	}  
		          
				}
			   else
				   new updatecommentservice1(backgroundservice.this).execute("","","","1");
		   }
		   catch(Exception e)
		   {
			   write("");
			   new updatecommentservice1(backgroundservice.this).execute("","","","1");
		   }
			return START_STICKY;
	   }

	   /** A client is binding to the service with bindService() */
	   boolean mAllowRebind;

	   /** Called when all clients have unbound with unbindService() */
	   @Override
	   public boolean onUnbind(Intent intent) {
	      return mAllowRebind;
	   }

	   /** Called when a client is binding to the service with bindService()*/
	   @Override
	   public void onRebind(Intent intent) {

	   }

	   /** Called when The service is no longer used and is being destroyed */
	   @Override
	   public void onDestroy() {

	   }
	   void callsomething(String data)
	   {
		   try
		   {
		   if(data.contains("Successfully registered the feedback")||data.contains("You can give feedback only"))
		   {
			  // Toast.makeText(getApplicationContext(), current, Toast.LENGTH_LONG).show();
			   current=current+"xyzzyspoonshift2";
			   Log.i("eventappointment",current);
			 String temp= read();
			 write(temp.replace(current, ""));
		   }
		   
		
		 
		 
		 if(read()!=null&&read().length()>10)
			{
	          String[] temp=read().split("xyzzyspoonshift2");
	          
	        	if(temp.length>0)  
	        	{
              if(temp[0].length()>10)
              {
	        	  String[] temp1=temp[0].split("xyzzyspoonshift1");
	        	  current=temp[0];
	        	  
			       new updatecommentservice1(backgroundservice.this).execute(temp1[0],temp1[1],temp1[2],temp1[3]);
	        	 
			  synchronized (main) {
				     main.wait();						
			                      }

              }
	        	}  
	          
			}
		 else
			   new updatecommentservice1(backgroundservice.this).execute("","","","1");
		 }
		   catch(Exception r)
		   {
			   new updatecommentservice1(backgroundservice.this).execute("","","","1");
		   }
	   }
	   public class updatecommentservice1  extends AsyncTask<String,Void,String> {
			
		   private Context context;
		   private int byGetOrPost = 0; 
		   //flag 0 means get and 1 means post.(By default it is get.)
		   Context con;
		   public updatecommentservice1 (Context context) {
		      
		    con=context;
		   }


		protected void onPreExecute(){

		   }
		int fromservice=0;
		   @Override
		   protected String doInBackground(String... arg0) {
			   
		    fromservice=Integer.parseInt(arg0[3]);
		    
		         try{
		        	 
		  				Thread.sleep(5000);
		  			
		            String link="http://theinspirer.in/ascent?action=setFeedback&schedId="+arg0[0]+"&empId="+arg0[3]+"&rating="+arg0[1]+"&comments="+arg0[2].replace(" ","%20");

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
			   
			 
				 
			callsomething(result);
		   }
		   
		  
	}

}
