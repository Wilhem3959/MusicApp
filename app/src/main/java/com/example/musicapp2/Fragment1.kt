package com.example.musicapp2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp2.model.MusicAdapter
import com.example.musicapp2.model.MusicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class Fragment1 : Fragment()
{
    lateinit var datosclassic: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_1, container, false)

        datosclassic = view?.findViewById<RecyclerView>(R.id.recyclerViewRock)!!
        swipeRefreshLayout = view?.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayoutRock)

        initRetrofitclasick()
        refreshApp()
        return view
    }

    private fun refreshApp()
    {
        swipeRefreshLayout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                initRetrofitclasick()
                swipeRefreshLayout.isRefreshing = false
            }
        })
    }

    fun initRetrofitclasick()
    {
        MusicApi.initRetrofit().findMusicByTitle("rock", "music","song", "50").enqueue(
            object : Callback<MusicResponse>
            {
                override fun onResponse(
                    call: Call<MusicResponse>,
                    response: Response<MusicResponse>
                ) {
                    if(response.isSuccessful) {
                        response.body()?.let {
                            updateUI(it)

                        } ?: errorMessage(response.message())
                    }
                }
                override fun onFailure(call: Call<MusicResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: " + t.message)
                }
            }
        )
    }
    val TAG = "MainActivity"
    private fun errorMessage(message: String)
    {
        Log.d(TAG, "errorMessage: $message")
    }

    private fun updateUI(it: MusicResponse)
    {
        datosclassic.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = MusicAdapter(it.results)
        }

        Log.d(TAG, "updateUI: ${it}")
    }
}