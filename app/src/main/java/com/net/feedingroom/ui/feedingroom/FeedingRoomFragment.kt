package com.net.feedingroom.ui.feedingroom

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.net.feedingroom.R

class FeedingRoomFragment : Fragment() {

    private val vm by viewModels<FeedingRoomFragmentViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        bindLiveData()
        val textView: TextView = root.findViewById(R.id.text_home)
        vm.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onResume() {
        super.onResume()
        searchFeedingRooms("南港展覽館")
    }

    private fun bindLiveData() {
        vm.feedingRooms.observe(viewLifecycleOwner) { info ->
            Log.d(javaClass.simpleName, "feedingRoomInfo = ${info}")
        }
    }

    private fun searchFeedingRooms(address: String) {
        vm.searchFeedingRooms(address)
    }
}