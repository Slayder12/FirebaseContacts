package com.example.firebasecontacts.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.firebasecontacts.R
import com.example.firebasecontacts.utils.Validator
import com.example.firebasecontacts.databinding.FragmentSignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        auth = Firebase.auth
        binding.signInBTN.setOnClickListener{
            signUpUser()
        }
        binding.redirectLoginTV.setOnClickListener{
            val navController = findNavController()
            navController.navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        return binding.root
    }

    private fun signUpUser() {
        val email = binding.emailSignUpET.text.toString()
        val password = binding.passwordSignUpTV.text.toString()
        val confirmPass = binding.confirmPasswordSignUpTV.text.toString()

        if (!Validator(requireContext()).signUpValidate(email, password, confirmPass)) return

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity()){
            if (it.isSuccessful){
                toast("Успешно")
                val navController = findNavController()
                navController.navigate(R.id.action_signUpFragment_to_mainFragment)
            } else{
                if (auth.currentUser != null){
                    toast("Пользователь уже зарегистрирован")
                    val navController = findNavController()
                    navController.navigate(R.id.action_signUpFragment_to_loginFragment)
                }
            }
        }
    }

    private fun toast(text: String) {
        Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}