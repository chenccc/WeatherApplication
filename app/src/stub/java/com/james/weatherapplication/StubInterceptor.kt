package com.james.weatherapplication

import android.content.Context
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class StubInterceptor(
    context: Context,
    private val postfix: String = ".json"
): Interceptor {
    private val assetManager = context.assets

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url
        url.let {
            val path = "stub_api/delivery_from_offset_"

            return try {
                buildResponse(request, url, path)
            } catch (ex: IOException) {
                buildResponse(request, 500, ex.localizedMessage)
            }
        }
    }

    /**
     *      example code to handle the api specific error
     *      if (apiUrl.endsWith("<api path>",ignoreCase = true)
     *      && httpMethod.equals("GET", ignoreCase = true))
     *          return <response code>
     */
    private fun getApiSpecificResponseCode(
        apiUrl: String,
        httpMethod: String
    ): Int? {
        //add api specific response code here
        if (apiUrl.contains("error")) {
            return 500
        }
        //default return
        return null
    }

    @Throws(IOException::class)
    private fun buildResponse(request: Request, url: HttpUrl, filepath: String): Response {
        val responseCode = getApiSpecificResponseCode(url.encodedPath, request.method) ?: 200
        val offset = url.queryParameter("offset")
        val filename = filepath + offset + postfix
        val json = getJsonFromFile(filename)
        return buildResponse(request, responseCode, json)
    }

    private fun buildResponse(request: Request, code: Int, message: String): Response {
        //emulate loading status
        Thread.sleep(1000L)
        return Response.Builder()
            .body(message.toResponseBody(null))
            .request(request)
            .message(message)
            .protocol(Protocol.HTTP_2)
            .code(code)
            .build()
    }

    @Throws(IOException::class)
    private fun getJsonFromFile(path: String): String {
        val inputStream = assetManager.open(path)
        inputStream.use { ins ->
            return parseStream(ins)
        }
    }

    @Throws(IOException::class)
    private fun parseStream(stream: InputStream): String {
        val builder = StringBuilder()
        val inputStream = BufferedReader(InputStreamReader(stream, "UTF-8"))
        var line = inputStream.readLine()
        while (line != null) {
            builder.append(line)
            line = inputStream.readLine()
        }
        return builder.toString()
    }

}