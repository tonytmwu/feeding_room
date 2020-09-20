package com.net.feedingroom.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import coil.load
import com.net.feedingroom.databinding.ViewFeedingRoomBinding
import com.net.feedingroom.model.FeedingRoom

class FeedingRoomAdapter: ListAdapter<FeedingRoom, FeedingRoomAdapter.ViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedingRoomAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vb = ViewFeedingRoomBinding.inflate(inflater, parent, false)
        return ViewHolder(vb)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val vb: ViewFeedingRoomBinding): RecyclerView.ViewHolder(vb.root) {
        fun bind(data: FeedingRoom) {
            vb.tvTitle.text = data.name
            vb.tvPhoneNumber.text = data.tel
            vb.tvAddress.text = data.address
            data.photo?.split("\n")?.takeIf { it.isNotEmpty() }?.let { photos ->
                vb.ivPhoto.load(FeedingRoom.IMG_BASE_URL + photos[0])
            }
        }
    }

    companion object {
        val diffCallback = object: DiffUtil.ItemCallback<FeedingRoom>() {
            override fun areItemsTheSame(oldItem: FeedingRoom, newItem: FeedingRoom): Boolean {
                return oldItem.number == newItem.number
            }

            override fun areContentsTheSame(oldItem: FeedingRoom, newItem: FeedingRoom): Boolean {
                return oldItem == newItem
            }
        }
    }
}