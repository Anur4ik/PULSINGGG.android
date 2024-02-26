package com.example.pulsinggg.adapter;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.pulsinggg.R;

import java.util.ArrayList;
import java.util.List;

public class BtAdapter extends ArrayAdapter<ListItem> {
    public static final String DEF_ITEM_TYPE = "normal"; // Тип елемента за замовчуванням
    public static final String TITLE_ITEM_TYPE = "title";// Тип заголовкового елемента
    public static final String DISCOVERY_ITEM_TYPE = "discovery"; // Тип елемента в режимі пошуку
    private SharedPreferences pref;// Об'єкт для збереження даних в налаштуваннях
    private List<ListItem> mainlist;// Основний список елементів
    private List<ViewHolder> listViewHolders;// Список елементів списку з їх відображенням
    private boolean isDiscovery=false;// Прапорець для визначення режиму пошуку

    // Конструктор класу BtAdapter
    public BtAdapter(@NonNull Context context, int resource, List<ListItem> btlist) {
        super(context, resource, btlist);
        mainlist = btlist;
        listViewHolders = new ArrayList<>();
        pref = context.getSharedPreferences(BtConst.MY_PREF, Context.MODE_PRIVATE);
    }
    // Перевизначений метод getView для відображення елементів списку
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        switch (mainlist.get(position).getItemType()) {
            case TITLE_ITEM_TYPE: convertView = titleItem(convertView, parent);
                break;
            default:
                convertView = defualtItem(convertView, position, parent);
                break;
        }

        return convertView;
    }

    // Метод для відображення елемента списку за замовчуванням
    private View defualtItem(View convertView, int position, ViewGroup parent) {
        ViewHolder viewHolder;
        // Перевірка наявності об'єкта ViewHolder для поточного елемента списку
        boolean hasViewHolder = false;
        if (convertView != null) hasViewHolder = (convertView.getTag() instanceof ViewHolder);
        // Створення нового об'єкта ViewHolder, якщо не існує
        if (convertView == null || !hasViewHolder) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bt_listitem, null, false);
            viewHolder.tvBtName = convertView.findViewById(R.id.tvBtName);
            viewHolder.checkBox = convertView.findViewById(R.id.checkBox);
            convertView.setTag(viewHolder);
            listViewHolders.add(viewHolder);
        } else { // Використання існуючого об'єкта ViewHolder
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.checkBox.setChecked(false);
        }
// Відображення або приховування прапорця в залежності від типу елемента
        if (mainlist.get(position).getItemType().equals(BtAdapter.DISCOVERY_ITEM_TYPE)) {
            viewHolder.checkBox.setVisibility(View.GONE);
            isDiscovery=true;
        } else {
            viewHolder.checkBox.setVisibility(View.VISIBLE);
            isDiscovery=false;
        }

// Встановлення назви Bluetooth-пристрою та обробник події для прапорця
        viewHolder.tvBtName.setText(mainlist.get(position).getBtDevice().getName());
        viewHolder.checkBox.setOnClickListener(v -> {
            if(!isDiscovery) {
                // Зняття відмітки з усіх інших прапорців
                for (ViewHolder holder : listViewHolders) {
                    holder.checkBox.setChecked(false);
                }
                // Встановлення прапорця для поточного елемента та збереження вибору
                viewHolder.checkBox.setChecked(true);
                savePref(position);
            }});
        // Позначення вибраного пристрою якщо він вже збережений в налаштуваннях
        if(pref.getString(BtConst.MAC_KEY,"no bt selector").equals(mainlist.get(position)
                .getBtDevice().getAddress()))viewHolder.checkBox.setChecked(true);
        isDiscovery=false;
        return convertView;

    } // Метод для відображення заголовкового елемента списку
    private View titleItem(View convertView, ViewGroup parent)
    {
       boolean hasViewHolder=false;
        if(convertView!=null){hasViewHolder=(convertView.getTag()instanceof ViewHolder);}
        // Створення нового заголовкового елемента, якщо не існує
        if(convertView==null || hasViewHolder){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.bt_listitem_title,null,false);
        }
        return convertView;
    }
    // Метод для збереження вибору пристрою Bluetooth в налаштуваннях
    private  void  savePref(int pos){
        SharedPreferences.Editor editor= pref.edit();
        editor.putString(BtConst.MAC_KEY,mainlist.get(pos).getBtDevice().getAddress());
        editor.apply();
    }
    // Вкладений клас ViewHolder для збереження посилань на елементи списку
    static class ViewHolder{
        TextView tvBtName;
        CheckBox checkBox;
    }


}
