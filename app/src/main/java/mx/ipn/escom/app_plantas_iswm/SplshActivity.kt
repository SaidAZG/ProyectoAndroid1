package mx.ipn.escom.app_plantas_iswm

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.splash_screen)
        redirect()
    }

    private fun redirect(){
        val prefM = PreferenceManager
        val tutorialKey = "NEW"
        val firstTime = getPreferences(MODE_PRIVATE).getBoolean(tutorialKey, true)
        prefM.initialize(this)

        Handler().postDelayed({
            if (firstTime) {
                getPreferences(MODE_PRIVATE).edit().putBoolean(tutorialKey, false).apply()
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                //Validar si ya hay una sesion activa
                    if (!prefM.getStatus()){
                        //Si no hay una sesion activa redirige al usuario al inicio de sesion
                        val intent = Intent(this@SplashActivity, IniciarSesion::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        //Si hay una sesion activa redirige al usuario al menu principal
                        val intent = Intent(this@SplashActivity, Menu::class.java)
                        startActivity(intent)
                        finish()
                    }
            }
        }, 4000)
    }

}