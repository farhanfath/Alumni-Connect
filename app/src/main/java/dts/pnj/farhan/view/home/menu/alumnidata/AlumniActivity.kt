package dts.pnj.farhan.view.home.menu.alumnidata

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dts.pnj.farhan.databinding.ActivityAlumniBinding
import dts.pnj.farhan.utils.ViewModelFactory
import dts.pnj.farhan.utils.viewmodel.AlumniViewModel

class AlumniActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlumniBinding
    private lateinit var adapter: AlumniAdapter

    private val viewModel by viewModels<AlumniViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlumniBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = ""

        adapter = AlumniAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.alumniList.observe(this) { alumniList ->
            adapter.updateData(alumniList)
        }

        viewModel.loadAllAlumni()
    }

    override fun onResume() {
        super.onResume()

        viewModel.loadAllAlumni()
    }
}