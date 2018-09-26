package com.example.jocke.unicornigotchi.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.jocke.unicornigotchi.dal.AccessUnicorn
import com.example.jocke.unicornigotchi.dto.NeedsSum
import com.example.jocke.unicornigotchi.dto.Unicorn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainViewModel : ViewModel() {

    private var unicorn: MutableLiveData<Unicorn>? = null
    private var needsSum: MutableLiveData<NeedsSum>? = null
    private var retrofit: Retrofit? = null


    fun getUnicornLiveData(): LiveData<Unicorn> {
        if (unicorn == null) {
            unicorn = MutableLiveData()
        }
        return unicorn as MutableLiveData<Unicorn>
    }

    fun getPositiveNeedsLiveData(): LiveData<NeedsSum> {
        if (needsSum == null) {
            needsSum = MutableLiveData()
        }
        return needsSum as MutableLiveData<NeedsSum>
    }

    fun fetchUnicorn(id: Int) {
        getUnicorn(id)
    }

    fun fetchSumOfPositiveNeeds() {
        getPositiveNeeds()
    }


    fun initializeRetrofit(): Retrofit? {
        if (retrofit != null) {
            return retrofit
        }
        val builder = Retrofit.Builder()
                //Get from 10.0.2.2: on emulator
                //https://unicornigotchiapi20180916102845.azurewebsites.net/
                .baseUrl("http://10.0.2.2:53836/")
                .addConverterFactory(GsonConverterFactory.create())
        retrofit = builder.build()
        return retrofit
    }

    private fun getUnicorn(id: Int) {
        retrofit?.let {
            val client: AccessUnicorn = it.create(AccessUnicorn::class.java)
            val unicornClient = client.getUnicorn(id)


            unicornClient.enqueue(object : Callback<Unicorn> {
                override fun onFailure(call: Call<Unicorn>?, t: Throwable?) {
                    Log.e("ERROR", t?.message)
                }

                override fun onResponse(call: Call<Unicorn>?, response: Response<Unicorn>?) {
                    unicorn?.value = response?.body()
                }
            })
        }
    }

    private fun getPositiveNeeds() {
        retrofit?.let {
            val client: AccessUnicorn = it.create(AccessUnicorn::class.java)
            val unicornClient = client.unicornNeedsSum


            unicornClient.enqueue(object : Callback<NeedsSum> {
                override fun onFailure(call: Call<NeedsSum>?, t: Throwable?) {
                    Log.e("ERROR", t?.message)
                }

                override fun onResponse(call: Call<NeedsSum>?, response: Response<NeedsSum>?) {
                    needsSum?.value = response?.body()
                }
            })
        }
    }
}
