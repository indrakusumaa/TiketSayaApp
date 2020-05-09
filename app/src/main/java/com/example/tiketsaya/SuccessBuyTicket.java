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

public class SuccessBuyTicket extends AppCompatActivity {
    Animation app_splash,bottom_to_top,top_to_bottom;
    ImageView ic_success_buy;
    TextView title,subtitle;
    Button btn_view, btn_dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_ticket);

        //load animation
        app_splash= AnimationUtils.loadAnimation(this,R.anim.app_splash);
        bottom_to_top= AnimationUtils.loadAnimation(this,R.anim.bottom_to_top);
        top_to_bottom= AnimationUtils.loadAnimation(this,R.anim.top_to_bottom);

        ic_success_buy = findViewById(R.id.imageView4);
        title = findViewById(R.id.textView2);
        subtitle = findViewById(R.id.textView3);
        btn_view = findViewById(R.id.btn_view_ticket);
        btn_dashboard = findViewById(R.id.btn_my_dashboard);

        ic_success_buy.startAnimation(app_splash);
        title.startAnimation(top_to_bottom);
        subtitle.startAnimation(top_to_bottom);
        btn_dashboard.startAnimation(bottom_to_top);
        btn_view.startAnimation(top_to_bottom);

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofil = new Intent(SuccessBuyTicket.this,ProfileAct.class);
                startActivity(gotoprofil);
            }
        });

        btn_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(SuccessBuyTicket.this,HomeAct.class);
                startActivity(gotohome);
            }
        });


    }
}
