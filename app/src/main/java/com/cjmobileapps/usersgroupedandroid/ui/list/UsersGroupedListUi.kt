package com.cjmobileapps.usersgroupedandroid.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cjmobileapps.usersgroupedandroid.R
import com.cjmobileapps.usersgroupedandroid.data.MockData
import com.cjmobileapps.usersgroupedandroid.data.model.UserGroup
import com.cjmobileapps.usersgroupedandroid.data.model.UsersGrouped
import com.cjmobileapps.usersgroupedandroid.ui.UsersGroupedTopAppBar
import com.cjmobileapps.usersgroupedandroid.ui.list.viewmodel.UsersGroupedListViewModel
import com.cjmobileapps.usersgroupedandroid.ui.list.viewmodel.UsersGroupedListViewModelImpl
import com.cjmobileapps.usersgroupedandroid.ui.list.viewmodel.UsersGroupedListViewModelImpl.UsersGroupedListState
import com.cjmobileapps.usersgroupedandroid.ui.theme.UsersGroupedAndroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun UsersGroupedListUi(
    navController: NavController,
    usersGroupedListViewModel: UsersGroupedListViewModel,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        topBar = { UsersGroupedTopAppBar(navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { innerPadding ->
        Box {
            when (val state = usersGroupedListViewModel.getState()) {
                is UsersGroupedListState.LoadingState -> {
                    UsersGroupedLoadingUi(modifier = Modifier.padding(innerPadding))
                }

                is UsersGroupedListState.UsersGroupedListLoadedState -> {
                    UsersGroupedListLoadedUi(
                        modifier = Modifier.padding(innerPadding),
                        usersGroupedListViewModel = usersGroupedListViewModel,
                        usersLoadedState = state,
                    )
                }
            }
        }

        val snackbarMessage: String? =
            when (val state = usersGroupedListViewModel.getSnackbarState()) {
                is UsersGroupedListViewModelImpl.UsersGroupedListSnackbarState.Idle -> null

                is UsersGroupedListViewModelImpl.UsersGroupedListSnackbarState.ShowGenericError ->
                    state.error
                        ?: stringResource(R.string.some_error_occurred)

                is UsersGroupedListViewModelImpl.UsersGroupedListSnackbarState.UnableToGetUsersGroupedListError ->
                    stringResource(
                        R.string.unable_to_get_users,
                    )
            }

        if (snackbarMessage != null) {
            UsersGroupedSnackbar(
                message = snackbarMessage,
                coroutineScope = coroutineScope,
                snackbarHostState = snackbarHostState,
                usersGroupedListViewModel = usersGroupedListViewModel,
            )
        }
    }
}

@Composable
fun UsersGroupedSnackbar(
    message: String,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    usersGroupedListViewModel: UsersGroupedListViewModel,
) {
    LaunchedEffect(key1 = message) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message = message)
            usersGroupedListViewModel.resetSnackbarState()
        }
    }
}

@Composable
fun UsersGroupedLoadingUi(modifier: Modifier) {
    UsersGroupedNotFoundLoadingUi(
        modifier = modifier,
        showProgressBar = true,
    )
}

@Composable
fun UsersGroupedNotFoundLoadingUi(
    modifier: Modifier,
    showProgressBar: Boolean = false,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if(showProgressBar) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.users_not_found),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UsersGroupedNotFoundLoadingUiReview() {
    UsersGroupedAndroidTheme {
        UsersGroupedNotFoundLoadingUi(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            showProgressBar = true,
        )
    }
}

@Composable
fun UsersGroupedListLoadedUi(
    modifier: Modifier,
    usersGroupedListViewModel: UsersGroupedListViewModel,
    usersLoadedState: UsersGroupedListState.UsersGroupedListLoadedState,
) {
    val usersGrouped = usersLoadedState.users

    UsersGroupedListContentUi(
        modifier = modifier,
        usersGrouped = usersGrouped,
    )

    when (val navigateRouteUiValue = usersGroupedListViewModel.getUsersGroupedListNavRouteUiState()) {
        is UsersGroupedListViewModelImpl.UsersGroupedListNavRouteUi.Idle -> {}
    }
}

@Composable
fun UsersGroupedListContentUi(
    modifier: Modifier,
    usersGrouped: UsersGrouped,
) {
    if(usersGrouped.userGroupList.isEmpty()) {
        UsersGroupedNotFoundLoadingUi(
            modifier = modifier
        )
    } else {
        LazyColumn(
            modifier = modifier.padding(16.dp),
        ) {
            items(usersGrouped.userGroupList) { userGroup ->
                UsersGroupedCardUi(
                    userGroup = userGroup,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UsersGroupedListContentUiReview() {
    UsersGroupedAndroidTheme {
        UsersGroupedListContentUi(
            modifier = Modifier.fillMaxWidth(),
            usersGrouped = MockData.mockUsersGrouped,
        )
    }
}

@Composable
fun UsersGroupedCardUi(
    userGroup: UserGroup
) {
    ElevatedCard(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        colors =
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "ListId: ${userGroup.listId}",
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
            UsersGroupDetailUi(userGroup = userGroup)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UsersGroupedCardUiPreview() {
    UsersGroupedAndroidTheme {
        UsersGroupedCardUi(MockData.mockUserGroup1)
    }
}

@Composable
fun UsersGroupDetailUi(
    userGroup: UserGroup
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        userGroup.users.forEach { user ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = user.toString(),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UsersGroupDetailUiPreview() {
    UsersGroupedAndroidTheme {
        UsersGroupDetailUi(userGroup = MockData.mockUserGroup1)
    }
}
