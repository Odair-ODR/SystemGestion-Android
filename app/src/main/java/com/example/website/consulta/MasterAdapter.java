package com.example.website.consulta;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.website.consulta.Entidad.Articulos;
import com.example.website.consulta.Entidad.Motor;

import java.util.ArrayList;

public class MasterAdapter<T> extends ArrayAdapter<T> {
    private ArrayList<T> lstAr;
    private int acces;
    private T t;

    public MasterAdapter(Context context, ArrayList<T> lstArticulos, int acces) {
        super(context, 0, lstArticulos);
        lstAr = lstArticulos;
        this.acces = acces;
    }

    public static class ViewHolder {
        public TextView item1;
        public TextView item2;
        public TextView item3;
        public TextView item4;
        public TextView item5;
        public TextView item6;
        public TextView item7;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (acces == 1) {
            return ConvertViewArticulo(position, convertView, parent);
        } else if (acces == 2) {
            return ConvertVieCodBar(position, convertView, parent);
        }
        else if(acces == 3){
            return ConvertVieMotores(position, convertView, parent);
        }else if(acces == 4) {
            return ConvertVieMotor(position, convertView, parent);
        }
        return convertView;
    }

    private View ConvertViewArticulo(int position, View convertView, ViewGroup parent) {
        T entidad = getItem(position);
        Articulos articulo = (Articulos) entidad;
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.row_list_alternante, parent, false);
            viewHolder.item3 = convertView.findViewById(R.id.txtCpdnew);
            viewHolder.item2 = convertView.findViewById(R.id.txtAlternante);
            viewHolder.item1 = convertView.findViewById(R.id.txtCodbar);
            //> viewHolder.item5 = convertView.findViewById(R.id.txtUnimed);
            //> viewHolder.item6 = convertView.findViewById(R.id.txtCampar);
            viewHolder.item4 = convertView.findViewById(R.id.txtTotSaldo);
            viewHolder.item7 = convertView.findViewById(R.id.txtMotor);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.item2.setText(articulo.getAlternante());
        viewHolder.item3.setText(articulo.getCpdnew());
        viewHolder.item1.setText(articulo.getCodbar());
        //> viewHolder.item5.setText(articulo.getUnimed());
        //> viewHolder.item6.setText(String.valueOf(articulo.getCampar()));
        viewHolder.item4.setText(String.valueOf(articulo.getTotSaldo()));
        viewHolder.item7.setText(articulo.getMotor());

        return convertView;
    }

    private View ConvertVieMotor(int position, View convertView, ViewGroup parent) {
        T entidad = getItem(position);
        Motor motor = (Motor) entidad;
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.row_motores, parent, false);
            viewHolder.item1 = convertView.findViewById(R.id.txtMarca);
            viewHolder.item2 = convertView.findViewById(R.id.txtMotor);
            viewHolder.item3 = convertView.findViewById(R.id.txtCili1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.item1.setText(motor.getMarca());
        viewHolder.item2.setText(motor.getMotor());
        viewHolder.item3.setText(motor.getCili1());

        return convertView;
    }

    private View ConvertVieCodBar(int position, View convertView, ViewGroup parent) {
        T entidad = getItem(position);
        Articulos articulo = (Articulos) entidad;
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.row_list_codbar, parent, false);
            viewHolder.item3 = convertView.findViewById(R.id.txtCpdnew);
            viewHolder.item2 = convertView.findViewById(R.id.txtAlternante);
            viewHolder.item5 = convertView.findViewById(R.id.txtUnimed);
            viewHolder.item6 = convertView.findViewById(R.id.txtCampar);
            viewHolder.item4 = convertView.findViewById(R.id.txtTotSaldo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.item2.setText(articulo.getAlternante());
        viewHolder.item3.setText(articulo.getCpdnew());
        viewHolder.item5.setText(articulo.getUnimed());
        viewHolder.item6.setText(String.valueOf(articulo.getCampar()));
        viewHolder.item4.setText(String.valueOf(articulo.getTotSaldo()));

        return convertView;
    }

    private View ConvertVieMotores(int position, View convertView, ViewGroup parent) {
        T entidad = getItem(position);
        Articulos articulo = (Articulos) entidad;
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.row_list_motor, parent, false);
            viewHolder.item3 = convertView.findViewById(R.id.txtCpdnew);
            viewHolder.item2 = convertView.findViewById(R.id.txtAlternante);
            viewHolder.item1 = convertView.findViewById(R.id.txtCodbar);
            viewHolder.item5 = convertView.findViewById(R.id.txtUnimed);
            viewHolder.item6 = convertView.findViewById(R.id.txtCampar);
            viewHolder.item4 = convertView.findViewById(R.id.txtTotSaldo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.item2.setText(articulo.getAlternante());
        viewHolder.item3.setText(articulo.getCpdnew());
        viewHolder.item1.setText(articulo.getCodbar());
        viewHolder.item5.setText(articulo.getUnimed());
        viewHolder.item6.setText(String.valueOf(articulo.getCampar()));
        viewHolder.item4.setText(String.valueOf(articulo.getTotSaldo()));

        return convertView;
    }
}