package com.test.eat2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static maes.tech.intentanim.CustomIntent.customType;

public class Signup2 extends AppCompatActivity {
    String MAJOR;
    String YEAR;
    String EMAIL;
    String NAME;
    TextView id;
    EditText password;
    EditText password_chk;
    EditText phone;
    EditText nick;
    Button join;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private long backKeyPressedTime = 0;
    RadioGroup Signup2_grp;
    int scs=0;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        Intent intent=getIntent();
        Signup2_grp = (RadioGroup) findViewById(R.id.signup2_grp);
        MAJOR=intent.getStringExtra("major");
        YEAR=intent.getStringExtra("year");
        EMAIL=intent.getStringExtra("email");
        NAME=intent.getStringExtra("name");
        id=(TextView) findViewById(R.id.signup2_id);
        id.setText(EMAIL);
        password=(EditText)findViewById(R.id.signup2_password);
        password_chk=(EditText)findViewById(R.id.signup2_password_chk);
        phone=(EditText)findViewById(R.id.signup2_phone);
        nick=(EditText)findViewById(R.id.signup2_nick);
        join=(Button)findViewById(R.id.signup2_join);
        toolbar = findViewById(R.id.signup2_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String PASSWORD = password.getText().toString();
                final String PASSWORD_CHK = password_chk.getText().toString();
                final String PHONE = phone.getText().toString();
                final String NICK = nick.getText().toString();
                int Rid = Signup2_grp.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(Rid);
                final String who = rb.getText().toString();
                if (PASSWORD.length() < 8 || PASSWORD.length() > 12) {
                    Toast.makeText(Signup2.this, "비밀번호는 8자 이상 12자 이하로 입력해주세요", Toast.LENGTH_SHORT).show();
                    password.setText(null);
                } else if (PASSWORD_CHK.equals(PASSWORD) == false) {
                    Toast.makeText(Signup2.this, "비밀번호가 맞지 않습니다", Toast.LENGTH_SHORT).show();
                    password_chk.setText(null);
                } else if (PHONE.length() != 10 && PHONE.length() != 11) {
                    Toast.makeText(Signup2.this, "전화번호를 제대로 입력해주세요", Toast.LENGTH_SHORT).show();
                }   else if (TextUtils.isEmpty(NICK) == true){
                    Toast.makeText(Signup2.this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> user = new HashMap<>();
                    user.put("password", PASSWORD);
                    user.put("학번",MAJOR);
                    user.put("입학년도",YEAR);
                    user.put("이름", NAME);
                    user.put("phone",PHONE);
                    user.put("Email",EMAIL);
                    user.put("닉네임", NICK);
                    user.put("성별",who);
                    db.collection("USER").document(EMAIL).set(user);
                    finish();
                    customType(Signup2.this, "right-to-left");
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
                customType(Signup2.this, "right-to-left");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Toast toast;
        toast = Toast.makeText(this, "초기화", Toast.LENGTH_SHORT);
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "회원가입을 종료 하시겠습니까?", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
        super.onBackPressed();
        customType(Signup2.this, "right-to-left");
    }
}