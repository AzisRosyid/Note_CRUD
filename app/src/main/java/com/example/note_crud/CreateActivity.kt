package com.example.note_crud

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.note_crud.databinding.ActivityCreateBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var binding: ActivityCreateBinding

class CreateActivity : AppCompatActivity() {

    private val api by lazy { ApiRetrofit().endPoint }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle("Create Notes")
        setupListener()
    }

    private fun setupListener(){
        binding.btnCreate.setOnClickListener{
            if(binding.etNote.toString().isNotEmpty()) {
                binding.progressBar.visibility = View.VISIBLE
                api.create( binding.etNote.text.toString() )
                    .enqueue(object : Callback<SubmitModel>{
                        override fun onResponse(
                            call: Call<SubmitModel>,
                            response: Response<SubmitModel>
                        ) {
                            if (response.isSuccessful) {
                                Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_SHORT).show()
                                finish()
                                binding.progressBar.visibility = View.GONE
                            }
                        }

                        override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                            Toast.makeText(baseContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
                            Log.e("OnFailure", t.toString())
                            binding.progressBar.visibility = View.GONE
                        }

                    })
            } else {
                Toast.makeText(baseContext, "All field must be filled", Toast.LENGTH_SHORT).show()
            }
        }
    }

}