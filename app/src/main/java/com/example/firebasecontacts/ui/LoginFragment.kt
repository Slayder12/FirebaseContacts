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
import com.example.firebasecontacts.databinding.FragmentLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        binding.loginBTN.setOnClickListener{
            login()
        }
        binding.emailLoginET.setText("example@mail.com")
        binding.passwordLoginTV.setText("qwerty")

        binding.redirectSignUpTV.setOnClickListener{
            val navController = findNavController()
            navController.navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        return binding.root
    }

    private fun login() {
        val email = binding.emailLoginET.text.toString()
        val password = binding.passwordLoginTV.text.toString()

        if (!Validator(requireContext()).loginValidate(email, password)) return

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity()){
            if (it.isSuccessful){
                toast("Успешно вошел в систему")
                val navController = findNavController()
                navController.navigate(R.id.action_loginFragment_to_mainFragment)
            } else{
                toast("Не удалось войти в систему")
            }
            binding.redirectSignUpTV.visibility = View.VISIBLE
        }
    }

    private fun toast(text: String) {
        Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_SHORT
        ).show()
    }

}