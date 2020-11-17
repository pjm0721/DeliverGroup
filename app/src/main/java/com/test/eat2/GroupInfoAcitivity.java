package com.test.eat2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static android.net.Uri.parse;
import static maes.tech.intentanim.CustomIntent.customType;

public class GroupInfoAcitivity extends AppCompatActivity {
    Toolbar toolbar;
    ActionBar actionBar;
    TextView title;
    TextView master;
    TextView sex;
    TextView dorm;
    TextView content;
    Button info_Btn;
    Button info_Btn2;
    FirebaseFirestore db;
    private String pakagename;
    LoginSharedPreferenceUtil util;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info_acitivity);
        final Intent intent=getIntent();
        toolbar = findViewById(R.id.info_toolbar);
        setSupportActionBar(toolbar);
        util=new LoginSharedPreferenceUtil(this);
        db=FirebaseFirestore.getInstance();
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);
        info_Btn2=(Button)findViewById(R.id.info_btn2);
        info_Btn=(Button)findViewById(R.id.info_btn);
        pakagename="com.kakao.talk";
        info_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DocumentReference docRef2 = db.collection("GROUP").document(intent.getStringExtra("그룹넘버"));
                docRef2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("fail", "Listen failed.", e);
                            return;
                        }

                        if (snapshot != null && snapshot.exists()) {
                            Log.d("current", "Current data: " + snapshot.getData());
                            if(snapshot.getData().get("상태").toString().equals("진행중")){
                                Intent intent_kakao=new Intent(Intent.ACTION_VIEW, Uri.parse(intent.getStringExtra("오픈카톡주소")));
                                Map<String, Object> users=new HashMap<>();
                                users.put("ID",util.getStringData("ID",null));
                                db.collection("GROUP").document(intent.getStringExtra("그룹넘버")).collection("users").document(util.getStringData("ID",null)).set(users);
                                startActivity(intent_kakao);
                              /*  try{
                                    Intent intent = getPackageManager().getLaunchIntentForPackage(pakagename);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                                catch (Exception e1){
                                    String url = "market://details?id=" + pakagename;
                                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(i);
                                }*/
                            }
                            else {
                                Toast.makeText(GroupInfoAcitivity.this,"이미 완료된 그룹입니다",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("current null", "Current data: null");
                        }
                    }
                });
            }
        });
        info_Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DocumentReference docRef2 = db.collection("GROUP").document(intent.getStringExtra("그룹넘버"));
                docRef2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("fail", "Listen failed.", e);
                            return;
                        }

                        if (snapshot != null && snapshot.exists()) {
                            Log.d("current", "Current data: " + snapshot.getData());
                            if(snapshot.getData().get("상태").toString().equals("진행중")){
                                Map<String, Object> state=new HashMap<>();
                                state.put("상태","종료");
                                db.collection("GROUP").document(intent.getStringExtra("그룹넘버")).update(state);
                                Toast.makeText(GroupInfoAcitivity.this,"해당 그룹이 종료되었습니다",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                                ActivityCompat.finishAffinity(GroupInfoAcitivity.this);
                                customType(GroupInfoAcitivity.this, "right-to-left");
                                finish();
                            }
                            else {
                            }
                        } else {
                        }
                    }
                });
            }
        });
        title=(TextView)findViewById(R.id.info_title);
        title.setText( intent.getStringExtra("제목"));
        master=(TextView)findViewById(R.id.info_master);
        master.setText(intent.getStringExtra("닉네임"));
        sex=(TextView)findViewById(R.id.info_sex);
        sex.setText(intent.getStringExtra("성별"));
        dorm=(TextView)findViewById(R.id.info_dorm);
        dorm.setText(intent.getStringExtra("기숙사"));
        content=(TextView)findViewById(R.id.info_content);
        content.setText(intent.getStringExtra("설명"));
        int version=intent.getIntExtra("버전",0);
        if(version==1){
            info_Btn.setVisibility(View.VISIBLE);
            info_Btn2.setVisibility(View.INVISIBLE);
        }
        else if(version==2){
            info_Btn.setVisibility(View.INVISIBLE);
            info_Btn2.setVisibility(View.INVISIBLE);
        }
        else {
            info_Btn.setVisibility(View.VISIBLE);
            info_Btn2.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //select back button
                finish();
                customType(GroupInfoAcitivity.this, "right-to-left");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        customType(GroupInfoAcitivity.this, "right-to-left");
    }

}