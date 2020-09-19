package com.test.eat2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Action3_Fragment extends Fragment {
    MainActivity activity;
    ViewGroup viewGroup;
    private Button loginbtn;
    private Button signupbtn;
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
        loginbtn=viewGroup.findViewById(R.id.loginbtn);
        signupbtn=viewGroup.findViewById(R.id.signupbtn);
        loginbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                activity.To_Login();
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                activity.To_Signup();
            }
    });
        return viewGroup;
    }
}

