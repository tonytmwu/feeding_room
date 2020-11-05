package com.net.feedingroom.ui.feedingroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.net.feedingroom.databinding.FragmentFeedingRoomBinding
import com.net.feedingroom.ui.view.DividerItemDecoration
import com.net.feedingroom.ui.view.FeedingRoomAdapter

class FeedingRoomFragment : Fragment() {

    private val defaultLocation = "南港展覽館"

    private var _vb: FragmentFeedingRoomBinding? = null
    private val vb get() = _vb!!
    private val vm by viewModels<FeedingRoomFragmentViewModel>()
    private val adapter by lazy { FeedingRoomAdapter() }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _vb = FragmentFeedingRoomBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _vb = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchBar()
        setupRecyclerView()
        bindLiveData()
    }
    
    private fun setupSearchBar() {
        vb.tvSearch.setOnEditorActionListener { v, actionId, _ ->
            when(actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    searchFeedingRooms(v.text.toString())
                    true
                }
                else -> false
            }
        }
    }

    private fun setupRecyclerView() {
        vb.rvFeedingRoom.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        vb.rvFeedingRoom.addItemDecoration(DividerItemDecoration(5,0))
        vb.rvFeedingRoom.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        searchFeedingRooms(defaultLocation)
    }

    private fun bindLiveData() {
        vm.feedingRooms.observe(viewLifecycleOwner) { info ->
            adapter.submitList(info?.rooms)
        }
    }

    private fun searchFeedingRooms(address: String) {
        vm.searchFeedingRooms(address)
    }
}