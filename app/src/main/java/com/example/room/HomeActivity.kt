package com.example.room

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.room.LoginActivity
import com.example.room.VoterAdapter
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var database: AppDatabase
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Inisialisasi database dan shared preferences
        database = AppDatabase.getInstance(this)
        sharedPreferences = getSharedPreferences("USER_PREF", MODE_PRIVATE)

        // Inisialisasi tombol dan RecyclerView
        val addButton = findViewById<Button>(R.id.btn_add_data)
        val logoutButton = findViewById<Button>(R.id.btn_logout)
        recyclerView = findViewById(R.id.recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // Muat data ke RecyclerView
        loadData()

        // Logika tombol tambah data
        addButton.setOnClickListener {
            startActivity(Intent(this, AddDataActivity::class.java))
        }

        // Logika tombol logout
        logoutButton.setOnClickListener {
            // Hapus status login dari SharedPreferences
            sharedPreferences.edit().putBoolean("IS_LOGGED_IN", false).apply()

            // Arahkan pengguna ke LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Tutup HomeActivity
        }
    }

    override fun onResume() {
        super.onResume()
        loadData() // Refresh data ketika kembali ke HomeActivity
    }

    private fun loadData() {
        lifecycleScope.launch {
            // Ambil data dari database
            val voters = database.voterDao().getAllVoters()

            // Pasang adapter ke RecyclerView
            recyclerView.adapter = VoterAdapter(
                voters,
                onEditClick = { voter ->
                    val intent = Intent(this@HomeActivity, EditDataActivity::class.java)
                    intent.putExtra("VOTER_ID", voter.id)
                    startActivity(intent)
                },
                onDeleteClick = { voter ->
                    lifecycleScope.launch {
                        database.voterDao().deleteVoter(voter)
                        loadData() // Refresh data setelah delete
                    }
                },
                onDetailClick = { voter ->
                    val intent = Intent(this@HomeActivity, DetailDataActivity::class.java)
                    intent.putExtra("VOTER_ID", voter.id)
                    startActivity(intent)
                }
            )
        }
    }
}
