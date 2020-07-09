package jp.co.arsaga.extensions.view

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController


fun FragmentActivity.setTitleNavHostFragment(navHostFragmentId: Int, toolbar: Toolbar) {
    findNavControllerByNavHostFragmentId(supportFragmentManager, navHostFragmentId).let {
        toolbar.setupWithNavController(it) }
}

fun FragmentActivity.safeShowDialogFragment(dialogFragment: DialogFragment) {
    if (supportFragmentManager.findFragmentByTag(dialogFragment.javaClass.name) is DialogFragment) return
    dialogFragment.show(supportFragmentManager, dialogFragment.javaClass.name)
}

fun FragmentActivity.dismissAllDialogFragment() {
    supportFragmentManager
        .fragments
        .filterIsInstance<DialogFragment>()
        .forEach { it.dismissAllowingStateLoss() }
}

fun FragmentActivity.setDestinationChangeListener(
    navHostFragmentId: Int,
    destinationChangedListenerList: List<NavController.OnDestinationChangedListener>
) {
    findNavControllerByNavHostFragmentId(supportFragmentManager, navHostFragmentId).let {
        destinationChangedListenerList.fold(it) { acc, onDestinationChangedListener ->
            acc.apply { addOnDestinationChangedListener(onDestinationChangedListener) }
        }
    }
}

fun FragmentActivity.getCurrentDisplayFragment(navHostFragmentId: Int): Fragment? = supportFragmentManager
    .findFragmentById(navHostFragmentId)
    ?.childFragmentManager
    ?.fragments
    ?.getOrNull(0)

fun findNavControllerByNavHostFragmentId(
    fragmentManager: FragmentManager,
    navHostFragmentId: Int
): NavController = (fragmentManager.findFragmentById(navHostFragmentId) as NavHostFragment).navController
