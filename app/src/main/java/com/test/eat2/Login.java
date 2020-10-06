package com.test.eat2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static maes.tech.intentanim.CustomIntent.customType;

public class Login extends AppCompatActivity {
    private EditText id;
    private EditText password;
    private Button loginbtn;
    private Button findid;
    private Button findpwd;
    private Button signupbtn;
    private CheckBox autoCheckBox;
    private LoginSharedPreferenceUtil util;
    private long backKeyPressedTime = 0;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        util = new LoginSharedPreferenceUtil(this);
        util.setBooleanData("AutoLogin", false);
        util.setStringData("ID", "");
        util.setStringData("권한", "null");
        id=(EditText)findViewById(R.id.loginid);
        password=(EditText)findViewById(R.id.loginpassword);
        findpwd=(Button)findViewById(R.id.login_findpwd_btn);
        findpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_find_pw=new Intent(getApplicationContext(),FindPassword.class);
                startActivity(intent_find_pw);
                customType(Login.this,"left-to-right");
            }
        });
        loginbtn=(Button)findViewById(R.id.loginbutton);
        signupbtn=(Button)findViewById(R.id.login_signup_btn);
        autoCheckBox=(CheckBox)findViewById(R.id.login_checkBox);
        loginbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                login();
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signup();
            }
        });
       id.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
                if(id.getText().toString().isEmpty()==false){
                    if(password.getText().toString().isEmpty()==false){
                        loginbtn.setBackgroundColor(Color.parseColor("#0000ff"));
                    }
                }
                else {
                        loginbtn.setBackgroundColor(Color.parseColor("#FFDCDCDC"));
                }
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
                if(password.getText().toString().isEmpty()==false){
                    if(id.getText().toString().isEmpty()==false){
                        loginbtn.setBackgroundColor(Color.parseColor("#0000ff"));
                    }
                }
                else {
                    loginbtn.setBackgroundColor(Color.parseColor("#FFDCDCDC"));
                }
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
            }
        });
    }
    private void login() {
        final String idtext = id.getText().toString();
        final String pwtext = password.getText().toString();
        final DocumentReference docRef = db.collection("USER").document(idtext);
        if (idtext.isEmpty() == true || pwtext.isEmpty() == true) {
            Toast.makeText(this, "아이디와 비밀번호를 입력해주세요", Toast.LENGTH_LONG).show();
            return;
        } else {
            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w("fail", "Listen failed.", e);
                        return;
                    }

                    if (snapshot != null && snapshot.exists()) {
                        if(snapshot.getData().get("password").equals(pwtext)){
                            login_success(idtext);
                        }
                        else {
                            Toast.makeText(Login.this,"아이디 또는 비밀번호가 맞지 않습니다",Toast.LENGTH_SHORT).show();
                            password.setText(null);
                        }
                    } else {
                        Toast.makeText(Login.this,"아이디 또는 비밀번호가 맞지 않습니다",Toast.LENGTH_SHORT).show();
                        Log.d("current null", "Current data: null");
                    }
                }
            });
        }
    }
    private int scs=0;
    private void signup(){
        Intent intent=new Intent(getApplicationContext(), Signup.class);
        startActivity(intent);
        customType(Login.this, "left-to-right");
    }
    private void login_success(String ID){
        scs=1;
        Boolean autologin=autoCheckBox.isChecked();
        if (autologin) {
            util.setBooleanData("AutoLogin", true);
        }
        else {
            util.setBooleanData("AutoLogin", false);
        }
        util.setStringData("ID",ID);
        DocumentReference dbref=db.collection("USER").document(ID);
        dbref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("fail", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d("current", "Current data: " + snapshot.getData());
                    util.setStringData("닉네임",snapshot.getData().get("닉네임").toString());
                } else {
                    Log.d("current null", "Current data: null");
                }
            }
        });
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        customType(Login.this, "top-to-bottom");
        finish();
    }
    @Override
    public void onBackPressed() {
        Toast toast=Toast.makeText(this,"초기화",Toast.LENGTH_SHORT);
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "종료 하시겠습니까?", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
    }

}