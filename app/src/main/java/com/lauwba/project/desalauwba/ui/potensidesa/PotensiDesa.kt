package com.lauwba.project.desalauwba.ui.potensidesa

import android.app.ProgressDialog
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.lauwba.project.config.Config
import com.lauwba.project.desalauwba.R
import com.lauwba.project.desalauwba.ui.RequestHandler
import com.lauwba.project.desalauwba.ui.berita.BeritaAdapter
import com.lauwba.project.desalauwba.ui.berita.BeritaModel
import kotlinx.android.synthetic.main.activity_layanan.*
import org.json.JSONObject

class PotensiDesa : AppCompatActivity() {

    private var list:MutableList<PotensidesaModel>?=null
    private var pd: ProgressDialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_potensi_desa)
        list= mutableListOf()
        get_data_potensidesa().execute()
    }

    inner class get_data_potensidesa : AsyncTask<String, Void, String>(){
        override fun onPreExecute() {
            super.onPreExecute()
            pd= ProgressDialog.show(this@PotensiDesa,"","Wait",true,true)
        }

        override fun doInBackground(vararg params: String?): String {

            val handler= RequestHandler()
            val result=handler.sendGetRequest(Config.url_potensidesa)
            return result
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            pd?.dismiss()
            val objek= JSONObject(result)
            val array=objek.getJSONArray("data")
            for (i in 0 until array.length()){
                val data=array.getJSONObject(i)
                val model= PotensidesaModel()
                model.id_potensi=data.getString("id_potensi")
                model.gambar=data.getString("gambar")
                model.nama_desa=data.getString("nama_desa")
                model.keterangan=data.getString("keterangan")
                list?.add(model)
                val adapter= list?.let { PotensidesaAdapter(this@PotensiDesa,it) }
                rv.layoutManager= LinearLayoutManager(this@PotensiDesa)
                rv.adapter=adapter
            }
        }

    }
}
