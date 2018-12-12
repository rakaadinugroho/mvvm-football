package xyz.rakalabs.englishfootball.data.remote

sealed class APIResponse<T> {
    data class Success<T>(val body: T): APIResponse<T>()
    data class Error<T>(val errorMessage: String, val errorCode: Int): APIResponse<T>()
}