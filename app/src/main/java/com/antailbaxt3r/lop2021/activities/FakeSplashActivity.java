package com.antailbaxt3r.lop2021.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.antailbaxt3r.lop2021.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class FakeSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_splash);
        String token[] = {""};
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if(task.isComplete()){
                token[0] = task.getResult();
                Log.e("AppConstants", "onComplete: new Token got: "+token[0] );
            }
        });
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(FakeSplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}