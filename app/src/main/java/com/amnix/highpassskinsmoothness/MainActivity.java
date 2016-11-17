package com.amnix.highpassskinsmoothness;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.amnix.skinsmoothness.AmniXSkinSmooth;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private final AmniXSkinSmooth amniXSkinSmooth = AmniXSkinSmooth.getInstance();
    private ImageView imageView;
    private Bitmap bitmap, processed;
    private EditText smooth, white;
    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        smooth = (EditText) findViewById(R.id.raddEditext);
        white = (EditText) findViewById(R.id.whitenessEditext);
        imageView = (ImageView) findViewById(R.id.imageView);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (bitmap == null || processed == null)
                    return;
                else if (isChecked)
                    imageView.setImageBitmap(processed);
                else
                    imageView.setImageBitmap(bitmap);

            }
        });
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPermissionGranted())
                    openImageIntent();
                else
                    askForPermission();
            }
        });
        findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap == null)
                    return;
                final float smoothR, whiteR;
                if (smooth.getText().toString().isEmpty() || !TextUtils.isDigitsOnly(smooth.getText().toString()))
                    smoothR = 0;
                else
                    smoothR = Float.valueOf(smooth.getText().toString());
                if (white.getText().toString().isEmpty() || !TextUtils.isDigitsOnly(white.getText().toString()))
                    whiteR = 0;
                else
                    whiteR = Float.valueOf(white.getText().toString());
                final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "Please Wait!", "Doing SOme Work", true, false);

                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {

                        amniXSkinSmooth.storeBitmap(bitmap, false);
                        amniXSkinSmooth.initSdk();git
                        amniXSkinSmooth.startFullBeauty(smoothR, whiteR);
                        amniXSkinSmooth.startSkinSmoothness(smoothR);
                        amniXSkinSmooth.startSkinWhiteness(whiteR);
                        processed = amniXSkinSmooth.getBitmapAndFree();
                        amniXSkinSmooth.unInitSdk();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        if (processed != null) {
                            imageView.setImageBitmap(processed);
                        } else {
                            Toast.makeText(MainActivity.this, "Unable to Process!", Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute();

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        amniXSkinSmooth.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 40) {
            if (isPermissionGranted())
                openImageIntent();
            else
                askForPermission();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 41 && resultCode == RESULT_OK) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                Snackbar.make(findViewById(R.id.fab), "Unable to get Image, Please Try Again!", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void askForPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 40);
    }

    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void openImageIntent() {
        startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), 41);

    }

}
