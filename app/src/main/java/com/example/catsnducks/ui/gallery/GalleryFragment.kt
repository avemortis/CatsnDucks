package com.example.catsnducks.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catsnducks.R
import com.example.catsnducks.adapters.GalleryAdapter
import com.example.catsnducks.adapters.RecyclerViewItemStateChangeListener
import com.example.catsnducks.data.database.DatabaseRepository
import com.example.catsnducks.data.model.Gallery
import com.example.catsnducks.data.model.Picture
import com.example.catsnducks.data.model.PicturePreview
import com.example.catsnducks.databinding.FragmentGalleryBinding
import com.example.catsnducks.ui.looker.PictureLookerDialogFragment
import com.example.catsnducks.utils.LoadListener
import com.example.catsnducks.utils.QueueDownloader

class GalleryFragment : Fragment(), GalleryAdapter.onGalleryItemListener, PictureLookerDialogFragment.OnLikeClickListener, RecyclerViewItemStateChangeListener, LoadListener {
    private lateinit var galleryViewModel: GalleryViewModel

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    lateinit var galleryRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root = binding.root

        galleryRecyclerView = binding.fragmentGalleryRecyclerview
        prepareRecyclerView(galleryRecyclerView)

        return root
    }

    fun prepareRecyclerView(recyclerView: RecyclerView){
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        val gallery = DatabaseRepository.get().getPictures()

        gallery.observe(viewLifecycleOwner, {
            Gallery.galleryPictures = it.toMutableList()
            val queueDownloader = QueueDownloader(viewLifecycleOwner, Gallery.galleryPictures, this)
            queueDownloader.start()
            recyclerView.adapter = GalleryAdapter(it, this)
        })

    }

    override fun onClick(position: Int) {
        val dialog = PictureLookerDialogFragment()
        val bundle = Bundle()
        val liked = true
        val url = Gallery.galleryPictures[position].url
        bundle.putString(PictureLookerDialogFragment.URL_TAG, url)
        bundle.putBoolean(PictureLookerDialogFragment.LIKE_TAG, liked)
        bundle.putInt(PictureLookerDialogFragment.POSITION_TAG, position)

        dialog.arguments = bundle
        dialog.show(this.parentFragmentManager, PictureLookerDialogFragment.TAG)
    }

    override fun onLikeClicked() {

    }
    companion object {
        const val TAG = "Gallery"
    }

    override fun onCreate(position: Int) {
    }

    override fun onDestroy(position: Int) {
    }

    override fun onSingleLoad() {
    }

    override fun onFullLoad() {
        galleryRecyclerView.adapter!!.notifyDataSetChanged()
    }
}