package dts.pnj.farhan.view.home.menu.alumnidata

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dts.pnj.farhan.data.database.alumni.Alumni
import dts.pnj.farhan.databinding.ItemAlumniBinding

class AlumniAdapter(private var alumniList: List<Alumni>) :
    RecyclerView.Adapter<AlumniAdapter.AlumniViewHolder>() {

    class AlumniViewHolder(val binding: ItemAlumniBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(alumni: Alumni) {
            binding.alumniName.text = alumni.name
            binding.alumniNim.text = alumni.nim

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailAlumniActivity::class.java).apply {
                    putExtra("alumni", alumni)
                }
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumniViewHolder {
        val binding = ItemAlumniBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlumniViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlumniViewHolder, position: Int) {
        holder.bind(alumniList[position])
    }

    override fun getItemCount(): Int = alumniList.size

    fun updateData(newAlumniList: List<Alumni>) {
        alumniList = newAlumniList
        notifyDataSetChanged()
    }
}