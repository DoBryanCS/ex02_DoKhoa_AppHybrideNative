package com.example.ex02_dokhoa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView: ImageView = this.findViewById(R.id.imageView)
        val spinner: Spinner = this.findViewById(R.id.villes)
        val rise: TextView = this.findViewById(R.id.textView8)
        val set: TextView = this.findViewById(R.id.textView7)
        val temp: TextView = this.findViewById(R.id.textView6)
        val precip: TextView = this.findViewById(R.id.textView5)
        val queue = Volley.newRequestQueue(this)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //pour obtenir l'élément présentement sélectionné
                //utiliser parent?.selectedItem
                //ou encore parent?.getItemAtPosition(position)
                Toast.makeText(
                    applicationContext,
                    "${parent?.selectedItem}",
                    Toast.LENGTH_SHORT
                ).show()

                val url = "https://api.weatherbit.io/v2.0/current?city="+ spinner.selectedItem+"&key=d5abede1e8a54f7fa9c0a3b0eb62ab01&include=minutely"

                @Suppress("RedundantSamConstructor")
                val jsonRequest = JsonObjectRequest (
                    Request.Method.GET, //Méthode GET, PUT, POST, DELETE, etc.
                    url, //url de la ressource
                    null,
                    Response.Listener {

                        val arr = it.getJSONArray("data").getJSONObject(0)
                        val sunrise = arr.getString("sunrise")
                        val sunset = arr.getString("sunset")
                        val temperature = arr.getString("temp")
                        val precipitation = arr.getString("precip")
                        val weather = arr.getJSONObject("weather")
                        val icon = weather.getString("icon")
                        val imgId = icon
                        rise.text = sunrise
                        set.text = sunset
                        temp.text = temperature
                        precip.text = precipitation
                        imageView.setImageResource(getResources().getIdentifier(imgId, "drawable", getPackageName()))

                    },
                    Response.ErrorListener {
                        //Traiter la réponse lorsqu'une erreur se produit (it contient erreur)
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                    })

//Ne pas oublier de placer la requête dans la file pour qu'elle soit exécutée
                queue.add(jsonRequest)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext, "PAS DE SELECTION", Toast.LENGTH_SHORT).show()
            }
        }
    }
}