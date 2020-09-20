package com.test.eat2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

public class Action2_Fragment extends Fragment {
    ViewGroup viewGroup;
    RecyclerView ingview;
    RecyclerView endview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_action2, container, false);
        ingview = viewGroup.findViewById(R.id.mygroup_view) ;
        endview = viewGroup. findViewById(R.id.mygroup_view2) ;
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
                ingview.setVisibility(View.VISIBLE) ;
                endview.setVisibility(View.INVISIBLE) ;
                break ;
            case 1 :
                ingview.setVisibility(View.INVISIBLE) ;
                endview.setVisibility(View.VISIBLE) ;
                break ;

        }
    }
}