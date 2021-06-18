package com.desiredsoftware.githubsearcher.ui.fragment.profile
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.desiredsoftware.githubsearcher.R
import com.desiredsoftware.githubsearcher.databinding.FragmentProfileBinding
import com.desiredsoftware.githubsearcher.presentation.profile.IProfileInfoDisplayer
import com.desiredsoftware.githubsearcher.presentation.profile.ProfilePresenter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class ProfileFragment : MvpAppCompatFragment(), IProfileInfoDisplayer {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    lateinit var navController: NavController

    private val profilePresenter by moxyPresenter { ProfilePresenter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = requireParentFragment().findNavController()

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val rootView = binding.root

        profilePresenter.setProfileInfo()

        return rootView
    }

    override fun showProfileInfo(userInfo: Map<String, String?>) {
        binding.textViewName.text = userInfo["displayName"]
        binding.textViewEmail.text = userInfo["email"]
        binding.imageViewAvatar?.let {
            Glide.with(this)
                .load(userInfo["photoUrl"])
                .error(R.mipmap.ic_github)
                .centerCrop().into(it)
        }
    }

    override fun showLoginFragment() {
        val action = ProfileFragmentDirections.
        actionNavigationProfileToLoginFragment()
        navController.navigate(action)
    }
}