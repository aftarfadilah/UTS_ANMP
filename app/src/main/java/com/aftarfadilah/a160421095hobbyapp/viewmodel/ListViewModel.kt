package com.aftarfadilah.week4.viewmodel

import android.app.Application
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

class ListViewModel(application: Application): AndroidViewModel(application) {
    val hobbiesLD = MutableLiveData<ArrayList<Hobby>>()
    val hobbyLoadLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    init {
        queue = Volley.newRequestQueue(getApplication())
    }

    fun refresh() {
        loadingLD.value = true
        hobbyLoadLD.value = false

        queue?.let { queue ->
            val url = "http://10.0.2.2/api/hobbyApp/hobbies/"

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    val sType = object : TypeToken<List<Hobby>>() { }.type
                    val result = Gson().fromJson<List<Hobby>>(response, sType)
                    hobbiesLD.value = result as ArrayList<Hobby>?
                    loadingLD.value = false
                    Log.d("showvoley", "Response: $response") // Log the response from the server
                    Log.d("showvoley", "Parsed Result: $result") // Log the parsed result
                },
                { error ->
                    Log.d("showvoley", "Error: $error") // Log any error that occurred
                    hobbyLoadLD.value = true
                    loadingLD.value = false
                })

            stringRequest.tag = TAG
            queue?.add(stringRequest)
        }
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}