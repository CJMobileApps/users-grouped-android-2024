package com.cjmobileapps.usersgroupedandroid.data.model

import com.cjmobileapps.usersgroupedandroid.data.MockData
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UserTest {

    @Test
    fun `User List toUsersGrouped() test`() {
        // Given
        val mockUsers1 = MockData.mockUsersInListId1.shuffled()
        val mockUsers2 = MockData.mockUsersInListId2.shuffled()
        val mockUsers3 = MockData.mockUsersInListId3.shuffled()

        val mockUserList = listOf(mockUsers1, mockUsers2, mockUsers3).flatten()

        // Then
        val mockUserGroups = mockUserList.toUsersGrouped()

        // Assertion
        Assertions.assertEquals(
            MockData.mockUsersGrouped,
            mockUserGroups
        )
    }
}