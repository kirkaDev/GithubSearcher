package com.desiredsoftware.githubsearcher.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.desiredsoftware.githubsearcher.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        navController = requireParentFragment().findNavController()

        val root = inflater.inflate(R.layout.fragment_login, container, false)
        val buttonLogin: MaterialButton = root.findViewById(R.id.buttonLogin)

        buttonLogin.setOnClickListener(View.OnClickListener {
                val provider: OAuthProvider.Builder = OAuthProvider.newBuilder("github.com")
                Firebase.auth
                    .startActivityForSignInWithProvider(requireActivity(), provider.build())
                    .addOnSuccessListener(
                        OnSuccessListener<AuthResult?> {
                            Log.d("OAuth", "Sign in is success")

                            val action = LoginFragmentDirections.actionLoginFragmentToNavigationProfile()
                            navController.navigate(action)

                    })
                    .addOnFailureListener(
                        OnFailureListener {
                            Log.d("OAuth", "Sign in is failure")
                            it.printStackTrace()
                        })
        })

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

}