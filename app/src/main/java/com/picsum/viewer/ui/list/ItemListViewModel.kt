package com.picsum.viewer.ui.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picsum.viewer.data.models.Item
import com.picsum.viewer.data.source.repository.PicsumRepository
import com.picsum.viewer.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemListViewModel @Inject constructor(
    val repository: PicsumRepository
) : ViewModel() {

    private var page = 0

    val isLoading = MutableLiveData<Boolean>()
    var error = MutableLiveData<String>()
    val newPage = MutableLiveData<List<Item>>()
    val items = MutableLiveData<List<Item>>()

    fun refreshData() {
        viewModelScope.launch {
            repository.deleteItems()
            loadNewPage()
        }
    }

    fun getItems() {
        isLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getItems()) {
                is Result.Success -> {
                    isLoading.value = false
                    if (result.data == null || result.data.isEmpty()) {
                        Log.d(TAG, "Loaded data is null or empty!")
                        loadNewPage()
                    } else {
                        items.value = result.data
                        Log.d(TAG, "Loaded data is successful")
                        error.value = null
                    }
                }
                is Result.Error -> {
                    Log.d(TAG, "Error during data load: ${result.exception}")
                    isLoading.value = false
                    error.value = result.exception.message
                }
                is Result.Loading -> isLoading.postValue(true)
            }
        }
    }

    fun loadNewPage() {
        Log.d(TAG, "Attempt to refresh data")
        isLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getItems(++page)) {
                is Result.Success -> {
                    isLoading.value = false
                    if (result.data != null) {
                        newPage.value = result.data
                        Log.d(TAG, "Refresh of the data is successful")
                        error.value = null
                    } else {
                        Log.d(TAG, "Refreshed data is null!")
                        error.value = "No data found"
                    }
                }
                is Result.Error -> {
                    Log.d(TAG, "Error during data refresh: ${result.exception}")
                    isLoading.value = false
                    error.value = result.exception.message
                }
                is Result.Loading -> isLoading.postValue(true)
            }
        }
    }

    companion object {
        var TAG = ItemListViewModel::class.java.simpleName
    }
}