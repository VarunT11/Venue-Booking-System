package com.geekhaven.venuebookingsystem.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekhaven.venuebookingsystem.api.ApiHandler
import com.geekhaven.venuebookingsystem.api.models.*
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.models.data.BookingRequest
import com.geekhaven.venuebookingsystem.models.data.User
import com.geekhaven.venuebookingsystem.models.data.toBookingRequest
import com.geekhaven.venuebookingsystem.models.data.toUser
import com.geekhaven.venuebookingsystem.repository.MainRepo
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class MainVM(apiHandler: ApiHandler, mAuth: FirebaseAuth, val signInClient: SignInClient) : ViewModel() {

  val mainRepo = MainRepo(apiHandler, mAuth, signInClient)

  private val apiRepo = mainRepo.apiRepo
  private val firebaseRepo = mainRepo.firebaseRepo

  private val logoutFlow = MutableSharedFlow<Unit>()
  fun getLogoutFlow(): Flow<Unit> = logoutFlow
  fun sendLogoutAction() { viewModelScope.launch { logoutFlow.emit(Unit) } }

  private val appBarConfig = MutableLiveData<AppBarConfig>()
  fun getAppBarConfig(): LiveData<AppBarConfig> = appBarConfig
  fun setAppBarConfig(config: AppBarConfig) { appBarConfig.value = config }

  private val searchClickFlow = MutableSharedFlow<Unit>()
  fun getSearchClickFlow(): Flow<Unit> = searchClickFlow
  fun sendSearchClickFlow() { viewModelScope.launch { searchClickFlow.emit(Unit) } }

  private val searchTextFlow = MutableSharedFlow<String>()
  fun getSearchTextFlow(): Flow<String> = searchTextFlow
  fun sendSearchText(text: String) { viewModelScope.launch { searchTextFlow.emit(text) } }

  private val filterButtonFlow = MutableSharedFlow<Unit>()
  fun getFilterButtonClickFlow(): Flow<Unit> = filterButtonFlow
  fun onFilterButtonClick() { viewModelScope.launch { filterButtonFlow.emit(Unit) } }

  private val toastMessageFlow = MutableSharedFlow<String>()
  fun getToastMessageFlow(): Flow<String> = toastMessageFlow
  fun showToastMessage(message: String) { viewModelScope.launch { toastMessageFlow.emit(message) } }

  private val showLoader = MutableLiveData(false)
  fun getShowLoader(): LiveData<Boolean> = showLoader
  private fun setLoader(show: Boolean) { showLoader.value = show }

  private val loaderMessage = MutableLiveData("")
  fun getLoaderMessage(): LiveData<String> = loaderMessage
  private fun setLoaderMessage(message: String) { loaderMessage.value = message }


  private val currentUserDetails = MutableLiveData<User>()
  fun getCurrentUserDetails(): LiveData<User> = currentUserDetails
  fun updateCurrentUserDetails(userResponse: UserResponse) {
    currentUserDetails.value =
      userResponse.toUser().copy(photoUrl = getPhotoUrl())
  }

  private fun getPhotoUrl(): String =
    firebaseRepo.getCurrentUserImageUrl().toString()

  private val currentUserRequests = MutableLiveData<List<BookingRequest>>()
  fun getCurrentUserRequests(): LiveData<List<BookingRequest>> = currentUserRequests
  private fun updateCurrentUserRequests(requestsResponse: List<BookingRequestResponse>) {
    currentUserRequests.value = requestsResponse.map { it.toBookingRequest() }
  }

  suspend fun loadUserDetails() {
    mainRepo.refreshToken()

    updateCurrentUserDetails(mainRepo.getCurrentUserResponseDetails())

    firebaseRepo.getCurrentUserEmail()
      ?.let { apiRepo.getBookingRequestsByReceiver(it) }
      ?.let { updateCurrentUserRequests(it) }
  }

  suspend fun <T> runWithLoader(message: String, task: suspend () -> T): T {
    try {
      setLoader(true).also { setLoaderMessage(message) }
      return task()
    } finally {
      setLoader(false)
    }
  }
}
