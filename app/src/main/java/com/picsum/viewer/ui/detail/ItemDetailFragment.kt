package com.picsum.viewer.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.google.android.material.transition.MaterialContainerTransform
import com.picsum.viewer.R
import com.picsum.viewer.databinding.FragmentItemDetailBinding
import com.picsum.viewer.util.ImageUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemDetailFragment : Fragment() {

    private val viewModel: ItemDetailViewModel by viewModels()

    private lateinit var binding: FragmentItemDetailBinding

    private var itemId = 0
    private var isBlurred = false
    private var isGrayScaled = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_item_detail, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            // The drawing view is the id of the view above which the container transform
            // will play in z-space.
            drawingViewId = R.id.nav_host_fragment
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
            // Set the color of the scrim to transparent as we also want to animate the
            // list fragment out of view
            scrimColor = Color.TRANSPARENT
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_BLURRED, isBlurred)
        outState.putBoolean(STATE_GRAYSCALED, isGrayScaled)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isGrayScaled = savedInstanceState?.getBoolean(STATE_GRAYSCALED) == true
        isBlurred = savedInstanceState?.getBoolean(STATE_BLURRED) == true
        setupBackNavigation()

        val safeArgs: ItemDetailFragmentArgs by navArgs()
        itemId = safeArgs.id
        viewModel.getItemInfo(itemId)
        loadImage()

        viewModel.itemInfo.observe(viewLifecycleOwner, Observer { imageInfo ->
            binding.data = imageInfo
        })

        setupButtons()
    }

    private fun setupBackNavigation() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding.detailToolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            NavigationUI.setupActionBarWithNavController(
                this,
                NavHostFragment.findNavController(this@ItemDetailFragment)
            )
        }
    }

    fun setupButtons() {
        binding.reset.setOnClickListener {
            isBlurred = false
            isGrayScaled = false
            loadImage()
        }
        binding.blur.setOnClickListener {
            isBlurred = isBlurred.not()
            loadImage()
        }
        binding.gray.setOnClickListener {
            isGrayScaled = isGrayScaled.not()
            loadImage()
        }
    }

    private fun loadImage() {
        ImageUtils.loadImage(binding.picture, itemId, isBlurred, isGrayScaled)

        binding.blur.setImageResource(if (isBlurred) R.drawable.ic_blur_off else R.drawable.ic_blur_on)
        binding.gray.setImageResource(if (isGrayScaled) R.drawable.ic_grayscale_off else R.drawable.ic_grayscale)
    }

    companion object {
        const val STATE_BLURRED = "blurred"
        const val STATE_GRAYSCALED = "grayscaled"
    }
}