package com.test.eat2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Action3_Fragment extends Fragment {
    MainActivity activity;
    ViewGroup viewGroup;
    private LoginSharedPreferenceUtil util;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    Boolean login;
    TextView email;
    TextView password;
    TextView phone;
    TextView nick;
    String EMAIL;
    Button logout_btn;
    Button change_pw;
    Button change_nick;
    int index;
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
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_action3, container, false);
        util=new LoginSharedPreferenceUtil(activity);
        email=viewGroup.findViewById(R.id.f3_email);
        password=viewGroup.findViewById(R.id.f3_password);
        phone=viewGroup.findViewById(R.id.f3_phone);
        nick=viewGroup.findViewById(R.id.f3_nick);
        change_pw=viewGroup.findViewById(R.id.change_pw_btn);
        change_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               activity.To_Change_PW();
            }
        });
        EMAIL=util.getStringData("ID",null);
        logout_btn=viewGroup.findViewById(R.id.f3_logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.Logout();
            }
        });
        info();
        return viewGroup;
    }
    private void info(){
        final DocumentReference docRef2 = db.collection("USER").document(EMAIL);
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
                    email.setText(snapshot.getData().get("Email").toString());
                    password.setText(snapshot.getData().get("password").toString());
                    phone.setText(snapshot.getData().get("phone").toString());
                    nick.setText(snapshot.getData().get("닉네임").toString());
                } else {
                    Log.d("current null", "Current data: null");
                }
            }
        });
    }
}

