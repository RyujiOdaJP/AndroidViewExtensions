package jp.co.arsaga.extensions.view

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun NavDirections.navigate(fragment: Fragment, navigateAnimation: NavigateAnimationType? = null) {
    fragment.findNavController().navigate(this, animateNavOptionsFactory(navigateAnimation))
}