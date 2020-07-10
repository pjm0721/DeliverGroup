package com.test.eat2;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Home_Fragment extends Fragment {
    private MainActivity activity;
    private Home_CustomAdapter adapter;
    private GridView gridView;
    private ViewGroup rootView;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        rootView=(ViewGroup)inflater.inflate(R.layout.fragment_home,container,false);
        gridView = rootView.findViewById(R.id.home_gridview);
        adapter=new Home_CustomAdapter(getContext());
        adapter.addItem(new Home_Grid_Item("긍지관",R.drawable.gji));
        adapter.addItem(new Home_Grid_Item("누리관",R.drawable.nuri));
        adapter.addItem(new Home_Grid_Item("봉사관",R.drawable.bongsa));
        adapter.addItem(new Home_Grid_Item("성실관",R.drawable.sungsil));
        adapter.addItem(new Home_Grid_Item("진리관",R.drawable.jinli));
        adapter.addItem(new Home_Grid_Item("첨성관",R.drawable.chumsung));
        adapter.addItem(new Home_Grid_Item("향토관",R.drawable.hyangto));
        adapter.addItem(new Home_Grid_Item("협동관",R.drawable.hyubdong));
        adapter.addItem(new Home_Grid_Item("화목관",R.drawable.hwamok));
        adapter.addItem(new Home_Grid_Item("명의관",R.drawable.myung));
        gridView.setAdapter(adapter);
        return rootView;
    }
}
