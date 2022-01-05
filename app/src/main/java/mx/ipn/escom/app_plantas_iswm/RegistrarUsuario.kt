package mx.ipn.escom.app_plantas_iswm

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.firestore.FirebaseFirestore
import mx.ipn.escom.app_plantas_iswm.databinding.SignupScreenBinding
import java.util.regex.Pattern


class RegistrarUsuario : AppCompatActivity(), View.OnClickListener{

    private val binding: SignupScreenBinding by lazy{
        DataBindingUtil.setContentView(this,R.layout.signup_screen)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Barra de navegacion superior
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.root
    }
    override fun onClick(v: View?) {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val name:String = binding.userName.text.toString()
        val last:String = binding.userLastName.text.toString()
        val mail:String = binding.userMail.text.toString()
        val pass:String =binding.userPass.text.toString()
        val pass2:String = binding.userPass2.text.toString()
        val map = hashMapOf(
            "name" to name,
            "last" to last,
            "mail" to mail,
            "pass" to pass
        )
        Log.i(TAG,"------------------------------"+mail+name+last+pass+pass2)
        if (name.isEmpty()){
            binding.userName.error = "Datos Vacíos"
        }else if (last.isEmpty()){
            binding.userLastName.error = "Datos Vacíos"
        }else if (mail.isEmpty()){
            binding.userMail.error = "Datos Vacíos"
        }else if (!validarEmail(mail)){
            binding.userMail.error = "Formato de Correo Incorrecto"
        }else if (pass.length < 8){
            binding.userPass.error = "Mínimo 8 caracteres"
        }else if (pass.isEmpty()){
            binding.userPass.error = "Datos Vacíos"
        }else if (pass2.isEmpty()){
            binding.userPass2.error = "Datos Vacíos"
        }else if (pass != pass2){
            binding.userPass2.error = "Las claves deben coincidir"
        }else{
            db.collection("users")
                .add(map)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        TAG,
                        "DocumentSnapshot added with ID: " + documentReference.id
                    )
                    var intent = Intent(this,IniciarSesion::class.java)
                    startActivity(intent)

                    Toast.makeText(this,"Usuario Registrado",Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
        }
        //TODO Aplicar validacion de claves coincidentes y formato de correo
    }

    private fun validarEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}