package com.my_site.cache

import com.my_site.features.register.RegisterReceiveRemote

data class TokenCache(
    val login: String,
    val token: String
)

object InMemoryCache {
    val users: MutableList<RegisterReceiveRemote> = mutableListOf()
    val tokens: MutableList<TokenCache> = mutableListOf()
}