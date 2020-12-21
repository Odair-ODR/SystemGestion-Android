package com.example.website.consulta.View

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.website.consulta.R
import com.example.website.consulta.View.MenuActivity

class MenuActivity : AppCompatActivity() {
    var listView: ListView? = null
    var mTitle = arrayOf("VENTAS", "CONSULTA", "REPORTE", "PROFORMA", "Item4")
    var mDescription = arrayOf("Facebook Description", "Whatsapp Description", "Twitter Description", "Instagram Description", "Youtube Description")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        listView = findViewById(R.id.lstMenuItems)
        val adapter = MyAdapter(this, mTitle, mDescription)
        listView?.setAdapter(adapter)
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

    internal inner class MyAdapter(context: Context, var rTitle: Array<String>, description: Array<String>?) : ArrayAdapter<String?>(context, R.layout.row, R.id.btnItem, rTitle) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val layoutInflater = applicationContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val row = layoutInflater.inflate(R.layout.row, parent, false)
            //> ImageView images = row.findViewById(R.id.image);
            //> TextView myTitle = row.findViewById(R.id.txtTitle);
            //> TextView myDescription = row.findViewById(R.id.txtDetail);
            val btnItem = row.findViewById<Button>(R.id.btnItem)

            // now set our resources on views
            //> images.setImageResource(rImgs[position]);
            //> myTitle.setText(rTitle[position]);
            //> myDescription.setText(rDescription[position]);
            btnItem.setOnClickListener(OnItemClickListener(position))
            btnItem.text = rTitle[position]
            //> row.setOnClickListener(new OnItemClickListener(position));
            return row
        }

        private inner class OnItemClickListener internal constructor(private val mPosition: Int) : View.OnClickListener {
            override fun onClick(arg0: View) {
                when (mPosition) {
                    0 -> {
                        val e = Intent(this@MenuActivity, Emision::class.java)
                        startActivity(e)
                    }
                    1 -> {
                        val intet = Intent(this@MenuActivity, ConsultaActivity::class.java)
                        startActivity(intet)
                    }
                    2 -> Toast.makeText(this@MenuActivity, "click position : $mPosition", Toast.LENGTH_SHORT).show()
                    3 -> Toast.makeText(this@MenuActivity, "click position : $mPosition", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(this@MenuActivity, "click position : $mPosition", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}