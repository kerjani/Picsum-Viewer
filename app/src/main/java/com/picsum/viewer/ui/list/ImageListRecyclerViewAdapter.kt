package com.picsum.viewer.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.picsum.viewer.BR
import com.picsum.viewer.R
import com.picsum.viewer.data.models.Item
import com.picsum.viewer.databinding.ItemListContentBinding
import com.picsum.viewer.util.ImageUtils

class ImageListRecyclerViewAdapter(
    private val values: List<Item>
) : RecyclerView.Adapter<ImageListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(val binding: ItemListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            binding.setVariable(BR.item, item)

            ImageUtils.loadImage(binding.picture, item.id)
            binding.root.setOnClickListener {
                val bundle = bundleOf("id" to item.id)
                val extras = FragmentNavigatorExtras(binding.root to "shared_element_container")
                binding.root.findNavController()
                    .navigate(R.id.itemDetailFragment, bundle, null, extras)
            }
            binding.executePendingBindings()
        }
    }
}