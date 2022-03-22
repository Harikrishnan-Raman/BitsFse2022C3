package com.example.medicineapplication.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.medicineapplication.R
import com.example.medicineapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        var root: View = binding.root

        val buttonOrderMedicines : Button = binding.buttonOrderMedicines
        buttonOrderMedicines.setOnClickListener {
            this.findNavController().navigate(R.id.navigation_order_medicines)
        }
        val buttonBookAnAppointment : Button = binding.buttonAppointment
        buttonBookAnAppointment.setOnClickListener {
            this.findNavController().navigate(R.id.navigation_book_appointment)
        }
        val buttonBookALabTest : Button = binding.buttonLabTest
        buttonBookALabTest.setOnClickListener {
            this.findNavController().navigate(R.id.navigation_book_lab_test)
        }
        val buttonGetAlternativeProducts : Button = binding.buttonGetAlternativeProducts
        buttonGetAlternativeProducts.setOnClickListener {
            this.findNavController().navigate(R.id.navigation_get_alternative)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}