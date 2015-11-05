package info.androidhive.customlistviewvolley;

import info.androidhive.customlistviewvolley.adater.CustomListAdapter;
import info.androidhive.customlistviewvolley.app.AppController;
import info.androidhive.customlistviewvolley.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
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

 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.register);
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(this.TELEPHONY_SERVICE);
 		String t=	telephonyManager.getDeviceId().toString();
 
         new checkalreadyregistered(this).execute(t);
     	pd = new TransparentProgressDialog(this, R.drawable.loading);
		pd.show();

		Spinner spinner2 = (Spinner) findViewById(R.id.spinner1);
         
         // Spinner click listener
         spinner2.setOnItemSelectedListener(this);

         //spinner2.setBackgroundResource(R.drawable.spinner);
         // Spinner Drop down elements
         List<String> categories1 = new ArrayList<String>();
         
        categories1.add("Chennai"); 
       
         categories1.add("Trivandrum"); 	               


         // Creating adapter for spinner
         ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, categories1);
         dataAdapter1.setDropDownViewResource(R.drawable.spinner_rowloki); 
         // Drop down layout style - list view with radio button
   
         // attaching data adapter to spinner
         spinner2.setAdapter(dataAdapter1);
         
         
		
		
		listner();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
	}
	
	public void submit(View v)
	{

	
		
		String text1="";
		EditText e1=(EditText)findViewById(R.id.editText1);
		EditText e2=(EditText)findViewById(R.id.editText2);
		Spinner  sp=(Spinner)findViewById(R.id.spinner1);
		EditText e4=(EditText)findViewById(R.id.editText4);
		EditText e5=(EditText)findViewById(R.id.editText5);
		
		if(e1.getText().toString().length()==0)
			text1="Enter Employee Id";
		else if(e2.getText().toString().length()==0)
			text1="Enter Your Name";
		else if(e4.getText().toString().length()==0)
			text1="Enter LG Name";
	
            if(validEmail(e5.getText().toString().trim()))
            { 
			String[] temp=e5.getText().toString().trim().split("@");
			Toast.makeText(getApplicationContext(),temp[1],Toast.LENGTH_SHORT).show();
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
			TelephonyManager telephonyManager = (TelephonyManager)getSystemService(this.TELEPHONY_SERVICE);
		String t=	telephonyManager.getDeviceId().toString();
	       new 	serversignin(this).execute(e1.getText().toString(),e2.getText().toString(),sp.getSelectedItem().toString(),e4.getText().toString(),e5.getText().toString(),t);	

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
	public static TextView imeicheck=null;
	TransparentProgressDialog pd;
	void listner()
	 {
		 eventappointmentbook=new TextView(this);
		 eventappointmentbook.addTextChangedListener(new TextWatcher() {

			   @Override
			   public void afterTextChanged(Editable s) {
				 pd.cancel();
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
					EditText e1=(EditText)findViewById(R.id.editText1);
					EditText e2=(EditText)findViewById(R.id.editText2);
					Spinner  sp=(Spinner)findViewById(R.id.spinner1);
					EditText e4=(EditText)findViewById(R.id.editText4);
					EditText e5=(EditText)findViewById(R.id.editText5);
				 employeeid=Integer.parseInt(e1.getText().toString().trim());
				   name=e2.getText().toString().trim();
				   location=sp.getSelectedItem().toString();
				   lg=e4.getText().toString().trim();
				   email=e5.getText().toString().trim();
				  // Toast.makeText(getApplicationContext(), eventappointmentbook.getText(), Toast.LENGTH_SHORT).show();
					Intent i = new Intent(getApplicationContext(), list.class);
			 		
			 		startActivity(i);
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
		 imeicheck=new TextView(this);
		 imeicheck.addTextChangedListener(new TextWatcher() {

			   @Override
			   public void afterTextChanged(Editable s) {
				  pd.cancel();
				  if(imeicheck.getText().toString().trim().contains("Exception"))
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
						return;
				  }
				  // Toast.makeText(getApplicationContext(), imeicheck.getText(), Toast.LENGTH_SHORT).show();
					String data1=imeicheck.getText().toString().trim();
					String[] data=data1.split("xyzzyspoonshift");
				Log.d("error",data1);
						if(data1.length()!=127)
					if(!data[0].equals("")&&!data1.contains("Exception"))
				   {
					   employeeid=Integer.parseInt(data[1].trim());
					   name=data[2].trim();
					   location=data[3].trim();
					   lg=data[4].trim();
					   email=data[5].trim();
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
	 }
	public static int employeeid;
	public static String name;
	public static String location;
	public static String lg;
	public static String email;
	
	
}
