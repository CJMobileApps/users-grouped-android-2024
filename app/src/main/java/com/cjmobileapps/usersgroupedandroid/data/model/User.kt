package com.cjmobileapps.usersgroupedandroid.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val listId: Long,
    val name: String?,
    @PrimaryKey val id: Long,
)

data class UsersGrouped(
    val userGroupList: List<UserGroup>
)

data class UserGroup(
    val listId: Long,
    val users: List<User>,
)

fun List<User>.toUsersGrouped(): UsersGrouped {
    val userGroupList = mutableListOf<UserGroup>()

    val usersGroupedMap = this.groupBy { it.listId }

    usersGroupedMap.forEach { (listId, users) ->
        val userGroup = UserGroup(
            listId = listId,
            users = users
                .filter { !it.name.isNullOrEmpty() }
                .sortedBy { it.name }
        )
        userGroupList.add(userGroup)
    }

    return UsersGrouped(userGroupList = userGroupList.sortedBy { it.listId })
}
