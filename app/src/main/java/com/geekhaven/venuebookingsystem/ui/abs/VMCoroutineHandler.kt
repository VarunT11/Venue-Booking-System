package com.geekhaven.venuebookingsystem.ui.abs

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class VMCoroutineHandler(private val scope: CoroutineScope) {

  private val exceptionFlow = MutableSharedFlow<Throwable?>()
  private val jobList: ArrayList<Job> = ArrayList()

  fun getExceptionFlow(): Flow<Throwable?> = exceptionFlow
  fun cancelAllTasks() = jobList.forEach { it.cancel() }

  private fun <T> launchTaskInScope(dispatcher: CoroutineDispatcher, task: suspend () -> T) =
    scope.launch(dispatcher) { task() }
      .also { jobList.add(it) }

  fun <T> launchTask(task: suspend () -> T) = launchTaskInScope(Dispatchers.Default, task)

  fun <T> launchTaskInMain(task: suspend () -> T) = launchTaskInScope(Dispatchers.Main, task)

  fun <T> launchCatchingTask(task: suspend () -> T, success: (T) -> Unit, failure: (Throwable?) -> Unit) {
    launchTask {
      runCatching { task() }
        .onSuccess { success(it) }
        .onFailure { handleFailure(it, failure) }
    }
  }

  fun <T> launchCatchingTaskInMain(task: suspend () -> T, success: (T) -> Unit, failure: (Throwable?) -> Unit) {
    launchTaskInMain {
      runCatching { task() }
        .onSuccess { success(it) }
        .onFailure { handleFailure(it, failure) }
    }
  }

  private fun handleFailure(failure: Throwable?, task: (Throwable?) -> Unit) {
    sendException(failure)
    task(failure)
  }

  fun sendException(throwable: Throwable?) {
    launchTask { exceptionFlow.emit(throwable) }
  }
}
