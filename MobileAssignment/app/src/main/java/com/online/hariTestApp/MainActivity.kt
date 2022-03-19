package com.pgpfse.hariTestApp

import myTestApp.R
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pgpfse.hariTestApp.Bean.ProdDB
import com.pgpfse.hariTestApp.DB.LoginDBAdapter

import com.pgpfse.hariTestApp.api.RetrofitInstance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val activity = this@MainActivity
    private lateinit var loginDBAdapter:LoginDBAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btSubmit!!.setOnClickListener(this)
        btDisplay!!.setOnClickListener(this)
        btDelete!!.setOnClickListener(this)
        btResetUI.setOnClickListener(this)
        loginDBAdapter = LoginDBAdapter(activity)
    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btSubmit -> addNewProductMapper()
            R.id.btDisplay -> displayEquivalentProduct()
            R.id.btDelete -> deleteProduct()
            R.id.btResetUI -> resetUI()
        }
    }
    private fun addNewProductMapper() {
        var check : Int = -1

        if (medicine1.text.toString().isEmpty() || medicine2.text.toString().isEmpty()) {
            Toast.makeText(applicationContext,
                "medicine1/medicine2 empty", Toast.LENGTH_SHORT).show()
        } else {
            println(medicine1.text.toString())
            println(medicine2.text.toString())
            var product = ProdDB(medicine1 = medicine1!!.text.toString().trim(),
                medicine2 = medicine2!!.text.toString().trim())

            check = loginDBAdapter.addProduct(product)
        }
        if (check != -1) {
            Toast.makeText(applicationContext, "Successfully Inserted", Toast.LENGTH_SHORT).show()
            medicine1.setText("")
            medicine2.setText("")
            medicine1.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(medicine1, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun displayEquivalentProduct() {

        if (etID.text.toString().isEmpty()) {
            Toast.makeText(applicationContext, "Please enter the product name", Toast.LENGTH_SHORT).show()
        } else {
            medicine2.setText("")
            medicine1.setText("")
            medicine_des.setText("")
            val productToDisplay: ProdDB = loginDBAdapter.fetchEquivalentProduct(etID.text.toString())
            if (productToDisplay.id == -1) {
                Toast.makeText(applicationContext,
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
                                    this@MainActivity,
                                    "Error Occurred(try): ${response.message()}",
                                    Toast.LENGTH_LONG).show()
                                Log.d("API",response.message())

                            }
                        }
                    }catch (e: Exception){
                        // Show API error. This is the error raised by the client.
                        Toast.makeText(this@MainActivity,
                            "Error Occurred: ${e.message}",
                            Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
    }


    private fun deleteProduct() {
        var check : Int = -1
        val displayID: String = etID.text.toString()
        if (displayID.isEmpty()) {
            Toast.makeText(applicationContext,
                "Please enter ID", Toast.LENGTH_SHORT).show()
        } else {
            check = loginDBAdapter.removeProduct(displayID)
            if (check > 0) {
                Toast.makeText(applicationContext,
                    "Details Deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext,
                    "Cannot Delete, something went wrong!!",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun resetUI() {
        medicine2.setText("")
        medicine1.setText("")
        medicine_des.setText("")
        etID.setText("")
        etID.hint = "ID"
        medicine1.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(medicine1, InputMethodManager.SHOW_IMPLICIT)
    }

}
