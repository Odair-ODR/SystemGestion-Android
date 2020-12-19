package com.example.website.consulta;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    ListView listView;
    String mTitle[] = {"VENTAS", "CONSULTA", "REPORTE", "PROFORMA", "Item4"};
    String mDescription[] = {"Facebook Description", "Whatsapp Description", "Twitter Description", "Instagram Description", "Youtube Description"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        listView = findViewById(R.id.lstMenuItems);
        MyAdapter adapter = new MyAdapter(this, mTitle, mDescription);
        listView.setAdapter(adapter);
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(MenuActivity.this, "Facebook Description", Toast.LENGTH_SHORT).show();
                }
                if (position == 0) {
                    Toast.makeText(MenuActivity.this, "Whatsapp Description", Toast.LENGTH_SHORT).show();
                }
                if (position == 0) {
                    Toast.makeText(MenuActivity.this, "Twitter Description", Toast.LENGTH_SHORT).show();
                }
                if (position == 0) {
                    Toast.makeText(MenuActivity.this, "Instagram Description", Toast.LENGTH_SHORT).show();
                }
                if (position == 0) {
                    Toast.makeText(MenuActivity.this, "Youtube Description", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rTitle[];
        //> String rDescription[];
        //> int rImgs[];

        MyAdapter(Context c, String title[], String description[]) {
            super(c, R.layout.row, R.id.btnItem, title);
            this.context = c;
            this.rTitle = title;
            //> this.rDescription = description;
            //> this.rImgs = imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            //> ImageView images = row.findViewById(R.id.image);
            //> TextView myTitle = row.findViewById(R.id.txtTitle);
            //> TextView myDescription = row.findViewById(R.id.txtDetail);
            Button btnItem = row.findViewById(R.id.btnItem);

            // now set our resources on views
            //> images.setImageResource(rImgs[position]);
            //> myTitle.setText(rTitle[position]);
            //> myDescription.setText(rDescription[position]);
            btnItem.setOnClickListener(new OnItemClickListener(position));
            btnItem.setText(rTitle[position]);
            //> row.setOnClickListener(new OnItemClickListener(position));
            return row;
        }

        private class OnItemClickListener implements View.OnClickListener {
            private int mPosition;

            OnItemClickListener(int position) {
                mPosition = position;
            }

            @Override
            public void onClick(View arg0) {
                switch (mPosition) {
                    case 0:
                        Intent e = new Intent( MenuActivity.this, Emision.class);
                        startActivity(e);
                        break;
                    case 1:
                        Intent intet = new Intent(MenuActivity.this, ConsultaActivity.class);
                        startActivity(intet);
                        break;
                    case 2:
                        Toast.makeText(MenuActivity.this, "click position : " + mPosition, Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(MenuActivity.this, "click position : " + mPosition, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(MenuActivity.this, "click position : " + mPosition, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }
}
