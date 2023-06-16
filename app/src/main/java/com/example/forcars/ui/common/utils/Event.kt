package com.example.forcars.ui.common.utils

open class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    /**
     * Retorna el contenido y evita que se maneje múltiples veces.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}