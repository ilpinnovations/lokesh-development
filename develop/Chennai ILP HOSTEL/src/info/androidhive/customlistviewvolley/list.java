package info.androidhive.customlistviewvolley;

import info.androidhive.customlistviewvolley.adater.CustomListAdapter;
import info.androidhive.customlistviewvolley.app.AppController;
import info.androidhive.customlistviewvolley.model.Movie;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
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


public class list extends Activity implements OnItemSelectedListener {
	// Log tag
	private static final String TAG = list.class.getSimpleName();

	// Movies json url
	private static  String url = "http://cchat.in/chennai/list.php";
	private ProgressDialog pDialog;
	private List<Movie> movieList =null;
	private ListView listView;
	private CustomListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
         
		callfiller();
	}
    
	
	TransparentProgressDialog pd;
	void callfiller()
	{
		
		movieList= new ArrayList<Movie>();
		listView = (ListView) findViewById(R.id.list);
		adapter = new CustomListAdapter(this, movieList);
		listView.setAdapter(adapter);
        
		pd = new TransparentProgressDialog(this, R.drawable.loading);
		pd.show();
		// changing action bar color
	

		// Creating volley request obj
		JsonArrayRequest movieReq = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.d(TAG, response.toString());
						hidePDialog();

						// Parsing json
						for (int i = 0; i < response.length(); i++) {
							try {

								JSONObject obj = response.getJSONObject(i);
								Movie movie = new Movie();
								movie.setTitle(obj.getString("title"));
								movie.setThumbnailUrl(obj.getString("image"));
								movie.setRating(((Number) obj.get("rating"))
										.doubleValue());
								movie.setYear(obj.getInt("releaseYear"));

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
						VolleyLog.d(TAG, "Error: " + error.getMessage());
					pd.cancel();

					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(movieReq);
	
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
	int from=0;
	int to=10;
	public void next(View v)
	{
		from=(from+10);
		to=(to+10);

		url="http://cchat.in/chennai/list.php?from="+from+"&to="+to;
		Log.d("asd",url);
		callfiller();
	}
	public void back(View v)
	{
		if((from-10)>=0)
		{
			from=(from-10);
			to=(to-10);
			url="http://cchat.in/chennai/list.php?from="+from+"&to="+to;
			callfiller();
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
			text.setText("No More Hostel Avilable");


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
	
	public void rating(View v)
	{
		final int position = listView.getPositionForView((View) v.getParent());
	    Movie pp=movieList.get(position);
	    String temp=String.valueOf(pp.getYear());
	    
	    /*PhoneCallListener phoneListener = new PhoneCallListener();
		TelephonyManager telephonyManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(phoneListener,
				PhoneStateListener.LISTEN_CALL_STATE);
	    */
	    
		Intent i = new Intent(getApplicationContext(), rating.class);
        ArrayList<String> temp1=pp.getGenre();
 		 ratingtable= temp1.get(0);
 		startActivity(i);
	}
	public static String ratingtable="";
	
	public void showcomments(View v)
	{
		final int position = listView.getPositionForView((View) v.getParent());
	    Movie pp=movieList.get(position);

	    Intent i = new Intent(getApplicationContext(), comments.class);
        ArrayList<String> temp1=pp.getGenre();
       // Toast.makeText(getApplicationContext(), temp1.get(0),Toast.LENGTH_SHORT).show();
 		 ratingtable= temp1.get(0);
 		 lon= temp1.get(2);
		 lat=temp1.get(1);
		 placename=pp.getTitle();
		 placeaddress=temp1.get(3);
		 placeimage=pp.getThumbnailUrl();
 		startActivity(i);
	    
	}
	public void maps(View v)
	{
		final int position = listView.getPositionForView((View) v.getParent());
	    Movie pp=movieList.get(position);
	    
	     ArrayList<String> temp1=pp.getGenre();
		 lon= temp1.get(2);
		 lat=temp1.get(1);
		 placename=pp.getTitle();
		 placeaddress=temp1.get(3);
		 placeimage=temp1.get(4);
		 
	     Intent i = new Intent(getApplicationContext(), maps.class);
       
 		 startActivity(i);
	}
	public static String placename="";
	public static String placeaddress="";
	public static String placeimage="";
   public static String lon="";
   public static String lat="";
   
   public void search(View v)
   {
      from=0;
      to=10;
      EditText E1=(EditText)findViewById(R.id.editText12);
		url="http://cchat.in/chennai/list.php?pattern="+E1.getText();
		callfiller();
   }
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


}
