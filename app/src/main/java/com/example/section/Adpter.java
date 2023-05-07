package com.example.section;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.section.Database.TaskDB;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Adpter extends RecyclerView.Adapter<Adpter.Holder> {
    ArrayList<Task>arrayList;
    Listener listener;
    TaskDB taskDB;
    Context context;

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public ArrayList<Task> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Task> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    public Adpter(ArrayList<Task> arrayList,Listener listener,Context context) {
        this.arrayList = arrayList;
        this.listener=listener;
        this.context=context;
        taskDB = new TaskDB(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Boolean Expired;
        DateFormat dateFormat = new SimpleDateFormat("M/d");
        Date  it= new Date(arrayList.get(position).getDate());
        Date Current = new Date();
        if (Current.after(it)){
            holder.stutes.setText("Expired");
            holder.stutes.setTextColor(Color.RED);
            holder.checkBox.setEnabled(false);
            holder.checkBox.setChecked(true);
            Expired=true;
        }else{
            if (arrayList.get(position).getChecked()==1){
                holder.checkBox.setChecked(true);
                holder.stutes.setText("Done");
                holder.stutes.setTextColor(Color.GREEN);
                holder.checkBox.setEnabled(true);
            }else{
                holder.checkBox.setChecked(false);
                holder.stutes.setText("processing");
                holder.stutes.setTextColor(Color.YELLOW);
                holder.checkBox.setEnabled(true);
            }
            Expired =false;
        }
        if (!arrayList.get(position).getDate().equals("")){
            holder.date.setText(dateFormat.format(new Date(arrayList.get(position).getDate())).toString());
        }
        if (!arrayList.get(position).getTitle().equals("")){
            holder.title.setText(arrayList.get(position).getTitle());
        }
        System.out.println(arrayList.get(position).getId()+" "+dateFormat.format(new Date(arrayList.get(position).getDate())).toString());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    holder.stutes.setText("Done");
                    holder.stutes.setTextColor(Color.GREEN);
                    arrayList.get(holder.getAdapterPosition()).setChecked(1);
                    taskDB.Modify(arrayList.get(holder.getAdapterPosition()));
                }else{
                    if (Current.before(it)){
                        holder.stutes.setText("processing");
                        holder.stutes.setTextColor(Color.YELLOW);
                        arrayList.get(holder.getAdapterPosition()).setChecked(0);
                        taskDB.Modify(arrayList.get(holder.getAdapterPosition()));
                    }
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.Onclick(arrayList.get(holder.getAdapterPosition()).getId(),Expired);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView title,date,stutes;
        CheckBox checkBox;
        public Holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.custom_tv_titel);
            date = itemView.findViewById(R.id.custom_tv_date);
            stutes = itemView.findViewById(R.id.custom_tv_stutes);
            checkBox = itemView.findViewById(R.id.custom_checkBox);
        }
    }
    public interface Listener{
        public void Onclick(int id,Boolean state);
    }

}
