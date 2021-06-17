package com.desiredsoftware.githubsearcher.ui.fragment.profile
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.desiredsoftware.githubsearcher.R
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    lateinit var navController: NavController

    private var user: FirebaseUser? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        user =  Firebase.auth.currentUser

        navController = requireParentFragment().findNavController()

                val root = inflater.inflate(R.layout.fragment_profile, container, false)

        val imageViewAvatar: ImageView = root.findViewById(R.id.imageViewAvatar)
        val textViewName: MaterialTextView = root.findViewById(R.id.textViewName)
        val textViewEmail: MaterialTextView = root.findViewById(R.id.textViewEmail)

        if (user!=null)
        {
            user?.let { loggedUser->

                var email = loggedUser.email
                var photoUrl = loggedUser.photoUrl.toString()
                var displayName = loggedUser.displayName


            textViewName.text = displayName
            textViewEmail.text = email
            imageViewAvatar?.let {
                Glide.with(this)
                    .load(photoUrl)
                    .error(R.mipmap.ic_github)
                    .override(400, 400)
                    .centerCrop().into(it)
                }
            }
        }
        else
        {
            val action =
                ProfileFragmentDirections.actionNavigationProfileToLoginFragment()
            navController.navigate(action)
        }

        return root
    }
}