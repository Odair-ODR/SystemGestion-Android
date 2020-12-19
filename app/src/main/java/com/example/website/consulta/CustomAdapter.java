package com.example.website.consulta;

import android.app.Activity;
import android.service.autofill.TextValueSanitizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private Activity mActivity;
    private ArrayList<List> mList;
    private static LayoutInflater mInflater = null;
    private List tempValues = null;
    private int i = 0;

    public CustomAdapter(Activity a, ArrayList d) {
        mActivity = a;
        mList = d;
    }

    @Override
    public int getCount() {
        if (mList.size() <= 0)
            return 1;
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView txtcodbar;
        public TextView txtalternante;
        public TextView txtdescripcion;
        public TextView txtsaldo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;
        if (convertView == null) {
            vi = mInflater.inflate(R.layout.activity_motores, null);
            holder = new ViewHolder();
            holder.txtcodbar = vi.findViewById(R.id.txtcodbar);
            holder.txtalternante = vi.findViewById(R.id.txtalternante);
            holder.txtdescripcion = vi.findViewById(R.id.txtdescrip);
            holder.txtsaldo = vi.findViewById(R.id.txtsaldo);
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        try {
            if (mList.size() <= 0) {
                holder.txtcodbar.setText("No data");
                holder.txtalternante.setText("No data");
                holder.txtdescripcion.setText("No data");
                holder.txtsaldo.setText("No data");
            }
            else
                {
                tempValues = null;
                tempValues = (List) mList.get(position);
                holder.txtcodbar.setText(tempValues.getCodbar());
                holder.txtalternante.setText(tempValues.getAlternante());
                holder.txtdescripcion.setText(tempValues.getDescripcion());
                holder.txtsaldo.setText(tempValues.getSaldo());
              }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();

        }
        return null;
    }
}
