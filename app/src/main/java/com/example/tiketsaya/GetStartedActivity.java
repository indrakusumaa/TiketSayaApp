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

public class GetStartedActivity extends AppCompatActivity {
    Button btn_sign_in,btn_new_account;
    Animation top_to_bottom,bottom_to_top;
    ImageView imgView2;
    TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        //load animation
        top_to_bottom= AnimationUtils.loadAnimation(this,R.anim.top_to_bottom);
        bottom_to_top=AnimationUtils.loadAnimation(this,R.anim.bottom_to_top);

        //load element
        imgView2=findViewById(R.id.imageView2);
        txtView=findViewById(R.id.textView);
        btn_sign_in=findViewById(R.id.btn_sign_in);
        btn_new_account=findViewById(R.id.btn_new_account);

        //run animation
        imgView2.startAnimation(top_to_bottom);
        txtView.startAnimation(top_to_bottom);

        btn_new_account.startAnimation(bottom_to_top);
        btn_sign_in.startAnimation(bottom_to_top);




        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosign = new Intent(GetStartedActivity.this,SignInAct.class);
                startActivity(gotosign);
                finish();

            }
        });

        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregist = new Intent(GetStartedActivity.this,RegisterOneAct.class);
                startActivity(gotoregist);
                finish();

            }
        });


    }
}
