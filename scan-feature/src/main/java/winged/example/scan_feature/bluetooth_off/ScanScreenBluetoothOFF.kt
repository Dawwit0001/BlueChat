package winged.example.scan_feature.bluetooth_off

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import winged.example.core.baseFragment.BaseFragment
import winged.example.core_data.utils.Constants
import winged.example.scan_feature.R
import winged.example.scan_feature.databinding.FragmentScanScreenBluetoothOffBinding
import winged.example.scan_feature.navigation.ScanActions
import javax.inject.Inject

@AndroidEntryPoint
class ScanScreenBluetoothOFF :
    BaseFragment<FragmentScanScreenBluetoothOffBinding>(FragmentScanScreenBluetoothOffBinding::inflate) {

    @Inject
    lateinit var actions: ScanActions

    private lateinit var startActivityLauncher: ActivityResultLauncher<Intent>

    private val viewModel = ViewModelProvider(this)[ScanScreenBluetoothOFFViewModel::class.java]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivityLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {}
        redirectIfBluetoothIsEnabledAndAllPermissionsAreGranted()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpReturnButton()
        setUpTurnOnBluetoothButton()
    }

    private fun setUpReturnButton() {
        binding.returnToPreviousIB.setOnClickListener {
            navigateUp()
        }
    }

    private fun setUpTurnOnBluetoothButton() {
        binding.turnOnBluetoothBTN.setOnClickListener {
            redirectIfBluetoothIsEnabledAndAllPermissionsAreGranted()
        }
    }

    private fun redirectIfBluetoothIsEnabledAndAllPermissionsAreGranted() {
        val bluetoothManager =
            requireContext().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter = bluetoothManager.adapter
        if (bluetoothAdapter.isEnabled) {
            if (areAllNecessaryPermissionsGranted()) {
                // if bluetooth is enabled && all permissions we need are set, we can navigate
                // to the scanning screen
                navigateTo(R.id.scanScreenBluetoothON)
            } else {
                // if some permissions are needed, request them
                requestMissingPermissions()
            }
        } else {
            // if bluetooth is not enabled, ask to enable it
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityLauncher.launch(enableBluetoothIntent)
        }
    }

    // still needs fixing!
    private fun areAllNecessaryPermissionsGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val bluetoothScanPermissionEnabled = checkIfPermissionIsGranted(
                Manifest.permission.BLUETOOTH_SCAN
            )
            val bluetoothAdminPermissionEnabled = checkIfPermissionIsGranted(
                Manifest.permission.BLUETOOTH_ADMIN
            )
            val bluetoothAdvPermissionEnabled = checkIfPermissionIsGranted(
                Manifest.permission.BLUETOOTH_ADVERTISE
            )
            val bluetoothConnectPermissionEnabled = checkIfPermissionIsGranted(
                Manifest.permission.BLUETOOTH_CONNECT
            )
            return bluetoothScanPermissionEnabled && bluetoothAdminPermissionEnabled && bluetoothAdvPermissionEnabled && bluetoothConnectPermissionEnabled
        } else {
            val bluetoothPermissionEnabled = checkIfPermissionIsGranted(
                Manifest.permission.BLUETOOTH
            )
            val bluetoothAdminPermissionEnabled = checkIfPermissionIsGranted(
                Manifest.permission.BLUETOOTH_ADMIN
            )
            return bluetoothPermissionEnabled && bluetoothAdminPermissionEnabled
        }
    }

    /** if permission is granted, returns true, if not, returns false */
    private fun checkIfPermissionIsGranted(permission: String): Boolean {
        return requireActivity().checkSelfPermission(
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    // still needs fixing!
    private fun requestMissingPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requireActivity().requestPermissions(
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.BLUETOOTH_ADVERTISE,
                    Manifest.permission.BLUETOOTH_CONNECT
                ),
                Constants.permissionRequestCode
            )
        } else {
            requireActivity().requestPermissions(
                arrayOf(
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.BLUETOOTH
                ),
                Constants.permissionRequestCode
            )
        }
    }
}
