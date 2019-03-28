package com.example.yeslifework01.takephoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView MyImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button boton = findViewById(R.id.boton);
        boton.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                     if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                         startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                                     }
                                 }
                             }

        );
        MyImageView = findViewById(R.id.foto);
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+"/myCameraPhoto.jpg");
        Uri uri = Uri.fromFile(file);
        MyImageView.setImageURI(uri);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            MyImageView = findViewById(R.id.foto);
            MyImageView.setImageBitmap(imageBitmap);


            try {
                FileOutputStream out = openFileOutput("myCameraPhoto.jpg", MODE_PRIVATE);
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.close();
                Toast toast = Toast.makeText(getApplicationContext(), "Photo successfully saved !!", Toast.LENGTH_SHORT);
                toast.show();
            } catch (Exception e) {
                Toast toast = Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT);
                toast.show();
                e.printStackTrace();
            }
        }
    }
}