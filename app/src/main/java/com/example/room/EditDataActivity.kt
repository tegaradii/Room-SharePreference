package com.example.room

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.room.Voter
import kotlinx.coroutines.launch

class EditDataActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private var voterId: Int = -1 // Default ID jika data tidak ditemukan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_data)

        database = AppDatabase.getInstance(this)

        val nameField = findViewById<EditText>(R.id.et_name)
        val nikField = findViewById<EditText>(R.id.et_nik)
        val genderGroup = findViewById<RadioGroup>(R.id.rg_gender)
        val maleButton = findViewById<RadioButton>(R.id.rb_male)
        val femaleButton = findViewById<RadioButton>(R.id.rb_female)
        val addressField = findViewById<EditText>(R.id.et_address)
        val saveButton = findViewById<Button>(R.id.btn_save)

        // Ambil ID Pemilih dari Intent
        voterId = intent.getIntExtra("VOTER_ID", -1)

        if (voterId != -1) {
            // Load data pemilih dari database
            lifecycleScope.launch {
                val voter = database.voterDao().getVoterById(voterId)
                voter?.let {
                    nameField.setText(it.name)
                    nikField.setText(it.nik)
                    addressField.setText(it.address)
                    if (it.gender == "Laki-Laki") {
                        maleButton.isChecked = true
                    } else {
                        femaleButton.isChecked = true
                    }
                }
            }
        }

        // Logika menyimpan data
        saveButton.setOnClickListener {
            val name = nameField.text.toString()
            val nik = nikField.text.toString()
            val gender = if (genderGroup.checkedRadioButtonId == R.id.rb_male) "Laki-Laki" else "Perempuan"
            val address = addressField.text.toString()

            if (voterId != -1) {
                // Update data pemilih
                lifecycleScope.launch {
                    database.voterDao().updateVoter(
                        Voter(
                            id = voterId,
                            name = name,
                            nik = nik,
                            gender = gender,
                            address = address
                        )
                    )
                    finish() // Tutup EditDataActivity
                }
            }
        }
    }
}

