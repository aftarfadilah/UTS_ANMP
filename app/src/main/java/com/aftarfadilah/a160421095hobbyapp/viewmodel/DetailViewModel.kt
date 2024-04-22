package com.aftarfadilah.a160421095hobbyapp.viewmodel

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.aftarfadilah.a160421095hobbyapp.model.Hobby
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetailViewModel(application: Application): AndroidViewModel(application) {
    val loadingLD = MutableLiveData<Boolean>()
    val studentLoadErrorLD = MutableLiveData<Boolean>()
    val hobbyLd = MutableLiveData<Hobby>()
    private var queue: RequestQueue? = null


    init {
        queue = Volley.newRequestQueue(getApplication())
    }




    fun fetch(id: String) {

        queue?.let { queue ->
            val url = "http://10.0.2.2/api/hobbyApp/hobbies/${id}"

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    val sType = object : TypeToken<Hobby>() {}.type
                    val result = Gson().fromJson<Hobby>(response, sType)
                    hobbyLd.value = result as Hobby?
                    loadingLD.value = false
                    Log.d("HobbyDetail", "Response: $response") // Log the response from the server
                    Log.d("HobbyDetail", "Parsed Result: $result") // Log the parsed result
                },
                { error ->
                    Log.d("HobbyDetail", "Error: $error") // Log any error that occurred
                    studentLoadErrorLD.value = true
                    loadingLD.value = false
                })

            stringRequest.tag = ContentValues.TAG
            queue?.add(stringRequest)
        }
    }
}