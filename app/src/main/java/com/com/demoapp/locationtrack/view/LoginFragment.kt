package com.com.demoapp.locationtrack.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.demoapp.locationtrack.R
import com.demoapp.locationtrack.databinding.FragmentLoginBinding
import com.example.locationtracker.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding?=null
    private val binding get() =_binding!!

    private lateinit var firebaseAuth :FirebaseAuth
    private var oneTapClient : SignInClient? = null
    private var signInRequest : BeginSignInRequest ? = null



    private var viewModel: LoginViewModel? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding=FragmentLoginBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        viewModel  = (activity as MainActivity).mainViewModel

        firebaseAuth = Firebase.auth
        activity?.let {
            oneTapClient = Identity.getSignInClient(it)
        }


        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.dafault_web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(false)
                    .build())
            .build()


        binding.register.setOnClickListener {
            it.findNavController().navigate(com.demoapp.locationtrack.R.id.action_loginFragment_to_homeFragment)
        }

        binding.googlesign.setOnClickListener{

            signInGoogle(it)
        }
        binding.loginpageloginbtn.setOnClickListener {
            val loginuname=binding.loginuname.text.toString().trim()
            val loginpwd=binding.loginupwd.text.toString().trim()
            if (loginpwd.isNotEmpty() && loginuname.isNotEmpty()){

                findUser(loginuname, loginpwd,false)

            }
        }
    }

    private val activityresultLauncher : ActivityResultLauncher<IntentSenderRequest> = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult())
    { result ->

        if(result.resultCode==RESULT_OK){

            try {

                val credential = oneTapClient?.getSignInCredentialFromIntent(result.data)
                val idToken = credential?.googleIdToken

                if(idToken!=null){
                    val firebaseCredental = GoogleAuthProvider.getCredential(idToken,null)

                    firebaseAuth.signInWithCredential(firebaseCredental).addOnCompleteListener {

                        if(it.isSuccessful){
                            Toast.makeText(activity?.applicationContext,"Sigin in sucessfully",Toast.LENGTH_LONG)
                            showLoginUser()
                        }
                    }
                }
            }catch (e:ApiException){
                e.printStackTrace()

            }
        }
    }


    private fun signInGoogle(view:View){
        CoroutineScope(Dispatchers.Main).launch {
            signInGoogle()

        }

    }


    private suspend fun signInGoogle(){
        val request = signInRequest?.let { oneTapClient?.beginSignIn(it)?.await() }
        val intentSenderRequest = request?.pendingIntent?.let { IntentSenderRequest.Builder(it).build() }
        activityresultLauncher.launch(intentSenderRequest)
    }

    override fun onStart() {
        super.onStart()

        val currentUser = Firebase.auth.currentUser

        if(currentUser!=null)run {
            showLoginUser()
        }
    }



    private fun showLoginUser(){
        val user  = Firebase.auth.currentUser
        user?.let {
            val userName = it.displayName
            val email = it.email

          findUser(email.toString(),userName.toString(),true)

           //

        }
    }

    private fun findUser(loginuname: String, loginpwd: String,createUser :Boolean) {

        lifecycleScope.launch {
            try {
                val user = viewModel?.findUser(loginuname, loginpwd)

                if (user?.username?.isNotEmpty()==true ) {
                    // Authentication successful
                    Toast.makeText(requireContext(), "Successfully Login", Toast.LENGTH_LONG).show()
//                  t.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    navigateToNextScreen()

                }else if(createUser) {
                    // Authentication successful
                    viewModel?.createUser(loginuname,loginpwd)
                    navigateToNextScreen()
                }else {
                    // Authentication failed
                    Toast.makeText(requireContext(), "Failure Login", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                // Handle exceptions if any
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun navigateToNextScreen() {

        val showLocationActivity =  Intent(activity,ShowCurrentLocationActivity::class.java)
        startActivity(showLocationActivity)


    }


}