package info.androidhive.customlistviewvolley;

import info.androidhive.customlistviewvolley.adater.CustomListAdapter;
import info.androidhive.customlistviewvolley.app.AppController;
import info.androidhive.customlistviewvolley.model.Movie;

import java.util.ArrayList;
import java.util.List;

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
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;


public class rating extends Activity {
	// Log tag
	private static final String TAG = rating.class.getSimpleName();

	// Movies json url

	int star1=0;
	int star2=0;
	int star3=0;
	int star4=0;
	 int star5=0;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.rating);
		
		View footerLayout = findViewById(R.id.include03);

		// Fetching the textview declared in footer.xml
		final ImageButton footerText = (ImageButton) footerLayout.findViewById(R.id.imageButton1);
		final ImageButton footerText2 = (ImageButton) footerLayout.findViewById(R.id.ImageButton01);
		final ImageButton footerText3 = (ImageButton) footerLayout.findViewById(R.id.ImageButton02);
		final ImageButton footerText4 = (ImageButton) footerLayout.findViewById(R.id.ImageButton03);
		final ImageButton footerText5 = (ImageButton) footerLayout.findViewById(R.id.ImageButton04);
		
		
		footerText.setOnClickListener(new  View.OnClickListener() {


			   @Override
			   public void onClick(View view) {
	                if(star1==0)
	                {
				   footerText.setBackgroundDrawable(getResources().getDrawable(R.drawable.starempty));
	                star1=1;
	                footerText2.setBackgroundDrawable(getResources().getDrawable(R.drawable.starempty));
	                star2=1;
	                footerText3.setBackgroundDrawable(getResources().getDrawable(R.drawable.starempty));
	                star3=1;
	                footerText4.setBackgroundDrawable(getResources().getDrawable(R.drawable.starempty));
	                star4=1;
	                footerText5.setBackgroundDrawable(getResources().getDrawable(R.drawable.starempty));
	                star5=1;
	                } else
	                {
	                	footerText.setBackgroundDrawable(getResources().getDrawable(R.drawable.star));
	                star1=0;
	                }   	
	 //               Toast.makeText(getApplicationContext(),"aaya", Toast.LENGTH_LONG).show();

				}
			});
		
		footerText2.setOnClickListener(new  View.OnClickListener() {


			   @Override
			   public void onClick(View view) {
	                if(star2==0)
	                {
				   footerText2.setBackgroundDrawable(getResources().getDrawable(R.drawable.starempty));
	                star2=1;
	                footerText3.setBackgroundDrawable(getResources().getDrawable(R.drawable.starempty));
	                star3=1;
	                footerText4.setBackgroundDrawable(getResources().getDrawable(R.drawable.starempty));
	                star4=1;
	                footerText5.setBackgroundDrawable(getResources().getDrawable(R.drawable.starempty));
	                star5=1;
	                } else
	                {
	                	footerText2.setBackgroundDrawable(getResources().getDrawable(R.drawable.star));
	                star2=0;
	                footerText.setBackgroundDrawable(getResources().getDrawable(R.drawable.star));
	                star1=0;
	                }   	
	 //               Toast.makeText(getApplicationContext(),"aaya", Toast.LENGTH_LONG).show();

				}
			});
		footerText3.setOnClickListener(new  View.OnClickListener() {


			   @Override
			   public void onClick(View view) {
	                if(star3==0)
	                {
				   footerText3.setBackgroundDrawable(getResources().getDrawable(R.drawable.starempty));
	                star3=1;
	                footerText4.setBackgroundDrawable(getResources().getDrawable(R.drawable.starempty));
	                star4=1;
	                footerText5.setBackgroundDrawable(getResources().getDrawable(R.drawable.starempty));
	                star5=1;
	                } else
	                {
	                	footerText3.setBackgroundDrawable(getResources().getDrawable(R.drawable.star));
	                star3=0;
	                footerText2.setBackgroundDrawable(getResources().getDrawable(R.drawable.star));
	                star2=0;
	                footerText.setBackgroundDrawable(getResources().getDrawable(R.drawable.star));
	                star1=0;
	                }   	
	 //               Toast.makeText(getApplicationContext(),"aaya", Toast.LENGTH_LONG).show();

				}
			});
		footerText4.setOnClickListener(new  View.OnClickListener() {


			   @Override
			   public void onClick(View view) {
	                if(star4==0)
	                {
				   footerText4.setBackgroundDrawable(getResources().getDrawable(R.drawable.starempty));
	                star4=1;
	                footerText5.setBackgroundDrawable(getResources().getDrawable(R.drawable.starempty));
	                star5=1;
	                } else
	                {
	                	footerText4.setBackgroundDrawable(getResources().getDrawable(R.drawable.star));
	                star4=0;
	                footerText3.setBackgroundDrawable(getResources().getDrawable(R.drawable.star));
	                star3=0;
	                footerText2.setBackgroundDrawable(getResources().getDrawable(R.drawable.star));
	                star2=0;
	                footerText.setBackgroundDrawable(getResources().getDrawable(R.drawable.star));
	                star1=0;
	                }   	
	 //               Toast.makeText(getApplicationContext(),"aaya", Toast.LENGTH_LONG).show();

				}
			});
		footerText5.setOnClickListener(new  View.OnClickListener() {


			   @Override
			   public void onClick(View view) {
	                if(star5==0)
	                {
				   footerText5.setBackgroundDrawable(getResources().getDrawable(R.drawable.starempty));
	                star5=1;
	                } else
	                {
	                	footerText5.setBackgroundDrawable(getResources().getDrawable(R.drawable.star));
	                star5=0;
	                footerText4.setBackgroundDrawable(getResources().getDrawable(R.drawable.star));
	                star4=0;
	                footerText3.setBackgroundDrawable(getResources().getDrawable(R.drawable.star));
	                star3=0;
	                footerText2.setBackgroundDrawable(getResources().getDrawable(R.drawable.star));
	                star2=0;
	                footerText.setBackgroundDrawable(getResources().getDrawable(R.drawable.star));
	                star1=0;
	                }   	
	 //               Toast.makeText(getApplicationContext(),"aaya", Toast.LENGTH_LONG).show();

				}
			});
		
		listner();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
	}
	public void comment(View v)
	{
		int y=0;
		EditText e1=(EditText)findViewById(R.id.editText1);
		if(star1==0)
			y++;
		if(star2==0)
			y++;
		if(star3==0)
			y++;
		if(star4==0)
			y++;
		if(star5==0)
			y++;
		
		new updatecomment(this).execute(e1.getText().toString().trim(),String.valueOf(y),list.ratingtable);
		pd = new TransparentProgressDialog(this, R.drawable.loading);
		pd.show();
	}
	TransparentProgressDialog pd;
	public static TextView result=null;
	void listner()
	 {
		 result=new TextView(this);
		 result.addTextChangedListener(new TextWatcher() {

			   @Override
			   public void afterTextChanged(Editable s) {
				pd.cancel();
				try
				{
				   //Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();
				   if(result.getText().toString().trim().equals("sucess"))
				   {
					   final Dialog dialog = new Dialog(rating.this);
						dialog.setContentView(R.layout.customerror);
						dialog.setTitle("");

						 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
							// dialog.getWindow().setTitleColor(R.color.titlecolor);
					       

						// set the custom dialog components - text, image and button
						TextView text = (TextView) dialog.findViewById(R.id.text);
						text.setText("Posted Sucessfully");


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
				   else if(result.getText().toString().trim().contains("Duplicate"))
				   {
					   final Dialog dialog = new Dialog(rating.this);
						dialog.setContentView(R.layout.customerror);
						dialog.setTitle("");

						 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
							// dialog.getWindow().setTitleColor(R.color.titlecolor);
					       

						// set the custom dialog components - text, image and button
						TextView text = (TextView) dialog.findViewById(R.id.text);
						text.setText("You Already Posted Your Review");


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
				   else if(result.getText().toString().trim().contains("Exception"))
				   {
					   final Dialog dialog = new Dialog(rating.this);
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
					   
				   }
					//Intent i = new Intent(getApplicationContext(), list.class);
				}
				catch(Exception e)
				{
					final Dialog dialog = new Dialog(rating.this);
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
