package com.test.eat2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.drm.DrmStore;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static maes.tech.intentanim.CustomIntent.customType;

public class GroupSearchActivity extends AppCompatActivity {
    String dorm_name;
    Toolbar toolbar;
    ActionBar actionBar;
    private ListView group_list_view;
    private GroupListAdapter groupListAdapter;
    private List<GroupListItem> group_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_search);
        Intent intent=getIntent();
        dorm_name=intent.getStringExtra("기숙사");
        final EditText editText=(EditText) findViewById(R.id.groupsearch_et);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editText.getText().toString().isEmpty()){
                    group_list.clear();
                    groupListAdapter.notifyDataSetChanged();
                }
                else {
                    searchGroup_get_data(editText.getText().toString());
                }
            }
        });
        toolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);
        group_list_view=(ListView)findViewById(R.id.search_group_view);
        group_list=new ArrayList<GroupListItem>();
        groupListAdapter=new GroupListAdapter(getApplicationContext(),group_list);
        group_list_view.setAdapter(groupListAdapter);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //select back button
                finish();
                customType(GroupSearchActivity.this, "right-to-left");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void searchGroup_get_data(final String text){
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
                                if (document.getData().get("기숙사").toString().equals(dorm_name) &&document.getData().get("상태").toString().equals("진행중")){
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
                                    if (document_content.toLowerCase().contains(text) || documnet_title.toLowerCase().contains(text)) {
                                        group_list.add(new GroupListItem(
                                                document_ref, documnet_title, document_content,
                                                document_Time, document_sex, document_open_kakao, document_nick, document_number, document_master, document_dorm
                                        ));
                                    }
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
        customType(GroupSearchActivity.this,"right-to-left");
    }
}