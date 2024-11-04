package com.farmiot.smartagriculture.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.farmiot.smartagriculture.R
import com.farmiot.smartagriculture.databinding.FragmentHomeBinding
import com.farmiot.smartagriculture.repository.MainRepository
import com.farmiot.smartagriculture.ui.viewmodel.HomeViewModel
import com.farmiot.smartagriculture.ui.viewmodel.SigninRegisterViewModel
import com.farmiot.smartagriculture.utils.SettingsUtility
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!


    val settingsUtility by inject<SettingsUtility>()
    val viewModel by inject<HomeViewModel>()
    val authViewModel by inject<SigninRegisterViewModel>()
    val mainRepository by inject<MainRepository>()
    private var loadingDialog: Dialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        viewModel = ViewModelProvider(this).get(SigninRegisterViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (settingsUtility.userId.isEmpty()) {
            findNavController().navigate(R.id.action_homeFragment_to_siginInFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun initialState() {
        if (settingsUtility.manualModeStatus == 1) {
            binding.toggleSwitchManualMode.isChecked = true
            binding.btnPumpOnOff.text = "Turn Off Pump"
            binding.btnPumpOnOff.setBackgroundColor(resources.getColor(R.color.colorChecked))
        } else {
            binding.btnPumpOnOff.text = "Turn On Pump"
            binding.btnPumpOnOff.setBackgroundColor(resources.getColor(R.color.colorUnchecked))
        }
    }
    fun changeMotorState() {
        binding.toggleSwitchManualMode.isChecked = true
        if (settingsUtility.manualModeStatus == 1) {
            settingsUtility.manualModeStatus = 2
            viewModel.updateManualModeData(2)
            binding.btnPumpOnOff.text = "Turn ON Pump"
            binding.btnPumpOnOff.setBackgroundColor(resources.getColor(R.color.colorUnchecked))
            binding.tvPumpStatusTitle.text = "Pump will be turned off soon"
        } else {
            settingsUtility.manualModeStatus = 1
            viewModel.updateManualModeData(1)
            binding.btnPumpOnOff.text = "Turn OFF Pump"
            binding.btnPumpOnOff.setBackgroundColor(resources.getColor(R.color.colorChecked))
            binding.tvPumpStatusTitle.text = "Pump will be turned on soon"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialState()
        binding.btnSignOut.setOnClickListener {
            authViewModel.logOut()
            findNavController().navigate(R.id.action_homeFragment_to_siginInFragment)
        }
        binding.toggleSwitchManualMode.setOnClickListener {
            if (binding.toggleSwitchManualMode.isChecked) {
                viewModel.updateManualModeData(2)
                binding.tvPumpStatusSubtitle.text = "Pump is operating in Manual Mode"
            } else {
                viewModel.updateManualModeData(0)
                binding.tvPumpStatusSubtitle.text = "Pump is operating in Automatic Mode"
            }
        }
        binding.btnPumpOnOff.setOnClickListener {
            changeMotorState()
        }

        viewModel.gardenInfo.observe(viewLifecycleOwner) {
            Log.d("TAG", "onViewCreated: $it")
            it?.info?.apply {

                binding.tvRoomTemp.setText("${weather?.temprature ?: 28}° C")

                binding.apply {

                    binding.progressCircularHumidity.progress =
                        weather?.humidity?.toFloat() ?: 0f
                    binding.tvRoomHumidity.text = "${weather?.humidity}%"

                    binding.progressCircularSoilMoisture.progress =
                        weather?.soilMoisture?.toFloat() ?: 0f
                    binding.tvSoilMoisture.text = "${weather?.soilMoisture}%"

                    binding.progressCircularWaterLevel.progress =
                        weather?.waterLevel?.toFloat() ?: 0f
                    binding.tvWaterLevel.text = "${weather?.waterLevel}%"


                    if (controller?.motorStatus == 1 && manual?.manualMode == 2)
                        binding.tvPumpStatusTitle.text = "Pump will be turned off soon"
                    else if (controller?.motorStatus == 0 && manual?.manualMode == 1)
                        binding.tvPumpStatusTitle.text = "Pump will be turned on soon"
                    else if (controller?.motorStatus == 1 && manual?.manualMode == 1)
                        binding.tvPumpStatusTitle.text = "Pump is running"
                    else if (controller?.motorStatus == 1 && manual?.manualMode == 0)
                        binding.tvPumpStatusTitle.text = "Pump is running"
                    else
                        binding.tvPumpStatusTitle.text = "Pump is off"

                }

                settingsUtility.motorStatus = controller?.motorStatus ?: 0

            }
        }
    }

}