package mx.ipn.escom.app_plantas_iswm

import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {

        /* */
        private lateinit var sharedPreferences: SharedPreferences

        /* Constants */
        private const val sesion = "SESION"
        private const val id = "ID"
        private const val name = "NAME"

        fun initialize(context: Context) {
            sharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        }

        //Salva el estatus de sesion
        fun saveStatus(status: Boolean) {
            sharedPreferences.edit().putBoolean(sesion, status).apply()
        }
        //Obtiene el estatus de sesion
        fun getStatus(): Boolean = sharedPreferences.getBoolean(sesion, false)

        //Salva el nombre del usuario
        fun saveName(username: String) {
                sharedPreferences.edit().putString(name, username).apply()
        }
        //Obtiene el nombre del usuario
        fun getName(): String? = sharedPreferences.getString("NAME", "NOT FOUND")

        //Salva el identificador del usuario
        fun saveUserID(id: String) {
            sharedPreferences.edit().putString("ID", id).apply()
        }
        //Obtiene el identificador del usuario
        fun getUserID(): String? = sharedPreferences.getString("ID", "not found")
    }