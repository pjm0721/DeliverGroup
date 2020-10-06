package com.test.eat2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static maes.tech.intentanim.CustomIntent.customType;

public class GroupListActivity extends AppCompatActivity {
    Toolbar toolbar;
    ActionBar actionBar;
    String dorm_name;
    private long refreshKeyPressedTime = 0;
    private ListView group_list_view;
    private GroupListAdapter groupListAdapter;
    private List<GroupListItem> group_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
       /* Toolbar tb = (Toolbar) findViewById(R.id.toolbar) ;
        setSupportActionBar(tb);*/
        Intent intent=getIntent();
        dorm_name=intent.getStringExtra("dorm_name");
        TextView textView=(TextView)findViewById(R.id.grouplist_title);
        textView.setText(dorm_name);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);
        group_list_view=(ListView)findViewById(R.id.mygroup_view);
        group_list=new ArrayList<GroupListItem>();
        groupListAdapter=new GroupListAdapter(getApplicationContext(),group_list);
        group_list_view.setAdapter(groupListAdapter);
        Grouplist_get_data(dorm_name);
        group_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(GroupListActivity.this, GroupInfoAcitivity.class);
                intent.putExtra("닉네임", group_list.get(i).get_Nick());
                intent.putExtra("제목", group_list.get(i).get_Title());
                intent.putExtra("기숙사", dorm_name);
                intent.putExtra("설명",group_list.get(i).get_Content());
                intent.putExtra("성별", group_list.get(i).get_Sex());
                intent.putExtra("오픈카톡주소", group_list.get(i).get_Open_Kakao());
                intent.putExtra("그룹장", group_list.get(i).get_Master());
                intent.putExtra("그룹넘버",  group_list.get(i).getGroup_number());
                intent.putExtra("시간",group_list.get(i).get_Time());
                intent.putExtra("버전",1);
                startActivity(intent);
                customType(GroupListActivity.this,"left-to-right");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.grouplist_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                Toast.makeText(this,"새로고침이 완료되었습니다",Toast.LENGTH_SHORT).show();
                    Grouplist_get_data(dorm_name);
                break;
            case R.id.action_plus:
                Intent intent2=new Intent(getApplicationContext(),AddGroupActivity.class);
                intent2.putExtra("기숙사",dorm_name);
                startActivity(intent2);
                customType(GroupListActivity.this, "left-to-right");
                break;
            case android.R.id.home:
                //select back button
                finish();
                customType(GroupListActivity.this, "right-to-left");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void Grouplist_get_data(final String text){
        FirebaseFirestore db;
        group_list.clear();
        db= FirebaseFirestore.getInstance();
        db.collection("GROUP").orderBy("그룹넘버", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("LoginActivity.java", document.getId() + " => " + document.getData());
                                if (document.getData().get("기숙사").toString().equals(text) &&document.getData().get("상태").toString().equals("진행중")){
                                    DocumentReference document_ref = document.getReference();
                                    String documnet_title = document.getData().get("제목").toString();
                                    String document_content = document.getData().get("내용").toString();
                                    String document_Time = document.getData().get("시간").toString();
                                    String document_sex = document.getData().get("성별").toString();
                                    String document_open_kakao = document.getData().get("오픈카톡주소").toString();
                                    String document_nick = document.getData().get("닉네임").toString();
                                    String document_number=document.getData().get("그룹넘버").toString();
                                    String document_master=document.getData().get("그룹장").toString();
                                    String document_dorm=document.getData().get("기숙사").toString();
                                    group_list.add(new GroupListItem(
                                            document_ref, documnet_title, document_content,
                                            document_Time, document_sex, document_open_kakao, document_nick,document_number,document_master,document_dorm
                                    ));
                                    groupListAdapter.notifyDataSetChanged();
                                }
                                ;
                            }
                        } else {
//                             Log.w("LoginActivity.java", "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        customType(GroupListActivity.this, "right-to-left");
    }
}