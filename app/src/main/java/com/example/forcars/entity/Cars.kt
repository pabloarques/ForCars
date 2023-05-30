package com.example.forcars.entity

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Cars(
    val id: String?,
    val marca: String?,
    val modelo: String?,
    val combustible: String?,
    val kilometros: Int,
    val cv: Int,
    val price: Int,
    val cambio: String?,
    val year: Int,
    val motor: String?,
    val image: String?,
    val ubicacion: String?
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(marca)
        parcel.writeString(modelo)
        parcel.writeString(combustible)
        parcel.writeInt(kilometros)
        parcel.writeInt(cv)
        parcel.writeInt(price)
        parcel.writeString(cambio)
        parcel.writeInt(year)
        parcel.writeString(motor)
        parcel.writeString(image)
        parcel.writeString(ubicacion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cars> {
        override fun createFromParcel(parcel: Parcel): Cars {
            return Cars(parcel)
        }

        override fun newArray(size: Int): Array<Cars?> {
            return arrayOfNulls(size)
        }
    }
}