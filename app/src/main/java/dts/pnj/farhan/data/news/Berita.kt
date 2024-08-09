package dts.pnj.farhan.data.news

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Berita(
    val judul: String,
    val deskripsi: String,
    val isi: String,
    val gambar: String
) : Parcelable
