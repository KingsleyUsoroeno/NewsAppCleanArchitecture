package com.techkingsley.newsappcleanarchitecture.business.interactors

import android.util.Log
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

sealed class ResultWrapper<out T> {
    object Loading : ResultWrapper<Nothing>()
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val errorResponse: ErrorResponse?) : ResultWrapper<Nothing>()
    class NetworkError(val exception: Exception) : ResultWrapper<Nothing>()
}

data class ErrorResponse(@SerializedName("status") val status: String, @SerializedName("code") val code: String, @SerializedName("message") val message: String)

suspend fun <T : Any> safeApiResult(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    call: suspend () -> Response<T>
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            val response = call.invoke()
            if (response.isSuccessful && response.body() != null && response.errorBody() == null) {
                Log.i("response body", "${response.body()}")
                ResultWrapper.Success(response.body()!!)
            } else {
                ResultWrapper.GenericError(extractErrorBody(response.errorBody()))
            }
        } catch (e: Exception) {
            println(e)
            e.printStackTrace()
            ResultWrapper.NetworkError(e)
        }
    }
}

private fun extractErrorBody(response: ResponseBody?): ErrorResponse? {
    return try {
        response?.let {
            val json = JSONObject(it.string())
            val status = json.optString("status")
            val code = json.optString("code")
            val message = json.optString("message")
            ErrorResponse(status, code, message)
        }

    } catch (exception: Exception) {
        null
    }
}

inline fun <reified T> ResultWrapper<T>.doIfFailure(callback: (error: ErrorResponse) -> Unit) {
    if (this is ResultWrapper.GenericError) {
        if (errorResponse != null) {
            callback(errorResponse)
        }
    }
}

inline fun <reified T> ResultWrapper<T>.doIfNetworkException(callback: (error: Exception) -> Unit) {
    if (this is ResultWrapper.NetworkError) {
        callback(exception)
    }
}

inline fun <reified T> ResultWrapper<T>.doIfSuccess(callback: (value: T) -> Unit) {
    if (this is ResultWrapper.Success) {
        callback(value)
    }
}