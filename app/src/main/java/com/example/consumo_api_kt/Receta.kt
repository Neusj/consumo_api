package com.example.consumo_api_kt

import android.os.Parcelable
import android.os.Parcel
import com.example.consumo_api_kt.Receta
import java.util.*

class Receta : Parcelable {
    var id = 0
    var titulo: String? = null
    var ingredientes: String? = null
    var preparacion: String? = null
    var imagen: String? = null
    var nombreCategoria: String? = null

    constructor() {}
    constructor(
        id: Int,
        titulo: String?,
        ingredientes: String?,
        preparacion: String?,
        imagen: String?,
        nombreCategoria: String?
    ) {
        this.id = id
        this.titulo = titulo
        this.ingredientes = ingredientes
        this.preparacion = preparacion
        this.imagen = imagen
        this.nombreCategoria = nombreCategoria
    }

    protected constructor(`in`: Parcel) {
        id = `in`.readInt()
        titulo = `in`.readString()
        ingredientes = `in`.readString()
        preparacion = `in`.readString()
        imagen = `in`.readString()
        nombreCategoria = `in`.readString()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Receta) return false
        val receta = o
        return id == receta.id && titulo == receta.titulo && ingredientes == receta.ingredientes && preparacion == receta.preparacion && imagen == receta.imagen && nombreCategoria == receta.nombreCategoria
    }




    override fun hashCode(): Int {
        return Objects.hash(id, titulo, ingredientes, preparacion, imagen, nombreCategoria)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(titulo)
        dest.writeString(ingredientes)
        dest.writeString(preparacion)
        dest.writeString(imagen)
        dest.writeString(nombreCategoria)
    }



    companion object CREATOR : Parcelable.Creator<Receta> {
        override fun createFromParcel(parcel: Parcel): Receta {
            return Receta(parcel)
        }

        override fun newArray(size: Int): Array<Receta?> {
            return arrayOfNulls(size)
        }
    }
}