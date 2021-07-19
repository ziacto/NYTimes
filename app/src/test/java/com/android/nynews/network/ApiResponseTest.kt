package com.android.nynews.network

import com.android.nynews.network.response.ApiResponse
import com.android.nynews.network.response.ApiSuccessResponse
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class ApiResponseTest {

    @Test
    fun exception() {
        val exception = Exception("IOException")
        val apiResponse = ApiResponse.create<String>(exception)
        MatcherAssert.assertThat(apiResponse.errorMessage, CoreMatchers.`is`("IOException"))
    }

    @Test
    fun success() {
        val apiResponse = ApiResponse.create(Response.success("response"))
        if (apiResponse is ApiSuccessResponse) {
            MatcherAssert.assertThat(apiResponse.body, CoreMatchers.`is`("response"))
        }
    }
}