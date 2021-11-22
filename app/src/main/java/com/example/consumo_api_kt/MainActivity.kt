package com.example.consumo_api_kt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class MainActivity : AppCompatActivity(), RecyclerAdapterReceta.recyclerItemClick, SearchView.OnQueryTextListener {
    private var listaRecetas: List<Receta>? = null
    private var recyclerViewRecetas: RecyclerView? = null
    private var buscaReceta: SearchView? = null
    private var recyclerAdapterReceta: RecyclerAdapterReceta? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listaRecetas = ArrayList()
        recyclerViewRecetas = findViewById(R.id.recyclerRecetas)
        buscaReceta = findViewById(R.id.buscaRecetas)
        val recetaViewModel = ViewModelProvider(this)[RecetaViewModel::class.java]
        recetaViewModel.listaRecetas.observe(this) { recetas ->
            listaRecetas = recetas
            Log.d("aaa", listaRecetas!!.size.toString())

            //obtener recetas
            recyclerAdapterReceta = RecyclerAdapterReceta(listaRecetas as ArrayList<Receta>, this, this)
            //se pasa las recyclerAdapter al recyclerView del layout
            recyclerViewRecetas?.setAdapter(recyclerAdapterReceta)
        }


        //para asignar que como mostrar los item (lista o grilla) en el recyclerView
        val manager = LinearLayoutManager(this)
        recyclerViewRecetas?.setLayoutManager(manager)

        //para mostrar la lista de recetas
        recetaViewModel.recetas

        //se asigna el listener al buscador de texto
        buscaReceta?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        recyclerAdapterReceta!!.filter(newText)
        return false
    }


    override fun OnItemClick(receta: Receta?) {
        Toast.makeText(
            this, "Esta receta es de la categoría: " + receta?.nombreCategoria,
            Toast.LENGTH_SHORT
        ).show()
    }
}