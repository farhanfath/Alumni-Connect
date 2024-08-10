package dts.pnj.farhan.view.home.menu.alumnidata

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dts.pnj.farhan.data.database.alumni.Alumni
import dts.pnj.farhan.databinding.ActivityDetailAlumniBinding
import dts.pnj.farhan.utils.ViewModelFactory
import dts.pnj.farhan.utils.viewmodel.AlumniViewModel

class DetailAlumniActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailAlumniBinding

    private val viewModel by viewModels<AlumniViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var alumni: Alumni

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAlumniBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = ""

        alumni = intent.getParcelableExtra("alumni") ?: return
        setupView(alumni)

        binding.deleteDataBtn.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        binding.updateDataBtn.setOnClickListener {
            updateData()
        }
    }

    private fun updateData() {
        val nim = binding.nimEt.text.toString()
        val name = binding.nameEt.text.toString()
        val birthDate = binding.birthDateEt.text.toString()
        val birthPlace = binding.birthPlaceEt.text.toString()
        val address = binding.addressEt.text.toString()
        val religion = binding.religionEt.text.toString()
        val phoneNumber = binding.phoneNumberEt.text.toString()
        val joinYear = binding.joinYearEt.text.toString()
        val graduateYear = binding.graduateYearEt.text.toString()
        val job = binding.jobEt.text.toString()
        val position = binding.positionEt.text.toString()

        val updatedAlumni = alumni.copy(
            nim = nim,
            name = name,
            birthDate = birthDate,
            birthPlace = birthPlace,
            address = address,
            religion = religion,
            phoneNumber = phoneNumber,
            joinYear = joinYear,
            graduateYear = graduateYear,
            job = job,
            position = position
        )

        viewModel.updateAlumni(updatedAlumni)
        finish()
        Toast.makeText(this, "Berhasil Mengupdate Data Alumni", Toast.LENGTH_SHORT).show()
    }

    private fun setupView(alumni: Alumni) {
        binding.nimEt.setText(alumni.nim)
        binding.nameEt.setText(alumni.name)
        binding.birthPlaceEt.setText(alumni.birthPlace)
        binding.birthDateEt.setText(alumni.birthDate)
        binding.addressEt.setText(alumni.address)
        binding.religionEt.setText(alumni.religion)
        binding.phoneNumberEt.setText(alumni.phoneNumber)
        binding.joinYearEt.setText(alumni.joinYear)
        binding.graduateYearEt.setText(alumni.graduateYear)
        binding.jobEt.setText(alumni.job)
        binding.positionEt.setText(alumni.position)
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Hapus")
            .setMessage("Apakah Anda yakin ingin menghapus data alumni ini?")
            .setPositiveButton("Hapus") { _, _ ->
                alumni.let { alumniId ->
                    viewModel.removeAlumni(alumniId)
                    Toast.makeText(this, "Berhasil Menghapus Data Alumni", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}