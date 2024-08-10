package dts.pnj.farhan.utils.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dts.pnj.farhan.data.database.alumni.Alumni
import dts.pnj.farhan.data.repository.DataRepository
import kotlinx.coroutines.launch

class AlumniViewModel(private val repository: DataRepository) : ViewModel() {

    private val _alumniList = MutableLiveData<List<Alumni>>()
    val alumniList: LiveData<List<Alumni>> get() = _alumniList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _addAlumniStatus = MutableLiveData<Result<Boolean>>()
    val addAlumniStatus: LiveData<Result<Boolean>> get() = _addAlumniStatus

    fun loadAllAlumni() {
        viewModelScope.launch {
            val alumni = repository.getAllAlumni()
            _alumniList.postValue(alumni)
        }
    }

    fun addAlumni(alumni: Alumni) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val existAlumni = repository.checkAlumni(alumni.nim)
                if (existAlumni != null) {
                    _addAlumniStatus.postValue(Result.success(false))
                } else {
                    repository.insertAlumni(alumni)
                    _addAlumniStatus.postValue(Result.success(true))
                }
            } catch (e: Exception) {
                _addAlumniStatus.postValue(Result.failure(e))
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun removeAlumni(alumni: Alumni) {
        viewModelScope.launch {
            repository.deleteAlumni(alumni)
        }
    }

    fun updateAlumni(alumni: Alumni) {
        viewModelScope.launch {
            repository.updateAlumni(alumni)
        }
    }
}