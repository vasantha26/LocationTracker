package com.com.demoapp.locationtrack.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.demoapp.locationtrack.R
import com.demoapp.locationtrack.databinding.FragmentNewRegBinding
import com.example.locationtracker.viewmodel.LoginViewModel

class NewRegFragment : Fragment(R.layout.fragment_new_reg) {

    private  var _binding: FragmentNewRegBinding?=null
    private val binding get() = _binding!!
    private var viewModel: LoginViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding=FragmentNewRegBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel  = (activity as MainActivity).mainViewModel

        binding.registerbtn.setOnClickListener {
            val loginuname=binding.registernuname.text.toString().trim()
            val loginpwd=binding.registernupwd.text.toString().trim()

            if (loginpwd.isNotEmpty() && loginuname.isNotEmpty()) {
                viewModel?.createUser(loginuname,loginpwd)
                Toast.makeText(requireContext(),"Successfully Registered", Toast.LENGTH_LONG).show()
                it.findNavController().navigate(R.id.action_newRegFragment_to_homeFragment2)

            }
        }

        binding.reglogin.setOnClickListener{
            it.findNavController().navigate(R.id.action_newRegFragment_to_homeFragment2)
        }
    }

}