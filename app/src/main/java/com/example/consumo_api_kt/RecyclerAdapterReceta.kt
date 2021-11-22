package com.example.consumo_api_kt

import com.example.consumo_api_kt.Receta
import com.example.consumo_api_kt.RecyclerAdapterReceta.recyclerItemClick
import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import com.example.consumo_api_kt.RecyclerAdapterReceta.RecyclerHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.consumo_api_kt.R
import com.bumptech.glide.Glide
import android.text.Html
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import java.util.ArrayList
import java.util.stream.Collectors

class RecyclerAdapterReceta(//para poblar el recycler
    private var recetas: MutableList<Receta>, //crea la instacia de evento click
    private val itemClick: recyclerItemClick, activity: Activity
) : RecyclerView.Adapter<RecyclerHolder>() {
    //para el buscador
    private val originalRecetas: MutableList<Receta>
    private val activity: Activity
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {

        //infla el view con el layout de categoria_list_item
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.receta_item_view, parent, false)
        return RecyclerHolder(view)
    }

    //se ejecuta tantas veces devuelva el getItemCount
    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {

        //selecciona cada receta
        val receta = recetas[position]

        //asigna datos a cada atributo
        // holder.imgItem.setImageResource(receta.getImagen());
        Glide.with(activity)
            .load("https://www.bakenem.com/storage/" + receta.imagen)
            .into(holder.imgItem)
        holder.titulo.text = extraeDiezPalabras(receta.titulo, 5)
        holder.descripcion.text =
            extraeDiezPalabras(Html.fromHtml(receta.preparacion).toString(), 10)

        //para el evento click
        holder.itemView.setOnClickListener { itemClick.OnItemClick(receta) }
    }

    override fun getItemCount(): Int {
        return recetas.size
    }

    //filtro para la busqueda
    fun filter(busqueda: String) {
        if (busqueda.length == 0) {
            recetas.clear()
            recetas.addAll(originalRecetas)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                recetas.clear()
                val collect = originalRecetas.stream()
                    .filter { receta: Receta ->
                        receta.titulo!!.toLowerCase().contains(busqueda.toLowerCase())
                    }
                    .collect(Collectors.toList())
                recetas = collect
            } else {
                //comparacion de busqueda con los titulos a la manera tradicional
                recetas.clear()
                for (r in originalRecetas) {
                    if (r.titulo!!.toLowerCase().contains(busqueda.toLowerCase())) {
                        recetas.add(r)
                    }
                }
            }
        }
        //actualiza la lista simultaneamente con los cambios
        notifyDataSetChanged()
    }

    class RecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgItem: ImageView
        val titulo: TextView
        val descripcion: TextView

        init {
            imgItem = itemView.findViewById(R.id.imgReceta)
            titulo = itemView.findViewById(R.id.txtTituloReceta)
            descripcion = itemView.findViewById(R.id.txtDescrip)
        }
    }

    interface recyclerItemClick {
        fun OnItemClick(receta: Receta?)
    }

    fun extraeDiezPalabras(parrafo: String?, cantidadEspacios: Int): String {
        var diezPalabras = ""
        var contador = 0
        for (i in 0 until parrafo!!.length) {
            if (parrafo[i].toString() == " ") {
                contador++
            }
            diezPalabras += if (contador < cantidadEspacios) {
                parrafo[i]
            } else {
                break
            }
        }
        return if (contador == cantidadEspacios) {
            "$diezPalabras ..."
        } else {
            diezPalabras
        }
    }

    init {
        originalRecetas = ArrayList()
        this.activity = activity
        originalRecetas.addAll(recetas)
    }
}