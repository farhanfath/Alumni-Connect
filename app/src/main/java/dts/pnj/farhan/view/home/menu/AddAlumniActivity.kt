package dts.pnj.farhan.view.home.menu

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import dts.pnj.farhan.R
import dts.pnj.farhan.data.database.alumni.Alumni
import dts.pnj.farhan.databinding.ActivityAddAlumniBinding
import dts.pnj.farhan.utils.ViewModelFactory
import dts.pnj.farhan.utils.viewmodel.AlumniViewModel
import java.util.Calendar

class AddAlumniActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAlumniBinding

    private val viewModel by viewModels<AlumniViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var currentEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAlumniBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = ""

        binding.saveDataBtn.setOnClickListener { addAlumniData() }

        binding.birthDateEt.setOnClickListener { openCalendar(binding.birthDateEt) }
        binding.joinYearEt.setOnClickListener { openCalendar(binding.joinYearEt) }
        binding.graduateYearEt.setOnClickListener { openCalendar(binding.graduateYearEt) }
    }

    private fun openCalendar(editText: TextInputEditText) {
        currentEditText = editText

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                currentEditText.setText(selectedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun addAlumniData() {
        val nim = binding.nimEt.text.toString()
        val name = binding.nameEt.text.toString()
        val birthDate = binding.birthDateEt.text.toString()
        val birthPlace = binding.BirthPlaceEt.text.toString()
        val address = binding.addressEt.text.toString()
        val religion = binding.religionEt.text.toString()
        val phoneNumber = binding.phoneNumberEt.text.toString()
        val joinYear = binding.joinYearEt.text.toString()
        val graduateYear = binding.graduateYearEt.text.toString()
        val job = binding.jobEt.text.toString()
        val position = binding.positionEt.text.toString()

        val alumni = Alumni(
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

        when {
            nim.isEmpty() -> {
                binding.nimEtLayout.error = getString(R.string.required)
            }
            name.isEmpty() -> {
                binding.nameEtLayout.error = getString(R.string.required)
            }
            birthDate.isEmpty() -> {
                binding.birthDateEtLayout.error = getString(R.string.required)
            }
            birthPlace.isEmpty() -> {
                binding.birthPlaceEtLayout.error = getString(R.string.required)
            }
            address.isEmpty() -> {
                binding.addressEtLayout.error = getString(R.string.required)
            }
            religion.isEmpty() -> {
                binding.religionEtLayout.error = getString(R.string.required)
            }
            phoneNumber.isEmpty() -> {
                binding.phoneNumberEtLayout.error = getString(R.string.required)
            }
            joinYear.isEmpty() -> {
                binding.joinYearEtLayout.error = getString(R.string.required)
            }
            graduateYear.isEmpty() -> {
                binding.graduateYearEtLayout.error = getString(R.string.required)
            }
            job.isEmpty() -> {
                binding.jobEtLayout.error = getString(R.string.required)
            }
            position.isEmpty() -> {
                binding.positionEtLayout.error = getString(R.string.required)
            }
            else -> {
                viewModel.addAlumni(alumni)
                viewModel.isLoading.observe(this) { isLoading ->
                    showLoading(isLoading)
                }
                viewModel.addAlumniStatus.observe(this) { result ->
                    result.onSuccess { success ->
                        if (success) {
                            Toast.makeText(this, "Berhasil Menambahkan Data Alumni", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "Gagal Menambahkan Data Alumni", Toast.LENGTH_SHORT).show()
                        }
                    }.onFailure {
                        Log.e("AddAlumniActivity", "Error adding alumni data: ${it.message}")
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.saveDataBtn.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.saveDataBtn.visibility = View.VISIBLE
        }
    }

}