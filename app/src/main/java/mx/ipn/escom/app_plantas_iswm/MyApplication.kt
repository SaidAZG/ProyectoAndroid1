package mx.ipn.escom.app_plantas_iswm

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class MyApplication: Application() {
    //Esta clase sirve para deshabilitar el modo oscuro predefinido de la aplicacion
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}