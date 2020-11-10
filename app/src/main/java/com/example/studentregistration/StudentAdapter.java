package com.example.studentregistration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StudentAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Student> studentsList;
    private LayoutInflater layoutInflater;

    public StudentAdapter(Context applicationContext, ArrayList<Student> studentsList) {
        super(applicationContext, R.layout.student_display);
        context = applicationContext;
        this.studentsList = studentsList;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return studentsList.size();
    }

    @Override
    public Object getItem(int i) {
        return studentsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.student_display, null);
            holder = new ViewHolder();

            holder.tvFirstname = view.findViewById(R.id.tvFirstname);
            holder.tvMiddlename = view.findViewById(R.id.tvMiddlename);
            holder.tvLastname = view.findViewById(R.id.tvLastname);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvFirstname.setText(studentsList.get(i).getFirstname());
        holder.tvMiddlename.setText(studentsList.get(i).getMiddlename());
        holder.tvLastname.setText(studentsList.get(i).getLastname());

        return view;
    }

    private static class ViewHolder {
        TextView tvFirstname, tvMiddlename, tvLastname;
    }
}
