package com.example.catsnducks.adapters

import android.graphics.BitmapFactory
import android.provider.ContactsContract
import android.util.Log
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

class GalleryAdapter(val pictures: List<Picture>, val listener: onGalleryItemListener): LifecycleRecyclerAdapter<GalleryAdapter.GalleryViewHolder>(){

    class GalleryViewHolder(viewItem: View, val listener: onGalleryItemListener): LifecycleViewHolder(viewItem), View.OnClickListener{
        var pic : ImageView? = viewItem.findViewById(R.id.fragment_gallery_single_item_picture)

        //private val observer = Observer()

        init {
            pic?.setOnClickListener(this)
            //lifecycle.addObserver(observer)
        }

        override fun onClick(v: View?) {
            listener.onClick(adapterPosition)
        }

/*        class Observer() : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun onCreate() {

            }
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {

            }
        }*/
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_gallery_single_item, parent, false)
        return GalleryViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        if (pictures[position].image!=null){
            if (pictures[position].bitmap==null){
                pictures[position].bitmap = BitmapFactory.decodeByteArray(pictures[position].image, 0, pictures[position].image!!.size)
            }
            holder.pic?.setImageBitmap(pictures[position].bitmap)
        }

    }

    override fun getItemCount(): Int = pictures.size

    override fun onViewDetachedFromWindow(holder: GalleryViewHolder) {
        super.onViewDetachedFromWindow(holder)

    }

    interface onGalleryItemListener{
        fun onClick(position: Int)
    }
}