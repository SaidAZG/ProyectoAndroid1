package mx.ipn.escom.app_plantas_iswm

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.firestore.FirebaseFirestore
import mx.ipn.escom.app_plantas_iswm.databinding.P3aLoginBinding
import mx.ipn.escom.app_plantas_iswm.databinding.P3bSignUpBinding
import com.google.firebase.firestore.QueryDocumentSnapshot

import com.google.firebase.firestore.QuerySnapshot

import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener
import android.util.Patterns
import java.util.regex.Pattern


class IniciarSesion : AppCompatActivity() {

    private val binding: P3aLoginBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.p3a_login)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.root
    }

    fun goToRegister(view: android.view.View) {
        var intent: Intent = Intent(this,RegistrarUsuario::class.java)
        startActivity(intent)
    }

    fun loginUser(view: android.view.View) {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val mail: String = binding.userMail.text.toString()
        val pass: String = binding.userPass.text.toString()

        db.collection("users").whereEqualTo("mail",mail).whereEqualTo("pass",pass)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result!!.documents.isNotEmpty()){
                        var id: String = task.result!!.documents[0].id
                        Toast.makeText(this,"Bienvenido",Toast.LENGTH_SHORT).show()
                        var intent: Intent = Intent(this,Menu::class.java)
                        intent.putExtra("id",id)
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