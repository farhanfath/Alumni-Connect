package dts.pnj.farhan.view.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dts.pnj.farhan.R
import dts.pnj.farhan.databinding.ActivityLoginBinding
import dts.pnj.farhan.utils.ViewModelFactory
import dts.pnj.farhan.utils.viewmodel.MainViewModel
import dts.pnj.farhan.view.home.HomeActivity

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this@LoginActivity)
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.loginBtn.setOnClickListener { loginUser() }
        binding.toRegisterText.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }
    }

    private fun loginUser() {
        val email = binding.emailEt.text.toString()
        val password = binding.passEt.text.toString()
        when {
            email.isEmpty() -> {
                binding.emailEtLayout.error = getString(R.string.required)
            }
            password.isEmpty() -> {
                binding.passEtLayout.error = getString(R.string.required)
            }
            else -> {
                viewModel.loginUser(email, password)
                viewModel.isLoading.observe(this) { isLoading ->
                    showLoading(isLoading)
                }
                viewModel.loginStatus.observe(this) { result ->
                    result.onSuccess { isLoggedIn ->
                        if (isLoggedIn) {
                            successLoginHandler()
                        } else {
                            Toast.makeText(this, "Login failed. Check your email and password.", Toast.LENGTH_SHORT).show()
                        }
                    }.onFailure {
                        Toast.makeText(this, "Login failed: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun successLoginHandler() {
        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressOverlay.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}