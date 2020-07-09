package jp.co.arsaga.extensions.view

import jp.mjc.kurasel.presentation.view.common.R

enum class NavigateAnimationType(
    val enterAnimationId: Int = -1,
    val exitAnimationId: Int = -1,
    val popEnterAnimationId: Int = -1,
    val popExitAnimationId: Int = -1
) {
    SLIDE(
        enterAnimationId = R.anim.slide_in_enter_animator,
        exitAnimationId = R.anim.slide_in_exit_animator,
        popEnterAnimationId = R.anim.slide_out_enter_animator,
        popExitAnimationId = R.anim.slide_out_exit_animator
    ),
    POP_UP(
        enterAnimationId = R.anim.pop_up_in_enter_animator,
        exitAnimationId = R.anim.pop_up_in_exit_animator,
        popEnterAnimationId = R.anim.pop_up_out_enter_animator,
        popExitAnimationId = R.anim.pop_up_out_exit_animator
    )
}