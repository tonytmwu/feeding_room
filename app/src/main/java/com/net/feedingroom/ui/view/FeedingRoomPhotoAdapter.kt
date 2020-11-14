package com.net.feedingroom.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.net.feedingroom.R
import com.net.feedingroom.databinding.ViewFeedingRoomPhotoBinding
import com.net.feedingroom.model.Photo

class FeedingRoomPhotoAdapter(): ListAdapter<Photo, FeedingRoomPhotoAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vb = ViewFeedingRoomPhotoBinding.inflate(inflater, parent, false)
        return ViewHolder(vb)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = getItem(position)
        holder.bind(photo)
    }

    inner class ViewHolder(private val vb: ViewFeedingRoomPhotoBinding): RecyclerView.ViewHolder(vb.root) {
        private val transformation = RoundedCornersTransformation(10f, 10f, 10f, 10f)

        fun bind(photo: Photo) {
            vb.ivPhoto.load(photo.url) {
                crossfade(true)
                error(R.drawable.ic_placeholder_img)
                transformations(transformation)
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