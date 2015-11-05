package com.androidexample.uploadtoserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;




import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.androidexample.uploadtoserver.R;
public class MainActivity extends Activity {
int TAKE_PHOTO_CODE = 0;
public static int count=0;

/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState)
{
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

//here,we are making a folder named picFolder to store pics taken by the camera using this application
       

   
}

public void click(View v)
{
	 final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/"; 
     File newdir = new File(dir); 
     newdir.mkdirs();
	   count++;
       String file = dir+count+".jpg";
       File newfile = new File(file);
       try {
           newfile.createNewFile();
       } catch (IOException e) {}       

       Uri outputFileUri = Uri.fromFile(newfile);

       Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
       cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

       startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
        Log.d("CameraDemo", "Pic saved");


    }
	
    if (count==10)
    {
    	Toast.makeText(getApplicationContext(), "Your Images are Uploaded",Toast.LENGTH_SHORT).show();
    	Intent i = new Intent(getApplicationContext(), UploadToServer.class);
        
   	 startActivity(i);
        //   File newfile = new File(file);
    }
}


}