package com.example.catsnducks.ui.morpheus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.catsnducks.appComponent
import com.example.catsnducks.data.api.PictureService
import com.example.catsnducks.data.components.DialogFragmentBundleFactory
import com.example.catsnducks.data.model.Picture
import com.example.catsnducks.databinding.DialogfragmentPictureLookerBinding
import com.example.catsnducks.databinding.FragmentMorpheusBinding
import com.example.catsnducks.ui.looker.PictureLookerDialogFragment
import com.example.catsnducks.utils.PictureTypes
import com.example.catsnducks.utils.Resource
import com.example.catsnducks.utils.Status


class MorpheusFragment : Fragment(), View.OnClickListener {
    private lateinit var catButton: Button
    private lateinit var duckButton: Button

    private lateinit var morpheusViewModel: MorpheusViewModel
    private var _binding: FragmentMorpheusBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        morpheusViewModel = ViewModelProvider(this).get(MorpheusViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMorpheusBinding.inflate(inflater, container, false)
        val root = binding.root

        catButton = binding.fragmentMorpheusButtonCat
        catButton.setOnClickListener(this)

        duckButton = binding.fragmentMorpheusButtonDuck
        duckButton.setOnClickListener(this)

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(): MorpheusFragment {
            return MorpheusFragment()
        }
        const val TAG = "MorpheusFragment"
    }


    private fun getCatPic(pictureService: PictureService){
        observeResponse(morpheusViewModel.getPic(pictureService, PictureTypes.CATS))
    }

    private fun getDuckPic(pictureService: PictureService){
        observeResponse(morpheusViewModel.getPic(pictureService, PictureTypes.DUCKS))
    }

    private fun observeResponse(liveData: LiveData<Resource<Picture>>){
        liveData.observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        setArgsAndShowPictureLookerDialogFragment(resource.data?.url!!)
                    }
                    Status.ERROR -> {
                        Log.d(TAG, "error")
                        resource.message?.let { error -> Log.d(TAG, error)}
                    }
                    Status.LOADING -> {
                        Log.d(TAG, "loading")
                    }
                }
            }
        })
    }

    private fun setArgsAndShowPictureLookerDialogFragment(url : String){
        val bundleFactory : DialogFragmentBundleFactory = requireActivity().appComponent.getDialogFragmentBundleFactory()
        val dialog = PictureLookerDialogFragment()
        val bundle = bundleFactory.getPictureLookerBundle(null)
        bundle.putString(PictureLookerDialogFragment.URL_TAG, url)

        dialog.arguments = bundle
        dialog.show(this.parentFragmentManager, PictureLookerDialogFragment.TAG)
    }

    override fun onClick(v: View?) {
        val pictureService = requireActivity().appComponent.getPictureService()
        when(v?.id){
            catButton.id -> {
                getCatPic(pictureService)
            }
            duckButton.id -> {
                getDuckPic(pictureService)
            }
        }
    }
}