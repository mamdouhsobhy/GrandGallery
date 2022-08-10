package com.example.grandgallery.core.presentation.base

sealed class BaseResult <out T> {
    data class DataState<T: Any>( val items: T?): BaseResult<T>()
    data class ErrorState(val errorCode: String, val errorMessage: String): BaseResult<Nothing>()
}