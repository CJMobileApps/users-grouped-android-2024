package com.cjmobileapps.usersgroupedandroid.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cjmobileapps.usersgroupedandroid.ui.list.UsersGroupedListUi
import com.cjmobileapps.usersgroupedandroid.ui.list.viewmodel.UsersGroupedListViewModel
import com.cjmobileapps.usersgroupedandroid.ui.list.viewmodel.UsersGroupedListViewModelImpl
import kotlinx.coroutines.CoroutineScope


@Composable
fun NavigationGraph(
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    NavHost(navController = navController, startDestination = NavItem.UsersGroupedList.navRoute) {
        composable(NavItem.UsersGroupedList.navRoute) {
            val usersGroupedListViewModel: UsersGroupedListViewModel = hiltViewModel<UsersGroupedListViewModelImpl>()

            UsersGroupedListUi(
                navController = navController,
                usersGroupedListViewModel = usersGroupedListViewModel,
                coroutineScope = coroutineScope,
                snackbarHostState = snackbarHostState,
            )
        }
    }
}

sealed class NavItem(
    val navRoute: String,
    val arguments: List<NamedNavArgument> = emptyList(),
) {
    data object UsersGroupedList : NavItem(navRoute = "nav_users_grouped_list")
}
