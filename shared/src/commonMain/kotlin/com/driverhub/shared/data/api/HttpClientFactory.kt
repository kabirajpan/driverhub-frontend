package com.driverhub.shared.data.api

import com.driverhub.shared.domain.repository.TokenStorage
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * HTTP Client Factory
 * Creates configured Ktor HttpClient instance
 * NOTE: Auth headers must be added per-request since TokenStorage is async
 */
object HttpClientFactory {
    
    fun create(): HttpClient {
        return HttpClient {
            // JSON Serialization
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            
            // Logging (keep for debugging, can be disabled in production)
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.BODY
            }
            
            // HTTP Timeout Configuration
            install(HttpTimeout) {
                requestTimeoutMillis = 30000  // 30 seconds
                connectTimeoutMillis = 15000  // 15 seconds
                socketTimeoutMillis = 30000   // 30 seconds
            }
            
            // CRITICAL: Set to false to handle errors manually
            // This prevents Ktor from throwing exceptions with IP addresses and URLs
            expectSuccess = false
        }
    }
}
