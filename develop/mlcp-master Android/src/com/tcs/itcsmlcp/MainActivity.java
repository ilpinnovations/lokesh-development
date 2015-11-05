package com.tcs.itcsmlcp;






import com.google.android.gcm.GCMRegistrar;

//import com.slidingmenu.lib.SlidingMenu;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
public class MainActivity extends Activity implements OnClickListener,OnItemClickListener {

	// 
	ImageButton btnSettings,btnSearch;
	

private ProgressDialog progress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewGroup vg = (ViewGroup) findViewById(R.id.root);
		Utils.setFontAllView(vg);
		
		
	final DatabaseHandler db = new DatabaseHandler(this);
		
	Toast.makeText(getApplicationContext(), ""+db.getContactsCount(), Toast.LENGTH_LONG).show();
		if(db.getContactsCount()<=0)
		{
			db.close();
			finish();
			Intent i=new Intent(this,InfoActivity.class);
			startActivity(i);
		
			
		}
		
		
		 progress = new ProgressDialog(this);
		

		btnSettings = (ImageButton) findViewById(R.id.btnImg_Settings);
		
		
		

		btnSettings.setOnClickListener(this);
		
		ListView listView = (ListView) findViewById(R.id.list_colors);
		String[] values = new String[] { "MY SLOT",  "MLCP PARKING INFO", "PARKING STATS" };

		Integer[] images = { R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher, R.drawable.ic_launcher 
				};
		// Create Color Adapter
		MyColorArrayAdapter adapter = new MyColorArrayAdapter(this, values,
				images);

		// Assign adapter to ListView
		listView.setAdapter(adapter);

		// Add Listener to handle click on list row
		listView.setOnItemClickListener(this);
		
		
		
		
		
		
	}
	
	
	 public void open(View view){
	      progress=ProgressDialog.show(MainActivity.this, "", "");
			 progress.setContentView(R.layout.progress);
		      progress.show();
	   final int totalProgressTime = 100;

	   final Thread t = new Thread(){

	   @Override
	   public void run(){
	 
	      int jumpTime = 0;
	      while(jumpTime < totalProgressTime){
	         try {
	            sleep(200);
	            jumpTime += 5;
	            progress.setProgress(jumpTime);
	         } catch (InterruptedException e) {
	           // TODO Auto-generated catch block
	           e.printStackTrace();
	         }
finally{
	
	progress.dismiss();
}
	      }

	   }
	   };
	   t.start();

	   }
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnImg_Settings){
		}

	}
	
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

		open(v);
		
		switch (position) {
		case 0:
			Intent intent = new Intent(this, MySlotActivity.class);
			
			

			intent.putExtra("id", v.getId());
			startActivity(intent);

			break;
		case 1:
Intent intent1 = new Intent(this, ParkingInfoActivity_New.class);
			
startActivity(intent1);

			break;

		case 2:
Intent intent2 = new Intent(this, ParkingStatsActivity.class);
			
			

			intent2.putExtra("id", v.getId());
			startActivity(intent2);
			break;
		
		default:
			break;
		}
		
	}
	
	
	
	  @Override
	    public boolean onCreateOptionsMenu(Menu menu)
	    {
	        MenuInflater menuInflater = getMenuInflater();
	        menuInflater.inflate(R.layout.menu, menu);
	        return true;
	    }
	     
	    /**
	     * Event Handling for Individual menu item selected
	     * Identify single menu item by it's id
	     * */
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	         
	        switch (item.getItemId())
	        {
	        case R.id.signout:
	            // Single menu item is selected do something
	            // Ex: launching new activity/screen or show alert message
	            //Toast.makeText(AndroidMenusActivity.this, "Bookmark is Selected", Toast.LENGTH_SHORT).show();
	        	 try {
	             	//GCMRegistrar.unregister(MainActivity.this);
	             	DatabaseHandler db = new DatabaseHandler(MainActivity.this);
	             	db.dropTable();
	             	finish();
	             	Toast.makeText(getApplicationContext(), "signout", Toast.LENGTH_LONG).show();
	             } 
	             catch (Exception e) {     
	             System.out.println("Error Message: " + e.getMessage());
	             }
	        	return true;
	 
	 
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }    
	

}
