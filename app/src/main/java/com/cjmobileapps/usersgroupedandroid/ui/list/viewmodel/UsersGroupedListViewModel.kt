package com.cjmobileapps.usersgroupedandroid.ui.list.viewmodel

interface UsersGroupedListViewModel {
    fun getState(): UsersGroupedListViewModelImpl.UsersGroupedListState

    fun getSnackbarState(): UsersGroupedListViewModelImpl.UsersGroupedListSnackbarState

    fun getUsersGroupedListNavRouteUiState(): UsersGroupedListViewModelImpl.UsersGroupedListNavRouteUi

    fun resetSnackbarState()

    fun resetNavRouteUiToIdle()
}
