package com.net.feedingroom.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.*
import com.net.feedingroom.R
import com.net.feedingroom.databinding.ViewFeedingRoomBinding
import com.net.feedingroom.databinding.ViewLoadingFeedingRoomBinding
import com.net.feedingroom.model.FeedingRoom

class FeedingRoomAdapter(
    private val listener: FeedingRoomAdapterListener? = null
): ListAdapter<FeedingRoom, FeedingRoomAdapter.ViewHolder>(diffCallback),
    FeedingRoomThumbnailAdapter.FeedingRoomThumbnailAdapterListener {

    interface FeedingRoomAdapterListener {
        fun onSelectFeedingRoom(room: FeedingRoom, position: Int?)
    }

    private val onlyChangeTextColor = true

    private fun getItems(): List<FeedingRoom> {
        val list = ArrayList<FeedingRoom>()
        for(i in 0 until this.itemCount) {
            list.add(this.getItem(i))
        }
        return list
    }

    fun cleanSelectedItem() {
        val position =  getItems().indexOfFirst { it.isSelected }
        if(position > -1) {
            getItem(position).isSelected = false
            notifyItemChanged(position, onlyChangeTextColor)
        }
    }

    fun selectItem(position: Int) {
        getItem(position).isSelected = true
        notifyItemChanged(position, onlyChangeTextColor)
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
        rv.addItemDecoration(DividerItemDecoration(5, 0, 0, 0))
        rv.adapter = FeedingRoomThumbnailAdapter(this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(getItem(position), position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        val onlyChangeTextColor = if(payloads.isNotEmpty()) {
            payloads[0] as? Boolean
        } else { null }
        holder.bind(getItem(position), position, onlyChangeTextColor)
    }

    abstract inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        open fun bind(data: FeedingRoom, position: Int?, onlyChangeTextColor: Boolean?) {}
    }

    private inner class DataViewHolder(
                            private val vb: ViewFeedingRoomBinding,
                            private val listener: FeedingRoomAdapterListener?
    ): ViewHolder(vb.root) {
        override fun bind(data: FeedingRoom, position: Int?, onlyChangeTextColor: Boolean?) {
            setSelectedColor(vb, data.isSelected)
            if(onlyChangeTextColor != true) {
                vb.tvTitle.text = data.name
                vb.tvPhoneNumber.text = data.tel
                vb.tvAddress.text = data.address
                (vb.rvPhotos.adapter as? FeedingRoomThumbnailAdapter)?.apply {
                    this.parentListAdapterPosition = position
                    submitList(data.photos)
                }
                vb.root.setOnClickListener { listener?.onSelectFeedingRoom(data, position) }
            }
        }
    }

    private fun setSelectedColor(vb: ViewFeedingRoomBinding, isSelected: Boolean) {
        val color = when(isSelected) {
            true -> ContextCompat.getColor(vb.tvTitle.context, R.color.colorAccent)
            false -> ContextCompat.getColor(vb.tvTitle.context, R.color.colorText)
        }
        vb.tvTitle.setTextColor(color)
        vb.tvPhoneNumber.setTextColor(color)
        vb.tvAddress.setTextColor(color)
    }

    private inner class LoadingViewHolder(vb: ViewLoadingFeedingRoomBinding): ViewHolder(vb.root)

    companion object {
        const val EMPTY = 1
        const val DATA = 2

        val diffCallback = object: DiffUtil.ItemCallback<FeedingRoom>() {
            override fun areItemsTheSame(oldItem: FeedingRoom, newItem: FeedingRoom): Boolean {
                return oldItem.number == newItem.number && oldItem.isSelected == newItem.isSelected
            }

            override fun areContentsTheSame(oldItem: FeedingRoom, newItem: FeedingRoom): Boolean {
                return oldItem == newItem
            }
        }
    }

    private fun findItemByRoomId(roomId: String): FeedingRoom? {
        return getItems().find { it.number != null && it.number ==  roomId}
    }

    override fun onThumbnailClick(roomId: String?, parentListAdapterPosition: Int?) {
        roomId?.let {
            findItemByRoomId(it)?.let { room ->
                listener?.onSelectFeedingRoom(room, parentListAdapterPosition)
            }
        }
    }
}