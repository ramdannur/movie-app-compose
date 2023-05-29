package id.ramdannur.movieappcompose.di

import id.ramdannur.movieappcompose.core.domain.usecase.MovieInteractor
import id.ramdannur.movieappcompose.core.domain.usecase.MovieUseCase
import id.ramdannur.movieappcompose.detail.DetailViewModel
import id.ramdannur.movieappcompose.favorite.FavoriteViewModel
import id.ramdannur.movieappcompose.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}