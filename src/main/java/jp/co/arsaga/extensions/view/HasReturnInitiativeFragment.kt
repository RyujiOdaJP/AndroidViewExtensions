package jp.co.arsaga.extensions.view

import androidx.fragment.app.Fragment

interface HasReturnInitiativeFragment {

    fun isReturnable(): Boolean

    companion object {
        fun returnHandling(
            currentFragment: Fragment?,
            returnAction: () -> Unit
        ) = currentFragment
            .takeIf { it !is HasReturnInitiativeFragment || it.isReturnable() }
            ?.run { returnAction() }
    }
}