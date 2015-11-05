package ilp.innovation.tcs;
/*
 * Author:Lokesh Deshmukh
 * Year:2015
 * Date:25-Sep-2015
 */
import ilp.innovation.adapter.CustomListAdapter;
import ilp.innovation.app.AppController;
import ilp.innovation.model.Movie;
import ilp.innovation.tcs.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

public class MainActivity extends Activity implements OnItemSelectedListener {
	// Log tag
	private static final String TAG = MainActivity.class.getSimpleName();

	// Movies json url

 public static int region=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			Class.forName("android.os.AsyncTask");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setContentView(R.layout.register);
		
		
		//custom action bar
		ActionBar mActionBar = getActionBar();
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);
		LayoutInflater mInflater = LayoutInflater.from(this);

		View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
		TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
		mTitleTextView.setText("My Ascent");

		mActionBar.setCustomView(mCustomView);
		mActionBar.setDisplayShowCustomEnabled(true);
		//custom action bar
		
		
		pd = new TransparentProgressDialog(this, R.drawable.loading);
		
		String temp=read();
		
		if(temp!=null&&temp.length()>5)
		{
			String[] value=temp.split("xyzzyspoonshift1");
		employeeid=Integer.parseInt(value[0].trim());
		region=Integer.parseInt(value[1].trim());
		/*start service
		  Intent service = new Intent(MainActivity.this, backgroundservice.class);
		  service.putExtra("emp_id", employeeid); 
		 Log.d("service","Started");

		   startService(service);
		start service*/
		   
		Intent i = new Intent(getApplicationContext(), list.class);
 		
 		startActivity(i);
		}
		else
		{

	
    
		pd.show();
			listner();
         new getcity(this).execute("");
		
		
		
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
	}
	
	public void submit(View v)
	{
try
{
	if(allowed==0)
	{
		
	      new getcity(this).execute("");
pd.show();
			return;
	}
		
		String text1="";
		EditText e1=(EditText)findViewById(R.id.editText1);
		EditText e2=(EditText)findViewById(R.id.editText2);
		Spinner  sp=(Spinner)findViewById(R.id.spinner1);

		EditText e5=(EditText)findViewById(R.id.editText5);
		
		if(e1.getText().toString().trim().length()==0)
		{
		
			text1="Enter Employee Id";
		}
		else if(e2.getText().toString().trim().length()==0)
			text1="Enter Your Name";
		
		
		try
		{
			int y=Integer.parseInt(e1.getText().toString().trim());
		}
		catch(Exception e)
		{
			text1="Enter Correct Employee Id";			
		}
	
            if(validEmail(e5.getText().toString().trim()))
            { 
			String[] temp=e5.getText().toString().trim().split("@");
			//Toast.makeText(getApplicationContext(),temp[1],Toast.LENGTH_SHORT).show();
			if(!temp[1].trim().toLowerCase().equals("tcs.com"))
			text1="Enter Offical Email ID";
            }
            else
            	text1="Enter Offical Email ID";
            	

		
		
		
		if(text1.length()!=0)
		{
		
		final Dialog dialog = new Dialog(MainActivity.this);
			dialog.setContentView(R.layout.customerror);
			dialog.setTitle("");

			 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
				// dialog.getWindow().setTitleColor(R.color.titlecolor);
		       

			// set the custom dialog components - text, image and button
			TextView text = (TextView) dialog.findViewById(R.id.text);
			text.setText(text1);


			Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialog.show();
		}
		else
		{
			/*TelephonyManager telephonyManager = (TelephonyManager)getSystemService(this.TELEPHONY_SERVICE);
		   String t=	telephonyManager.getDeviceId().toString();*/
	       new 	serversignin(this).execute(e1.getText().toString().trim(),e2.getText().toString().trim(),String.valueOf(sp.getSelectedItemPosition()+1),e5.getText().toString().trim());	
           pd.show();
		}
}
catch(Exception e)
{
	
}
	}

	 private boolean validEmail(String email) {
		    Pattern pattern = Patterns.EMAIL_ADDRESS;
		    return pattern.matcher(email).matches();
		}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	public static TextView eventappointmentbook=null;
	public static TextView eventappointmentbook1=null;
	public static TextView imeicheck=null;
	TransparentProgressDialog pd;
	void listner()
	 {
		 eventappointmentbook=new TextView(this);
		 eventappointmentbook.addTextChangedListener(new TextWatcher() {

			   @Override
			   public void afterTextChanged(Editable s) {
				 pd.cancel();
				// Toast.makeText(getApplicationContext(), eventappointmentbook.getText(), Toast.LENGTH_SHORT).show();
				 
				 if(eventappointmentbook.getText().toString().trim().contains("Exception"))
				 {
					 final Dialog dialog = new Dialog(MainActivity.this);
						dialog.setContentView(R.layout.customerror);
						dialog.setTitle("");

						 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
							// dialog.getWindow().setTitleColor(R.color.titlecolor);
					       

						// set the custom dialog components - text, image and button
						TextView text = (TextView) dialog.findViewById(R.id.text);
						text.setText("No Internet Connection");


						Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
						// if button is clicked, close the custom dialog
						dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
							}
						});
						dialog.show();
				 }
				 else if(eventappointmentbook.getText().toString().trim().contains("User has already")||eventappointmentbook.getText().toString().trim().contains("Successfully"))
				 {
					EditText e1=(EditText)findViewById(R.id.editText1);
					EditText e2=(EditText)findViewById(R.id.editText2);
					Spinner  sp=(Spinner)findViewById(R.id.spinner1);
					EditText e5=(EditText)findViewById(R.id.editText5);
 
					write(e1.getText().toString().trim()+"xyzzyspoonshift1"+String.valueOf(sp.getSelectedItemPosition()+1));
					
					
					employeeid=Integer.parseInt(e1.getText().toString().trim());
					region=Integer.parseInt(String.valueOf(sp.getSelectedItemPosition()+1));
					//start service
					  Intent service = new Intent(MainActivity.this, backgroundservice.class);
					  service.putExtra("emp_id", employeeid); 
					 Log.d("service","Started");
					 startService(service);
					//start service
					   
					Intent i = new Intent(getApplicationContext(), list.class);
			 		
			 		startActivity(i);
				 }
			   }

			   @Override    
			   public void beforeTextChanged(CharSequence s, int start,
			     int count, int after) {
			   }

			   @Override    
			   public void onTextChanged(CharSequence s, int start,
			     int before, int count) {
			     
			   }
			  });
		 
		 
		 eventappointmentbook1=new TextView(this);
		 eventappointmentbook1.addTextChangedListener(new TextWatcher() {

			   @Override
			   public void afterTextChanged(Editable s) {
				 pd.cancel();
				 //Toast.makeText(getApplicationContext(), eventappointmentbook1.getText(), Toast.LENGTH_LONG).show();
				 if(eventappointmentbook1.getText().toString().toLowerCase().contains("exception"))
				 {
					 final Dialog dialog = new Dialog(MainActivity.this);
						dialog.setContentView(R.layout.customerror);
						dialog.setTitle("");

						 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
							// dialog.getWindow().setTitleColor(R.color.titlecolor);
					       

						// set the custom dialog components - text, image and button
						TextView text = (TextView) dialog.findViewById(R.id.text);
						text.setText("No Internet Connection");


						Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
						// if button is clicked, close the custom dialog
						dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
							}
						});
						dialog.show();
						allowed=0;
				 }
				 else
				 {
					 allowed=1;
				// Toast.makeText(getApplicationContext(), eventappointmentbook1.getText(), Toast.LENGTH_LONG).show();
				 Spinner spinner2 = (Spinner) findViewById(R.id.spinner1);
		            
		            // Spinner click listener
		            spinner2.setOnItemSelectedListener(MainActivity.this);
		            List<String> categories1 = new ArrayList<String>();
				 try {
					
					 String[] temp=eventappointmentbook1.getText().toString().split("region_id");
					 
					 
					for(int i=1; i < temp.length; i++){
			            
			           String[] temp1=temp[i].split("region_name");
                        
			            
			           categories1.add(temp1[1].replace("[","").replace("]","").replace("{","").replace("}","").replace(",","").replace("\"","").replace(":","")); 
			          
			     
			         }
					ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, categories1);
		            dataAdapter1.setDropDownViewResource(R.drawable.spinner_rowloki); 
		            // Drop down layout style - list view with radio button
		      
		            // attaching data adapter to spinner
		            spinner2.setAdapter(dataAdapter1);
		            allowed=1;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 }
			   }

			   @Override    
			   public void beforeTextChanged(CharSequence s, int start,
			     int count, int after) {
			   }

			   @Override    
			   public void onTextChanged(CharSequence s, int start,
			     int before, int count) {
			     
			   }
			  });
		
	 }
	public int allowed=0;
	public static int employeeid;
	public static String name;
	public static String location;
	public static String lg;
	public static String email;
	 
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
		       FileOutputStream fOut = openFileOutput("samplefile.txt",
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
	public String read()
	{
try
{
        FileInputStream fIn = openFileInput("samplefile.txt");
        InputStreamReader isr = new InputStreamReader(fIn);

        /* Prepare a char-Array that will
         * hold the chars we read back in. */
        char[] inputBuffer = new char[40];

        // Fill the Buffer with data from the file
        isr.read(inputBuffer);

        // Transform the chars to a String
        String readString = new String(inputBuffer);

        // Check if we read back the same chars that we had written out
    

        Log.i("File Reading stuff", "success = " + readString.replaceAll("[^ -~]", "").trim());
        return readString.replaceAll("[^ -~]", "").trim();
}
catch(Exception e)
{

}
return null;
	}
	public void writeoffline(String data)
	{
		try { 
		       // catches IOException below
		       

		       /* We have to use the openFileOutput()-method
		       * the ActivityContext provides, to
		       * protect your file from others and
		       * This is done for security-reasons.
		       * We chose MODE_WORLD_READABLE, because
		       *  we have nothing to hide in our file */             
		       FileOutputStream fOut = openFileOutput("offline.txt",
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
	Dialog dialog;
	public void aboutus(View v)
	{
	

		dialog = new Dialog(MainActivity.this);

		dialog.setContentView(R.layout.aboutuspopup);

		dialog.setTitle("");


		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
		Button dialogButton = (Button) dialog.findViewById(R.id.button1);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				dialog.dismiss();
			}
		});

		
		dialog.show();


	}
	
}
