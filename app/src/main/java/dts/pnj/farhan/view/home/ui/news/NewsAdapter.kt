package dts.pnj.farhan.view.home.ui.news

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dts.pnj.farhan.data.news.Berita
import dts.pnj.farhan.databinding.ItemBeritaBinding
import dts.pnj.farhan.view.home.ui.news.detailnews.DetailNewsActivity

class BeritaAdapter(private val beritaList: List<Berita>) :
    RecyclerView.Adapter<BeritaAdapter.BeritaViewHolder>() {

    inner class BeritaViewHolder(private val binding: ItemBeritaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(berita: Berita) {
            binding.judulTv.text = berita.judul
            binding.descTv.text = berita.deskripsi
            Glide.with(binding.beritaIv.context)
                .load(berita.gambar)
                .into(binding.beritaIv)

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailNewsActivity::class.java).apply {
                    putExtra("berita", berita)
                }
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeritaViewHolder {
        val binding = ItemBeritaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeritaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeritaViewHolder, position: Int) {
        holder.bind(beritaList[position])
    }

    override fun getItemCount() = beritaList.size
}