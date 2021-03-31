package androidx.lifecycle

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * author : zp
 * date : 2020/11/25
 * Potato
 */

fun <T> LiveData<T>.debounce(
    duration: Long = 1000L,
    coroutineContext: CoroutineContext = Dispatchers.Main
) = MediatorLiveData<T>().also { mld ->

    val source = this
    var job: Job? = null

    mld.addSource(source) {
        job?.cancel()
        job = CoroutineScope(coroutineContext).launch {
            delay(duration)
            mld.value = source.value
        }
    }
}

fun <T> LiveData<T>.notSticky() = MediatorLiveData<T>().also { mld ->
    val hasSetValue = this.version != -1
    var version = 0
    mld.addSource(this) {
        if (hasSetValue && version == 0) {

        } else {
            mld.value = this.value
        }
        version += 1
    }
}

fun <T> LiveData<T>.observeOnce(observer: (T) -> Unit) {
    observeForever(object : Observer<T> {
        override fun onChanged(value: T) {
            removeObserver(this)
            observer(value)
        }
    })
}

fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, object : Observer<T> {
        override fun onChanged(value: T) {
            removeObserver(this)
            observer(value)
        }
    })
}

fun <T> LiveData<T>.notNull() = MediatorLiveData<T>().also { mld ->
    mld.addSource(this, Observer {
        if (value != null) {
            mld.value = value
        }
    })
}

