package com.example.empty.ui.temp

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.empty.ui.dashboard.DashboardActivity
import com.empty.ui.options.OptionsViewModel
import com.empty.ui.temp.TempViewModel
import com.empty.ui.temp.tempActivity
import com.example.empty.R
import com.example.empty.databinding.FragmentOptionsBinding
import com.example.empty.databinding.FragmentTempBinding

class TempFragment : Fragment() {

    private var _binding: FragmentTempBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(TempViewModel::class.java)

        _binding = FragmentTempBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        //val intent = Intent(activity, tempActivity::class.java)
        //startActivity(intent)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}