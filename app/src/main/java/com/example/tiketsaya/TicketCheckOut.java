package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class TicketCheckOut extends AppCompatActivity {
    Button btn_pay_now,btn_minus,btn_plus;
    TextView txtjumlah_ticket,txt_totalharga,txt_total_balance,nama_wisata,lokasi,ketentuan;
    Integer value_jmlTiket=1;
    Integer myWallet=0;
    Integer value_total_harga=0;
    Integer value_harga_tiket=0;
    Integer sisa_balance=0;
    ImageView notice_uang;
    DatabaseReference reference,reference2,reference3,reference4;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "" ;

    String date_wisata="";
    String time_wisata="";

    //generate nomor random
    Integer nomor_transaksi = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_check_out);

        getUsernameLocal();

        //mengambil data dari ticket detail
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

        btn_pay_now = findViewById(R.id.btn_pay_now);
        btn_minus = findViewById(R.id.btn_minus);
        btn_plus = findViewById(R.id.btn_plus);
        txtjumlah_ticket = findViewById(R.id.txtjumlah_ticket);
        txt_totalharga = findViewById(R.id.txt_totalharga);
        txt_total_balance = findViewById(R.id.txt_total_balance);
        notice_uang = findViewById(R.id.notice_uang);

        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);

        //setting value baru
        txtjumlah_ticket.setText(value_jmlTiket.toString());
        txt_totalharga.setText("US $" + value_total_harga+"");




        //default, hide button minus
        btn_minus.animate().alpha(0).setDuration(300).start();
        btn_minus.setEnabled(false);
        notice_uang.setVisibility(View.GONE);

        //mengambil user balance

        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myWallet = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                txt_total_balance.setText("US $" + myWallet+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //mengambil data dari Firebase berdasar intent
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //menimpa data dari Firebase
                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());
                date_wisata = dataSnapshot.child("ketentuan").getValue().toString();
                time_wisata = dataSnapshot.child("ketentuan").getValue().toString();
                value_harga_tiket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());

                value_total_harga=value_harga_tiket*value_jmlTiket;
                txt_totalharga.setText("US $"+value_total_harga+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value_jmlTiket += 1;
                txtjumlah_ticket.setText(value_jmlTiket.toString());
                if (value_jmlTiket > 1) {
                    btn_minus.animate().alpha(1).setDuration(300).start();
                    btn_minus.setEnabled(true);
                }
                value_total_harga=value_harga_tiket*value_jmlTiket;
                txt_totalharga.setText("US $"+value_total_harga+"");
                if(value_total_harga > myWallet){
                    btn_pay_now.animate().translationY(200).alpha(0).setDuration(350).start();
                    btn_pay_now.setEnabled(false);
                    txt_total_balance.setTextColor(Color.parseColor( "#D1206B"));
                    notice_uang.setVisibility(View.VISIBLE);

                }
            }
        });


        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value_jmlTiket-= 1;
                txtjumlah_ticket.setText(value_jmlTiket.toString());
                    if ( value_jmlTiket < 2 )
                    {
                        btn_minus.animate().alpha(0).setDuration(300).start();
                        btn_minus.setEnabled(false);
                    }
                value_total_harga=value_harga_tiket*value_jmlTiket;
                txt_totalharga.setText("US $"+value_total_harga+"");
                if(value_total_harga < myWallet){
                    btn_pay_now.animate().translationY(0).alpha(1).setDuration(350).start();
                    btn_pay_now.setEnabled(true);
                    txt_total_balance.setTextColor(Color.parseColor( "#203DD1"));
                    notice_uang.setVisibility(View.GONE);

                }
            }
        });




        btn_pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //menyimpan data tiket user dari Firebase dan membuat tabel baru "MyTickets"
                reference3 = FirebaseDatabase.getInstance().getReference().child("MyTickets").child(username_key_new).child(nama_wisata.getText().toString() + nomor_transaksi);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_ticket").setValue(nama_wisata.getText().toString() + nomor_transaksi);
                        reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("date_wisata").setValue(date_wisata);
                        reference3.getRef().child("time_wisata").setValue(time_wisata);
                        reference3.getRef().child("jumlah_tiket").setValue(value_jmlTiket.toString());

                        Intent gotosuccessbuy = new Intent(TicketCheckOut.this, SuccessBuyTicket.class);
                        startActivity(gotosuccessbuy);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //update data user balance ke firebase
                reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisa_balance = myWallet - value_total_harga;
                        reference4.getRef().child("user_balance").setValue(sisa_balance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
    }
    public void getUsernameLocal() {

        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");

    }
}
