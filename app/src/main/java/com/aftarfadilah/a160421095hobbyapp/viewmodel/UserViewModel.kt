package com.aftarfadilah.a160421095hobbyapp.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.aftarfadilah.a160421095hobbyapp.model.Hobby
import com.aftarfadilah.a160421095hobbyapp.model.User
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserViewModel(application: Application): AndroidViewModel(application) {
    val userLD = MutableLiveData<User>()
    val studentLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    init {
        queue = Volley.newRequestQueue(getApplication())
    }

    fun fetch(id: Number) {
        loadingLD.value = true
        studentLoadErrorLD.value = false

        queue?.let { queue ->
            val url = "http://10.0.2.2/api/hobbyApp/users/${id.toString()}"

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    val sType = object : TypeToken<User>() { }.type
                    val result = Gson().fromJson<User>(response, sType)
                    userLD.value = result as User?
                    loadingLD.value = false
                    Log.d("UserVolley", "Response: $response") // Log the response from the server
                    Log.d("UserVolley", "Parsed Result: $result") // Log the parsed result
                },
                { error ->
                    Log.d("UserVolley", "Error: $error") // Log any error that occurred
                    studentLoadErrorLD.value = true
                    loadingLD.value = false
                })

            stringRequest.tag = TAG
            queue?.add(stringRequest)
        }
    }

    fun edit(id: Number, username: String, namaDepan: String, namaBelakang: String) {
        loadingLD.value = true
        studentLoadErrorLD.value = false

        queue?.let { queue ->
            val url = "http://10.0.2.2/api/hobbyApp/users/edit.php"

            val stringRequest = object : StringRequest(
                Method.POST, url,
                { response ->
                    try {
                        val sType = object : TypeToken<User>() {}.type
                        val result = Gson().fromJson<User>(response, sType)
                        userLD.postValue(result)
                        Log.d("UserVolley", "Response: $response")
                        Log.d("UserVolley", "Parsed Result: $result")
                    } catch (e: Exception) {
                        Log.e("UserVolley", "Parsing error: $e")
                        studentLoadErrorLD.postValue(true)
                    }
                    loadingLD.postValue(false)
                    Toast.makeText(getApplication(), "Success Saved Profile", Toast.LENGTH_SHORT).show()
                },
                { error ->
                    Log.e("UserVolley", "Error: ${error.message}")
                    studentLoadErrorLD.postValue(true)
                    loadingLD.postValue(false)
                    Toast.makeText(getApplication(), "Failed Saving", Toast.LENGTH_SHORT).show()
                }) {
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["id"] = id.toString()
                    params["username"] = username
                    params["nama_depan"] = namaDepan
                    params["nama_belakang"] = namaBelakang
                    return params
                }
            }

            stringRequest.tag = TAG
            queue?.add(stringRequest)
        }
    }
}