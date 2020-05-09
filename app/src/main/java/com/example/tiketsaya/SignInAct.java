package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class SignInAct extends AppCompatActivity {
    TextView btn_new_account;
    Button btn_sign_in;
    EditText xpassword,xusername;

    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btn_sign_in = findViewById(R.id.btn_sign_in);
        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);
        btn_new_account = findViewById(R.id.btn_new_account);


        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregist = new Intent(SignInAct.this,RegisterOneAct.class);
                startActivity(gotoregist);
                finish();
            }
        });


        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // ubah state menjadi loading
                btn_sign_in.setEnabled(false);
                btn_sign_in.setText("loading ...");

                final  String username = xusername.getText().toString();
                final  String password= xpassword.getText().toString();

                if(username.isEmpty() ){
                    Toast.makeText(getApplicationContext(), "Username kosong!",Toast.LENGTH_SHORT).show();
                    btn_sign_in.setEnabled(true);
                    btn_sign_in.setText("SIGN IN");
                }else {
                    if(password.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Password kosong!",Toast.LENGTH_SHORT).show();
                        btn_sign_in.setEnabled(true);
                        btn_sign_in.setText("SIGN IN");
                    }else{
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    //ambil password
                                    String passwordfromDatabase = dataSnapshot.child("password").getValue().toString();

                                    //validasi password
                                    if(passwordfromDatabase.equals(password))
                                    {

                                        //simpan username (key) kepada local
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, username);
                                        editor.apply();

                                        //pindah act
                                        Intent gotohome = new Intent(SignInAct.this,HomeAct.class);
                                        startActivity(gotohome);


                                    }else {
                                        Toast.makeText(getApplicationContext(), "Password anda salah!",Toast.LENGTH_SHORT).show();
                                        btn_sign_in.setEnabled(true);
                                        btn_sign_in.setText("Sign In");
                                    }
                                }else {
                                    Toast.makeText(getApplicationContext(), "Username tidak terdaftar!",Toast.LENGTH_SHORT).show();
                                    btn_sign_in.setEnabled(true);
                                    btn_sign_in.setText("Sign In");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }

            }
        });


    }
}
