package com.test.eat2;

import androidx.annotation.NonNull;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static maes.tech.intentanim.CustomIntent.customType;

public class AddGroupActivity extends AppCompatActivity {
    Toolbar toolbar;
    ActionBar actionBar;
    TextView dorm;
    Spinner sex_spinner;
    Button register_btn;
    EditText TITLE;
    EditText CONTENT;
    EditText OPEN_KAKAO;
    String dorm_name;
    int cnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        dorm_name=intent.getStringExtra("기숙사");
        setContentView(R.layout.activity_add_group);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);
        dorm=(TextView)findViewById(R.id.add_dorm);
        dorm.setText(dorm_name);
        sex_spinner=(Spinner)findViewById(R.id.add_sex_spinner);
        register_btn=(Button)findViewById(R.id.add_register_btn);
        TITLE=(EditText)findViewById(R.id.add_title);
        OPEN_KAKAO=(EditText)findViewById(R.id.add_open);
        CONTENT=(EditText)findViewById(R.id.add_content);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                group_register();
            }
        });
        get_cnt();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //select back button
                finish();
                customType(AddGroupActivity.this, "right-to-left");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void group_register(){
        if(TITLE.getText().toString().isEmpty()){
            Toast.makeText(AddGroupActivity.this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
        }
        else if(sex_spinner.getSelectedItem().toString().equals("성별 선택")){
            Toast.makeText(AddGroupActivity.this, "참여인원 성별을 선택해주세요", Toast.LENGTH_SHORT).show();
        }
        else if(OPEN_KAKAO.getText().toString().isEmpty()){
            Toast.makeText(AddGroupActivity.this, "오픈카톡 주소을 입력해주세요", Toast.LENGTH_SHORT).show();
        }
        else if(CONTENT.getText().toString().isEmpty()){
            Toast.makeText(AddGroupActivity.this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
        }
        else {
            FirebaseFirestore db=FirebaseFirestore.getInstance();
            LoginSharedPreferenceUtil util = new LoginSharedPreferenceUtil(AddGroupActivity.this);
            SimpleDateFormat format1=new SimpleDateFormat ("MM-dd HH:mm");
            Date time=new Date();
            String time1=format1.format(time);
            Map<String, Object> group = new HashMap<>();
            group.put("제목", TITLE.getText().toString());
            group.put("기숙사",dorm_name);
            group.put("성별",sex_spinner.getSelectedItem().toString());
            group.put("오픈카톡주소", OPEN_KAKAO.getText().toString());
            group.put("내용",CONTENT.getText().toString());
            group.put("시간",time1);
            group.put("상태","진행중");
            group.put("그룹장",util.getStringData("ID",null));
            group.put("그룹넘버",cnt);
            group.put("닉네임",util.getStringData("닉네임",null));
            Map<String, Object> group_cnt=new HashMap<>();
            group_cnt.put("cnt",cnt+1);
            db.collection("GROUP").document(Integer.toString(cnt)).set(group);
            db.collection("CNT").document("group cnt").set(group_cnt);
            Map<String, Object> users=new HashMap<>();
            users.put("ID",util.getStringData("ID",null));
            db.collection("GROUP").document(Integer.toString(cnt)).collection("users").document(util.getStringData("ID",null)).set(users);
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            ActivityCompat.finishAffinity(AddGroupActivity.this);
            customType(AddGroupActivity.this, "right-to-left");
            Toast.makeText(AddGroupActivity.this,"해당 그룹이 등록되었습니다",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    private void get_cnt(){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("CNT").document("group cnt");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        cnt= Integer.parseInt(document.getData().get("cnt").toString());
                    } else {
                    }
                } else {
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        customType(AddGroupActivity.this, "right-to-left");
    }
}