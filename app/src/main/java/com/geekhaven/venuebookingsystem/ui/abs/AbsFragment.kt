package com.geekhaven.venuebookingsystem.ui.abs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.exceptions.ServerException
import com.geekhaven.venuebookingsystem.exceptions.SimpleApiException
import com.geekhaven.venuebookingsystem.viewmodels.MainVM
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder

abstract class AbsFragment<VB : ViewBinding, VM: AbsFragmentVM> : Fragment() {

  abstract val fragmentName: String
  abstract val vmClass: Class<VM>

  abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
  abstract fun addLiveDataObservers()
  abstract fun addViewListeners()

  private var _binding: VB? = null
  protected val binding get() = _binding!!

  protected lateinit var mainVM: MainVM
  protected lateinit var mVM: VM

  protected lateinit var navController: NavController

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    _binding = inflateViewBinding(inflater, container)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    init()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
    mVM.closeVM()
  }

  private fun init() {
    mainVM = ViewModelProvider(requireActivity()).get()
    mVM = ViewModelProvider(requireActivity())[vmClass]

    mVM.startVM(mainVM)

    launchInMainScope { FragmentNavigationHandler().invoke() }
    launchInDefaultScope { LoggerHandler().invoke() }
    launchInDefaultScope { FragmentExceptionHandler().invoke() }
    launchInMainScope { AlertDialogHandler().invoke() }

    addLiveDataObservers()
    addViewListeners()
  }

  inner class FragmentNavigationHandler {
    suspend fun invoke() {
      navController = initNavController()
      navActionHandler()
    }

    private fun initNavController() = requireActivity().supportFragmentManager
      .findFragmentById(R.id.navHostMain)
      .let { it as NavHostFragment }
      .navController

    private suspend fun navActionHandler() {
      mVM.getNavFlow()
        .collect { handleNavAction(it) }
    }

    private fun handleNavAction(action: Int) {
      if (action == -1) navController.popBackStack()
      else navController.navigate(action)
    }
  }

  inner class LoggerHandler {
    suspend fun invoke() {
      mVM.getLogFlow()
        .collect { addNewLog(it) }
    }

    private fun addNewLog(message: String) {
      Log.d(fragmentName, message)
    }
  }

  inner class FragmentExceptionHandler {
    suspend fun invoke() {
      mVM.getExceptionFlow()
        .collect { handleException(it) }
    }

    private fun handleException(exception: Throwable?){
      Log.d(fragmentName, "Exception: ${exception?.message}")
      showExceptionMessage(exception)
    }

    private fun showExceptionMessage(exception: Throwable?){
      when(exception){
        is SimpleApiException -> mainVM.showToastMessage(exception.message)
        is ServerException -> mainVM.showToastMessage("Server error, please try again later")
      }
    }
  }

  inner class AlertDialogHandler {

    suspend fun invoke() {
      val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext())

      mVM.getAlertDialogConfig().observe(viewLifecycleOwner) {
        alertDialogBuilder.apply {
            setTitle(it.title)
            setMessage(it.message)
            setPositiveButton("Yes") { _, _ -> it.onYes() }
            setNegativeButton("No") { dialog, _ -> dialog.dismiss() }

        }
      }

      mVM.getAlertDialogShowFlow()
        .collect { alertDialogBuilder.show() }
    }
  }

  protected fun launchInDefaultScope(task: suspend () -> Unit) =
    mVM.launchTask(task)

  protected fun launchInMainScope(task: suspend () -> Unit) =
    mVM.launchTaskInMain(task)

  protected fun showDatePicker(title: String, defaultSelection: Long, onSelect: (long: Long) -> Unit) {
    val calendarConstraints = CalendarConstraints.Builder()
      .setValidator(DateValidatorPointForward.now())
      .build()

    val datePicker = MaterialDatePicker.Builder.datePicker()
      .setTitleText(title)
      .setCalendarConstraints(calendarConstraints)
      .setSelection(defaultSelection)
      .build()

    datePicker.show(requireActivity().supportFragmentManager, "DatePicker")
    datePicker.addOnPositiveButtonClickListener { onSelect(it) }
  }

}
