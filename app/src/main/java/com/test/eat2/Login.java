package com.test.eat2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {
    private EditText id;
    private EditText password;
    private Button loginbtn;
    private Button findid;
    private Button findpwd;
    private Button signupbtn;
    private CheckBox autoCheckBox;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        id=(EditText)findViewById(R.id.loginid);
        password=(EditText)findViewById(R.id.loginpassword);
        findid=(Button)findViewById(R.id.login_findid_btn);
        findpwd=(Button)findViewById(R.id.login_findpwd_btn);
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

    }
    private void login(){
        final String idtext = id.getText().toString();
        final String pwtext = password.getText().toString();
        if (idtext.isEmpty() == true || pwtext.isEmpty() == true) {
            Toast.makeText(this, "아이디와 비밀번호를 입력해주세요", Toast.LENGTH_LONG).show();
            return;
        }
        db.collection("USER")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                    if(document.getId().equals(idtext)==true){
                                        if(document.getData().get("password").toString().equals(pwtext)==true){
                                            login_success(idtext,(String)document.getData().get("이름"),(String)document.getData().get("PHONE"));
                                        }
                                    }
                            }
                        }
                        else {

                        }
                    }
                });
    }
    private int scs=0;
    private void signup(){
        Intent intent=new Intent(getApplicationContext(), Signup.class);
        startActivity(intent);
    }
    private void login_success(String ID, String NAME,String PHONE){
        scs=1;
        Boolean autologin=autoCheckBox.isChecked();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);

    }
}