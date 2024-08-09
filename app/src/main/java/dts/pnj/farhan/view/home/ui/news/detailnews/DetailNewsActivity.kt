package dts.pnj.farhan.view.home.ui.news.detailnews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import dts.pnj.farhan.data.news.Berita
import dts.pnj.farhan.databinding.ActivityDetailNewsBinding

class DetailNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = ""

        val berita: Berita = intent.getParcelableExtra("berita") ?: return

        binding.judulTv.text = berita.judul
        binding.descTv.text = berita.deskripsi
        binding.isiTv.text = berita.isi
        Glide.with(this)
            .load(berita.gambar)
            .into(binding.beritaImage)

    }
}