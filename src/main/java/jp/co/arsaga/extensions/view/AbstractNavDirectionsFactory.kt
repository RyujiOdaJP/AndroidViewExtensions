package jp.co.arsaga.extensions.view

import androidx.navigation.NavDirections

abstract class AbstractNavDirectionsFactory {
    abstract fun create(query: Any?): NavDirections?
}