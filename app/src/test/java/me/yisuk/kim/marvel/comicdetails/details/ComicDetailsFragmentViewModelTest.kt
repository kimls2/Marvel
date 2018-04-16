package me.yisuk.kim.marvel.comicdetails.details

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import me.yisuk.kim.marvel.RxSchedulers
import me.yisuk.kim.marvel.data.entities.MarvelComic
import me.yisuk.kim.marvel.remote.calls.ComicDetailsCall
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * @author [Yisuk Kim](kimls125@gmail.com) on 12-04-2018.
 */
class ComicDetailsFragmentViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var comicDetailsCall: ComicDetailsCall
    @Mock
    private lateinit var testDataObserver: Observer<MarvelComic>

    private lateinit var comicDetailsFragmentViewModel: ComicDetailsFragmentViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val schedulers = RxSchedulers(Schedulers.trampoline(), Schedulers.trampoline(), Schedulers.trampoline())
        comicDetailsFragmentViewModel = ComicDetailsFragmentViewModel(schedulers, comicDetailsCall)
    }

    @Test
    fun liveDataValue_shouldReturnNull_whenComicIdIsNull() {
        comicDetailsFragmentViewModel.comicId = null
        comicDetailsFragmentViewModel.data.observeForever(testDataObserver)
        assertThat(comicDetailsFragmentViewModel.data.value, nullValue())
    }

    @Test
    fun setUpLiveData_success() {
        val comicId = 1234L
        val mockData = MarvelComic(comicId, 20, "Test title", "Test description")
        given(comicDetailsCall.refresh(comicId)).willReturn(Completable.complete())
        given(comicDetailsCall.data(comicId)).willReturn(Flowable.just(mockData))

        comicDetailsFragmentViewModel.comicId = comicId
        comicDetailsFragmentViewModel.data.observeForever(testDataObserver)

        assertThat(comicDetailsFragmentViewModel.data.value, `is`(mockData))
        assertThat(comicDetailsFragmentViewModel.data.value?.marvelComicId, `is`(mockData.marvelComicId))
        assertThat(comicDetailsFragmentViewModel.data.value?.title, `is`(mockData.title))
        assertThat(comicDetailsFragmentViewModel.data.value?.description, `is`(mockData.description))
    }
}