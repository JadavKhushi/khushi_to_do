package com.example.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todolist.R
import com.example.todolist.databinding.FragmentSignUpBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {

    private lateinit var auth:FirebaseAuth
    private lateinit var navControl: NavController
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentSignUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        registerEvents()
    }
    private fun init(view: View){
        navControl = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
    }
    private fun registerEvents(){

        //for click on sign in to go on page of sign in
        binding.authTextView.setOnClickListener {
            navControl.navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        //for sign up and register user then go to home page
        binding.nextBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passEt.text.toString().trim()
            val verifyPass = binding.repassEt.text.toString().trim()

            if(email.isNotEmpty() && pass.isNotEmpty() && verifyPass.isNotEmpty()){
                if (pass == verifyPass){
                    //progressbar
                    binding.progressBar.visibility = View.VISIBLE

                    auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(
                        OnCompleteListener {
                            if (it.isSuccessful){
                                Toast.makeText(context, "Registered Successfully!!", Toast.LENGTH_SHORT).show()
                                navControl.navigate(R.id.action_signUpFragment_to_homeFragment)
                            }else{
                                Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
                            }
                            binding.progressBar.visibility = View.GONE
                        })

                }else{
                    Toast.makeText(context, "Your password is not match!", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(context, "Empty field is not allowed!!", Toast.LENGTH_SHORT).show()
            }
        }
    }


}