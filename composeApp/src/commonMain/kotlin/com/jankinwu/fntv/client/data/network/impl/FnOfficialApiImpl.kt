package com.jankinwu.fntv.client.data.network.impl

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.jankinwu.fntv.client.data.model.FnBaseResponse
import com.jankinwu.fntv.client.data.model.MediaDbData
import com.jankinwu.fntv.client.data.model.SystemAccountData
import com.jankinwu.fntv.client.data.network.FnOfficialApi
import com.jankinwu.fntv.client.data.network.client
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import korlibs.crypto.MD5
import kotlinx.serialization.json.Json
import kotlin.random.Random
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


class FnOfficialApiImpl private constructor() : FnOfficialApi {

    companion object {
        @Volatile
        private var INSTANCE: FnOfficialApiImpl? = null

        fun getInstance(): FnOfficialApiImpl {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: FnOfficialApiImpl().also { INSTANCE = it }
            }
        }

        private const val api_key = "NDzZTVxnRKP8Z0jXg1VAMonaG8akvh"
        private const val api_secret = "16CCEB3D-AB42-077D-36A1-F355324E4237"

        private val json = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }

        val mapper = jacksonObjectMapper()
    }

    @OptIn(ExperimentalTime::class)
    private fun genAuthx(url: String, data: Any? = null): String {
        val nonce = generateRandomDigits()
        val timestamp = Clock.System.now().toEpochMilliseconds()
        val dataJson = data?.let { json.encodeToString(it) } ?: ""
        val dataJsonMd5 = if (dataJson.isNotEmpty()) getMd5(dataJson) else getMd5("")

        val signArray = arrayOf(
            api_key,
            url,
            nonce,
            timestamp.toString(),
            dataJsonMd5,
            api_secret
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

    override suspend fun getMediaDbList(): List<MediaDbData> {
        return try {
            // 验证基础URL
            if (SystemAccountData.fnOfficialBaseUrl.isBlank()) {
                throw IllegalArgumentException("飞牛官方URL未配置")
            }
            val url = "/v/api/v1/mediadb/list"
            val authx = genAuthx(url)
            println("authx: $authx")
            val response = client.get("${SystemAccountData.fnOfficialBaseUrl}$url") {
                header(HttpHeaders.Authorization, SystemAccountData.authorization)
                header(HttpHeaders.Accept, "application/json")
                header(HttpHeaders.ContentType, "application/json")
                header("Authx", authx)
            }
            // 先以字符串形式获取响应内容进行调试
            val responseString = response.bodyAsText()
            println("Response content: $responseString")

            // 然后解析为对象
            val body = mapper.readValue<FnBaseResponse<MediaDbData>>(responseString)
//            val body = Json.decodeFromString<FnBaseResponse<MediaDbData>>(responseString)

//            val body = response.body<FnBaseResponse<List<MediaDbData>>>()
            if (body.code != 0) {
                println("请求异常: ${body.msg}")
            }

            body.data ?: emptyList()
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

}


