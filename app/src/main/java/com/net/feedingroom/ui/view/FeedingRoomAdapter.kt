package com.net.feedingroom.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import coil.load
import com.net.feedingroom.databinding.ViewFeedingRoomBinding
import com.net.feedingroom.model.FeedingRoom
import com.net.feedingroom.model.FeedingRoomInfo
import com.net.feedingroom.model.Photo

class FeedingRoomAdapter: ListAdapter<FeedingRoom, FeedingRoomAdapter.ViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vb = ViewFeedingRoomBinding.inflate(inflater, parent, false)
        setupRecyclerView(parent.context, vb.rvPhotos)
        return ViewHolder(vb)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.init()
    }

    class ViewHolder(private val vb: ViewFeedingRoomBinding): RecyclerView.ViewHolder(vb.root) {
        fun bind(data: FeedingRoom) {
            vb.tvTitle.text = data.name
            vb.tvPhoneNumber.text = data.tel
            vb.tvAddress.text = data.address
            val photos = data.photo?.split("\n")?.takeIf { it.isNotEmpty() }?.map { fileName ->
                Photo(FeedingRoom.IMG_BASE_URL + fileName)
            }
            (vb.rvPhotos.adapter as? ListAdapter<Photo, FeedingRoomPhotoAdapter.ViewHolder>)?.submitList(photos)
        }

        fun init() {
            vb.mlRoot.transitionToStart()
        }
    }

    private fun setupRecyclerView(context: Context, rv: RecyclerView) {
        rv.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        rv.addItemDecoration(DividerItemDecoration(0, 5))
        rv.adapter = FeedingRoomPhotoAdapter()
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