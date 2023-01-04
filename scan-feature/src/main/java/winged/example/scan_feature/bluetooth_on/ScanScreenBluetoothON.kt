package winged.example.scan_feature.bluetooth_on

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import winged.example.core.baseFragment.BaseFragment
import winged.example.scan_feature.databinding.FragmentScanScreenBluetoothOnBinding
import winged.example.scan_feature.navigation.ScanActions
import javax.inject.Inject

class ScanScreenBluetoothON : BaseFragment() {

    @Inject
    lateinit var actions: ScanActions

    private var _binding: FragmentScanScreenBluetoothOnBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScanScreenBluetoothOnBinding.inflate(inflater, container, false)
        setUpReturnButton()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun setUpReturnButton() {
        binding.returnToPreviousIB.setOnClickListener {
            actions.navigateToConversationList()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
