package com.example.poc_demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class MainActivity extends AppCompatActivity {
    TextView pathVideo;
    VideoView video_View;

    Button takevideo;

    private static final int pic_id = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pathVideo = findViewById(R.id.TextViewPath);
        video_View=findViewById(R.id.Video_Player1);
        takevideo = findViewById(R.id.VideoButton);



        takevideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askPermission();

            }
        });
    }

    private void askPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            selectVideo();

        }
        //when permission not granted.
        else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, pic_id);

        }
    }

    private void selectVideo() {
        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType("video/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,pic_id);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == pic_id && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            selectVideo();
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == pic_id && resultCode == RESULT_OK && data != null) {

            Uri videoUri = data.getData();
            video_View.setVideoURI(videoUri);

            String path = videoUri.getPath();
            File filepath = new File(path);
            pathVideo.setText(filepath.getAbsolutePath());

            video_View.setVisibility(View.GONE);

            MediaRecorder mediaRecorder= new MediaRecorder();
            mediaRecorder.setVideoEncodingBitRate(690000);



        }
    }
}
