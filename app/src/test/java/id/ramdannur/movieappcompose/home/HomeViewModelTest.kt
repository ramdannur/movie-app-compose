package id.ramdannur.movieappcompose.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.ramdannur.movieappcompose.DataDummy
import id.ramdannur.movieappcompose.MainCoroutineRule
import id.ramdannur.movieappcompose.core.domain.model.Movie
import id.ramdannur.movieappcompose.core.domain.usecase.MovieUseCase
import id.ramdannur.movieappcompose.core.ui.common.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieUseCase: MovieUseCase
    private lateinit var viewModel: HomeViewModel

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = HomeViewModel(movieUseCase)
    }

    @Test
    fun `test setKeyword() sets the keyword`() {
        // Arrange
        val keyword = "Action"

        // Act
        viewModel.setKeyword(keyword)

        // Assert
        assertEquals(keyword, viewModel.keyword)
    }

    @Test
    fun `test getAllMovies() emits success state with movie list`() =
        runTest {
            val movies = DataDummy.generateDummyMovie()
            val successState = UiState.Success(movies)

            Mockito.`when`(movieUseCase.getDiscoverMovie()).thenReturn(flowOf(successState))

            // Act
            viewModel.getAllMovies()

            Mockito.verify(movieUseCase).getDiscoverMovie()

            // Collect the emitted values using the testCoroutineRule's testDispatcher
            val observedValues = mutableListOf<UiState<List<Movie>>>()
            val job = launch {
                viewModel.uiState
                    .collect { observedValues.add(it) }
            }

            // Yield the thread to allow other coroutines to execute
            advanceUntilIdle()

            // Assert
            val lastState = observedValues.last()
            Assert.assertNotNull(lastState)
            Assert.assertTrue(lastState is UiState.Success)
            assertEquals(successState, lastState)

            // Cancel the job to prevent a timeout error
            job.cancel()
        }

    @Test
    fun `test getAllMovies() emits error state`() = runTest {
        // Arrange
        val errorMessage = "Error fetching movies"
        val exception = RuntimeException(errorMessage)

        // Mock the movieUseCase.getDiscoverMovie() function to throw an exception
        Mockito.`when`(movieUseCase.getDiscoverMovie()).thenReturn(flow {
            throw exception
        })

        // Act
        viewModel.getAllMovies()

        // Collect the emitted values using the testCoroutineRule's testDispatcher
        val observedValues = mutableListOf<UiState<List<Movie>>>()

        val job = launch {
            viewModel.uiState
                .collect { observedValues.add(it) }
        }

        // Yield the thread to allow other coroutines to execute
        advanceUntilIdle()

        // Assert the collected values
        val errorState = observedValues.last() as UiState.Error
        assertEquals(errorMessage, errorState.errorMessage)

        // Cancel the job to prevent a timeout error
        job.cancel()
    }

    @Test
    fun `test searchMovie() emits success state with movie list`() =
        runTest {
            // Arrange
            val keyword = "Action"
            val movies = DataDummy.generateDummyMovie()
            val successState = UiState.Success(movies)

            Mockito.`when`(movieUseCase.getSearchMovie(keyword.trim()))
                .thenReturn(flowOf(successState))

            // Act
            viewModel.searchMovie(keyword)

            Mockito.verify(movieUseCase).getSearchMovie(keyword)

            // Collect the emitted values using the testCoroutineRule's testDispatcher
            val observedValues = mutableListOf<UiState<List<Movie>>>()
            val job = launch {
                viewModel.uiState
                    .collect { observedValues.add(it) }
            }

            // Yield the thread to allow other coroutines to execute
            advanceUntilIdle()

            // Assert
            val lastState = observedValues.last()
            Assert.assertNotNull(lastState)
            Assert.assertTrue(lastState is UiState.Success)
            assertEquals(successState, lastState)

            // Cancel the job to prevent a timeout error
            job.cancel()
        }

    @Test
    fun `test searchMovie() emits error state`() = runTest {
        // Arrange
        val keyword = "Action"
        val errorMessage = "Error fetching movies"
        val exception = RuntimeException(errorMessage)

        // Mock the movieUseCase.getDiscoverMovie() function to throw an exception
        Mockito.`when`(movieUseCase.getSearchMovie(keyword)).thenReturn(flow {
            throw exception
        })

        // Act
        viewModel.searchMovie(keyword)

        // Collect the emitted values using the testCoroutineRule's testDispatcher
        val observedValues = mutableListOf<UiState<List<Movie>>>()

        val job = launch {
            viewModel.uiState
                .collect { observedValues.add(it) }
        }

        // Yield the thread to allow other coroutines to execute
        advanceUntilIdle()

        // Assert the collected values
        val errorState = observedValues.last() as UiState.Error
        assertEquals(errorMessage, errorState.errorMessage)

        // Cancel the job to prevent a timeout error
        job.cancel()
    }
}