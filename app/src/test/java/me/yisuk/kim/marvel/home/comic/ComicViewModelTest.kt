package me.yisuk.kim.marvel.home.comic

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import me.yisuk.kim.marvel.RxSchedulers
import me.yisuk.kim.marvel.commons.State
import me.yisuk.kim.marvel.commons.Status
import me.yisuk.kim.marvel.remote.calls.ComicCall
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * @author [Yisuk Kim](kimls125@gmail.com) on 12-04-2018.
 */
class ComicViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var comicCall: ComicCall
    @Mock
    private lateinit var testStateObserver: Observer<State>

    private lateinit var comicViewModel: ComicViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val schedulers = RxSchedulers(Schedulers.trampoline(), Schedulers.trampoline(), Schedulers.trampoline())
        comicViewModel = ComicViewModel(schedulers, comicCall)
    }

    @Test
    fun testNull() {
        assertThat(comicViewModel.viewState, notNullValue())
    }

    @Test
    fun fullRefresh_shouldSendRefreshingStatus() {
        given(comicCall.refresh(Unit)).willReturn(Completable.complete())
        comicViewModel.viewState.observeForever(testStateObserver)
        comicViewModel.fullRefresh()

        verify(testStateObserver).onChanged(State(Status.REFRESHING))
        verify(testStateObserver).onChanged(State(Status.SUCCESS))
    }

    @Test
    fun viewState_shouldSendErrorMessage_ifCallFails() {
        given(comicCall.refresh(Unit)).willReturn(Completable.error(Throwable()))
        comicViewModel.viewState.observeForever(testStateObserver)
        comicViewModel.fullRefresh()

        verify(testStateObserver).onChanged(State(Status.ERROR))
    }

    @Test
    fun onListScrolledToEnd_shouldSendLoadingMoreMessage() {
        given(comicCall.loadNextPage()).willReturn(Completable.complete())
        comicViewModel.viewState.observeForever(testStateObserver)
        comicViewModel.onListScrolledToEnd()

        verify(testStateObserver).onChanged(State(Status.LOADING_MORE))
    }
}