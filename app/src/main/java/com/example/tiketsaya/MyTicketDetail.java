package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyTicketDetail extends AppCompatActivity {
    LinearLayout btn_back;
    DatabaseReference reference;
    TextView xnama_wisata,xtime_wisata,xketentuan,xlokasi,xdate_wisata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket_detail);
        xnama_wisata = findViewById(R.id.xnama_wisata);
        xtime_wisata = findViewById(R.id.xtime_wisata);
        xketentuan = findViewById(R.id.xketentuan);
        xlokasi = findViewById(R.id.xlokasi);
        xdate_wisata = findViewById(R.id.xdate_wisata);
        btn_back = findViewById(R.id.btn_back);

        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String nama_wisata_baru = bundle.getString("nama_wisata");

        //mengambil data dari firebase
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(nama_wisata_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xnama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                xlokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                xtime_wisata.setText(dataSnapshot.child("time_wisata").getValue().toString());
                xdate_wisata.setText(dataSnapshot.child("date_wisata").getValue().toString());
                xketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtoprofile = new Intent(MyTicketDetail.this, ProfileAct.class);
                startActivity(backtoprofile);
            }
        });
    }
}
