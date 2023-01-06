package winged.example.scan_feature.bluetooth_on

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import winged.example.core.baseFragment.BaseFragment
import winged.example.scan_feature.databinding.FragmentScanScreenBluetoothOnBinding
import winged.example.scan_feature.navigation.ScanActions
import javax.inject.Inject

@AndroidEntryPoint
class ScanScreenBluetoothON : BaseFragment<FragmentScanScreenBluetoothOnBinding>(FragmentScanScreenBluetoothOnBinding::inflate) {

    @Inject
    lateinit var actions: ScanActions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpReturnButton()
    }

    private fun setUpReturnButton() {
        binding.returnToPreviousIB.setOnClickListener {
            actions.navigateToConversationList()
        }
    }
}
