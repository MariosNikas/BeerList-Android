package com.testproject.beerlist4.files

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.testproject.beerlist4.R
import com.testproject.beerlist4.ui.MainAdapter
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.beerList.observe(this) { allbeers ->
            val adapter = MainAdapter(allbeers)
            val recyclerView = findViewById<RecyclerView>(R.id.beersRv)
            recyclerView?.layoutManager = LinearLayoutManager(this)
            recyclerView?.adapter = adapter
        }


    }
}