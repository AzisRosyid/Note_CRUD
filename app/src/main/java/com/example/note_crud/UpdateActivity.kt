package com.example.note_crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.note_crud.databinding.ActivityUpdateBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var binding: ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private val api by lazy { ApiRetrofit().endPoint }
    private val note by lazy {intent.getSerializableExtra("note") as Note}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Update Notes"
        binding.etNote.setText(note.note)
        setupListener()
    }

    private fun setupListener(){
        binding.btnUpdate.setOnClickListener{
            binding.progressBar.visibility = View.VISIBLE
            api.update( note.id, binding.etNote.text.toString())
                .enqueue(object : Callback<SubmitModel> {
                    override fun onResponse(
                        call: Call<SubmitModel>,
                        response: Response<SubmitModel>
                    ) {
                        if(response.isSuccessful){
                            Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_SHORT).show()
                            finish()
                            binding.progressBar.visibility = View.GONE
                        }
                    }

                    override fun onFailure(call: Call<SubmitModel>, t: Throwable) {

                    }
                })
        }
    }



}