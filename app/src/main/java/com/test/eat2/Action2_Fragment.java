package com.test.eat2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static maes.tech.intentanim.CustomIntent.customType;

public class Action2_Fragment extends Fragment {
    MainActivity activity;
    ViewGroup viewGroup;
    FirebaseFirestore db;
    private ListView ing_list_view;
    private ListView end_list_view;
    private GroupListAdapter ingListAdapter;
    private GroupListAdapter endListAdapter;
    private List<GroupListItem> end_list;
    private List<GroupListItem> ing_list;
    LoginSharedPreferenceUtil util;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_action2, container, false);
        ing_list_view = viewGroup.findViewById(R.id.mygroup_view) ;
        ing_list = new ArrayList<GroupListItem>();
        ingListAdapter=new GroupListAdapter(activity,ing_list);
        ing_list_view.setAdapter(ingListAdapter);
        ing_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(activity, GroupInfoAcitivity.class);
                intent.putExtra("닉네임", ing_list.get(i).get_Nick());
                intent.putExtra("제목", ing_list.get(i).get_Title());
                intent.putExtra("기숙사", ing_list.get(i).get_Dorm());
                intent.putExtra("설명",ing_list.get(i).get_Content());
                intent.putExtra("성별", ing_list.get(i).get_Sex());
                intent.putExtra("오픈카톡주소", ing_list.get(i).get_Open_Kakao());
                intent.putExtra("그룹장", ing_list.get(i).get_Master());
                intent.putExtra("그룹넘버",  ing_list.get(i).getGroup_number());
                intent.putExtra("시간",ing_list.get(i).get_Time());
                if(ing_list.get(i).get_Master().equals(util.getStringData("ID",null))){
                    intent.putExtra("버전",3);
                }
                else {
                    intent.putExtra("버전",1);
                }
                startActivity(intent);
                customType(activity,"left-to-right");
            }
        });
        end_list_view = viewGroup. findViewById(R.id.mygroup_view2) ;
        end_list = new ArrayList<GroupListItem>();
        endListAdapter=new GroupListAdapter(activity,end_list);
        end_list_view.setAdapter(endListAdapter);
        end_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(activity, GroupInfoAcitivity.class);
                intent.putExtra("닉네임", end_list.get(i).get_Nick());
                intent.putExtra("제목", end_list.get(i).get_Title());
                intent.putExtra("기숙사", end_list.get(i).get_Dorm());
                intent.putExtra("설명",end_list.get(i).get_Content());
                intent.putExtra("성별", end_list.get(i).get_Sex());
                intent.putExtra("오픈카톡주소", end_list.get(i).get_Open_Kakao());
                intent.putExtra("그룹장", end_list.get(i).get_Master());
                intent.putExtra("그룹넘버",  end_list.get(i).getGroup_number());
                intent.putExtra("시간",end_list.get(i).get_Time());
                intent.putExtra("버전",2);
                startActivity(intent);
                customType(activity,"left-to-right");
            }
        });
        util=new LoginSharedPreferenceUtil(activity);
        list_get_data();
        TabLayout tablayout=viewGroup.findViewById(R.id.tabLayout);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos=tab.getPosition();
                changeView(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return viewGroup;
    }
    private void changeView(int index) {
        switch (index) {
            case 0 :
                ing_list_view.setVisibility(View.VISIBLE) ;
                end_list_view.setVisibility(View.INVISIBLE) ;
                db=FirebaseFirestore.getInstance();
                break ;
            case 1 :
                ing_list_view.setVisibility(View.INVISIBLE) ;
                end_list_view.setVisibility(View.VISIBLE) ;
                break ;

        }
    }
    private void list_get_data(){
        db=FirebaseFirestore.getInstance();
         db.collection("GROUP").orderBy("그룹넘버", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot document : task.getResult()) {
                                    final DocumentReference docRef2 = db.collection("GROUP").document(document.getData().get("그룹넘버").toString()).collection("users").document(util.getStringData("ID",null));
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
                                                if(document.getData().get("상태").toString().equals("진행중")) {
                                                    ing_list.add(new GroupListItem(
                                                            document_ref, documnet_title, document_content,
                                                            document_Time, document_sex, document_open_kakao, document_nick, document_number, document_master, document_dorm
                                                    ));
                                                    ingListAdapter.notifyDataSetChanged();
                                                }
                                                else {
                                                    end_list.add(new GroupListItem(
                                                            document_ref, documnet_title, document_content,
                                                            document_Time, document_sex, document_open_kakao, document_nick, document_number, document_master, document_dorm
                                                    ));
                                                    endListAdapter.notifyDataSetChanged();
                                                }
                                            } else {
                                                Log.d("current null", "Current data: null");
                                            }
                                        }
                                    });
                                    /*db.collection("GROUP").document(document.getData().get("그룹넘버").toString()).collection("users").
                                            get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document2 : task.getResult()) {
                                                    if (document2.getData().get("ID").toString().equals(util.getStringData("ID", null))) {
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
                                                        ing_list.add(new GroupListItem(
                                                                document_ref, documnet_title, document_content,
                                                                document_Time, document_sex, document_open_kakao, document_nick,document_number,document_master,document_dorm
                                                        ));
                                                        ingListAdapter.notifyDataSetChanged();
                                                    }
                                                }
                                            } else {

                                            }
                                        }
                                    });*/

                                  /*  db.collection("GROUP").document(document.getData().get("그룹넘버").toString()).collection("users").
                                            get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document2 : task.getResult()) {
                                                    if (document2.getData().get("ID").toString().equals(util.getStringData("ID", null))) {
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
                                                        end_list.add(new GroupListItem(
                                                                document_ref, documnet_title, document_content,
                                                                document_Time, document_sex, document_open_kakao, document_nick,document_number,document_master,document_dorm
                                                        ));
                                                        endListAdapter.notifyDataSetChanged();
                                                    }
                                                }
                                            } else {

                                            }
                                        }
                                    });*/
                            }
                        }
                        else {
                            //Log.w("Buyer_MarketListActiviry", "Error getting documents.", task.getException());
                        }
                    }
                });

    }
}