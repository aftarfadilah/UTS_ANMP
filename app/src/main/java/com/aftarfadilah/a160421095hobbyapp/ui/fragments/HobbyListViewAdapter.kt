package com.aftarfadilah.a160421095hobbyapp.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.aftarfadilah.a160421095hobbyapp.R
import com.aftarfadilah.a160421095hobbyapp.databinding.FragmentHobbyDetailBinding
import com.aftarfadilah.a160421095hobbyapp.databinding.HobbyListItemBinding
import com.aftarfadilah.a160421095hobbyapp.model.Hobby
import com.squareup.picasso.Picasso

class HobbyListViewAdapter(
    val hobbyList: ArrayList<Hobby>
): RecyclerView.Adapter<HobbyListViewAdapter.HobbyViewHolder>() {
    class HobbyViewHolder(var binding: HobbyListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HobbyListViewAdapter.HobbyViewHolder {
        val binding = HobbyListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HobbyListViewAdapter.HobbyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HobbyViewHolder, position: Int) {
        holder.binding.txtJudul.text = hobbyList[position].judul
        holder.binding.txtNamaPenulis.text = "${hobbyList[position].username_penulis}"

        holder.binding.btnDetail.setOnClickListener {
            val action = HomeFragmentDirections.toDetail(hobbyList[position].id.toString() ?: "")
            Navigation.findNavController(it).navigate(action)
        }
        val picasso = Picasso.Builder(holder.binding.root.context)
        picasso.listener{picasso, uri, exception -> exception.printStackTrace()}
        picasso.build().load(hobbyList[position].url_gambar).into(holder.binding.imageView)
    }
    fun updateStudentList(newStudentList: ArrayList<Hobby>) {
        hobbyList.clear()
        hobbyList.addAll(newStudentList)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return hobbyList.size
    }
}