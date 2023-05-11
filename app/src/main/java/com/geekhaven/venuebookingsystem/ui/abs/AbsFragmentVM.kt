package com.geekhaven.venuebookingsystem.ui.abs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekhaven.venuebookingsystem.config.ui.AlertDialogConfig
import com.geekhaven.venuebookingsystem.repository.ApiRepo
import com.geekhaven.venuebookingsystem.repository.FirebaseRepo
import com.geekhaven.venuebookingsystem.repository.MainRepo
import com.geekhaven.venuebookingsystem.viewmodels.MainVM
import com.google.android.gms.auth.api.identity.SignInClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class AbsFragmentVM: ViewModel() {

  protected abstract fun onFragmentStart()

  protected lateinit var mainVM: MainVM

  protected val mainRepo: MainRepo
    get() = mainVM.mainRepo

  protected val apiRepo: ApiRepo
    get() = mainRepo.apiRepo

  protected val firebaseRepo: FirebaseRepo
    get() = mainRepo.firebaseRepo

  protected val signInClient: SignInClient
    get() = mainVM.signInClient

  private val coroutineHandler = VMCoroutineHandler(viewModelScope)

  private val navFlow = MutableSharedFlow<Int>()
  private val logFlow = MutableSharedFlow<String>()

  private lateinit var alertDialogConfig: MutableLiveData<AlertDialogConfig>
  private val alertDialogShowFlow = MutableSharedFlow<Unit>()

  fun startVM(mainVM: MainVM) {
    alertDialogConfig = MutableLiveData()
    setMainViewModel(mainVM)
    onFragmentStart()
  }

  fun closeVM() {
    coroutineHandler.cancelAllTasks()
  }

  private fun setMainViewModel(mainVM: MainVM) { this.mainVM = mainVM }

  fun getNavFlow(): Flow<Int> = navFlow
  fun getLogFlow(): Flow<String> = logFlow
  fun getExceptionFlow(): Flow<Throwable?> = coroutineHandler.getExceptionFlow()
  fun getAlertDialogConfig(): LiveData<AlertDialogConfig> = alertDialogConfig
  fun getAlertDialogShowFlow(): Flow<Unit> = alertDialogShowFlow

  protected fun sendNavAction(action: Int) { launchTask { navFlow.emit(action) } }
  protected fun sendLog(log: String) { launchTask { logFlow.emit(log) } }
  protected fun sendException(exception: Throwable?) { coroutineHandler.sendException(exception) }
  protected fun setAlertDialogConfig(config: AlertDialogConfig) { alertDialogConfig.value = config }
  protected fun showAlertDialog() { launchTask { alertDialogShowFlow.emit(Unit) } }

  fun <T> launchTask(task: suspend () -> T) =
    coroutineHandler.launchTask(task)

  fun <T> launchTaskInMain(task: suspend () -> T) =
    coroutineHandler.launchTaskInMain(task)

  fun <T> launchCatchingTask(
    task: suspend () -> T,
    success: (T) -> Unit = { },
    failure: (Throwable?) -> Unit = { }
  ) = coroutineHandler.launchCatchingTask(task, success, failure)

  fun <T> launchCatchingTaskInMain(
    task: suspend () -> T,
    success: (T) -> Unit = { },
    failure: (Throwable?) -> Unit = { }
  ) = coroutineHandler.launchCatchingTaskInMain(task, success, failure)

  fun <T> launchCatchingTaskWithLoader(
    task: suspend () -> T,
    success: (T) -> Unit = { },
    failure: (Throwable?) -> Unit = { },
    loaderMessage: String = "Loading",
  ) = launchCatchingTaskInMain(
    task = { runLoadingTask(loaderMessage, task) },
    success = success,
    failure = failure
  )

  private suspend fun <T> runLoadingTask(message: String, task: suspend () -> T) =
     mainVM.runWithLoader(message, task)

}
