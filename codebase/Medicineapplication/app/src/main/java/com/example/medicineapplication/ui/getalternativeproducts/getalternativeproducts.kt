package com.example.medicineapplication.ui.getalternativeproducts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import com.example.medicineapplication.R
import com.example.medicineapplication.databinding.FragmentGetalternativeproductsBinding
import com.example.medicineapplication.ui.Bean.ProdDB
import com.example.medicineapplication.ui.DB.LoginDBAdapter
import com.example.medicineapplication.ui.api.RetrofitInstance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_getalternativeproducts.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*


class getalternativeproductsFragment : Fragment() {

    private var _binding: FragmentGetalternativeproductsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    var btn: Button? = null
    lateinit var loginDBAdapter:LoginDBAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_getalternativeproducts, container, false)
        loginDBAdapter = LoginDBAdapter(requireContext())


        btn = rootView.findViewById<View>(R.id.btResetUI) as Button
        btn!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                medicine2.setText("")
                medicine1.setText("")
                medicine_des.setText("")
                etID.setText("")
                medicine1.requestFocus()
            }
        })

        btn = rootView.findViewById<View>(R.id.btSubmit) as Button
        btn!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var check : Int = -1

                if (medicine1.text.toString().isEmpty() || medicine2.text.toString().isEmpty()) {
                    Toast.makeText(requireContext(),
                        "medicine1/medicine2 empty", Toast.LENGTH_SHORT).show()
                } else {
                    println(medicine1.text.toString())
                    println(medicine2.text.toString())
                    var product = ProdDB(medicine1 = medicine1!!.text.toString().trim(),
                        medicine2 = medicine2!!.text.toString().trim())

                    check = loginDBAdapter.addProduct(product)
                }
                if (check != -1) {
                    Toast.makeText(requireContext(), "Successfully Inserted", Toast.LENGTH_SHORT).show()
                    medicine1.setText("")
                    medicine2.setText("")
                    medicine1.requestFocus()
                }
            }
        })

        btn = rootView.findViewById<View>(R.id.btDisplay) as Button
        btn!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (etID.text.toString().isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter the product name", Toast.LENGTH_SHORT).show()
                } else {
                    medicine2.setText("")
                    medicine1.setText("")
                    medicine_des.setText("")
                    val productToDisplay: ProdDB = loginDBAdapter.fetchEquivalentProduct(etID.text.toString())
                    if (productToDisplay.id == -1) {
                        Toast.makeText(requireContext(),
                            "Details not present", Toast.LENGTH_SHORT).show()

                    } else {
                        medicine1.setText(productToDisplay.medicine1)
                        medicine2.setText(productToDisplay.medicine2)

                        CoroutineScope(Dispatchers.IO).launch{
                            try {
                                // Use launch and pass Dispatchers.IO to tell that
                                // the result of this Coroutine is expected on the IO thread.
                                val response = RetrofitInstance.simpleApiClient.getRequest((productToDisplay.id).toString())
                                withContext(Dispatchers.Main){
                                    if (response.isSuccessful && response.body()!=null){
                                        val data = response.body()
                                        medicine_des.setText(data?.name)
                                    } else {
                                        // Show API error.
                                        Toast.makeText(
                                            requireContext(),
                                            "Error Occurred(try): ${response.message()}",
                                            Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                            catch (e: Exception){
                                // Show API error. This is the error raised by the client.
                                Toast.makeText(requireContext(),
                                    "Error Occurred: ${e.message}",
                                    Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            }
        })

        btn = rootView.findViewById<View>(R.id.btDelete) as Button
        btn!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var check : Int = -1
                val displayID: String = etID.text.toString()
                if (displayID.isEmpty()) {
                    Toast.makeText(requireContext(),
                        "Please enter ID", Toast.LENGTH_SHORT).show()
                } else {
                    check = loginDBAdapter.removeProduct(displayID)
                    if (check > 0) {
                        Toast.makeText(requireContext(),
                            "Details Deleted", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(),
                            "Cannot Delete, something went wrong!!",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        return rootView
    }




}