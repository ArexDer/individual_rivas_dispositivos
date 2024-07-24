package com.rivas.diego.proyectorivas.ui.fragments.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rivas.diego.proyectorivas.R
import com.rivas.diego.proyectorivas.databinding.FragmentProfileSettingsBinding
import com.rivas.diego.proyectorivas.ui.core.ManageUIStates

class ProfileSettingsFragment : Fragment() {

    private lateinit var manageUIStates: ManageUIStates

    private lateinit var binding: FragmentProfileSettingsBinding
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load current user data
        loadUserData()

        binding.btnSave.setOnClickListener {
            saveUserData()
        }
        binding.btnReturn.setOnClickListener {
            findNavController().navigate(R.id.action_profileSettingsFragment_to_moviesFragment)
        }

        initVariables()
    }

    private fun initVariables() {
        manageUIStates = ManageUIStates(requireActivity(), binding.lytLoading.mainLayout)




    }

    private fun loadUserData() {
        val user = auth.currentUser ?: return
        firestore.collection("users").document(user.uid).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    binding.etFirstName.setText(document.getString("firstName"))
                    binding.etLastName.setText(document.getString("lastName"))
                    binding.etAge.setText(document.getString("age"))
                    binding.etNickname.setText(document.getString("nickname"))

                    val interests = document.get("interests") as? List<String> ?: listOf()
                    binding.cbHorror.isChecked = "Horror" in interests
                    binding.cbAction.isChecked = "Action" in interests
                    binding.cbAdventure.isChecked = "Adventure" in interests
                    binding.cbNovels.isChecked = "Novels" in interests
                    binding.cbDrama.isChecked = "Drama" in interests
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Failed to load data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


    private fun saveUserData() {
        val user = auth.currentUser ?: return
        val interests = mutableListOf<String>()
        if (binding.cbHorror.isChecked) interests.add("Horror")
        if (binding.cbAction.isChecked) interests.add("Action")
        if (binding.cbAdventure.isChecked) interests.add("Adventure")
        if (binding.cbNovels.isChecked) interests.add("Novels")
        if (binding.cbDrama.isChecked) interests.add("Drama")

        val userData = mapOf(
            "firstName" to binding.etFirstName.text.toString().trim(),
            "lastName" to binding.etLastName.text.toString().trim(),
            "age" to binding.etAge.text.toString().trim(),
            "nickname" to binding.etNickname.text.toString().trim(),
            "interests" to interests
        )

        firestore.collection("users").document(user.uid).set(userData)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Data updated successfully", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Failed to update data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}