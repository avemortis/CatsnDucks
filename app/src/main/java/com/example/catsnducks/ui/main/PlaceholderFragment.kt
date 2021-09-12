package com.example.catsnducks.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.catsnducks.appComponent
import com.example.catsnducks.data.api.PictureService
import com.example.catsnducks.data.components.DaggerPictureServiceComponent
import com.example.catsnducks.utils.Status
import com.example.catsnducks.databinding.FragmentMainBinding
import com.example.catsnducks.ui.looker.PictureLookerDialogFragment

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
        //getPic()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root

        val textView: TextView = binding.sectionLabel
        pageViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    fun getPic(){
        val pictureService = requireActivity().appComponent.getPictureService()
        pageViewModel.getPic(pictureService).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        setArgsAndShowPictureLookerDialogFragment(resource.data?.url!!)
                    }
                    Status.ERROR -> {
                        Log.d(TAG, "error")
                        resource.message?.let { error -> Log.d(TAG, error)}
                        getPic()
                    }
                    Status.LOADING -> {
                        Log.d(TAG, "loading")
                    }
                }
            }
        })
    }

    fun setArgsAndShowPictureLookerDialogFragment(url : String){
        val dialog : PictureLookerDialogFragment = PictureLookerDialogFragment()
        val bundle = Bundle()
        bundle.putString(PictureLookerDialogFragment.TAG, url)

        dialog.arguments = bundle
        dialog.show(this.parentFragmentManager, PictureLookerDialogFragment.TAG)
    }

    companion object {
        const val TAG = "Fragment"
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}