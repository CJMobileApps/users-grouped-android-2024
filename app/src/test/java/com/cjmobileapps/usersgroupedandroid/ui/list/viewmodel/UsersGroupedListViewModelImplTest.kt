package com.cjmobileapps.usersgroupedandroid.ui.list.viewmodel

import com.cjmobileapps.usersgroupedandroid.data.MockData
import com.cjmobileapps.usersgroupedandroid.data.model.UsersGrouped
import com.cjmobileapps.usersgroupedandroid.data.usersgrouped.UsersGroupedUseCase
import com.cjmobileapps.usersgroupedandroid.testutil.BaseTest
import com.cjmobileapps.usersgroupedandroid.ui.list.viewmodel.UsersGroupedListViewModelImpl.UsersGroupedListState
import com.cjmobileapps.usersgroupedandroid.util.TestCoroutineDispatchers
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor

class UsersGroupedListViewModelImplTest : BaseTest() {
    private lateinit var usersGroupedListViewModel: UsersGroupedListViewModel

    @Mock
    lateinit var mockUsersGroupedUseCase: UsersGroupedUseCase

    private val onErrorArgumentCaptor =
        argumentCaptor<() -> Unit>()

    private val onUsersResponseArgumentCaptor =
        argumentCaptor<(UsersGrouped) -> Unit>()

    private fun setupViewModel() {
        usersGroupedListViewModel = UsersGroupedListViewModelImpl(
            usersGroupedUseCase = mockUsersGroupedUseCase,
            coroutineDispatchers = TestCoroutineDispatchers,
        )
    }

    @Test
    fun `usersGroupedListViewModel apis never init`() {
        // then
        setupViewModel()
        var state = usersGroupedListViewModel.getState()

        // verify
        Assertions.assertTrue(state is UsersGroupedListState.LoadingState)

        // then
        var userGroupedListRouteUi = usersGroupedListViewModel.getUsersGroupedListNavRouteUiState()
        state = usersGroupedListViewModel.getState()

        // verify
        Assertions.assertTrue(state is UsersGroupedListState.LoadingState)
        Assertions.assertTrue(userGroupedListRouteUi is UsersGroupedListViewModelImpl.UsersGroupedListNavRouteUi.Idle)

        // then
        usersGroupedListViewModel.resetNavRouteUiToIdle()
        userGroupedListRouteUi = usersGroupedListViewModel.getUsersGroupedListNavRouteUiState()
        state = usersGroupedListViewModel.getState()
        Assertions.assertTrue(state is UsersGroupedListState.LoadingState)
        Assertions.assertTrue(userGroupedListRouteUi is UsersGroupedListViewModelImpl.UsersGroupedListNavRouteUi.Idle)
    }

    @Test
    fun `fetch users happy flow`() = runTest {
        // then init setup
        setupViewModel()
        var state = usersGroupedListViewModel.getState()

        // verify
        Assertions.assertTrue(state is UsersGroupedListState.LoadingState)

        // when
        Mockito.`when`(mockUsersGroupedUseCase.fetchUsersApi(onErrorArgumentCaptor.capture())).thenReturn(Unit)
        Mockito.`when`(mockUsersGroupedUseCase.getUsersFromDB(
            onUsersResponse = onUsersResponseArgumentCaptor.capture(),
            onError = onErrorArgumentCaptor.capture()
        )).thenReturn(Unit)

        // then
        setupViewModel()
        onUsersResponseArgumentCaptor.firstValue.invoke(MockData.mockUsersGrouped)
        state = usersGroupedListViewModel.getState()

        // verify
        Assertions.assertTrue((state is UsersGroupedListState.UsersGroupedListLoadedState))
        if (state !is UsersGroupedListState.UsersGroupedListLoadedState) return@runTest

        Assertions.assertEquals(
            MockData.mockUsersGrouped,
            state.users
        )
        Assertions.assertTrue(state.usersNavRouteUi.value is UsersGroupedListViewModelImpl.UsersGroupedListNavRouteUi.Idle)
    }
}
