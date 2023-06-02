package com.example.forcars.ui.common

import androidx.lifecycle.Observer
import com.example.forcars.ui.common.utils.Event

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {

    override fun onChanged(value: Event<T>) {
        value.getContentIfNotHandled()?.let {
            onEventUnhandledContent(it)
        }
    }
}