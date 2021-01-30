package com.example.inzapk;
/*sending do GIT: File -> Settings -> Version Control -> GitHub
 Then go to VCS -> Import into Version Control -> Share Project on GitHub.*/

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.sax.StartElementListener;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

 /*log to check openCV connection*/
    static
    {
        if(OpenCVLoader.initDebug())
        {
            Log.d(TAG, "OpenCV is Loaded Successfully.");

        }
        else{
            Log.d(TAG, "OpenCV not Working or Loaded.");
        }

    }


    ImageView   im1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


       /* if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }*/




        setContentView(R.layout.activity_main);
        im1 = (ImageView)findViewById(R.id.imageView1);


    }

/*gallery oppening*/
    public void openGallery(View v) {
        //CHECK PERMISSION
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //permission is granted-can go to gallery
            Toast.makeText(this, "Otwieranie galerii urządzenia!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 101);

        } else
            {//permission is not granted
                //asking for permission:
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                if(!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    //permission is denied and "don't ask again" checkbox was checked- asking for permission is blocked:
                    Toast.makeText(this, "Musisz zezwolić aplikacji na użycie pamięci w ustawieniach urządzenia!", Toast.LENGTH_LONG).show();
                }
            }


    }


    @SuppressLint("Assert")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101 && resultCode ==RESULT_OK && data==null)
        {
            //get image Uri
            assert false;
            Uri imageUri = data.getData();
            //get path from Uri
            String path = getPath(imageUri);
        }
    }

/*picked file's path*/
    private String getPath(Uri uri)
    {
        if(uri ==null)
        {
            return null;
        }
        else
        {
            String[] projection ={MediaStore.Images.Media.DATA};
            Cursor cursor =  getContentResolver().query(uri,projection,null,null,null);

            if(cursor!=null)
            {
                int col_index =cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(col_index);
            }

        }
        return uri.getPath();
    }
}
