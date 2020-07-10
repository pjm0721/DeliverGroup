package com.test.eat2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class Home_CustomAdapter extends BaseAdapter {
    private Context mContext;
    //private int[] data;
    ArrayList<Home_Grid_Item> data = new ArrayList<Home_Grid_Item>();

    //    public CustomAdapter(Context mContext, int[] data){
//    public CustomAdapter(Context mContext, ArrayList<Grid_item> data){
    public Home_CustomAdapter(Context mContext){
        this.mContext = mContext;
//        this.data = data;
    }

    public void addItem(Home_Grid_Item grid_item){
        data.add(grid_item);
    }

    public int getCount(){
//        return data.length;
        return data.size();
    }

    public Object getItem(int position) {
//        return data[position];
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Home_GridItemView view = new Home_GridItemView(mContext);
        Home_Grid_Item item = data.get(position);

        view.setImage(item.getResId());
        view.setTeamName(item.getTeamName());

        return view;
        /*
        ImageView imageView;

        if(convertView == null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));//그리드뷰안에 들어갈 이미지뷰의 폭과 넓이
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5, 5,5,5);
        }else{
            imageView = (ImageView)convertView;
        }
        imageView.setImageResource(data[position]);

        return imageView;
        */

    }
}
