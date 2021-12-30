package mx.ipn.escom.app_plantas_iswm

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.firestore.FirebaseFirestore
import mx.ipn.escom.app_plantas_iswm.databinding.LoginScreenBinding


class IniciarSesion : AppCompatActivity() {

    private val binding: LoginScreenBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.login_screen)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.root
        supportActionBar?.hide()
    }

    fun goToRegister(view: android.view.View) {
        var intent: Intent = Intent(this,RegistrarUsuario::class.java)
        startActivity(intent)
    }

    fun loginUser(view: android.view.View) {
        val prefM = PreferenceManager
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val mail: String = binding.userMail.text.toString()
        val pass: String = binding.userPass.text.toString()
        prefM.initialize(this)

        db.collection("users").whereEqualTo("mail",mail).whereEqualTo("pass",pass)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    if (task.result!!.documents.isNotEmpty()){

                        //Inicializamos la variable de sesion
                            prefM.saveStatus(true)
                        //Asignamos la llave identificador del usuario
                            var id: String = task.result!!.documents[0].id
                            var name: String = task.result!!.documents[0]["name"].toString() + " " + task.result!!.documents[0]["last"].toString()
                            prefM.saveUserID(id)
                            prefM.saveName(name)

                        Toast.makeText(this, "Bienvenido $name",Toast.LENGTH_SHORT).show()
                        var intent: Intent = Intent(this,Menu::class.java)
                        //TODO [
                        //  Ahora en lugar de pasar la variable de sesion como un objeto en los intent ahora hay que utilizar la variable de sesion en los sharedPreferences
                        //  En el splashActivity validar la variable de sesión para escoger la actividad de destino del usuario
                        //  El cierre de sesión es lo único que establece la variable de sesión como null
                        // ] DONE.
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this,"Datos sin coincidencias",Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }

    }
}