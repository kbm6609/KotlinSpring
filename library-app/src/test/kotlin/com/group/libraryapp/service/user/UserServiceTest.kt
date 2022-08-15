package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


//아래와 같이 @Autowired constructor 를 달면 아래 프로퍼티에 들어잇는 오토 와이어드를 생략가능
@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {


    @AfterEach
    fun clean() {
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("유저 저장")
    fun saveUserTest() {
        //given
        val request = UserCreateRequest("최태현", null)
        //when
        userService.saveUser(request)
        //then
        val results = userRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("최태현")
        assertThat(results[0].age).isNull()

    }

    @Test
    @DisplayName("유저 조회")
    fun getUsersTest() {
        //given
        userRepository.saveAll(
            listOf(
                User("A", 20),
                User("B", null)
            )
        )
        //when
        val results = userService.getUsers()

        //then
        assertThat(results).hasSize(2)
        assertThat(results).extracting("name").containsExactlyInAnyOrder("A", "B")
        assertThat(results).extracting("age").containsExactlyInAnyOrder(20, null)
    }

    @Test
    @DisplayName("유저 수정")
    fun updateUserNameTest() {
        //given
        val saveUser = userRepository.save(User("A", null))
        val request = UserUpdateRequest(saveUser.id!!, "B")
        //when
        userService.updateUserName(request)
        //then
        val result = userRepository.findAll()[0]
        assertThat(result.name).isEqualTo("B")

    }

    @Test
    @DisplayName("유저 삭제")
    fun deleteUserTest() {
        //given
        userRepository.save(User("A", null))
        //when
        userService.deleteUser("A")
        //then
        assertThat(userRepository.findAll()).isEmpty()
    }

    @Test
    @DisplayName("대출 기록이 없는 유저도 응답에 포함된다.")
    fun getUserLoanHistoriesTest1() {
        //given
        userRepository.save(User("A", null))
        //when
        val results = userService.getUserLoanHistories()
        //then
        assertThat(results[0].name).isEqualTo("A")
        assertThat(results[0].books).isEmpty()
    }

    @Test
    @DisplayName("대출 기록이 많은 유저의 응답이 정상 동작한다.")
    fun getUserLoanHistoriesTest2() {
        //given
        val saveUser = userRepository.save(User("A", null))
        userLoanHistoryRepository.saveAll(
            listOf(
                UserLoanHistory.fixture(saveUser, "책1", UserLoanStatus.LOANED),
                UserLoanHistory.fixture(saveUser, "책2", UserLoanStatus.LOANED),
                UserLoanHistory.fixture(saveUser, "책3", UserLoanStatus.RETURNED),
            )
        )

        //when
        val results = userService.getUserLoanHistories()
        //then
        assertThat(results[0].name).isEqualTo("A")
        assertThat(results[0].books).hasSize(3)
        assertThat(results[0].books).extracting("name")
            .containsExactlyInAnyOrder("책1", "책2", "책3")
        assertThat(results[0].books).extracting("isReturn")
            .containsExactlyInAnyOrder(false, false, true)
    }

}