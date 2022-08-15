package com.group.libraryapp.calculator

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class JunitCalculatorTest {

    @Test
    fun addTest(){
        //given
        val calculator =Calculator(5)

        //when
        calculator.add(3)
        //then
        assertThat(calculator.number).isEqualTo(8)
    }
    @Test
    fun minusTest(){
        //given
        val calculator =Calculator(5)

        //when
        calculator.minus(3)
        //then
        assertThat(calculator.number).isEqualTo(2)
    }
    @Test
    fun multiplyTest(){
        //given
        val calculator =Calculator(5)

        //when
        calculator.multiply(3)
        //then
        assertThat(calculator.number).isEqualTo(15)
    }
    @Test
    fun divideTest(){
        //given
        val calculator =Calculator(5)

        //when
        calculator.divide(3)
        //then
        assertThat(calculator.number).isEqualTo(1)
    }
    @Test
    fun divideExceptionTest(){
        //given
        val calculator =Calculator(5)

//        //when
//        val message = assertThrows<IllegalArgumentException> {
//            calculator.divide(0)
//        }.message
//        //then
//        assertThat(message).isEqualTo("0으로 나눌수 없습니다.")
        //위 부분을 아래와 같이 바꿀수도잇다.

        //when
        //then
        assertThrows<IllegalArgumentException> {
            calculator.divide(0)
        }.apply {
            assertThat(message).isEqualTo("0으로 나눌수 없습니다.")
        }
    }
}
