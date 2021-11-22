package com.example.consumo_api_kt

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.consumo_api_kt.Receta
import com.example.consumo_api_kt.RecetaApiClient.RecetaService
import com.example.consumo_api_kt.RecetaApiClient
import org.json.JSONObject
import org.json.JSONArray
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class RecetaViewModel : ViewModel() {
    val listaRecetas = MutableLiveData<ArrayList<Receta>>()

    //servicio Json
    val recetas:

    //para consumir Json en un hilo no principal
            Unit
        get() {

            //servicio Json
            val service = RecetaApiClient.instance.recetaService

            //para consumir Json en un hilo no principal
            service?.recetas?.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    val lista = parseListaRecetas(response.body())
                    listaRecetas.value = lista
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    Log.d("Error_carga_recetas", t.localizedMessage)
                }
            })
        }

    private fun parseListaRecetas(stringResponse: String?): ArrayList<Receta> {
        val recetas = ArrayList<Receta>()
        try {
            val jsonObject = JSONObject(stringResponse)
            val jsonArray = jsonObject.getJSONArray("recetas")
            var jsonObjectAux: JSONObject
            var id: Int
            var titulo: String?
            var ingredientes: String?
            var preparacion: String?
            var imagen: String?
            var nombreCategoria: String?
            for (i in 0 until jsonArray.length()) {
                jsonObjectAux = jsonArray.getJSONObject(i)
                id = jsonObjectAux.getInt("id")
                titulo = jsonObjectAux.getString("titulo")
                ingredientes = jsonObjectAux.getString("ingredientes")
                preparacion = jsonObjectAux.getString("preparacion")
                imagen = jsonObjectAux.getString("imagen")
                nombreCategoria = jsonObjectAux.getString("nombre")
                recetas.add(Receta(id, titulo, ingredientes, preparacion, imagen, nombreCategoria))
            }
        } catch (e: JSONException) {
            Log.d("ErrorRecetasJson", e.localizedMessage)
        }
        return recetas
    }
}