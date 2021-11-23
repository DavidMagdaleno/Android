package Api

import Modelo.Perros
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    val myResponse: MutableLiveData<Perros> = MutableLiveData()//con cuidado si uno es un arraylist y otro no
    val myResponseList: MutableLiveData<List<Perros>> = MutableLiveData()

    fun getPost(message:String) {
        viewModelScope.launch {
            myResponse.value = UserNetwork.retrofit.getFotoPerro(message)
        }
    }

    fun getPosts() {
        viewModelScope.launch {
            myResponseList.value = UserNetwork.retrofit.getPerros()
        }
    }
}