package mx.ipn.escom.app_plantas_iswm

import android.content.ContentValues.TAG
import android.os.Bundle
import android.content.Intent
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import mx.ipn.escom.app_plantas_iswm.databinding.P3bSignUpBinding
import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnFailureListener

import com.google.firebase.firestore.DocumentReference

import com.google.android.gms.tasks.OnSuccessListener




class RegistrarUsuario : AppCompatActivity(), View.OnClickListener{

    private val binding: P3bSignUpBinding by lazy{
        DataBindingUtil.setContentView(this,R.layout.p3b_sign_up)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Barra de navegacion superior
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
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

        //TODO Aplicar validacion de claves coincidentes y formato de correo
    }
}