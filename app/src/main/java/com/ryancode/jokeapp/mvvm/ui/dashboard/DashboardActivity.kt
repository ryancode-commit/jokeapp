package com.ryancode.jokeapp.mvvm.ui.dashboard

import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryancode.jokeapp.databinding.JokeCategoryBinding
import com.ryancode.jokeapp.mvvm.data.model.joke.CategoryAndAlias
import com.ryancode.jokeapp.mvvm.data.model.joke.mapCategoriesWithAliases
import com.ryancode.jokeapp.mvvm.ui.dashboard.adapter.ParentViewAdapter
import com.ryancode.jokeapp.mvvm.ui.dashboard.viewmodel.UpdateViewModel
import com.ryancode.jokeapp.mvvm.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    private val viewModel : UpdateViewModel by viewModels()
    private lateinit var binding : JokeCategoryBinding
    private val adapterParentCategory : ParentViewAdapter by lazy {
        ParentViewAdapter(emptyList(),viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = JokeCategoryBinding.inflate(layoutInflater)
        getCategoryJokes()
        setContentView(binding.root)
    }

    private fun getCategoryJokes() {
        viewModel.getJokesCategory()
        observer()
        initRefresh()
    }

    private fun initRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getJokesCategory() // Panggil ulang data kategori jokes

            // Observer untuk menutup SwipeRefreshLayout ketika data sudah di-load
            viewModel.getJokesCategory.observe(this) { resource ->
                when (resource) {
                    is Resource.Success, is Resource.Error -> binding.swipeRefresh.isRefreshing = false
                    is Resource.Loading -> binding.swipeRefresh.isRefreshing = true
                }
            }
        }

    }

    private fun observer() {
        viewModel.getJokesCategory.observe(this){response ->
            when(response){
                is Resource.Error -> {
                    binding.shimmerView.visibility = View.GONE
                    binding.rvCategory.visibility = View.GONE
                }
                is Resource.Loading -> {
                    binding.shimmerView.visibility = View.VISIBLE
                    binding.rvCategory.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.rvCategory.visibility = View.VISIBLE
                    binding.shimmerView.visibility = View.GONE
                    val mapCategoryAndAlias = mapCategoriesWithAliases(response.data!!)
                    setupRecyclerViewParent(mapCategoryAndAlias)
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun exitByBackKey() {
        AlertDialog.Builder(this)
            .setMessage("Apa anda yakin ingin keluar?")
            .setPositiveButton("Ya") { _: DialogInterface?, _: Int ->
                finish()
            }
            .setNegativeButton("Tidak") { _: DialogInterface?, _: Int -> }
            .show()
    }

    private fun setupRecyclerViewParent(
        listCategory: List<CategoryAndAlias>
    ) {
        adapterParentCategory.updateData(listCategory)
        adapterParentCategory.notifyDataSetChanged()
        showCategoryJoke()
    }

    private fun showCategoryJoke() {
        binding.rvCategory.adapter = adapterParentCategory
        binding.rvCategory.layoutManager = LinearLayoutManager(this)

    }
}