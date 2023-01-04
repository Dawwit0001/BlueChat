package winged.example.scan_feature.bluetooth_off

import android.Manifest
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import winged.example.core.baseFragment.BaseFragment
import winged.example.scan_feature.R
import winged.example.scan_feature.databinding.FragmentScanScreenBluetoothOffBinding
import winged.example.scan_feature.navigation.ScanActions
import javax.inject.Inject

@AndroidEntryPoint
class ScanScreenBluetoothOFF : BaseFragment() {

    @Inject
    lateinit var actions: ScanActions

    private var _binding: FragmentScanScreenBluetoothOffBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanScreenBluetoothOffBinding.inflate(inflater, container, false)
        setUpReturnButton()
        setUpRedirectionIfBluetoothIsEnabled()
        areAllNecessaryPermissionsGranted()
        return binding.root
    }

    private fun setUpReturnButton() {
        binding.returnToPreviousIB.setOnClickListener {
            navigateUp()
        }
    }

    private fun setUpRedirectionIfBluetoothIsEnabled() {
        val bluetoothManager =
            requireContext().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        if (bluetoothManager.adapter.isEnabled) {
            /* probably would be wise to add some kind of purgatory screen to make the decision to
            which of those 2 fragments (On/Off) should the user be redirected,
            but I cannot be bothered  */
            navigateTo(R.id.scanScreenBluetoothON)
        }
    }

    private fun areAllNecessaryPermissionsGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            checkIfPermissionIsGranted(Manifest.permission.BLUETOOTH_SCAN) && checkIfPermissionIsGranted(Manifest.permission.BLUETOOTH_ADVERTISE) && checkIfPermissionIsGranted(Manifest.permission.BLUETOOTH_CONNECT)
        } else { checkIfPermissionIsGranted(Manifest.permission.BLUETOOTH) && checkIfPermissionIsGranted(Manifest.permission.BLUETOOTH_ADMIN) }
    }
    private fun checkIfPermissionIsGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
