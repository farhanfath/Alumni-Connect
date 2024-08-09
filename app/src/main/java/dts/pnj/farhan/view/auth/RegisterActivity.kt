package dts.pnj.farhan.view.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dts.pnj.farhan.R
import dts.pnj.farhan.data.database.user.User
import dts.pnj.farhan.databinding.ActivityRegisterBinding
import dts.pnj.farhan.utils.ViewModelFactory
import dts.pnj.farhan.utils.viewmodel.MainViewModel

class RegisterActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this@RegisterActivity)
    }

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.registerBtn.setOnClickListener { registerUser() }
        binding.toLoginText.setOnClickListener { finish() }
    }

    private fun registerUser() {
        val nim = binding.nimEt.text.toString()
        val name = binding.nameEt.text.toString()
        val className = binding.classEt.text.toString()
        val email = binding.emailEt.text.toString()
        val password = binding.passEt.text.toString()

        when {
            nim.isEmpty() -> {
                binding.nimEtLayout.error = getString(R.string.required)
            }
            name.isEmpty() -> {
                binding.nameEtLayout.error = getString(R.string.required)
            }
            className.isEmpty() -> {
                binding.classEtLayout.error = getString(R.string.required)
            }
            email.isEmpty() -> {
                binding.emailEtLayout.error = getString(R.string.required)
            }
            password.isEmpty() -> {
                binding.passEtLayout.error = getString(R.string.required)
            }
            else -> {
                val user = User(nim = nim, name = name, className = className, email = email, password = password)
                viewModel.registerUser(user)
                viewModel.isLoading.observe(this) { isLoading ->
                    showLoading(isLoading)
                }
                viewModel.registerStatus.observe(this) { result ->
                    result.onSuccess { isRegistered ->
                        if (isRegistered) {
                            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show()
                        }
                    }.onFailure {
                        Toast.makeText(this, "Registration failed: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressOverlay.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}