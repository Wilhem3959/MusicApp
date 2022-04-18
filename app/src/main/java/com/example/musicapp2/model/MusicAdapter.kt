package com.example.musicapp2.model

import android.app.AlertDialog
import android.content.DialogInterface
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp2.R
import com.squareup.picasso.Picasso

val TAG = "MusicAdapter"
class MusicAdapter (private val dataSet: List<MusicItem>):
    RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    class MusicViewHolder(private val view: View): RecyclerView.ViewHolder(view)
    {
        var mp: MediaPlayer? = null
        private val songButton: CardView = view.findViewById(R.id.cardView)
        private val Song: TextView = view.findViewById(R.id.tv_SongName)
        private val Artist: TextView = view.findViewById(R.id.tv_ArtistName)
        private val moviePrice: TextView = view.findViewById(R.id.tv_price)
        private val movieCurrency: TextView = view.findViewById(R.id.tv_currency)
        private val SongImage: ImageView= view.findViewById(R.id.iv_art)

        fun onBind(dataItem: MusicItem){
            val builder=AlertDialog.Builder(view.context)
            Song.text = dataItem.trackName
            Artist.text = dataItem.artistName
            moviePrice.text = "${dataItem.trackPrice}"
            movieCurrency.text = dataItem.currency
            Picasso.get().load(dataItem.artworkUrl100).into(SongImage)

            songButton.setOnClickListener {
                mp= MediaPlayer()
                try{
                    mp?.setDataSource(dataItem.previewUrl)
                    mp?.prepare()
                    mp?.start()
                }catch (ex: Exception){
                    Log.d(TAG, "onBind: song no detected")
                }
                builder.setTitle("Preview")
                builder.setMessage("Do you want to stop the preview?")
                builder.setPositiveButton("yes",{ dialogInterface: DialogInterface, i: Int -> mp?.stop()})
                builder.setCancelable(false)
                builder.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        return MusicViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.music_adapter,parent,false)
        )
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {

        holder.onBind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}