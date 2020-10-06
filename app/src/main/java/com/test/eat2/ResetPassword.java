package com.test.eat2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static maes.tech.intentanim.CustomIntent.customType;

public class ResetPassword extends AppCompatActivity {
    Toolbar toolbar;
    ActionBar actionBar;
    EditText pw;
    EditText pw_chk;
    Button finish_btn;
    private long backKeyPressedTime = 0;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent=getIntent();
        setContentView(R.layout.activity_reset_password);
        pw=(EditText)findViewById(R.id.reset_pw);
        pw_chk=(EditText)findViewById(R.id.reset_pw_chk);
        finish_btn=(Button)findViewById(R.id.reset_finish);
        toolbar = findViewById(R.id.reset_pw_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);
        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ID=intent.getStringExtra("email");
                final String PASSWORD=pw.getText().toString();
                final String PASSWORD_CHK=pw.getText().toString();
                if (PASSWORD.length() < 8 || PASSWORD.length() > 12) {
                    Toast.makeText(ResetPassword.this, "비밀번호는 8자 이상 12자 이하로 입력해주세요", Toast.LENGTH_SHORT).show();
                    pw.setText(null);
                } else if (PASSWORD_CHK.equals(PASSWORD) == false) {
                    Toast.makeText(ResetPassword.this, "비밀번호가 맞지 않습니다", Toast.LENGTH_SHORT).show();
                    pw_chk.setText(null);
                }
                else {
                    Map<String, Object> user = new HashMap<>();
                    user.put("password", PASSWORD);
                    db.collection("USER").document(ID).update(user);
                    Toast.makeText(ResetPassword.this,"비밀번호가 변경되었습니다",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //select back button
                finish();
                customType(ResetPassword.this, "right-to-left");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        customType(ResetPassword.this, "right-to-left");
    }
}