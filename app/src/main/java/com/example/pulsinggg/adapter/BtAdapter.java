package com.example.pulsinggg.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pulsinggg.R;

import java.util.ArrayList;
import java.util.List;

public class BtAdapter extends ArrayAdapter<ListItem> {
    public static final String DEF_ITEM_TYPE="normal";
    public static final String TITLE_ITEM_TYPE="title";
    public static final String DISCOVERY_ITEM_TYPE="discovery";
   private SharedPreferences pref;
    private List<ListItem>mainlist;
    private List<ViewHolder> listViewHolders;

    public BtAdapter(@NonNull Context context, int resource, List<ListItem> btlist) {
        super(context, resource, btlist);
        mainlist=btlist;
        listViewHolders=new ArrayList<>();
        pref=context.getSharedPreferences(BtConst.MY_PREF,Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {switch (mainlist.get(position).getItemType())
    {case TITLE_ITEM_TYPE:convertView=titleItem(convertView,parent);
        break;
        default:convertView= defualtItem(convertView,position,parent);break;
    }

        return convertView;
    }
    private View defualtItem(View convertView, int position, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.bt_listitem,null,false);
            viewHolder.tvBtName=convertView.findViewById(R.id.tvBtName);
            viewHolder.checkBox=convertView.findViewById(R.id.checkBox);
            convertView.setTag(viewHolder);
            listViewHolders.add(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) convertView.getTag();
        }

        viewHolder.tvBtName.setText(mainlist.get(position).getBtDevice().getName());
        viewHolder.checkBox.setOnClickListener(v -> {
            for (ViewHolder holder : listViewHolders) {
                holder.checkBox.setChecked(false);
            }
            viewHolder.checkBox.setChecked(true);
            savePref(position);
        });
        if(pref.getString(BtConst.MAC_KEY,"no bt selector").equals(mainlist.get(position)
                .getBtDevice().getAddress()))viewHolder.checkBox.setChecked(true);
        return convertView;

    }
    private View titleItem(View convertView, ViewGroup parent)
    {

        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.bt_listitem_title,null,false);

        }
        return convertView;

    }
    private  void  savePref(int pos){
        SharedPreferences.Editor editor= pref.edit();
        editor.putString(BtConst.MAC_KEY,mainlist.get(pos).getBtDevice().getAddress());
        editor.apply();
    }
    static class ViewHolder{
        TextView tvBtName;
        CheckBox checkBox;
    }


}
