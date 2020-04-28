package com.example.iskchat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartPage extends AppCompatActivity {
    FirebaseUser user;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       checkPermission();
         user = FirebaseAuth.getInstance().getCurrentUser();
        Intent   intent = new Intent();

        if(user!=null){
            intent.setClass(this,MainAdapter.class);
            startActivity(intent);
            finish();

        }else
        setContentView(R.layout.main);

    }
    public void toLog(View view){
         user = FirebaseAuth.getInstance().getCurrentUser();
     Intent   intent = new Intent();

        if(user!=null){
            intent.setClass(this,MainAdapter.class);
            finish();
            startActivity(intent);
        }
        else
            intent.setClass(this,LoginActivity.class);

        startActivity(intent);
        finish();    }
        public void toSign(View view){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }
    public  boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED &&checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED ) {
                Log.v("Permission","Permission is granted");

                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                Log.v("Permission","Permission is revoked");

                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("Permission","Permission is granted");

            return true;
        }
    }

}
