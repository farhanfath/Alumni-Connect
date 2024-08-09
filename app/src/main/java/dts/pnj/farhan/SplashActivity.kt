package dts.pnj.farhan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.asLiveData
import dts.pnj.farhan.data.UserPreference
import dts.pnj.farhan.data.dataStore
import dts.pnj.farhan.view.auth.LoginActivity
import dts.pnj.farhan.view.home.HomeActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()

        userPreference = UserPreference.getInstance(applicationContext.dataStore)

        MainScope().launch {
            userPreference.getUser().asLiveData().observe(this@SplashActivity) { user ->
                if (user.name.isNotEmpty()) {
                    navigateToMain()
                } else {
                    navigateToLogin()
                }
            }
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun navigateToMain() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}