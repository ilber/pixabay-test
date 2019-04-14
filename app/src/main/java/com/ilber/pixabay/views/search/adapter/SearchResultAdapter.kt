package com.ilber.pixabay.views.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.ilber.pixabay.R
import com.ilber.pixabay.views.search.SearchViewModel
import kotlinx.android.synthetic.main.search_result_item.view.image_preview
import kotlinx.android.synthetic.main.search_result_item.view.image_tags
import kotlinx.android.synthetic.main.search_result_item.view.user_name

class SearchResultAdapter : RecyclerView.Adapter<SearchResultAdapter.ResultImageViewHolder>() {
    private var result = emptyList<SearchViewModel.SearchResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultImageViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.search_result_item,
            parent, false
        )

        return ResultImageViewHolder(itemView)
    }

    override fun getItemCount(): Int = result.count()

    override fun onBindViewHolder(holder: ResultImageViewHolder, position: Int) {
        holder.setItem(result[position])
    }

    override fun onViewRecycled(holder: ResultImageViewHolder) {
        super.onViewRecycled(holder)

        holder.clear()
    }

    fun updateResults(newResult: List<SearchViewModel.SearchResult>) {
        val diffResult = DiffUtil.calculateDiff(
            SearchResultDiffUtilCallback(
                result, newResult
            )
        )
        diffResult.dispatchUpdatesTo(this)

        result = newResult
    }

    class SearchResultDiffUtilCallback(
        private val oldList: List<SearchViewModel.SearchResult>,
        private val newList: List<SearchViewModel.SearchResult>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].previewUrl == newList[newItemPosition].previewUrl

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }

    inner class ResultImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setItem(result: SearchViewModel.SearchResult) {
            with(itemView) {
                user_name.text = itemView.resources.getString(R.string.search_item_user, result.user)
                image_tags.text = itemView.resources.getString(R.string.search_item_tags, result.tags)

                Glide.with(itemView)
                    .load(result.previewUrl)
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .into(image_preview)
            }
        }

        fun clear() {
            with(itemView) {
                Glide.with(itemView)
                    .clear(image_preview)
            }
        }
    }
}