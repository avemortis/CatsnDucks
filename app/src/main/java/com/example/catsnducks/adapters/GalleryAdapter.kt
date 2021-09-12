package com.example.catsnducks.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import com.example.catsnducks.R
import com.example.catsnducks.adapters.lifecycler.LifecycleRecyclerAdapter
import com.example.catsnducks.adapters.lifecycler.LifecycleViewHolder
import com.example.catsnducks.data.database.DatabaseRepository
import com.example.catsnducks.data.model.Picture

class GalleryAdapter(val pictures: List<Picture>, val listener: onGalleryItemListener): RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>(){

    class GalleryViewHolder(viewItem: View, val listener: onGalleryItemListener): RecyclerView.ViewHolder(viewItem), View.OnClickListener{
        var pic : ImageView? = viewItem.findViewById(R.id.fragment_gallery_single_item_picture)

        init {
            pic?.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onClick(adapterPosition)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_gallery_single_item, parent, false)
        return GalleryViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val bitmap = BitmapFactory.decodeByteArray(pictures[position].image, 0, pictures[position].image!!.size)

        holder.pic?.setImageBitmap(bitmap)
    }

    override fun getItemCount(): Int = pictures.size



    interface onGalleryItemListener{
        fun onClick(position: Int)
    }
}