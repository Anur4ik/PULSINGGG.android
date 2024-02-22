package com.example.pulsinggg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pulsinggg.R;

import java.util.List;

public class BtAdapter extends ArrayAdapter<ListItem> {
    ViewHolder viewHolder;
    private List<ListItem>mainlist;
    public BtAdapter(@NonNull Context context, int resource, List<ListItem> btlist) {
        super(context, resource, btlist);
        mainlist=btlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       if(convertView==null){
           viewHolder=new ViewHolder();
           convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.bt_listitem,null,false);
           viewHolder.tvBtName=convertView.findViewById(R.id.tvBtName);
           viewHolder.checkBox=convertView.findViewById(R.id.checkBox);
           convertView.setTag(viewHolder);
       }
       else {viewHolder=(ViewHolder) convertView.getTag();}
         viewHolder.tvBtName.setText(mainlist.get(position).getBtName());
        viewHolder.checkBox.setChecked(true);
        return convertView;
    }
    static class ViewHolder{
        TextView tvBtName;
        CheckBox checkBox;
    }
}
