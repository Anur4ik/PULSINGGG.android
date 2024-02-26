package com.example.pulsinggg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pulsinggg.adapter.BtAdapter;
import com.example.pulsinggg.adapter.ListItem;

import java.util.List;

public class MyListAdapter extends ArrayAdapter<ListItem1> {
   private  List<ListItem1> mainList;
    public MyListAdapter(@NonNull Context context, int resource, List<ListItem1> Hs_list) {
        super(context, resource, Hs_list);
mainList=Hs_list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hs_list, null, false);
            viewHolder.tvPulse = convertView.findViewById(R.id.H_Pulse);
            viewHolder.tvData = convertView.findViewById(R.id.H_data);
            viewHolder.tvTime = convertView.findViewById(R.id.H_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvPulse.setText(mainList.get(position).getPulse());
        viewHolder.tvData.setText(mainList.get(position).getDate());
        viewHolder.tvTime.setText(mainList.get(position).getTime());
        return convertView;
    }

    class ViewHolder {
        TextView tvPulse;
        TextView tvData;
        TextView tvTime;

    }
}
