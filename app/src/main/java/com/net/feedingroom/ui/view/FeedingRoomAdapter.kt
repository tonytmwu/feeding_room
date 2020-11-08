package com.net.feedingroom.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.net.feedingroom.databinding.ViewFeedingRoomBinding
import com.net.feedingroom.databinding.ViewLoadingFeedingRoomBinding
import com.net.feedingroom.model.FeedingRoom
import com.net.feedingroom.model.Photo

class FeedingRoomAdapter(
    private val listener: FeedingRoomAdapterListener? = null
): ListAdapter<FeedingRoom, FeedingRoomAdapter.ViewHolder>(diffCallback) {

    interface FeedingRoomAdapterListener {
        fun onSelectFeedingRoom(room: FeedingRoom)
    }

    override fun getItemViewType(position: Int): Int {
        return with(getItem(position)) {
            when(this.name.isNullOrEmpty()) {
                true -> EMPTY
                false -> DATA
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            EMPTY -> {
                val vb = ViewLoadingFeedingRoomBinding.inflate(inflater, parent, false)
                LoadingViewHolder(vb)
            }
            else -> {
                val vb = ViewFeedingRoomBinding.inflate(inflater, parent, false)
                setupRecyclerView(parent.context, vb.rvPhotos)
                DataViewHolder(vb, listener)
            }
        }
    }

    private fun setupRecyclerView(context: Context, rv: RecyclerView) {
        rv.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        rv.addItemDecoration(DividerItemDecoration(0, 5))
        rv.adapter = FeedingRoomPhotoAdapter()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.init()
    }

    abstract inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        open fun bind(data: FeedingRoom) {}
        open fun init() {}
    }

    private inner class DataViewHolder(private val vb: ViewFeedingRoomBinding,
                     private val listener: FeedingRoomAdapterListener?): ViewHolder(vb.root) {
        override fun bind(data: FeedingRoom) {
            vb.tvTitle.text = data.name
            vb.tvPhoneNumber.text = data.tel
            vb.tvAddress.text = data.address
            val photos = data.photo?.split("\n")?.takeIf { it.isNotEmpty() }?.map { fileName ->
                Photo(FeedingRoom.IMG_BASE_URL + fileName)
            }
            (vb.rvPhotos.adapter as? ListAdapter<Photo, FeedingRoomPhotoAdapter.ViewHolder>)?.submitList(photos)
            vb.viewClick.setOnClickListener {
                listener?.onSelectFeedingRoom(data)
            }
        }

        override fun init() {
            vb.mlRoot.transitionToStart()
        }
    }

    private inner class LoadingViewHolder(vb: ViewLoadingFeedingRoomBinding): ViewHolder(vb.root)

    companion object {
        const val EMPTY = 1
        const val DATA = 2

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