package jp.co.arsaga.extensions.view

import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController

interface HasTitleActivity {
    val titleBar: Toolbar

    fun setTitle(title : String) {
        titleBar.title = title
    }

    fun setNavigation(navController : NavController)
    {
        titleBar.setupWithNavController(navController)
    }
}