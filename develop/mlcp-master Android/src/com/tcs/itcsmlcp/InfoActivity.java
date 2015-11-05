package com.tcs.itcsmlcp;

import static com.tcs.itcsmlcp.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.tcs.itcsmlcp.CommonUtilities.EXTRA_MESSAGE;
import static com.tcs.itcsmlcp.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.tcs.itcsmlcp.CommonUtilities.SENDER_ID;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcs.itcsmlcp.MainActivity;
import com.tcs.itcsmlcp.ServerUtilities;
import com.tcs.itcsmlcp.WakeLocker;
import com.google.android.gcm.GCMRegistrar;
import com.tcs.itcsmlcp.AlertDialogManager;
import com.tcs.itcsmlcp.ConnectionDetector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends Activity implements OnClickListener,OnItemSelectedListener {

	//************************
	// label to display gcm messages
		TextView lblMessage;
		
		// Asyntask
		AsyncTask<Void, Void, Void> mRegisterTask;
		
		// Alert dialog manager
		AlertDialogManager alert = new AlertDialogManager();
		
		// Connection detector
		ConnectionDetector cd;
		
		public static String name;
		public static String email;
	//*************************
	
	
	String serverURL_adminReg = AppConstant.URL;
//	String serverURL_adminReg =AppConstant.URL+"adminreg_json.php";
	
	 MyApp aController;
	 private ProgressDialog progress;
	 
	// UI elements
		EditText txtID;
		EditText txtName; 
		EditText txtEmail;
//		EditText txtPass;
//		EditText txtPassCnfrm;
		
//		CheckBox twoW,fourW;
		DatabaseHandler db;
		// Register button
		Button btnSub;
	 
		ImageButton btnBack;
		// Spinner element
		Spinner spinner;
		String loc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
		
		txtID = (EditText) findViewById(R.id.txtID);
		txtName = (EditText) findViewById(R.id.txtName);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		
		spinner = (Spinner) findViewById(R.id.spinner);
//		txtPass = (EditText) findViewById(R.id.txtPass);
//		txtPassCnfrm = (EditText) findViewById(R.id.txtPassCnfrm);
		btnSub = (Button) findViewById(R.id.btnSubmit);
		
		// Spinner click listener
				spinner.setOnItemSelectedListener(this);
		
	
		db = new DatabaseHandler(this);
		
		/*if(db.getContactsCount()>0)
		{
//			db.close();
			finish();
			Intent i=new Intent(this,MainActivity.class);
			startActivity(i);
			
			
		}*/
		
		loadSpinnerData();
		
		
		
		
		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(InfoActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}
		
		/*twoW=(CheckBox)findViewById(R.id.twoW);
		fourW=(CheckBox)findViewById(R.id.fourW);*/
		
		
		
		/* aController = (MyApp) getApplicationContext();
//		aController = (MyApp) getApplicationContext();
			// Check if Internet Connection present
			if (!aController.isConnectingToInternet()) {
				// Internet Connection is not present
				aController.showAlertDialog(InfoActivity.this,
						"Internet Connection Error",
						"Please connect to working Internet connection", false,InfoActivity.this);

						// stop executing code by return
//				finish();
			   return;
			   
			 
			 }
		*/
			/*else if(!GCMRegistrar.isRegisteredOnServer(this)) {
				
					Intent i=new Intent(this,RegisterActivity.class);
					startActivity(i);
					// Skips registration.				
					Toast.makeText(getApplicationContext(), "Please register yourself with TCS Park-IN Server", Toast.LENGTH_LONG).show();
				finish();
				} 		*/
	
			
			
			/*else{
		
new LongOperation_updateslot(serverURL_adminReg,this).execute("");
			}*/
			
			
			// Click event on Register button
			btnSub.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {  
					// Get data from EditText
					
					
					if((txtID.getText().toString().length()==0) || (txtID.getText().toString().length()<6) )//|| (txtID.getText().toString().length()>6
					{        	
						txtID.setError( "Please Enter Valid Employee ID" );
					}
					else if(txtName.getText().toString().length()==0)
					{        	
						txtName.setError( "Please Enter Name" );
					}
				    else if(!isValidName(txtName.getText().toString()))
					{
						 txtName.setError( "Please Enter Valid Name" );
					}
					/*else if(txtBatch.getText().toString().length()==0  )
					{   txtBatch.setError( "Please Enter Batch(LG)" ); 	
					
					
					}*/	
				    
					
					else if(txtEmail.getText().toString().length()==0  )
					{   txtEmail.setError( "Please Enter Email" ); 	
						
						
					}	
					
					else if(!isValidEmail(txtEmail.getText().toString()))
					{
						 txtEmail.setError( "Please Enter Valid Email" );
					}
					/*
					else  if((txtPass.getText().toString().length()==0) || (txtPass.getText().toString().length()<8))
					{        	
						txtPass.setError( "Please Enter Password of atleast 8 characters" );
					}
					else  if(!(txtPass.getText().toString()).equalsIgnoreCase(txtPassCnfrm.getText().toString()) )
					{        	
						txtPassCnfrm.setError( "Please Enter the Correct Password" );
					}*/
					/*else if(!(twoW.isChecked() || fourW.isChecked()))
					{
						 twoW.setError( "Please Select Vehicle Type" );
						 fourW.setError( "Please Select Vehicle Type" );
					}*/
					
					
					else{
					  // GET IMEI NUMBER      
					 TelephonyManager tManager = (TelephonyManager) getBaseContext()
					    .getSystemService(Context.TELEPHONY_SERVICE);
//					  String deviceIMEI = tManager.getDeviceId();
					  String devicePhn = tManager.getLine1Number();
//					  Toast.makeText(getApplicationContext(), devicePhn, Toast.LENGTH_LONG).show();
//					  int id=Integer.parseInt(txtID.getText().toString());
						String name = txtName.getText().toString(); 
						String id = txtID.getText().toString();
						String email = txtEmail.getText().toString();
//						String pass = txtPass.getText().toString();
						String pass = "passDemo";
					
		          /*  int chek_2W=0,chek_4W=0;		
					if(twoW.isChecked() && fourW.isChecked())
					{
						chek_2W=1;
						chek_4W=1;
					}
					else if(fourW.isChecked())
					{
						chek_4W=1;
					}
					else if (twoW.isChecked()) {
						chek_2W=1;
					}
*/					// Check if user filled the form
					if(name.trim().length() > 0 && email.trim().length() > 0){
						final String empName =name;
						final String empEmail = email;
						InfoActivity.name = name;
						InfoActivity.email = email;
						
						
						
						String org = "testorg";
						
						new LongOperation_reg(serverURL_adminReg,name,id,email,pass,InfoActivity.this).execute("");
						        
					        /**
					         * CRUD Operations
					         * */
					        // Inserting Contacts
					        Log.d("Insert: ", "Inserting ..");
					     
						
						
						
						
						
					}else{
						
						// user doen't filled that data
						aController.showAlertDialog(InfoActivity.this, "Error!", "Please enter your details", false,InfoActivity.this);
					}
					}
				}
			});
			
			
			
//			btnBack = (ImageButton) findViewById(R.id.btnImg_Back);
////			btnLike = (ImageButton) findViewById(R.id.btnImg_Like);
//
//			btnBack.setOnClickListener(this);
//			btnLike.setOnClickListener(this);

			
			
			
			
	}
	
	@Override 
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}
	
	/**
	 * Receiving push messages
	 * */
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
			
			/**
			 * Take appropriate action on this message
			 * depending upon your app requirement
			 * For now i am just displaying it on the screen
			 * */
			
			// Showing received message
			lblMessage.append(newMessage + "\n");			
			Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();
			
			// Releasing wake lock
			WakeLocker.release();
		}
	};
	
	
	/**
	 * Function to load the spinner data from SQLite database
	 * */
	private void loadSpinnerData() {
		// database handler
//		DatabaseHandler db = new DatabaseHandler(getApplicationContext());

		// Spinner Drop down elements
		List<String> lables = new ArrayList<String>();
          
		lables.add("Kochi");
		
		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, lables);

		// Drop down layout style - list view with radio button
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinner.setAdapter(dataAdapter);
	}

	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// On selecting a spinner item
		 loc = parent.getItemAtPosition(position).toString();

		// Showing selected spinner item
//		Toast.makeText(parent.getContext(), "You selected: " + label,
//				Toast.LENGTH_LONG).show();

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void onClick(View v) {
/*		if (v.getId() == R.id.btnImg_Back) {
			finish();
		}*/
		/*if (v.getId() == R.id.btnImg_Like) {

		}*/

	}
	private boolean isValidEmail(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	private boolean isValidName(String name) {
		String NAME_PATTERN = "[A-Za-z-\\s]*";
 
		Pattern pattern = Pattern.compile(NAME_PATTERN);
		Matcher matcher = pattern.matcher(name);
		return matcher.matches();
	}
	
	
	

	  
	  
	  //+++++++++++++++++++++++++++++++++++++++++++++++++++
		
		 // Class with extends AsyncTask class
	  private class LongOperation_reg  extends AsyncTask<String, Void,String> {
	       String _url,_adminname,_adminemail,_adminorg,_adminpass;
	       Activity __context;
//	       int _vtype_4w,_empid,_vtype_2w;
	       String _id;
	       
	     
	//System.out.print(dateFormat.format(date)); //2014/08/06 15:59:48

	       
	  	// Required initialization
	  	public LongOperation_reg(String url,String name,String id,String email,String pass,Activity c)
	  	{
	  		_url=url;
	  		__context=c;
	  		
	  		_adminname=name;
	  		//_adminorg=org;
	  		_id = id;
	  		_adminemail=email;
	  		_adminpass=pass;
	  		
	  		
	  		
	  	}
	      private final HttpClient Client = new DefaultHttpClient();
	      private String Content;
	      private String Error = null;
//	      private ProgressDialog Dialog = new ProgressDialog(__context);
	      
	      String data =""; 
//	      TextView uiUpdate = (TextView) findViewById(R.id.output);
//	      TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
	      int sizeData = 0;  
//	      EditText serverText = (EditText) findViewById(R.id.serverText);
	      
	      
	      protected void onPreExecute() {
	          // NOTE: You can call UI Element here.
	           
	          //Start Progress Dialog (Message)
	         
//	          Dialog.setMessage("Please wait..");
//	          Dialog.show();
	          
//	          try{
//	          	// Set Request parameter
//	              data +="&" + URLEncoder.encode("data", "UTF-8") + "="+serverText.getText();
//		            	
//	          } catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
	    	  //progress=ProgressDialog.show(InfoActivity.this, "", "");
			//	 progress.setContentView(R.layout.progress);
			    //  progress.show();
	    	  
	    	 

				   
				   
	    	  
	      }

	      // Call after onPreExecute method
	      protected String doInBackground(String... urls) {
	      	
	      	
	      	BufferedReader reader=null;
	 
		             // Send data 
		            try
		            {  
								String getIsValidUser = "GetIsValidUser";
								//String employeeId="836849";
								//String name="ashish kumar";
								String data = URLEncoder.encode("tag", "UTF-8") + "=" + URLEncoder.encode(getIsValidUser, "UTF-8");
											data += "&" + URLEncoder.encode("employeeId", "UTF-8")+ "=" + URLEncoder.encode(_id, "UTF-8");
											data += "&" + URLEncoder.encode("name", "UTF-8")+ "=" + URLEncoder.encode(_adminname, "UTF-8"); 
								
		            	
		            	//Toast.makeText(getApplicationContext(), _url, Toast.LENGTH_SHORT).show();
		               URL url = new URL(_url);
		               
//		                 
//		              // Send POST data request
		 
		              URLConnection conn = url.openConnection(); 
		              conn.setDoOutput(true); 
		              OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
		              wr.write( data ); 
		              wr.flush(); 
//		          
		              // Get the server response 
		               
		              reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		              StringBuilder sb = new StringBuilder();
		              String line = null;
		              
		            
			            // Read Server Response
			            while((line = reader.readLine()) != null)
			                {
			                       // Append server response in string
			                       sb.append(line + "\n");
			                }
		                
		                // Append Server Response To Content String 
		               Content = sb.toString();
		            }
		            catch(Exception ex)
		            {
		            	//Toast.makeText(getApplicationContext(), "hii", Toast.LENGTH_SHORT).show();
		            	Error = ex.getMessage();
		            }
		            finally
		            {
		                try
		                {
		     
		                    reader.close();
		                }
		   
		                catch(Exception ex) {}
		            }
	      	
	          /*****************************************************/
	          return "";
	      }
	       
	      @SuppressWarnings("unused")
		protected void onPostExecute(String unused) {
	          // NOTE: You can call UI Element here.
	    		// Toast.makeText(__context,Error, Toast.LENGTH_LONG).show();
//	           Close progress dialog
	    	 
//	          Dialog.dismiss();
	           
	          if (Error != null) {
	               
//	              uiUpdate.setText("Output : "+Error);
	        	  Toast.makeText(__context, "Error due to some network problem! Please connect to internet. ", Toast.LENGTH_LONG).show();
	               
	          } else {
	            
	        	
	        	  Log.d("RESPONSE----Ashish", Content.toString()); 
	        	  //Toast.makeText(__context, Content, Toast.LENGTH_LONG).show();
	        	  /****************** Start Parse Response JSON Data *************///(ashish kumar baghel)
	        	  JSONObject jsonResponse;
					try {
						if(Content!=null){
							/****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
							//jsonResponse = new JSONObject(Content);
							JSONObject myJson = new JSONObject(Content);
							// use myJson as needed, for example 
							String tag = myJson.optString("tag");
							//Toast.makeText(__context, "tag:"+tag, Toast.LENGTH_LONG).show();
							
							
							String error = myJson.optString("error");
							if(error.equals("false")){
								/////////*********************************************************
								//********************************************
								GCMRegistrar.checkDevice(InfoActivity.this);

								// Make sure the manifest was properly set - comment out this line
								// while developing the app, then uncomment it when it's ready.
				 				GCMRegistrar.checkManifest(InfoActivity.this);
								
								//registerReceiver(mHandleMessageReceiver, new IntentFilter(
								//		DISPLAY_MESSAGE_ACTION));
								
								// Get GCM registration id
								final String regId = GCMRegistrar.getRegistrationId(InfoActivity.this);

								// Check if regid already presents
								if (regId.equals("")) {
									// Registration is not present, register now with GCM			
									GCMRegistrar.register(InfoActivity.this, SENDER_ID);
								} else {
									// Device is already registered on GCM
									if (GCMRegistrar.isRegisteredOnServer(InfoActivity.this)) {
										// Skips registration.				
										Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
									} else {
										// Try to register again, but not in the UI thread.
										// It's also necessary to cancel the thread onDestroy(),
										// hence the use of AsyncTask instead of a raw thread.
										final Context context =InfoActivity.this;
										mRegisterTask = new AsyncTask<Void, Void, Void>() {

											@Override
											protected Void doInBackground(Void... params) {
												// Register on our server
												// On server creates a new user
												ServerUtilities.register(context, _adminname, _adminemail, regId);
												return null;
											}

											@Override
											protected void onPostExecute(Void result) {
												mRegisterTask = null;
											}

										};
										mRegisterTask.execute(null, null, null);
									}
								}
								
								//********************************************
								/////////**********************************************************
								//Toast.makeText(__context, "Valid User", Toast.LENGTH_LONG).show();
								 db.addContact(new Info(Integer.parseInt(_id), _adminname, loc, _adminemail));
									
							        if(db.getContactsCount()>0)
									{
							        	
							        	Toast.makeText(getApplicationContext(), "Submitted Successfully", Toast.LENGTH_LONG).show();	
							        	db.close();
										finish();
										Intent i=new Intent(InfoActivity.this,MainActivity.class);
										startActivity(i);
										
										
									}
								
							}else if(error.equals("true")){
								String errorMsg = myJson.optString("errorMsg");
								Toast.makeText(__context, errorMsg, Toast.LENGTH_LONG).show();
							}
							
							

						}
					}catch(Exception ex){

					}

	        	  
	        	  
	        	
	           }
			
      }
	       
	  }
		//+++++++++++++++++++++++++++++++++++++++++++++++++++

	  
	  
	  
	  
	  
}
