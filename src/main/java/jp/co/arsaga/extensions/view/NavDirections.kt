package jp.co.arsaga.extensions.view

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun NavDirections.navigate(fragment: Fragment, navigateAnimation: NavigateAnimationType? = null) {
    fragment.findNavController().navigate(this, animateNavOptionsFactory(navigateAnimation))
}
fun NavDirections.navigate(activity: Activity, navigateAnimation: NavigateAnimationType? = null) {
    activity.getNavController()?.navigate(this, animateNavOptionsFactory(navigateAnimation))
}
