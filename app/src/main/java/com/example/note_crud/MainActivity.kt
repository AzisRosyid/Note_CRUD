package com.example.note_crud

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.note_crud.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val api by lazy { ApiRetrofit().endPoint}
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle("Main Activity")
        setupList()
        setupListener()
    }

    override fun onStart() {
        super.onStart()
        getNote()
    }

    val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        deleteNote = false;
    }

    var deleteNote: Boolean = false;

    fun basicAlert(note:Note){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmation?")
        builder.setMessage("Are you sure delete this data")
        builder.setPositiveButton("YES"){dialog, which ->
                binding.progressBar.visibility = View.VISIBLE
                api.delete(note.id)
                    .enqueue(object :  Callback<SubmitModel>{
                        override fun onResponse(
                            call: Call<SubmitModel>,
                            response: Response<SubmitModel>
                        ) {
                            if(response.isSuccessful){
                                Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_SHORT).show()
                                onStart()
                                binding.progressBar.visibility = View.GONE
                            }
                        }

                        override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                            Toast.makeText(baseContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
                            Log.e("OnFailure", t.toString())
                            binding.progressBar.visibility = View.GONE
                        }
                    })

        }
        builder.setNegativeButton("NO", negativeButtonClick)
        builder.show()
    }


    private fun setupList(){
        noteAdapter = NoteAdapter(arrayListOf(), object: NoteAdapter.onAdapterListener {
            override fun onClick(note: Note) {
                startActivity(Intent(baseContext, UpdateActivity::class.java).putExtra("note", note))
            }
        }, object : NoteAdapter.onDeleteListener {
            override fun onClick(note: Note) {
                basicAlert(note)
            }
        })
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = noteAdapter
        }
    }


    private fun setupListener(){
        binding.btnCreate.setOnClickListener{
            startActivity(Intent(this, CreateActivity::class.java))
        }
    }

     private fun getNote(){
         binding.progressBar.visibility = View.VISIBLE
         api.data().enqueue(object: Callback<MainModel>{
             override fun onResponse(call: Call<MainModel>, response: Response<MainModel>) {
                 noteAdapter.setData(response.body()!!.notes)
                 binding.progressBar.visibility = View.GONE
             }

             override fun onFailure(call: Call<MainModel>, t: Throwable) {
                 Toast.makeText(baseContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
                 Log.e("OnFailure", t.toString())
                 binding.progressBar.visibility = View.GONE
             }

         })
     }

}