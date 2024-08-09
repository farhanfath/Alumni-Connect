package dts.pnj.farhan.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dts.pnj.farhan.databinding.ActivityMainBinding
import dts.pnj.farhan.utils.ViewModelFactory
import dts.pnj.farhan.utils.viewmodel.MainViewModel
import dts.pnj.farhan.view.auth.LoginActivity

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this@MainActivity)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (user.name.isEmpty()) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                binding.name.text = user.name
                binding.nim.text = user.nim
                binding.email.text = user.email
                binding.kelas.text = user.className
            }
        }
    }
}