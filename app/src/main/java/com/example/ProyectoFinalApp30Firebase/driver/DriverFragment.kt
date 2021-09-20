package com.example.ProyectoFinalApp30Firebase.driver

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ProyectoFinalApp30Firebase.driver.options.DriverMapActivity
import com.example.ProyectoFinalApp30Firebase.login.Login.*
import com.example.buttonnavigation.R
import com.example.buttonnavigation.databinding.DriverFragmentBinding


class DriverFragment : Fragment() {

    private lateinit var binding: DriverFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DriverFragmentBinding.inflate(inflater, container, false)

        binding.textView7.setText(currentDriverName)
        binding.textView20.setText("CC: "+ currentDriverCC)
        binding.textView18.setText("Viajes reaizados: " + currentDriverTravelsMade)
        binding.textView16.setText("Vehiculo: "+ currentDriverCar)
        binding.textView17.setText("Placa: " + currentDriverCarPlate)
        binding.button6.setOnClickListener(){
            activity?.let{
                val intent = Intent (it, DriverMapActivity::class.java)
                it.startActivity(intent)
            }        }
        binding.button7.setOnClickListener(){
            findNavController().navigate(R.id.action_driverFragment_to_commentsFragment)
        }

        binding.button8.setOnClickListener(){
            activity?.let{
                val intent = Intent (it, LoginActivity::class.java)
                it.startActivity(intent)
            }
        }
        return binding.root
    }


    }