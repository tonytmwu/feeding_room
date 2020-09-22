package com.net.feedingroom.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.net.feedingroom.databinding.FeedingRoomPhotoViewBinding
import com.net.feedingroom.model.Photo

class FeedingRoomPhotoAdapter: ListAdapter<Photo, FeedingRoomPhotoAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vb = FeedingRoomPhotoViewBinding.inflate(inflater, parent, false)
        return ViewHolder(vb)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffCallback = object: DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(private val vb: FeedingRoomPhotoViewBinding): RecyclerView.ViewHolder(vb.root) {
        private val transformation = RoundedCornersTransformation(10f, 10f, 10f, 10f)

        fun bind(data: Photo) {
            vb.ivPhoto.load(data.url) {
                transformations(transformation)
            }
        }
    }

}