package com.jankinwu.fntv.client.data.network.impl

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.jankinwu.fntv.client.data.model.SystemAccountData
import com.jankinwu.fntv.client.data.model.request.FavoriteRequest
import com.jankinwu.fntv.client.data.model.request.MediaListQueryRequest
import com.jankinwu.fntv.client.data.model.response.FnBaseResponse
import com.jankinwu.fntv.client.data.model.response.MediaDbListResponse
import com.jankinwu.fntv.client.data.model.response.MediaListQueryResponse
import com.jankinwu.fntv.client.data.model.response.PlayDetailResponse
import com.jankinwu.fntv.client.data.network.FnOfficialApi
import com.jankinwu.fntv.client.data.network.fnOfficialClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import korlibs.crypto.MD5
import kotlin.random.Random
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


class FnOfficialApiImpl() : FnOfficialApi {

    companion object {
        private const val API_KEY = "NDzZTVxnRKP8Z0jXg1VAMonaG8akvh"
        private const val API_SECRET = "16CCEB3D-AB42-077D-36A1-F355324E4237"

        val mapper = jacksonObjectMapper().apply {
            // 禁止格式化输出
            disable(SerializationFeature.INDENT_OUTPUT)
            // 忽略未知字段
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        }
    }

    override suspend fun getMediaDbList(): List<MediaDbListResponse> {
        return get("/v/api/v1/mediadb/list")
    }

    override suspend fun getMediaList(request: MediaListQueryRequest): MediaListQueryResponse {
        return post("/v/api/v1/item/list", request)
    }

    override suspend fun getPlayList(): List<PlayDetailResponse> {
        return get("/v/api/v1/play/list")
    }

    override suspend fun favorite(guid: String): Boolean {
        val favoriteRequest = FavoriteRequest(guid)
        return put("/v/api/v1/item/favorite", favoriteRequest)
    }

    override suspend fun cancelFavorite(guid: String): Boolean {
        val favoriteRequest = FavoriteRequest(guid)
        return delete("/v/api/v1/item/favorite", favoriteRequest)
    }

    override suspend fun watched(guid: String): Boolean {
        return post("/v/api/v1/item/watched")
    }

    override suspend fun cancelWatched(guid: String): Boolean {
        return delete("/v/api/v1/item/watched")
    }


    private suspend inline fun <reified T> get(
        url: String,
        noinline block: (HttpRequestBuilder.() -> Unit)? = null
    ): T {
        return try {
            if (SystemAccountData.fnOfficialBaseUrl.isBlank()) {
                throw IllegalArgumentException("飞牛官方URL未配置")
            }
            val authx = genAuthx(url)
            println("authx: $authx")
            val response = fnOfficialClient.get("${SystemAccountData.fnOfficialBaseUrl}$url") {
                header("Authx", authx)
                block?.invoke(this)
            }
            val responseString = response.bodyAsText()
            println("Get response content: $responseString")

            val body = mapper.readValue<FnBaseResponse<T>>(responseString)
            if (body.code != 0) {
                println("请求异常: ${body.msg}, url: $url")
                throw Exception("请求失败, url: $url, code: ${body.code}, msg: ${body.msg}")
            }

            body.data ?: throw Exception("返回数据为空")
        } catch (e: java.net.UnknownHostException) {
            throw Exception("网络连接失败，请检查网络设置", e)
        } catch (e: io.ktor.client.network.sockets.ConnectTimeoutException) {
            throw Exception("连接超时，请稍后重试", e)
        } catch (e: io.ktor.client.plugins.ClientRequestException) {
            throw Exception("请求失败: ${e.message}", e)
        } catch (e: Exception) {
            throw Exception("获取数据失败: ${e.message}", e)
        }
    }

    private suspend inline fun <reified T> post(
        url: String,
        body: Any? = null,
        noinline block: (HttpRequestBuilder.() -> Unit)? = null
    ): T {
        return try {
            // 校验 baseURL 是否存在
            if (SystemAccountData.fnOfficialBaseUrl.isBlank()) {
                throw IllegalArgumentException("飞牛官方URL未配置")
            }

            val authx = genAuthx(url, body)
            println("authx: $authx")

            val response = fnOfficialClient.post("${SystemAccountData.fnOfficialBaseUrl}$url") {
                header(HttpHeaders.ContentType, "application/json; charset=utf-8")
                header("Authx", authx)
                if (body != null) {
                    setBody(body)
                }
                block?.invoke(this)
            }

            val responseString = response.bodyAsText()
            println("POST response content: $responseString")

            // 解析为对象
            val responseBody = mapper.readValue<FnBaseResponse<T>>(responseString)
            if (responseBody.code != 0) {
                println("请求异常: ${responseBody.msg}, url: $url, request body: $body")
                throw Exception("请求失败, url: $url, code: ${responseBody.code}, msg: ${responseBody.msg}")
            }

            responseBody.data ?: throw Exception("返回数据为空")
        } catch (e: java.net.UnknownHostException) {
            throw Exception("网络连接失败，请检查网络设置", e)
        } catch (e: io.ktor.client.network.sockets.ConnectTimeoutException) {
            throw Exception("连接超时，请稍后重试", e)
        } catch (e: io.ktor.client.plugins.ClientRequestException) {
            throw Exception("请求失败: ${e.message}", e)
        } catch (e: Exception) {
            throw Exception("获取数据失败: ${e.message}", e)
        }
    }

    private suspend inline fun <reified T> put(
        url: String,
        body: Any? = null,
        noinline block: (HttpRequestBuilder.() -> Unit)? = null
    ): T {
        return try {
            // 校验 baseURL 是否存在
            if (SystemAccountData.fnOfficialBaseUrl.isBlank()) {
                throw IllegalArgumentException("飞牛官方URL未配置")
            }

            val authx = genAuthx(url, body)
            println("authx: $authx")

            val response = fnOfficialClient.put("${SystemAccountData.fnOfficialBaseUrl}$url") {
                header(HttpHeaders.ContentType, "application/json; charset=utf-8")
                header("Authx", authx)
                if (body != null) {
                    setBody(body)
                }
                block?.invoke(this)
            }

            val responseString = response.bodyAsText()
            println("PUT response content: $responseString")

            // 解析为对象
            val responseBody = mapper.readValue<FnBaseResponse<T>>(responseString)
            if (responseBody.code != 0) {
                println("请求异常: ${responseBody.msg}, url: $url, request body: $body")
                throw Exception("请求失败, url: $url, code: ${responseBody.code}, msg: ${responseBody.msg}")
            }

            responseBody.data ?: throw Exception("返回数据为空")
        } catch (e: java.net.UnknownHostException) {
            throw Exception("网络连接失败，请检查网络设置", e)
        } catch (e: io.ktor.client.network.sockets.ConnectTimeoutException) {
            throw Exception("连接超时，请稍后重试", e)
        } catch (e: io.ktor.client.plugins.ClientRequestException) {
            throw Exception("请求失败: ${e.message}", e)
        } catch (e: Exception) {
            throw Exception("获取数据失败: ${e.message}", e)
        }
    }

    private suspend inline fun <reified T> delete(
        url: String,
        body: Any? = null,
        noinline block: (HttpRequestBuilder.() -> Unit)? = null
    ): T {
        return try {
            // 校验 baseURL 是否存在
            if (SystemAccountData.fnOfficialBaseUrl.isBlank()) {
                throw IllegalArgumentException("飞牛官方URL未配置")
            }

            val authx = genAuthx(url, body)
            println("authx: $authx")

            val response = fnOfficialClient.delete("${SystemAccountData.fnOfficialBaseUrl}$url") {
                header(HttpHeaders.ContentType, "application/json; charset=utf-8")
                header("Authx", authx)
                if (body != null) {
                    setBody(body)
                }
                block?.invoke(this)
            }

            val responseString = response.bodyAsText()
            println("Delete response content: $responseString")

            // 解析为对象
            val responseBody = mapper.readValue<FnBaseResponse<T>>(responseString)
            if (responseBody.code != 0) {
                println("请求异常: ${responseBody.msg}, url: $url, request body: $body")
                throw Exception("请求失败, url: $url, code: ${responseBody.code}, msg: ${responseBody.msg}")
            }

            responseBody.data ?: throw Exception("返回数据为空")
        } catch (e: java.net.UnknownHostException) {
            throw Exception("网络连接失败，请检查网络设置", e)
        } catch (e: io.ktor.client.network.sockets.ConnectTimeoutException) {
            throw Exception("连接超时，请稍后重试", e)
        } catch (e: io.ktor.client.plugins.ClientRequestException) {
            throw Exception("请求失败: ${e.message}", e)
        } catch (e: Exception) {
            throw Exception("获取数据失败: ${e.message}", e)
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun genAuthx(url: String, data: Any? = null): String {
        val nonce = generateRandomDigits()
        val timestamp = Clock.System.now().toEpochMilliseconds()
        val dataJson = data?.let { mapper.writeValueAsString(it) } ?: ""
        val dataJsonMd5 = if (dataJson.isNotEmpty()) getMd5(dataJson) else getMd5("")

        val signArray = arrayOf(
            API_KEY,
            url,
            nonce,
            timestamp.toString(),
            dataJsonMd5,
            API_SECRET
        )

        val signStr = signArray.joinToString("_")
        val sign = getMd5(signStr)
        return "nonce=$nonce&timestamp=$timestamp&sign=${sign}"
    }

    private fun generateRandomDigits(length: Int = 6): String {
        return (1..length).joinToString("") {
            Random.nextInt(0, 10).toString()
        }
    }

    private fun getMd5(input: String): String {
        return MD5.digest(input.toByteArray(Charsets.UTF_8)).hex
    }
}


