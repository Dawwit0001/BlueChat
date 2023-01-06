package winged.example.scan_feature.bluetooth_off

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
            Log.i("idk1", "setUpTurnOnBluetoothButton: ${areAllNecessaryPermissionsGranted()}")
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

    private fun areAllNecessaryPermissionsGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            checkIfPermissionIsGranted(Manifest.permission.BLUETOOTH_SCAN) && checkIfPermissionIsGranted(
                Manifest.permission.BLUETOOTH_ADMIN
            ) && checkIfPermissionIsGranted(Manifest.permission.BLUETOOTH_ADVERTISE) && checkIfPermissionIsGranted(
                Manifest.permission.BLUETOOTH_CONNECT
            )
        } else {
            checkIfPermissionIsGranted(Manifest.permission.BLUETOOTH) && checkIfPermissionIsGranted(
                Manifest.permission.BLUETOOTH_ADMIN
            )
        }
    }

    private fun checkIfPermissionIsGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestMissingPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.BLUETOOTH_ADVERTISE,
                    Manifest.permission.BLUETOOTH_CONNECT
                ),
                Constants.permissionRequestCode
            )
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.BLUETOOTH
                ),
                Constants.permissionRequestCode
            )
        }
    }
}
