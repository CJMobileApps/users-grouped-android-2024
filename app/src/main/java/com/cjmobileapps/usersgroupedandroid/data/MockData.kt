package com.cjmobileapps.usersgroupedandroid.data

import com.cjmobileapps.usersgroupedandroid.data.model.User
import com.cjmobileapps.usersgroupedandroid.data.model.UserGroup
import com.cjmobileapps.usersgroupedandroid.data.model.UsersGrouped

// TODO delete with bash script with release build
object MockData {

    val mockUsersInListId1 = listOf(
        User(
            listId = 1,
            name = "Item 0",
            id = 0
        ),
        User(
            listId = 1,
            name = "Item 101",
            id = 101
        ),
        User(
            listId = 1,
            name = "Item 110",
            id = 110
        )
    )

    val mockUsersInListId2 = listOf(
        User(
            listId = 2,
            name = "Item 105",
            id = 105
        ),
        User(
            listId = 2,
            name = "Item 122",
            id = 122
        ),
        User(
            listId = 2,
            name = "Item 139",
            id = 139
        )
    )

    val mockUsersInListId3 = listOf(
        User(
            listId = 3,
            name = "Item 102",
            id = 102
        ),
        User(
            listId = 3,
            name = "Item 102",
            id = 102
        ),
        User(
            listId = 3,
            name = "Item 132",
            id = 132
        )
    )

    val mockUserGroup1 = UserGroup(
        listId = 1,
        users = mockUsersInListId1
    )

    val mockUserGroup2 = UserGroup(
        listId = 2,
        users = mockUsersInListId2
    )

    val mockUserGroup3 = UserGroup(
        listId = 3,
        users = mockUsersInListId3
    )

    val mockUsersGrouped = UsersGrouped(
        userGroupList = listOf(
            mockUserGroup1,
            mockUserGroup2,
            mockUserGroup3
        )
    )
}