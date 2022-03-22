package com.example.medicineapplication.ui.underconstruction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.medicineapplication.databinding.FragmentUnderconstructionBinding

class UnderConstructionFragment : Fragment() {

    private var _binding: FragmentUnderconstructionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val underConstructionViewModel =
            ViewModelProvider(this).get(UnderConstructionModel::class.java)

        _binding = FragmentUnderconstructionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textUnderConstruction
        underConstructionViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}