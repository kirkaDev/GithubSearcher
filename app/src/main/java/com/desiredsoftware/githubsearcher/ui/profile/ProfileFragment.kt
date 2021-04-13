package com.desiredsoftware.githubsearcher.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.desiredsoftware.githubsearcher.BuildConfig
import com.desiredsoftware.githubsearcher.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GetTokenResult
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*


class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val user: FirebaseUser? =  Firebase.auth.currentUser

        user?.getIdToken(true)?.addOnCompleteListener(OnCompleteListener<GetTokenResult> { task ->
            if (task.isSuccessful) {
                val idToken = task.result!!.token
                Log.d("Auth Token", "$idToken")// Send token to your backend via HTTPS
                // ...
            } else {
                // Handle error -> task.getException();
                Log.d("Auth Token", "Get token fail")// Send token to your backend via HTTPS
            }
        })


        if (user==null) {

            val provider: OAuthProvider.Builder = OAuthProvider.newBuilder("github.com")
            Firebase.auth
                .startActivityForSignInWithProvider(requireActivity(), provider.build())
                .addOnSuccessListener(
                    OnSuccessListener<AuthResult?> {
                        if (BuildConfig.DEBUG) {
                            val profileInfo: Map<String, Any> =
                                it.getAdditionalUserInfo().getProfile()
                            profileInfo.forEach { (key, value) ->
                                Log.d("OAuth", "$key : $value")
                            }
                        }
                    })
                .addOnFailureListener(
                    OnFailureListener {
                        Log.d("OAuth", "Sign in is failure")
                        it.printStackTrace()
                    })
        }

        profileViewModel =
                ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        profileViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}