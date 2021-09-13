package mx.ipn.escom.app_plantas_iswm

import android.content.ContentValues.TAG
import android.os.Bundle
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
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val binding: P3bSignUpBinding by lazy{
        DataBindingUtil.setContentView(this,R.layout.p3b_sign_up)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Barra de navegacion superior
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.p3b_sign_up)
    }
    override fun onClick(v: View?) {

        db.collection("users/")
            .add(hashMapOf(
                "name" to binding.userName.text.toString(),
                "last" to binding.userLastName.text.toString(),
                "mail" to binding.userMail.text.toString(),
                "pass" to binding.userPass.text.toString()
            ))
            .addOnSuccessListener { documentReference ->
                Log.d(
                    TAG,
                    "DocumentSnapshot added with ID: " + documentReference.id
                )
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
        Toast.makeText(this,"Usuario Registrado",Toast.LENGTH_SHORT).show()
        TODO("Aplicar validacion de claves coincidentes y formato de correo")
    }
}