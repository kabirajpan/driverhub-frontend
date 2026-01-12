package com.driverhub.shared.domain.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

actual class TokenStorage(context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "auth_prefs",
        Context.MODE_PRIVATE
    )
    
    actual suspend fun saveTokens(accessToken: String, refreshToken: String) {
        prefs.edit(commit = true) {
            putString(KEY_ACCESS_TOKEN, accessToken)
            putString(KEY_REFRESH_TOKEN, refreshToken)
        }
    }
    
    actual suspend fun getAccessToken(): String? {
        return prefs.getString(KEY_ACCESS_TOKEN, null)
    }
    
    actual suspend fun getRefreshToken(): String? {
        return prefs.getString(KEY_REFRESH_TOKEN, null)
    }
    
    actual suspend fun clearTokens() {
        prefs.edit(commit = true) {
            remove(KEY_ACCESS_TOKEN)
            remove(KEY_REFRESH_TOKEN)
            remove(KEY_USER_ROLE)
        }
    }
    
    // ADD 'actual' keyword here
    actual suspend fun saveUserRole(role: String) {
        prefs.edit(commit = true) {
            putString(KEY_USER_ROLE, role)
        }
    }
    
    // ADD 'actual' keyword here
    actual suspend fun getUserRole(): String? {
        return prefs.getString(KEY_USER_ROLE, null)
    }
    
    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_USER_ROLE = "user_role"
    }
}
