package com.selinapn.appassignment2.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.selinapn.appassignment2.Location
import com.selinapn.appassignment2.LocationRepository

import com.selinapn.appassignment2.R


class LocationEntryFragment : Fragment() {


    private lateinit var locationRepository: LocationRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        locationRepository = LocationRepository(requireContext())

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location_entry, container, false)

        //update UI, get view references
        val zipcodeEditText: EditText = view.findViewById(R.id.zipcodeEditText)
        val button: Button = view.findViewById(R.id.button)

        button.setOnClickListener {
            val zipcode: String = zipcodeEditText.text.toString()

            if (zipcode.length != 5) {
                Toast.makeText(requireContext(),
                    R.string.Zipcode_entry_error,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                locationRepository.saveLocation(Location.Zipcode(zipcode))
                findNavController().navigateUp()
            }
        }

        return view
    }

}
