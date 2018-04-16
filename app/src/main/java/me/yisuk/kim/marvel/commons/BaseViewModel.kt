package me.yisuk.kim.marvel.commons

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 10-04-2018.
 */
abstract class BaseViewModel : ViewModel() {

    val disposables = CompositeDisposable()

    protected val messages: BehaviorSubject<State> = BehaviorSubject.create()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    protected fun onError(t: Throwable) {
        Timber.e(t)
        sendMessage(State(Status.ERROR, t.localizedMessage))
    }

    protected fun onSuccess() {
        sendMessage(State(Status.SUCCESS))
    }

    protected fun sendMessage(state: State) {
        messages.onNext(state)
    }
}