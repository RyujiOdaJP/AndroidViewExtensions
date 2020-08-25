package jp.co.arsaga.extensions.view

import androidx.navigation.NavDirections

abstract class AbstractNavDirections {
    abstract fun factory(query: Any?): NavDirections
}