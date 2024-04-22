package com.aftarfadilah.a160421095hobbyapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aftarfadilah.a160421095hobbyapp.R
import com.aftarfadilah.a160421095hobbyapp.databinding.FragmentHomeBinding
import com.aftarfadilah.a160421095hobbyapp.databinding.FragmentProfileBinding
import com.aftarfadilah.a160421095hobbyapp.ui.activities.MainActivity
import com.aftarfadilah.a160421095hobbyapp.viewmodel.UserViewModel

class ProfileFragment : Fragment() {
    private lateinit var viewModel: UserViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = (activity as? MainActivity)?.LoggedInUserId

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Fetch data
        viewModel.fetch(userId ?: 0)

        // Observe LiveData
        observeViewModel()

        binding.btnSave.setOnClickListener {
            var username = binding.etUsername.text?.toString() ?: ""
            var namaDepan = binding.etNamaDepan.text?.toString() ?: ""
            var namaBelakang = binding.etNamaBelakang.text?.toString() ?: ""

            viewModel.edit(userId ?: 0, username, namaDepan, namaBelakang)
        }
    }
    fun observeViewModel() {
        viewModel.userLD.observe(viewLifecycleOwner, Observer { user ->

            binding.etUsername.setText(user.username ?: "")
            binding.etNamaDepan.setText(user.nama_depan ?: "")
            binding.etNamaBelakang.setText(user.nama_belakang ?: "")
        })
    }
}