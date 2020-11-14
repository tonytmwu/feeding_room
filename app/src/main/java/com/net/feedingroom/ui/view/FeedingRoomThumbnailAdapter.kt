package com.net.feedingroom.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.net.feedingroom.R
import com.net.feedingroom.databinding.ViewFeedingRoomThumbnailBinding
import com.net.feedingroom.model.Photo

class FeedingRoomThumbnailAdapter(
    private val listener: FeedingRoomThumbnailAdapterListener? = null
): ListAdapter<Photo, FeedingRoomThumbnailAdapter.ViewHolder>(diffCallback) {

    var parentListAdapterPosition: Int? = null

    interface FeedingRoomThumbnailAdapterListener {
        fun onThumbnailClick(roomId: String?, parentListAdapterPosition: Int?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vb = ViewFeedingRoomThumbnailBinding.inflate(inflater, parent, false)
        return ViewHolder(vb)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val vb: ViewFeedingRoomThumbnailBinding): RecyclerView.ViewHolder(vb.root) {
        private val transformation = RoundedCornersTransformation(10f, 10f, 10f, 10f)

        fun bind(data: Photo) {
            vb.ivPhoto.apply {
                setOnClickListener { listener?.onThumbnailClick(data.roomId, parentListAdapterPosition) }
                load(data.url) {
                    crossfade(true)
                    error(R.drawable.ic_placeholder_img)
                    transformations(transformation)
                }
            }
        }
    }

    companion object {
        val diffCallback = object: DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem.url == newItem.url
            }
        }
    }

}