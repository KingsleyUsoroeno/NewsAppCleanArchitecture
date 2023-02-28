package com.techkingsley.remote.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response

//sealed class ResultWrapper<out T> {
//    data class Success<out T>(val value: T) : ResultWrapper<T>()
//    data class GenericError(val errorResponse: Any?) : ResultWrapper<Nothing>()
//    class NetworkError(val exception: Exception) : ResultWrapper<Nothing>()
//}
//
//suspend fun <T : Any> safeApiResult(
//    dispatcher: CoroutineDispatcher = Dispatchers.IO,
//    genericError: (error: ResponseBody?) -> Any?,
//    call: suspend () -> Response<T>
//): ResultWrapper<T> {
//    return withContext(dispatcher) {
//        try {
//            val response = call.invoke()
//            if (response.isSuccessful && response.body() != null && response.errorBody() == null) {
//                ResultWrapper.Success(response.body()!!)
//            } else {
//                ResultWrapper.GenericError(genericError(response.errorBody()))
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            ResultWrapper.NetworkError(e)
//        }
//    }
//}