package com.example.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessRegister extends AppCompatActivity {
    Button btn_explore;
    TextView app_title,app_subtitle;
    ImageView logo_success_register;
    Animation app_splash,bottom_to_top,top_to_bottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        //load animation
        app_splash= AnimationUtils.loadAnimation(this,R.anim.app_splash);
        bottom_to_top= AnimationUtils.loadAnimation(this,R.anim.bottom_to_top);
        top_to_bottom= AnimationUtils.loadAnimation(this,R.anim.top_to_bottom);

        //load element
         logo_success_register= findViewById(R.id.ic_success);
        app_title = findViewById(R.id.app_title);
        app_subtitle=findViewById(R.id.app_subtitle);
        btn_explore = findViewById(R.id.btn_explore);

        //run animation
        logo_success_register.startAnimation(app_splash);
        app_title.startAnimation(top_to_bottom);
        app_subtitle.startAnimation(bottom_to_top);
        btn_explore.startAnimation(bottom_to_top);


        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(SuccessRegister.this,HomeAct.class);
                startActivity(gotohome);
            }
        });

    }
}
