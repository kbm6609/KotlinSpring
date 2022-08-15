package com.group.libraryapp.calculator

import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class Calculator(
    private var _number:Int,
)
{
    val ch ='A'
    fun checkType(){
        println(ch.javaClass)
    }
    val number: Int
        get() = this._number
    fun add(operation : Int){
        this._number +=operation
    }
    fun minus(operation: Int) {
        this._number -=operation
    }
    fun multiply(operation: Int) {
        this._number *=operation
    }
    fun divide(operation: Int) {
        if(operation == 0)
            throw IllegalArgumentException("0으로 나눌수 없습니다.")
        this._number /= operation
    }
}
