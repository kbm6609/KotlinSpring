package com.group.libraryapp.domain.user

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import javax.persistence.*

@Entity
class User(
    var name: String,
    val age: Int?,
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
    init {
        if(name.isBlank()){
            throw  IllegalArgumentException("이름은 비어 있을 수 없습니다")
        }
    }

    fun updateName(name: String) {
        this.name = name
    }
    fun loanBook(book : Book){
        this.userLoanHistories.add(UserLoanHistory.fixture(this,book.name))
    }
    fun returnBook(bookName: String) {
        /**
         * first 에 람다를 넣을수가 있다
         * 해당 로직은 첫번째로 북네임이 같은 객체를 찾아서 반환해주는 로직이다.
         */
        this.userLoanHistories.first { history ->
            history.bookName == bookName
        }.doReturn()
    }
}