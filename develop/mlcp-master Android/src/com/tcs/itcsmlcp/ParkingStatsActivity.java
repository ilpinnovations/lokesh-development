package com.tcs.itcsmlcp;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ParkingStatsActivity extends Activity {

	
	String serverURL_bookslot =AppConstant.URL;
	 MyApp aController;
	 private ProgressDialog progress;
	 String[] values,values1;
	 Integer[]  images = { R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher, R.drawable.ic_launcher};
	 ListView listView;
	 
	 ImageButton btnBack,btnRefresh;
public static TextView result=null;
	 TextView lblHead;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parking_info_new);
		listner();
		ViewGroup vg = (ViewGroup) findViewById(R.id.root);
		Utils.setFontAllView(vg);
		
		lblHead=(TextView)findViewById(R.id.text_price);
		lblHead.setText("PARKING STATS");
		
		btnBack = (ImageButton) findViewById(R.id.btn_back);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
		btnRefresh = (ImageButton) findViewById(R.id.btn_refresh);
		btnRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new getstats(ParkingStatsActivity.this).execute("");
			}
		});
		 aController = (MyApp) getApplicationContext();
		 
		 /*cars=(TextView)findViewById(R.id.cars);
		 slots_cars=(TextView)findViewById(R.id.slots_cars);
		 bikes=(TextView)findViewById(R.id.bikes);
		 slots_bikes=(TextView)findViewById(R.id.slots_bikes);*/
		 
		 
		  listView = (ListView) findViewById(R.id.list_colors);
		 values = new String[] { "Yesterday Busy Traffic Time:",  "Slots almost full by:"};//, "No. of Two Wheeler Parked:","Available slots for Two Wheeler Parking:"};

         	// Check if Internet Connection present
			if (!aController.isConnectingToInternet()) {
				// Internet Connection is not present
				aController.showAlertDialog(ParkingStatsActivity.this,
						"Internet Connection Error",
						"Please connect to working Internet connection", false,ParkingStatsActivity.this);

						// stop executing code by return
				
			   return;
			 }
		
			else{
		
				new getstats(ParkingStatsActivity.this).execute("");
			}
	}
	void listner()
	{
		 result=new TextView(this);
		 result.addTextChangedListener(new TextWatcher() {

			   @Override
			   public void afterTextChanged(Editable s) {
			
				try
				{
				   Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();
				   if(result.getText().toString().trim().contains("Exception"))
				   {
					   final Dialog dialog = new Dialog(ParkingStatsActivity.this);
						dialog.setContentView(R.layout.customerror);
						dialog.setTitle("");

						 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
							// dialog.getWindow().setTitleColor(R.color.titlecolor);
					       

						// set the custom dialog components - text, image and button
						TextView text = (TextView) dialog.findViewById(R.id.text);
						text.setText("No Internet Access");


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
					   
					  JSONObject obj=new JSONObject(result.getText().toString().trim());
					    JSONArray obj1=obj.getJSONArray("values");
					    TextView t1=(TextView)findViewById(R.id.textView2);
					    TextView t2=(TextView)findViewById(R.id.textView4);
					    JSONObject temp=obj1.getJSONObject(0);
					    t1.setText(temp.optString("available"));
					    
					    t2.setText(temp.optString("total"));
					    
					  
				   }
				  
					//Intent i = new Intent(getApplicationContext(), list.class);
				}
				catch(Exception e)
				{
					final Dialog dialog = new Dialog(ParkingStatsActivity.this);
					dialog.setContentView(R.layout.customerror);
					dialog.setTitle("");

					 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
						// dialog.getWindow().setTitleColor(R.color.titlecolor);
				       

					// set the custom dialog components - text, image and button
					TextView text = (TextView) dialog.findViewById(R.id.text);
					text.setText("Internet Not Available");


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
			 	//	startActivity(i);
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
	


	
}
