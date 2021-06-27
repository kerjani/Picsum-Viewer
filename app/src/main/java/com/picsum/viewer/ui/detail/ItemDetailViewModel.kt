package com.picsum.viewer.ui.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picsum.viewer.data.models.ImageInfo
import com.picsum.viewer.data.source.repository.PicsumRepository
import com.picsum.viewer.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    val repository: PicsumRepository
) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    var error = MutableLiveData<String>()
    val itemInfo = MutableLiveData<ImageInfo>()

    fun getItemInfo(id: Int) {
        Log.d(TAG, "Attempt to refresh data")
        isLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getImageInfo(id)) {
                is Result.Success -> {
                    isLoading.value = false
                    itemInfo.value = result.data
                    Log.d(TAG, "Refresh of the data is successful")
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
        var TAG = ItemDetailViewModel::class.java.simpleName
    }
}
