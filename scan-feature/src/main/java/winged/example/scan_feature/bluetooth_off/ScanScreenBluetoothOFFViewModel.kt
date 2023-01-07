package winged.example.scan_feature.bluetooth_off

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScanScreenBluetoothOFFViewModel: ViewModel() {
    private val _eventLiveData = MutableLiveData<Boolean>()
    val eventLiveData: LiveData<Boolean> = _eventLiveData

    fun reactOnPermissionDecision(wasEnabledByUser: Boolean) {
        _eventLiveData.postValue(wasEnabledByUser)
    }
}