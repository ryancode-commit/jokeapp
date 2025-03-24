package com.ryancode.jokeapp.mvvm.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ryancode.jokeapp.databinding.ItemJokeBinding
import com.ryancode.jokeapp.mvvm.data.model.joke.Joke
class ChildJokeAdapter(private var fullList: List<Joke>) :
    RecyclerView.Adapter<ChildJokeAdapter.ChildViewHolder>() {

    private val visibleItems = mutableListOf<Joke>()
    private var nextItemIndex = 2

    init {
        visibleItems.addAll(fullList.take(2))
    }

    class ChildViewHolder(private val binding: ItemJokeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String, position: String) {
            binding.numberJoke.text = position
            binding.joke.text = item

            binding.joke.setOnClickListener {
                MaterialAlertDialogBuilder(binding.root.context)
                    .setTitle("Joke Detail")
                    .setMessage(item)
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChildViewHolder {
        val binding = ItemJokeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val nowPosition = position + 1
        val jokeText = visibleItems[position].joke ?: "${visibleItems[position].setup}\n${visibleItems[position].delivery}"
        holder.bind(jokeText, nowPosition.toString())
    }

    override fun getItemCount(): Int = visibleItems.size

    fun showMoreData() {
        val endIndex = (nextItemIndex + 2).coerceAtMost(fullList.size)
        val newItems = fullList.subList(nextItemIndex, endIndex)

        val startPos = visibleItems.size
        visibleItems.addAll(newItems)
        notifyItemRangeInserted(startPos, newItems.size)

        nextItemIndex = endIndex
    }


    fun updateData(newItems: List<Joke>) {
        fullList = newItems
        visibleItems.clear()
        visibleItems.addAll(fullList.take(2))
        nextItemIndex = 2
        notifyDataSetChanged()
    }
}
