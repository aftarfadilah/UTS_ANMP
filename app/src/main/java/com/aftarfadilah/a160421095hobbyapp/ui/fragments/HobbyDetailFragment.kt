package com.aftarfadilah.a160421095hobbyapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aftarfadilah.a160421095hobbyapp.databinding.FragmentHobbyDetailBinding
import com.aftarfadilah.a160421095hobbyapp.viewmodel.DetailViewModel
import com.squareup.picasso.Picasso

class HobbyDetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentHobbyDetailBinding

    private var currentArticleIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHobbyDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun goNextArticle() {
        currentArticleIndex++
        observeViewModel()
    }

    fun goPrevArticle() {
        currentArticleIndex--
        observeViewModel()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hobbyId = arguments?.getString("hobbyId")

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        // Fetch data
        viewModel.fetch(hobbyId ?: "")

        // Observe LiveData
        observeViewModel()

        binding.btnPrev.setOnClickListener {
            goPrevArticle()
        }

        binding.btnNext.setOnClickListener {
            goNextArticle()
        }
    }

    private fun observeViewModel() {
        viewModel.hobbyLd.observe(viewLifecycleOwner, Observer { hobby ->
            // Update UI with student details
            binding.txtJudul.setText(hobby.judul.toString() ?: "")
            binding.txtNamaPenulis.setText(hobby.username_penulis ?: "")
            binding.txtJudulArtikel.setText(hobby.artikel[currentArticleIndex].judul_artikel ?: "")
            binding.txtKonten.setText(hobby.artikel[currentArticleIndex].konten ?: "")
            val picasso = Picasso.Builder(binding.root.context)
            picasso.listener{picasso, uri, exception -> exception.printStackTrace()}
            picasso.build().load(hobby.url_gambar).into(binding.imageView)

            binding.progressBar.visibility = View.GONE
        })

//        viewModel.studentLD.observe(viewLifecycleOwner, Observer {
//            var student = it
//            val btnUpdate = view?.findViewById<Button>(R.id.btnUpdate)
//            btnUpdate?.setOnClickListener {
//                Observable.timer(5, TimeUnit.SECONDS)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe {
//                        Log.d("Messages", "five seconds")
//                        MainActivity.showNotification(
//                            student.name.toString(),
//                            "A new notification created",
//                            R.drawable.baseline_boy_24
//                        )
//                    }
//            }
//        })
    }
}