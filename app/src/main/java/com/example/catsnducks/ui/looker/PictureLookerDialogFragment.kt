package com.example.catsnducks.ui.looker

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.catsnducks.R
import com.example.catsnducks.data.database.DatabaseRepository
import com.example.catsnducks.data.model.Gallery
import com.example.catsnducks.data.model.Picture
import com.example.catsnducks.data.model.PicturePreview
import com.example.catsnducks.databinding.DialogfragmentPictureLookerBinding
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream


class PictureLookerDialogFragment() : DialogFragment(), View.OnClickListener {
    private lateinit var likeButton: ImageView
    private lateinit var picture: ImageView

    private var _binding: DialogfragmentPictureLookerBinding? = null
    private val binding get() = _binding!!

    private var touched = false

    private lateinit var pictureLookerViewModel: PictureLookerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pictureLookerViewModel = ViewModelProvider(this).get(PictureLookerViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogfragmentPictureLookerBinding.inflate(inflater, container, false)
        val root = binding.root

        picture = binding.dialogFragmentPictureLookerPicture
        picture.setOnClickListener(this)

        likeButton = binding.dialogFragmentPictureLookerLikeButton
        likeButton.setOnClickListener(this)

        if (pictureLookerViewModel.url.isEmpty()) pictureLookerViewModel.url = arguments?.getString(URL_TAG)!!
        pictureLookerViewModel.position = arguments?.getInt(POSITION_TAG)!!
        pictureLookerViewModel.liked = arguments?.getBoolean(LIKE_TAG)

        loadPicture()

        return root
    }

    private fun setLike(like : Boolean?){
        if (like == true){
            val icon : Drawable? = ContextCompat.getDrawable(requireContext(), R.drawable.ic_heart_active)
            likeButton.setImageDrawable(icon)
        }
    }

    private fun changeLike(){
        pictureLookerViewModel.liked = (!pictureLookerViewModel.liked!!)
        if (pictureLookerViewModel.liked == true) {
            deleteFromDatabase(pictureLookerViewModel.position!!)
            this.dismiss()
        }
    }

    private fun loadPicture(){
        val liked : Boolean? = arguments?.getBoolean(LIKE_TAG)
        setLike(liked)
        if (liked == true){
            val pos : Int = arguments?.getInt(POSITION_TAG)!!
            picture.setImageBitmap(Gallery.galleryPictures[pos].bitmap)
        }
        else {
            Picasso.get().load(pictureLookerViewModel.url)
                .resize(600, 600)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE).into(picture)
        }
    }

    private fun saveToDataBase(){
        val bitmap = (picture.getDrawable() as BitmapDrawable).getBitmap()
        val stream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

        val image = stream.toByteArray()

        val repository = DatabaseRepository.get()
        val pic = Picture()
        pic.url = pictureLookerViewModel.url
        repository.addPicture(pic)

        val preview = PicturePreview()
        preview.image = image
        repository.addPreview(preview)
    }

    private fun deleteFromDatabase(position : Int){
        val repository = DatabaseRepository.get()
        repository.deletePictureById(Gallery.galleryPictures[position].id!!)
        repository.deletePreviewById(Gallery.galleryPictures[position].id!!)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            picture.id -> {
                if (!touched) touched = true
                else {
                    saveToDataBase()
                    Log.d(TAG, "saved")
                }
            }
            likeButton.id -> {
                changeLike()
            }
        }
    }

    companion object {
        const val TAG = "PictureLooker"

        const val LIKE_TAG = "LikeTag"
        const val URL_TAG = "UrlTag"
        const val POSITION_TAG = "PositionTag"
    }

    interface OnLikeClickListener{
        fun onLikeClicked()
    }
}