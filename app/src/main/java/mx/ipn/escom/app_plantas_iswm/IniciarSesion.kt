package mx.ipn.escom.app_plantas_iswm

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.firestore.FirebaseFirestore
import mx.ipn.escom.app_plantas_iswm.databinding.P3aLoginBinding
import mx.ipn.escom.app_plantas_iswm.databinding.P3bSignUpBinding

class IniciarSesion : AppCompatActivity(), View.OnClickListener {
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val binding: P3aLoginBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.p3b_sign_up)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}