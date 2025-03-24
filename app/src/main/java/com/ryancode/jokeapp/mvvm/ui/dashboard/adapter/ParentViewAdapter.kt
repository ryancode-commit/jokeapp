package com.ryancode.jokeapp.mvvm.ui.dashboard.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ryancode.jokeapp.R
import com.ryancode.jokeapp.databinding.ItemCategoryJokeBinding
import com.ryancode.jokeapp.mvvm.data.model.joke.CategoryAndAlias
import com.ryancode.jokeapp.mvvm.ui.dashboard.viewmodel.UpdateViewModel
import com.ryancode.jokeapp.mvvm.utils.Resource

class ParentViewAdapter(
    private var itemsCategory:List<CategoryAndAlias>,
    private val viewModel: UpdateViewModel
) :
    RecyclerView.Adapter<ParentViewAdapter.ParentViewHolder>() {

    private var expandedPosition = -1

    inner class ParentViewHolder(private val binding: ItemCategoryJokeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemCategory: CategoryAndAlias, position: Int) {
            binding.tvTitleCategory.text = itemCategory.nameCategory
            binding.tvAliasCategory.text = itemCategory.alias ?: ""

            val isExpanded = position == expandedPosition
            binding.llJoke.visibility = if (isExpanded) View.VISIBLE else View.GONE
            val childAdapter = ChildJokeAdapter(emptyList())
            binding.rvJokes.adapter = childAdapter
            binding.rvJokes.layoutManager = LinearLayoutManager(binding.root.context)

            val targetRotation = if (isExpanded) 90f else 0f
            binding.ivArrow.rotation = targetRotation // Pastikan posisi awal sesuai dengan state


            binding.root.setOnClickListener {
                val previousExpanded = expandedPosition
                expandedPosition = if (isExpanded) -1 else position

                notifyItemChanged(previousExpanded)
                notifyItemChanged(expandedPosition)

                binding.ivArrow.animate()
                    .rotation(if (expandedPosition == position) 90f else 0f)
                    .setDuration(300)
                    .setInterpolator(OvershootInterpolator())
                    .start()

                if (!isExpanded) {
                    viewModel.getJokes(itemCategory.nameCategory, 6)
                }
            }




            viewModel.getJokes.observeForever { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.shimmerView.visibility = View.VISIBLE
                        binding.rvJokes.visibility = View.GONE
                        binding.btnAddMoreData.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        if (position == expandedPosition) {
                            val jokeList = resource.data?.jokes ?: emptyList()
                            childAdapter.updateData(jokeList)
                            binding.rvJokes.visibility = View.VISIBLE
                            binding.shimmerView.visibility = View.GONE

                            binding.btnAddMoreData.visibility = if (jokeList.size > 2) View.VISIBLE else View.GONE
                        }
                    }
                    is Resource.Error -> {
                        binding.shimmerView.visibility = View.GONE
                        binding.rvJokes.visibility = View.GONE
                        Toast.makeText(binding.root.context, resource.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }


            binding.btnAddMoreData.setOnClickListener {
                childAdapter.showMoreData()
                if (childAdapter.itemCount >= (viewModel.getJokes.value?.data?.jokes?.size ?: 0)) {
                    binding.btnAddMoreData.visibility = View.GONE
                }
            }

            binding.btnTop.setOnClickListener {
                val currentPosition = adapterPosition
                if (currentPosition != RecyclerView.NO_POSITION && currentPosition != 0) {
                    val selectedItem = itemsCategory[currentPosition] // Ambil item yang diklik
                    val previousFirstItem = itemsCategory[0] // Simpan item pertama sebelumnya

                    itemsCategory = itemsCategory.toMutableList().apply {
                        removeAt(currentPosition) // Hapus dari posisi lama
                        add(0, selectedItem) // Tambahkan ke posisi pertama
                    }

                    // Reset expandedPosition agar semua item tertutup
                    val previousExpanded = expandedPosition
                    expandedPosition = -1

                    // Animasi perpindahan item
                    notifyItemMoved(currentPosition, 0)

                    // Perbarui UI untuk item yang sebelumnya pertama dan yang sekarang pertama
                    notifyItemChanged(0) // Perbarui item pertama yang baru
                    notifyItemChanged(itemsCategory.indexOf(previousFirstItem)) // Perbarui item yang sebelumnya di posisi pertama
                    notifyItemChanged(previousExpanded) // Tutup item yang sebelumnya expanded (jika ada)

                    // Smooth scroll ke atas
                    binding.root.post {
                        (binding.root.parent as? RecyclerView)?.smoothScrollToPosition(0)
                    }
                }
            }




// **Pastikan kode ini ada di dalam fungsi `bind` agar diperbarui setiap kali item ditampilkan**
            if (adapterPosition == 0) {
                binding.btnTop.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.btn_color_top))
                binding.btnTop.text = binding.root.context.getString(R.string.top)
            } else {
                binding.btnTop.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.btn_color_go_top))
                binding.btnTop.text = binding.root.context.getString(R.string.go_top)
            }

        }
    }






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val binding = ItemCategoryJokeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParentViewHolder(binding)
    }

    override fun getItemCount(): Int = itemsCategory.size

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        holder.bind(itemsCategory[position],position)
    }

    fun updateData(newItemsCategory: List<CategoryAndAlias>) {
        itemsCategory = newItemsCategory
        notifyDataSetChanged()
    }
}