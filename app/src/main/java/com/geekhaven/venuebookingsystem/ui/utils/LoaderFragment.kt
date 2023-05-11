package com.geekhaven.venuebookingsystem.ui.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.geekhaven.venuebookingsystem.databinding.FragmentLoaderBinding
import com.geekhaven.venuebookingsystem.viewmodels.MainVM

class LoaderFragment : DialogFragment() {

  private var _binding: FragmentLoaderBinding? = null
  private val binding
    get() = _binding!!

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    _binding = FragmentLoaderBinding.inflate(inflater, container, false)
    return binding.root
  }

  private lateinit var mainVM: MainVM

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    dialog?.setCanceledOnTouchOutside(false)
    dialog?.setCancelable(false)

    mainVM = ViewModelProvider(requireActivity()).get()
    mainVM.getLoaderMessage().observe(viewLifecycleOwner) {
      binding.tvLoader.text = it
    }
  }

  override fun onResume() {
    super.onResume()
    dialog?.window?.setLayout(900,800)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  companion object {
    @JvmStatic
    fun newInstance() = LoaderFragment()
  }
}
