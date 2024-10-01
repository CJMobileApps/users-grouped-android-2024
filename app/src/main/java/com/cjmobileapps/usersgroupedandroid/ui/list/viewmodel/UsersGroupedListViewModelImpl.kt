package com.cjmobileapps.usersgroupedandroid.ui.list.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cjmobileapps.usersgroupedandroid.util.coroutine.CoroutineDispatchers
import com.cjmobileapps.usersgroupedandroid.data.model.UsersGrouped
import com.cjmobileapps.usersgroupedandroid.data.usersgrouped.UsersGroupedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UsersGroupedListViewModelImpl
    @Inject
    constructor(
        usersGroupedUseCase: UsersGroupedUseCase,
        coroutineDispatchers: CoroutineDispatchers,
    ): ViewModel(), UsersGroupedListViewModel  {
        private val compositeJob = Job()

        private val exceptionHandler =
            CoroutineExceptionHandler { _, throwable ->
                Timber.tag(tag)
                    .e("coroutineExceptionHandler() error occurred: $throwable \n ${throwable.message}")
                snackbarState.value = UsersGroupedListSnackbarState.ShowGenericError()
            }

        private val coroutineContext =
            compositeJob + coroutineDispatchers.main + exceptionHandler + SupervisorJob()

        private val coroutineContextUsersFlow =
            compositeJob + coroutineDispatchers.main + exceptionHandler + SupervisorJob()

        private val usersState = mutableStateOf<UsersGroupedListState>(UsersGroupedListState.LoadingState)

        private val snackbarState = mutableStateOf<UsersGroupedListSnackbarState>(UsersGroupedListSnackbarState.Idle)

        private val tag = UsersGroupedListViewModelImpl::class.java.simpleName

        override fun getState() = usersState.value

        override fun getSnackbarState() = snackbarState.value

        init {
            viewModelScope.launch(coroutineContext) {
                usersGroupedUseCase.fetchUsersApi(onError = {
                    usersState.value = UsersGroupedListState.UsersGroupedListLoadedState()
                    snackbarState.value =
                        UsersGroupedListSnackbarState.UnableToGetUsersGroupedListError()
                })
            }

            viewModelScope.launch(coroutineContextUsersFlow) {
                usersGroupedUseCase.getUsersFromDB(
                    onUsersResponse = { users ->
                        usersState.value =
                            UsersGroupedListState.UsersGroupedListLoadedState(users = users)
                    },
                    onError = {
                        usersState.value = UsersGroupedListState.UsersGroupedListLoadedState()
                        snackbarState.value =
                            UsersGroupedListSnackbarState.UnableToGetUsersGroupedListError()
                    }
                )
            }
        }

        override fun resetSnackbarState() {
            snackbarState.value = UsersGroupedListSnackbarState.Idle
        }

        override fun getUsersGroupedListNavRouteUiState(): UsersGroupedListNavRouteUi {
            val state = getState()
            if (state !is UsersGroupedListState.UsersGroupedListLoadedState) return UsersGroupedListNavRouteUi.Idle
            return state.usersNavRouteUi.value
        }

        override fun resetNavRouteUiToIdle() {
            val state = getState()
            if (state !is UsersGroupedListState.UsersGroupedListLoadedState) return
            state.usersNavRouteUi.value = UsersGroupedListNavRouteUi.Idle
        }

        sealed class UsersGroupedListState {
            data object LoadingState : UsersGroupedListState()

            data class UsersGroupedListLoadedState(
                val users: UsersGrouped = UsersGrouped(emptyList()),
                val usersNavRouteUi: MutableState<UsersGroupedListNavRouteUi> = mutableStateOf(UsersGroupedListNavRouteUi.Idle),
            ) : UsersGroupedListState()
        }

        sealed class UsersGroupedListSnackbarState {
            data object Idle : UsersGroupedListSnackbarState()

            data class ShowGenericError(
                val error: String? = null,
            ) : UsersGroupedListSnackbarState()

            data class UnableToGetUsersGroupedListError(
                val error: String? = null,
            ) : UsersGroupedListSnackbarState()
        }

        sealed class UsersGroupedListNavRouteUi {
            data object Idle : UsersGroupedListNavRouteUi()
        }
    }
