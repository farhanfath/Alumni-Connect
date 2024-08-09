package dts.pnj.farhan.utils.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dts.pnj.farhan.data.repository.DataRepository
import dts.pnj.farhan.data.database.user.User
import dts.pnj.farhan.data.response.UserPref
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DataRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _loginStatus = MutableLiveData<Result<Boolean>>()
    val loginStatus: LiveData<Result<Boolean>> get() = _loginStatus

    private val _registerStatus = MutableLiveData<Result<Boolean>>()
    val registerStatus: LiveData<Result<Boolean>> get() = _registerStatus

    fun getSession(): LiveData<UserPref> {
        return repository.getUserData().asLiveData()
    }
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun registerUser(user: User) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val existingUser = repository.getUser(user.email, user.password)
                if (existingUser != null) {
                    _registerStatus.postValue(Result.success(false))
                } else {
                    repository.insertUser(user)
                    _registerStatus.postValue(Result.success(true))
                }
            } catch (e: Exception) {
                _registerStatus.postValue(Result.failure(e))
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val user = repository.getUser(email, password)
                if (user != null) {

                    val userPref = UserPref(
                        email = user.email,
                        nim = user.nim,
                        name = user.name,
                        className = user.className
                    )
                    repository.saveSession(userPref)
                    _loginStatus.postValue(Result.success(true))
                } else {
                    _loginStatus.postValue(Result.success(false))
                }
            } catch (e: Exception) {
                _loginStatus.postValue(Result.failure(e))
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}