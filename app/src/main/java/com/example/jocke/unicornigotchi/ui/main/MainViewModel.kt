package com.example.jocke.unicornigotchi.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.jocke.unicornigotchi.dal.AccessUnicorn
import com.example.jocke.unicornigotchi.dto.Unicorn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainViewModel : ViewModel() {

    private var unicorns: MutableLiveData<List<Unicorn>>? = null
    private var retrofit: Retrofit? = null


    fun getUnicorns(): LiveData<List<Unicorn>> {
        if (unicorns == null) {
            unicorns = MutableLiveData()
        }
        return unicorns as MutableLiveData<List<Unicorn>>
    }

    fun fetchData() {
        getAllUnicorns()
    }


    fun initializeRetrofit(): Retrofit? {
        if (retrofit != null) {
            return retrofit
        }
        val builder = Retrofit.Builder()
                //Get from 10.0.2.2: on emulator
                //https://unicornigotchiapi20180916102845.azurewebsites.net/
                .baseUrl("https://unicornigotchiapi20180916102845.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
        retrofit = builder.build()
        return retrofit
    }

    private fun getAllUnicorns() {
        retrofit?.let {
            val client: AccessUnicorn = it.create(AccessUnicorn::class.java)
            val unicornClient = client.allUnicorns


            unicornClient.enqueue(object : Callback<List<Unicorn>> {
                override fun onFailure(call: Call<List<Unicorn>>?, t: Throwable?) {
                    Log.e("ERROR", t?.message)
                }

                override fun onResponse(call: Call<List<Unicorn>>?, response: Response<List<Unicorn>>?) {
                    unicorns?.value = response?.body()
                }
            })
        }
    }


    private var count1: MutableLiveData<Int>? = null
    private var count = 0
    //Increment test
    fun incrementCount() {
        count++
        count1?.value = count
    }

    fun getCount(): LiveData<Int> {
        if (count1 == null) {
            count1 = MutableLiveData()
        }

        return count1 as MutableLiveData<Int>
    }
}
