package com.example.thecat.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thecat.data.LogInInfo
import com.example.thecat.net.HomeNetworkRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val networkRepository: HomeNetworkRepository,
) : ViewModel() {
    val data = MutableLiveData<LogInInfo?>()
    val registerData = MutableLiveData<LogInInfo?>()
    val logout = MutableLiveData<Any?>()

   fun login(userName:String,pwd:String): MutableLiveData<LogInInfo?> {
        viewModelScope.launch {
            networkRepository.login(userName,pwd).let { loginInfo ->
              data.value = loginInfo
            }
        }
       return data
   }

    fun register(userName:String,pwd:String,rePwd:String): MutableLiveData<LogInInfo?> {
        viewModelScope.launch {
            networkRepository.register(userName,pwd,rePwd).let { loginInfo ->
                registerData.value = loginInfo
            }
        }
        return registerData
    }

    fun logOut(): MutableLiveData<Any?> {
        viewModelScope.launch {
            networkRepository.logout().let { loginInfo ->
                logout.value = loginInfo
            }
        }
        return logout
    }

}