package btsdigital.kz.ui.login

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import btsdigital.kz.R
import btsdigital.kz.databinding.FragmentImageBinding
import com.squareup.picasso.Picasso

const val IMAGE_URL_EXTRA = "ImageUrl"
const val IMAGE_TITLE_EXTRA = "ImageTitle"

class ImageFragment : Fragment() {

    private lateinit var binding: FragmentImageBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(false)

        arguments?.let {
            binding.toolbarImage.title = it.getString(IMAGE_TITLE_EXTRA)

            Picasso.get()
                    .load(it.getString(IMAGE_URL_EXTRA))
                    .fit()
                    .centerInside()
                    .into(binding.imageView)
        }
    }
}
