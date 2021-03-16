package com.picsum.viewer.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.picsum.viewer.R
import com.picsum.viewer.data.models.Item
import com.picsum.viewer.databinding.FragmentItemListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemListFragment : Fragment() {

    private val viewModel: ItemListViewModel by viewModels()

    private lateinit var binding: FragmentItemListBinding

    private var columnCount: Int = 1
    private var items = ArrayList<Item>()
    private var itemListAdapter = ImageListRecyclerViewAdapter(items)

    private lateinit var itemListLayoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_item_list, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList(binding.itemList)
        viewModel.items.observe(viewLifecycleOwner, {
            it?.let {
                items.addAll(it)
                itemListAdapter.notifyDataSetChanged()
                binding.itemList.adapter = itemListAdapter
            }
        })
        viewModel.newPage.observe(viewLifecycleOwner, {
            // add lazy loaded items
            items.addAll(it)
            itemListAdapter.notifyDataSetChanged()
        })
        viewModel.error.observe(viewLifecycleOwner, { error ->
            error?.let {
                Snackbar.make(view, error, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun setupList(recyclerView: RecyclerView) {
        resources.let {
            columnCount = it.getInteger(R.integer.columns)
            itemListLayoutManager = GridLayoutManager(requireContext(), columnCount)
            recyclerView.layoutManager = itemListLayoutManager
            recyclerView.addItemDecoration(
                GridItemDecoration(
                    it.getDimensionPixelOffset(R.dimen.item_decoration),
                    it.getInteger(R.integer.columns)
                )
            )
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.swipeRefresh.isRefreshing) {
                    if (itemListLayoutManager.findLastCompletelyVisibleItemPosition() == itemListAdapter.itemCount - 1) {
                        //bottom of list!
                        viewModel.loadNewPage()
                    }
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.refreshData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getItems()
    }

}