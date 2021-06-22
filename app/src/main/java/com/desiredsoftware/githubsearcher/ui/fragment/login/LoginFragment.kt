package com.desiredsoftware.githubsearcher.ui.fragment.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.desiredsoftware.githubsearcher.databinding.FragmentLoginBinding
import com.desiredsoftware.githubsearcher.presentation.login.ILoginHandler
import com.desiredsoftware.githubsearcher.presentation.login.LoginPresenter
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class LoginFragment : MvpAppCompatFragment(), ILoginHandler {

    private val loginPresenter by moxyPresenter { LoginPresenter() }

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val rootView = binding.root

        navController = requireParentFragment().findNavController()

        binding.buttonLogin.setOnClickListener(View.OnClickListener {
        loginPresenter.makeLogin("github.com")
        })

        return rootView
    }

    override fun openOAuthProvider(providerId: String) {
        val provider: OAuthProvider.Builder = OAuthProvider.newBuilder(providerId)
        Firebase.auth
            .startActivityForSignInWithProvider(requireActivity(), provider.build())
            .addOnSuccessListener(
                OnSuccessListener<AuthResult?> {
                    Log.d("OAuth", "Sign in is success")
                    val action =
                        LoginFragmentDirections.actionLoginFragmentToNavigationProfile()
                    navController.navigate(action)
                })
            .addOnFailureListener(
                OnFailureListener {
                    Log.d("OAuth", "Sign in is failure")
                    it.printStackTrace()
                })
    }

}