package com.example.room

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class DetailDataActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private var voterId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_data)

        database = AppDatabase.getInstance(this)

        val nameText = findViewById<TextView>(R.id.tv_name)
        val nikText = findViewById<TextView>(R.id.tv_nik)
        val genderText = findViewById<TextView>(R.id.tv_gender)
        val addressText = findViewById<TextView>(R.id.tv_address)
        val backButton = findViewById<Button>(R.id.btn_back)

        // Dapatkan ID Pemilih dari Intent
        voterId = intent.getIntExtra("VOTER_ID", -1)

        if (voterId != -1) {
            // Ambil data pemilih dari database
            lifecycleScope.launch {
                val voter = database.voterDao().getVoterById(voterId)
                voter?.let {
                    nameText.text = it.name
                    nikText.text = it.nik
                    genderText.text = it.gender
                    addressText.text = it.address
                }
            }
        }

        backButton.setOnClickListener {
            finish() // Kembali ke halaman sebelumnya
        }
    }
}
