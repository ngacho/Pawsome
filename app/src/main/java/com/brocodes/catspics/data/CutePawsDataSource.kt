package com.brocodes.catspics.data

import android.util.Log
import androidx.paging.PageKeyedDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CutePawsDataSource(private val pixabayMethods: PixabayMethods, private val petType : String) : PageKeyedDataSource<Int, ImageItem>() {

    private lateinit var call: Call<PixabayResponse>
    private var page = 1

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ImageItem>) {
        call = pixabayMethods.getPaws(queryValue = petType)
        call.enqueue(object : Callback<PixabayResponse>{
            override fun onFailure(call: Call<PixabayResponse>, t: Throwable) {
                Log.d("Response", "Beep boop, response found")
            }

            override fun onResponse(call: Call<PixabayResponse>, response: Response<PixabayResponse>) {
                val listing = response.body()
                val imageItems = listing?.hits
                callback.onResult(imageItems ?: listOf(), page - 1, page + 1)

            }

        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ImageItem>) {
        call = pixabayMethods.loadMorePaws(queryValue = petType, resultPage = params.key)
        call.enqueue(object : Callback<PixabayResponse>{
            override fun onFailure(call: Call<PixabayResponse>, t: Throwable) {
                Log.d("Response", "Beep boop, response found")
            }

            override fun onResponse(call: Call<PixabayResponse>, response: Response<PixabayResponse>) {
                val listing = response.body()
                val imageItems = listing?.hits
                callback.onResult(imageItems ?: listOf(), page + 1)

            }

        })

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ImageItem>) {
        call = pixabayMethods.loadMorePaws(queryValue = petType, resultPage = params.key)
        call.enqueue(object : Callback<PixabayResponse>{
            override fun onFailure(call: Call<PixabayResponse>, t: Throwable) {
                Log.d("Response", "Beep boop, response found")
            }

            override fun onResponse(call: Call<PixabayResponse>, response: Response<PixabayResponse>) {
                val listing = response.body()
                val imageItems = listing?.hits
                callback.onResult(imageItems ?: listOf(), page + 1)
            }

        })
    }
}