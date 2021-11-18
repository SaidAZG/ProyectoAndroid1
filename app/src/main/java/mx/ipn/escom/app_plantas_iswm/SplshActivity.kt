package mx.ipn.escom.app_plantas_iswm

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.cover)
        val tutorialKey = "SOME_KEY"
        val firstTime = getPreferences(MODE_PRIVATE).getBoolean(tutorialKey, true)
        Handler().postDelayed({
            if (firstTime) {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
                getPreferences(MODE_PRIVATE).edit().putBoolean(tutorialKey, false).apply()
            }else{
                val intent = Intent(this@SplashActivity, IniciarSesion::class.java)
                startActivity(intent)
                finish()
            }
        }, 4000)
    }
}