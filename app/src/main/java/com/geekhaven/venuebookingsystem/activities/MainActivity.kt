package com.geekhaven.venuebookingsystem.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.application.MainApplication
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.SearchBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.databinding.ActivityMainBinding
import com.geekhaven.venuebookingsystem.models.data.BookingRequest
import com.geekhaven.venuebookingsystem.models.type.BookingRequestStatus.Received
import com.geekhaven.venuebookingsystem.ui.utils.LoaderFragment
import com.geekhaven.venuebookingsystem.viewmodels.MainVM
import com.geekhaven.venuebookingsystem.viewmodels.MainVMFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var mainVM: MainVM
  private lateinit var navController: NavController

  private lateinit var loaderFragment: LoaderFragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val mApplication = application as MainApplication
    val mainVMFactory = MainVMFactory(
      apiHandler = mApplication.apiHandler,
      mAuth = mApplication.mAuth,
      signInClient = mApplication.signInClient
    )

    mainVM = ViewModelProvider(this, mainVMFactory).get()

    navController = supportFragmentManager
      .findFragmentById(R.id.navHostMain)
      .let { it as NavHostFragment }
      .navController

    AppBarHandler().invoke()
    NavigationHandler().invoke()
    ShowToastHandler().invoke()
    ShowLoaderHandler().invoke()
  }

  inner class AppBarHandler{
    fun invoke(){
      binding.btnBack.setOnClickListener { navController.popBackStack() }
      binding.inputSearchBar.addTextChangedListener { mainVM.sendSearchText(it.toString()) }
      binding.btnSearchFilter.setOnClickListener { mainVM.onFilterButtonClick() }

      binding.inputSearchBar.setOnFocusChangeListener { _, focus ->
        run {
          if (focus) mainVM.sendSearchClickFlow()
        }
      }

      mainVM.getAppBarConfig().observe(this@MainActivity) { config ->
        handleTitleAndSearchConfig(config)
      }
    }

    private fun handleTitleAndSearchConfig(appBarConfig: AppBarConfig){
      if(appBarConfig.titleBarConfig != null) handleTitleConfig(appBarConfig.titleBarConfig)
      else binding.titleLayout.visibility = getVisibilityInt(false)

      if(appBarConfig.searchBarConfig != null) handleSearchConfig(appBarConfig.searchBarConfig)
      else binding.searchView.visibility = getVisibilityInt(false)
    }

    private fun handleTitleConfig(titleConfig: TitleBarConfig){
      binding.titleLayout.visibility = getVisibilityInt(true)

      binding.btnBack.visibility = getVisibilityInt(titleConfig.showBackButton)
      binding.tvMainTitle.text = titleConfig.title

      if(titleConfig.titleIconRes == null)
        binding.imgTitleIcon.visibility = getVisibilityInt(false)
      else {
        binding.imgTitleIcon.visibility = getVisibilityInt(true)
        binding.imgTitleIcon.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, titleConfig.titleIconRes))
      }
    }

    private fun handleSearchConfig(searchBarConfig: SearchBarConfig) {
      binding.searchView.visibility = getVisibilityInt(true)
      binding.btnSearchFilter.visibility = getVisibilityInt(searchBarConfig.showFilterButton)
      binding.inputSearchBar.hint = searchBarConfig.hint
    }

    private fun getVisibilityInt(visible: Boolean) =
      if (!visible) View.GONE else View.VISIBLE
  }

  inner class NavigationHandler {
    fun invoke() {
      handleLogoutAction()
      handleNavBarVisibility()
      handleNavBarItemsVisibility()
      handleNavBarItemSelect()
    }

    private fun handleLogoutAction() {
      mainVM.viewModelScope.launch(Dispatchers.Main) {
        mainVM.getLogoutFlow()
          .collect {
            binding.bnvMain.selectedItemId = R.id.menuItemHome
            navController.navigate(R.id.action_homeFragment_to_loginFragment)
          }
      }
    }

    private fun handleNavBarVisibility() {
      navController.addOnDestinationChangedListener { _: NavController, navDestination: NavDestination, _: Bundle? ->
        listOf(R.id.splashFragment, R.id.loginFragment)
          .contains(navDestination.id)
          .let { if(!it) View.VISIBLE else View.GONE }
          .let { binding.bnvMain.visibility = it }
      }
    }

    private fun handleNavBarItemsVisibility(){
      handleRequestItemVisibility()
    }

    private fun handleRequestItemVisibility() {
      mainVM.getCurrentUserRequests().observe(this@MainActivity) {
        binding.bnvMain.menu.findItem(R.id.menuItemRequests).isVisible = it.isNotEmpty()
        if(it.isNotEmpty()) handleRequestsItemBadge(it)
      }
    }

    private fun handleRequestsItemBadge(requests: List<BookingRequest>) {
      val badge = binding.bnvMain.getOrCreateBadge(R.id.menuItemRequests)
      val size = requests.filter { it.requestStatus is Received }.size
      badge.isVisible = size > 0
      badge.number = size
    }

    private var currentNavItemId by Delegates.notNull<Int>()

    private fun handleNavBarItemSelect() {
      currentNavItemId = R.id.menuItemHome

      binding.bnvMain.setOnItemSelectedListener {
        handleNavigation(it.itemId)
        true
      }

      binding.bnvMain.setOnItemReselectedListener {
        getDestinationFragmentId(currentNavItemId)
          .also { navController.popBackStack(it, true) }
          .also { navController.navigate(it) }
      }
    }

    private fun handleNavigation(selectedItemId: Int) {
      getDestinationFragmentId(currentNavItemId)
        .also { navController.popBackStack(it, true) }

      currentNavItemId = selectedItemId

      getDestinationFragmentId(currentNavItemId)
        .also { navController.navigate(it) }
    }

    private fun getDestinationFragmentId(selectedItemId: Int) = when(selectedItemId) {
      R.id.menuItemHome -> R.id.homeFragment
      R.id.menuItemBookings -> R.id.bookingsListFragment
      R.id.menuItemRequests -> R.id.bookingRequestListFragment
      R.id.menuItemProfile -> R.id.profileFragment
      else -> R.id.homeFragment
    }
  }

  inner class ShowToastHandler {
    fun invoke() {
      mainVM.viewModelScope.launch(Dispatchers.Main) {
        mainVM.getToastMessageFlow().collect { showToast(it) }
      }
    }

    private fun showToast(message: String){
      Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }
  }

  inner class ShowLoaderHandler {
    fun invoke() {
      loaderFragment = LoaderFragment.newInstance()
      val fragmentManager = this@MainActivity.supportFragmentManager

      mainVM.getShowLoader().observe(this@MainActivity) { showLoader ->
        if (showLoader) loaderFragment.takeIf { !it.isAdded }?.show(fragmentManager, "Loader")
        else loaderFragment.takeIf { it.isVisible || it.isAdded }?.dismiss()
      }
    }
  }
}
