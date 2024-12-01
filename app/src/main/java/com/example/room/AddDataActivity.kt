package com.example.room

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.room.AppDatabase
import com.example.room.Voter
import kotlinx.coroutines.launch

class AddDataActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)

        database = AppDatabase.getInstance(this)

        val nameField = findViewById<EditText>(R.id.et_name)
        val nikField = findViewById<EditText>(R.id.et_nik)
        val genderGroup = findViewById<RadioGroup>(R.id.rg_gender)
        val addressField = findViewById<EditText>(R.id.et_address)
        val saveButton = findViewById<Button>(R.id.btn_save)

        saveButton.setOnClickListener {
            val name = nameField.text.toString()
            val nik = nikField.text.toString()
            val gender = if (genderGroup.checkedRadioButtonId == R.id.rb_male) "Laki-Laki" else "Perempuan"
            val address = addressField.text.toString()

            // Validasi input
            if (name.isNotBlank() && nik.isNotBlank() && address.isNotBlank()) {
                val voter = Voter(
                    name = name,
                    nik = nik,
                    gender = gender,
                    address = address
                )

                lifecycleScope.launch {
                    database.voterDao().insertVoter(voter) // Masukkan data ke database
                    Toast.makeText(this@AddDataActivity, "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                    finish() // Kembali ke HomeActivity
                }
            } else {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
