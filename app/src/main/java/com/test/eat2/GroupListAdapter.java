package com.test.eat2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GroupListAdapter extends BaseAdapter {
    private Context context;
    private List<GroupListItem> boardlist;

    public GroupListAdapter(Context context, List<GroupListItem> boardlist) {
        this.context = context;
        this.boardlist = boardlist;
    }

    @Override
    public int getCount() {
        return boardlist.size();
    }

    @Override
    public Object getItem(int position) {
        return boardlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.group_list_item, null );
        LoginSharedPreferenceUtil util;
        util=new LoginSharedPreferenceUtil(context);
        TextView group_list_title = (TextView)v.findViewById(R.id.gl_title);
        TextView group_list_content = (TextView)v.findViewById(R.id.gl_content);
        TextView group_list_time = (TextView)v.findViewById(R.id.gl_time);
        ImageView img1=(ImageView)v.findViewById(R.id.gl_img1);
        ImageView img2=(ImageView)v.findViewById(R.id.gl_img2);
        ImageView img3=(ImageView)v.findViewById(R.id.gl_img3);

        group_list_title.setText(boardlist.get(position).get_Title());
        group_list_content.setText(boardlist.get(position).get_Content());
        group_list_time.setText(boardlist.get(position).get_Time());
        String SEX=boardlist.get(position).get_Sex();
        String MASTER=boardlist.get(position).get_Master();
        if(SEX.equals("무관")){
            img1.setImageResource(R.drawable.woman);
            img2.setVisibility(View.VISIBLE);
            img2.setImageResource(R.drawable.man1);
        }
        else if(SEX.equals("남자")){
            img1.setImageResource(R.drawable.man1);
            img2.setVisibility(View.INVISIBLE);
        }
        else {
            img1.setImageResource(R.drawable.woman);
            img2.setVisibility(View.INVISIBLE);
        }
        if(util.getStringData("ID",null).equals(MASTER)){
            img3.setVisibility(View.VISIBLE);
        }
        else {
            img3.setVisibility(View.INVISIBLE);
        }
        return v;
    }
}
