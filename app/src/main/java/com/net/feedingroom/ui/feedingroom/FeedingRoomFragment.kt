package com.net.feedingroom.ui.feedingroom

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.net.feedingroom.databinding.FragmentFeedingRoomBinding
import com.net.feedingroom.model.FeedingRoom
import com.net.feedingroom.ui.activity.MainActivityViewModel
import com.net.feedingroom.ui.state.LoadingState
import com.net.feedingroom.ui.view.DividerItemDecoration
import com.net.feedingroom.ui.view.FeedingRoomAdapter

class FeedingRoomFragment : Fragment(), FeedingRoomAdapter.FeedingRoomAdapterListener {

    private var searchLocation = "南港展覽館"

    private var _vb: FragmentFeedingRoomBinding? = null
    private val vb get() = _vb!!
    private val vm by viewModels<FeedingRoomFragmentViewModel>()
    private val vmMainActivity: MainActivityViewModel by activityViewModels()
    private val adapter by lazy { FeedingRoomAdapter(this) }

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
                    hideKeyboard()
                    v.clearFocus()
                    true
                }
                else -> false
            }
        }
    }

    private fun hideKeyboard() {
        (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
            ?.hideSoftInputFromWindow(vb.tvSearch.windowToken, 0)
    }

    private fun setupRecyclerView() {
        vb.rvFeedingRoom.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        vb.rvFeedingRoom.addItemDecoration(DividerItemDecoration(0,0, 0, 5))
        vb.rvFeedingRoom.adapter = adapter
    }

    private fun bindLiveData() {
        vm.feedingRooms.observe(viewLifecycleOwner) { info ->
            if(info?.rooms == null || info.rooms.isEmpty()) {
                showNoData()
            } else {
                showData()
            }
            adapter.submitList(info?.rooms)
        }

        vm.loadingState.observe(viewLifecycleOwner) { state ->
            when(state) {
                LoadingState.Loading -> vb.shimmerLoading.showShimmer(true)
                LoadingState.Done -> vb.shimmerLoading.hideShimmer()
            }
        }

        vmMainActivity.currentLocationLiveData.observe(viewLifecycleOwner) { latLng ->
            vm.searchFeedingRooms(latLng.latitude, latLng.longitude)
            context?.let { vm.logSearchKeyword(it, latLng = latLng) }
        }
    }

    private fun showNoData() {
        vb.tvEmpty.visibility = View.VISIBLE
        vb.rvFeedingRoom.visibility = View.GONE
    }

    private fun showData() {
        vb.tvEmpty.visibility = View.GONE
        vb.rvFeedingRoom.visibility = View.VISIBLE
    }

    private fun searchFeedingRooms(address: String) {
        vm.searchFeedingRooms(address)
        searchLocation = address
        context?.let { vm.logSearchKeyword(it, location = address) }
    }

    override fun onSelectFeedingRoom(room: FeedingRoom, position: Int?) {
        if(vmMainActivity.selectedFeedingRoomLiveData.value != room) {
            vmMainActivity.updateSelectedFeedingRoom(room)
            adapter.cleanSelectedItem()
            position?.let { adapter.selectItem(it) }
            expandBottomSheet()
        }
    }

    private fun expandBottomSheet() {
        BottomSheetBehavior.from(vb.bottomSheetRoot)?.let { bottomSheet ->
            if(bottomSheet.state == BottomSheetBehavior.STATE_HIDDEN) {
                bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }
}