package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.UserLoanHistoryResponse
import com.group.libraryapp.dto.user.response.UserResponse
import com.group.libraryapp.util.fail
import com.group.libraryapp.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService constructor(
    private val userRepository: UserRepository,
) {
    @Transactional
    fun saveUser(request: UserCreateRequest) {
        val newUser = User(request.name, request.age)
        userRepository.save(newUser)
    }

    @Transactional(readOnly = true)
    fun getUsers(): List<UserResponse> {
        return userRepository.findAll().map { user ->
            UserResponse.of(user)
        }
    }

    @Transactional
    fun updateUserName(request: UserUpdateRequest) {
        val updateUser = userRepository.findByIdOrThrow(request.id)
        updateUser.updateName(request.name)
    }

    @Transactional
    fun deleteUser(name: String) {
        val deleteUser = userRepository.findByName(name) ?: fail()
        userRepository.delete(deleteUser)
    }

    @Transactional(readOnly = true)
    fun getUserLoanHistories(): List<UserLoanHistoryResponse> {
        return userRepository.findAllWithHistories().map(UserLoanHistoryResponse::of)
    }
}

