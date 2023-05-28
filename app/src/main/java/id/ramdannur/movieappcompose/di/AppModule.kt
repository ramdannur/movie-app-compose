package id.ramdannur.movieappcompose.di

import id.ramdannur.movieappcompose.ui.screen.detail.DetailViewModel
import id.ramdannur.movieappcompose.ui.screen.favorite.FavoriteViewModel
import id.ramdannur.movieappcompose.ui.screen.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}