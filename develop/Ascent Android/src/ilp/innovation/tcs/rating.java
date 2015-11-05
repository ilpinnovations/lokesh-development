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
import ilp.innovation.tcs.backgroundservice.updatecommentservice1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
		
		pd=new TransparentProgressDialog(this, R.drawable.loading);

		try
		{
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
		TextView t1=(TextView)findViewById(R.id.textView2);
		TextView t2=(TextView)findViewById(R.id.textView3);
		TextView t3=(TextView)findViewById(R.id.textView4);
		
		t1.setText(list.title);
		t2.setText("Faculty: "+list.facultyname);
		//Toast.makeText(getApplicationContext(),String.valueOf(list.facultytime),Toast.LENGTH_SHORT).show();
		t3.setText(list.facultytime);
		listner();
		}
		catch(Exception e)
		{
			
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
	}
	public void comment(View v)
	{
		String[] temp1;
		

		
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String formattedDate = df.format(c.getTime());
		
		
		  temp1 = formattedDate.split("-");
	     Date date1 =new Date(Integer.parseInt(temp1[2]), Integer.parseInt(temp1[1]),Integer.parseInt(temp1[0]));

	     temp1=list.date.split("-");
	     Date date2 = new Date(Integer.parseInt(temp1[2]), Integer.parseInt(temp1[1]),Integer.parseInt(temp1[0]));

	     if (date1.compareTo(date2)<0)
	      {
	    	 final Dialog dialog = new Dialog(rating.this);
				dialog.setContentView(R.layout.customerror);
				dialog.setTitle("");

				 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
					// dialog.getWindow().setTitleColor(R.color.titlecolor);
			       

				// set the custom dialog components - text, image and button
				TextView text = (TextView) dialog.findViewById(R.id.text);
				text.setText("You cannot give feedback for future events.");


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
	     
		
		
		
		try
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
	
		if(y==0||e1.getText().toString().trim().length()==0)
		{
			 final Dialog dialog = new Dialog(rating.this);
				dialog.setContentView(R.layout.customerror);
				dialog.setTitle("");

				 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
					// dialog.getWindow().setTitleColor(R.color.titlecolor);
			       

				// set the custom dialog components - text, image and button
				TextView text = (TextView) dialog.findViewById(R.id.text);
				text.setText("Please Rate and Comment");


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
		Log.d("here1","here1");
		//write(String.valueOf(list.Sched_id)+"xyzzyspoonshift1"+String.valueOf(y)+"xyzzyspoonshift1"+e1.getText().toString().trim().replace(" ", "%20")+"xyzzyspoonshift1"+String.valueOf(MainActivity.employeeid)+"xyzzyspoonshift2");
		pd.show();
		new updatecommentservice11(getApplicationContext()).execute(String.valueOf(list.Sched_id),String.valueOf(MainActivity.employeeid),String.valueOf(y),e1.getText().toString().trim().replace("\"", "").replace("'",""));
		
		//show dialog
		 
		}
		catch(Exception e)
		{
			Log.d("here1",e.getMessage().toString());
		}
	}

	
	
	
	Dialog dialog;
	public void aboutus(View v)
	{
	

		dialog = new Dialog(rating.this);

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
	
	
	public static TextView eventappointmentbook=null;

	TransparentProgressDialog pd;
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
				 if(eventappointmentbook.getText().toString().trim().contains("You can give feedback only once"))
				 {
					 final Dialog dialog = new Dialog(rating.this);
						dialog.setContentView(R.layout.customerror);
						dialog.setTitle("");

						 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
							// dialog.getWindow().setTitleColor(R.color.titlecolor);
					       

						// set the custom dialog components - text, image and button
						TextView text = (TextView) dialog.findViewById(R.id.text);
						text.setText("You cannot give multiple feedback for a session");


						Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
						// if button is clicked, close the custom dialog
						dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								finish();
								dialog.dismiss();
							}
						});
						dialog.show();
				    }
				 else if(eventappointmentbook.getText().toString().trim().contains("Successfully registered"))
				 {
					 final Dialog dialog = new Dialog(rating.this);
						dialog.setContentView(R.layout.customerror);
						dialog.setTitle("");

						 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
							// dialog.getWindow().setTitleColor(R.color.titlecolor);
					       

						// set the custom dialog components - text, image and button
						TextView text = (TextView) dialog.findViewById(R.id.text);
						text.setText("Feedback Submitted Sucessfully");


						Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
						// if button is clicked, close the custom dialog
						dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								finish();
								dialog.dismiss();
							}
						});
						dialog.show();
				 }
				 else
				 {
					 final Dialog dialog = new Dialog(rating.this);
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
								finish();
								dialog.dismiss();
							}
						});
						dialog.show();
				 }
		}catch(Exception e)
		{
			 final Dialog dialog = new Dialog(rating.this);
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
