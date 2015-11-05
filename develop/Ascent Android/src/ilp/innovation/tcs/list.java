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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;


public class list extends Activity implements OnItemSelectedListener {
	// Log tag
	private static final String TAG = list.class.getSimpleName();

	// Movies json url
	private static  String url = "http://theinspirer.in/ascent/Android_get_schedule.php?date=2015-09-09&regionId=1";
	private ProgressDialog pDialog;
	private List<Movie> movieList =null;
	private ListView listView;
	private CustomListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
         listner();
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
	
		
		
		
		DatePicker pick=new DatePicker(this);
	int year=pick.getYear();
	int day=pick.getDayOfMonth();
	int month=pick.getMonth();
	String daystr=String.valueOf(day);
	String monthstr=String.valueOf(month+1);
	
	if(String.valueOf(day).length()==1)
		daystr="0"+(day);
	if(String.valueOf(month+1).length()==1)
		monthstr="0"+(month+1);
	pick1=pick;
	url = "http://theinspirer.in/ascent/Android_get_schedule.php?date="+year+"-"+monthstr+"-"+daystr+"&regionId="+MainActivity.region;
	//Toast.makeText(getApplicationContext(),pick.,Toast.LENGTH_LONG).show();
	
	TextView temp=(TextView)findViewById(R.id.textView4);
	temp.setText(daystr+"-"+monthstr+"-"+year);
		callfiller(pick);
	}
    
	
	TransparentProgressDialog pd;
	String offlinedata="";
	void callfiller(final DatePicker pick)
	{
		//.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
		movieList= new ArrayList<Movie>();
		listView = (ListView) findViewById(R.id.list);
		adapter = new CustomListAdapter(this, movieList);
		listView.setAdapter(adapter);
        
		pd = new TransparentProgressDialog(this, R.drawable.loading);
		pd.show();
		// changing action bar color
	ConnectionDetector	cd = new ConnectionDetector(getApplicationContext());
	
	String date=String.valueOf(pick.getDayOfMonth())+"L"+String.valueOf(pick.getMonth())+"L"+String.valueOf(pick.getYear());
	
	Boolean flag=true;
	int lengthofdata=0;
	try
	{
lengthofdata=readoffline(date).length();
	}
	catch(Exception e)
	{
		lengthofdata=0;
		flag=false;
	}
	
if(lengthofdata>40&&flag)
{
	
		try
		{
	             		
			
	                    JSONArray response=new JSONArray(readoffline(pick.getDayOfMonth()+"L"+pick.getMonth()+"L"+pick.getYear()));
						Log.d(TAG, response.toString());
						hidePDialog();

						// Parsing json
						for (int i = 0; i < response.length(); i++) {
							try {

								JSONObject obj = response.getJSONObject(i);
								Movie movie = new Movie();
								movie.setTitle(obj.getString("title"));
								movie.setThumbnailUrl(obj.getString("image"));
								movie.setRating(obj.getString("rating"));
								movie.setYear(obj.getString("releaseYear"));

								// Genre is json array
							
								JSONArray genreArry = obj.getJSONArray("genre");
								ArrayList<String> genre = new ArrayList<String>();
								for (int j = 0; j < genreArry.length(); j++) {
									genre.add((String) genreArry.get(j));
									
								}
								movie.setGenre(genre);

								// adding movie to movies array
								movieList.add(movie);
	                     	} catch (JSONException e) {
								e.printStackTrace();
							}

						}

						// notifying list adapter about data changes
						// so that it renders the list view with updated data
						adapter.notifyDataSetChanged();
		}catch(Exception e)
		{
//			Log.d("error offline",e.getMessage());
		}
				

	
}
else if(cd.isConnectingToInternet())
{

		// Creating volley request obj
		JsonArrayRequest movieReq = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						//Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show(); 
						
						if(response.toString().length()>40)
						{
							Log.d("offlinedata","written");
							
							
						writeoffline(response.toString(),pick.getDayOfMonth()+"L"+pick.getMonth()+"L"+pick.getYear());
						}
						else
						{
						
							Movie movie = new Movie();
							movie.setTitle("Session details are not available for given date");
							movie.setThumbnailUrl("");
							movie.setRating("");
							movie.setYear("");

							ArrayList<String> genre = new ArrayList<String>();
							movie.setGenre(genre);

							// adding movie to movies array
							movieList.add(movie);
						}
						Log.d(TAG, response.toString());
						hidePDialog();

						// Parsing json
						for (int i = 0; i < response.length(); i++) {
							try {

								JSONObject obj = response.getJSONObject(i);
								Movie movie = new Movie();
								movie.setTitle(obj.getString("title"));
								movie.setThumbnailUrl(obj.getString("image"));
								movie.setRating(obj.getString("rating"));
								movie.setYear(obj.getString("releaseYear"));

								// Genre is json array
							
								JSONArray genreArry = obj.getJSONArray("genre");
								ArrayList<String> genre = new ArrayList<String>();
								for (int j = 0; j < genreArry.length(); j++) {
									genre.add((String) genreArry.get(j));
									
								}
								movie.setGenre(genre);

								// adding movie to movies array
								movieList.add(movie);
                         	} catch (JSONException e) {
								e.printStackTrace();
							}

						}

						// notifying list adapter about data changes
						// so that it renders the list view with updated data
						adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
				
						Movie movie = new Movie();
						movie.setTitle("Session details are not available for given date");
						movie.setThumbnailUrl("");
						movie.setRating("");
						movie.setYear("");

						ArrayList<String> genre = new ArrayList<String>();
						movie.setGenre(genre);

						// adding movie to movies array
						movieList.add(movie);
						adapter.notifyDataSetChanged();
						
						VolleyLog.d(TAG, "Error: " + error.getMessage());
					pd.cancel();

					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(movieReq);
	
}
else
{
	 final Dialog dialog = new Dialog(list.this);
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
		pd.hide();
		
	
}


//Toast.makeText(getApplicationContext(), offlinedata,Toast.LENGTH_LONG).show();
//Log.i("offlinedata",readoffline());
	}
	

	private void hidePDialog() {
		pd.cancel();
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
	
	
	public void rating(View v)
	{
		
		
		
		final int position = listView.getPositionForView((View) v.getParent());
	    Movie pp=movieList.get(position);
	    String temp=String.valueOf(pp.getYear());
	    
	  
        ArrayList<String> temp1=pp.getGenre();
 		 
 		Sched_id=Integer.parseInt(temp1.get(3));
 		facultyname=pp.getRating();
 		facultytime=pp.getYear();
 		title=pp.getTitle();
 		TextView t3=(TextView)findViewById(R.id.textView4);
 		date=t3.getText().toString().trim();
 		
 		
 		new updatecommentservice12(getApplicationContext()).execute(String.valueOf(list.Sched_id),String.valueOf(MainActivity.employeeid));
		pd.show();
 		  
	}
	public static String date="";
	public static int Sched_id=0; 
	public static String title="";
	
	
	public static String facultyname="";
	public static String facultytime="";
   
 
   @Override
   public void onDestroy()
   {
       android.os.Process.killProcess(android.os.Process.myPid());
       super.onDestroy();
   }
   @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	finish();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
   public void writeoffline(String data,String path)
	{
		try { 
		       // catches IOException below
		       

		       /* We have to use the openFileOutput()-method
		       * the ActivityContext provides, to
		       * protect your file from others and
		       * This is done for security-reasons.
		       * We chose MODE_WORLD_READABLE, because
		       *  we have nothing to hide in our file */             
		       FileOutputStream fOut = openFileOutput("offline"+path+".txt",
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
   public String readoffline(String path)
	{
try
{
       FileInputStream fIn = openFileInput("offline"+path+".txt");
       InputStreamReader isr = new InputStreamReader(fIn);

       /* Prepare a char-Array that will
        * hold the chars we read back in. */
       char[] inputBuffer = new char[14000];

       // Fill the Buffer with data from the file
       isr.read(inputBuffer);

       // Transform the chars to a String
       String readString = new String(inputBuffer);

       // Check if we read back the same chars that we had written out
   

       Log.i("File Reading stuff", "successmili= " + readString.replaceAll("[^ -~]", "").trim());
       return readString.replaceAll("[^ -~]", "").trim();
}
catch(Exception e)
{

}
return null;
	}
   
   public void search(View v)
   {
	   final Dialog dialog = new Dialog(list.this);
		dialog.setContentView(R.layout.datetimepicker);
		dialog.setTitle("");

		 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
			// dialog.getWindow().setTitleColor(R.color.titlecolor);
	       

		// set the custom dialog components - text, image and button
		 final DatePicker pick=(DatePicker)dialog.findViewById(R.id.datePicker1);


		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				int year=pick.getYear();
				int day=pick.getDayOfMonth();
				int month=pick.getMonth();
				String daystr=String.valueOf(day);
				String monthstr=String.valueOf(month+1);
				
				
				if(String.valueOf(day).length()==1)
					daystr="0"+(day);
				if(String.valueOf(month+1).length()==1)
					monthstr="0"+(month+1);
				
				url = "http://theinspirer.in/ascent/Android_get_schedule.php?date="+year+"-"+monthstr+"-"+daystr+"&regionId="+MainActivity.region;
				Log.d("url---> ",url);
				pick1=pick;
				TextView temp=(TextView)findViewById(R.id.textView4);
				temp.setText(daystr+"-"+monthstr+"-"+year);
				dialog.dismiss();
				callfiller(pick);
				
				
				
				
			
			}
		});
		dialog.show();
		
		
		
   }
   Dialog dialog;
	public void aboutus(View v)
	{
	

		dialog = new Dialog(list.this);

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
	DatePicker pick1;
	public void forceupdate(View v)
	{	
		try
	
	{
			ConnectionDetector temp=new ConnectionDetector(getApplicationContext());
			if(!temp.isConnectingToInternet())
			{
				final Dialog dialog = new Dialog(list.this);
				dialog.setContentView(R.layout.customerror);
				dialog.setTitle("");

				 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
					// dialog.getWindow().setTitleColor(R.color.titlecolor);
			       

				// set the custom dialog components - text, image and button
				TextView text = (TextView) dialog.findViewById(R.id.text);
				text.setText("Not Connected To Internet");


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
			
	//	Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
	movieList= new ArrayList<Movie>();
	listView = (ListView) findViewById(R.id.list);
	adapter = new CustomListAdapter(this, movieList);
	listView.setAdapter(adapter);
		pd.show();
		JsonArrayRequest movieReq = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						//Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show(); 
						if(response.toString().length()>40)
						{
							Log.d("offlinedata","written");
							
							
						writeoffline(response.toString(),pick1.getDayOfMonth()+"L"+pick1.getMonth()+"L"+pick1.getYear());
						}
						else
						{
							final Dialog dialog = new Dialog(list.this);
							dialog.setContentView(R.layout.customerror);
							dialog.setTitle("");

							 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
								// dialog.getWindow().setTitleColor(R.color.titlecolor);
						       

							// set the custom dialog components - text, image and button
							TextView text = (TextView) dialog.findViewById(R.id.text);
							text.setText("You Are Offline");


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
						Log.d(TAG, response.toString());
						hidePDialog();

						// Parsing json
						for (int i = 0; i < response.length(); i++) {
							try {

								JSONObject obj = response.getJSONObject(i);
								Movie movie = new Movie();
								movie.setTitle(obj.getString("title"));
								movie.setThumbnailUrl(obj.getString("image"));
								movie.setRating(obj.getString("rating"));
								movie.setYear(obj.getString("releaseYear"));

								// Genre is json array
							
								JSONArray genreArry = obj.getJSONArray("genre");
								ArrayList<String> genre = new ArrayList<String>();
								for (int j = 0; j < genreArry.length(); j++) {
									genre.add((String) genreArry.get(j));
									
								}
								movie.setGenre(genre);

								// adding movie to movies array
								movieList.add(movie);
                         	} catch (JSONException e) {
								e.printStackTrace();
							}

						}

						// notifying list adapter about data changes
						// so that it renders the list view with updated data
						adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						final Dialog dialog = new Dialog(list.this);
						dialog.setContentView(R.layout.customerror);
						dialog.setTitle("");

						 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
							// dialog.getWindow().setTitleColor(R.color.titlecolor);
					       

						// set the custom dialog components - text, image and button
						TextView text = (TextView) dialog.findViewById(R.id.text);
						text.setText("You Are Offline");


						Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
						// if button is clicked, close the custom dialog
						dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
							}
						});
						dialog.show();
						VolleyLog.d(TAG, "Error: " + error.getMessage());
					pd.cancel();

					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(movieReq);
	}
		catch(Exception e)
		{
			final Dialog dialog = new Dialog(list.this);
			dialog.setContentView(R.layout.customerror);
			dialog.setTitle("");

			 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
				// dialog.getWindow().setTitleColor(R.color.titlecolor);
		       

			// set the custom dialog components - text, image and button
			TextView text = (TextView) dialog.findViewById(R.id.text);
			text.setText("Oops Some Error Occured");


			Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialog.show();
			pd.hide();
		}
	}
	public static TextView eventappointmentbook=null;
	void listner()
	 {
		 eventappointmentbook=new TextView(this);
		 eventappointmentbook.addTextChangedListener(new TextWatcher() {

			   @Override
			   public void afterTextChanged(Editable s) {
		try
		{
				// Toast.makeText(getApplicationContext(), eventappointmentbook.getText(), Toast.LENGTH_SHORT).show();
				 pd.hide();
				 if(eventappointmentbook.getText().toString().trim().equals("0"))
				 {
					 Intent i = new Intent(getApplicationContext(), rating.class);
				 		startActivity(i);
				 }
				 else if(eventappointmentbook.getText().toString().trim().equals("1"))
				 {
					 final Dialog dialog = new Dialog(list.this);
						dialog.setContentView(R.layout.customerror);
						dialog.setTitle("");

						 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
							// dialog.getWindow().setTitleColor(R.color.titlecolor);
					       

						// set the custom dialog components - text, image and button
						TextView text = (TextView) dialog.findViewById(R.id.text);
						text.setText("You Already Submitted Feedback For This Session");


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
					 final Dialog dialog = new Dialog(list.this);
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
		}catch(Exception e)
		{
			 final Dialog dialog = new Dialog(list.this);
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
