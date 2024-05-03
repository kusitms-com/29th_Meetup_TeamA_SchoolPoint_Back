package com.asap.asapbackend.global.jwt

import com.asap.asapbackend.domain.user.domain.model.Provider
import com.fasterxml.jackson.annotation.JsonProperty


class PrivateClaims(
    val claims: Claims,
    val tokenType: TokenType
) {

    companion object : ClaimsType {
        override fun retrieveClaimsClassType(): Map<String, Class<*>> = mapOf(
            JwtConst.TOKEN_TYPE to TokenType::class.java
        ).plus(Claims.UserClaims.retrieveClaimsClassType()).plus(Claims.RegistrationClaims.retrieveClaimsClassType())
    }

    fun convertToClaims(): Map<String, Any> = mapOf(
        JwtConst.TOKEN_TYPE to tokenType,
    ).plus(this.claims.convertToClaims())

}

sealed interface Claims {
    fun createPrivateClaims(tokenType: TokenType): PrivateClaims
    fun convertToClaims(): Map<String, Any>

    data class RegistrationClaims(
        @param:JsonProperty("social_id")
        @get:JsonProperty("social_id")
        val socialId: String,
        @param:JsonProperty("provider")
        val provider: Provider
    ) : Claims {
        override fun createPrivateClaims(tokenType: TokenType) = PrivateClaims(this, tokenType)
        override fun convertToClaims(): Map<String, Any> = mapOf(
            JwtConst.REGISTRATION_CLAIMS to this,
        )

        companion object : ClaimsType {
            override fun retrieveClaimsClassType(): Map<String, Class<*>> {
                return mapOf(
                    JwtConst.REGISTRATION_CLAIMS to RegistrationClaims::class.java,
                )
            }
        }
    }

    data class UserClaims(
        @param:JsonProperty("user_id")
        @get:JsonProperty("user_id")
        val userId: Long
    ) : Claims {
        override fun createPrivateClaims(tokenType: TokenType) = PrivateClaims(this, tokenType)
        override fun convertToClaims(): Map<String, Any> = mapOf(
            JwtConst.USER_CLAIMS to this
        )

        companion object : ClaimsType {
            override fun retrieveClaimsClassType(): Map<String, Class<*>> {
                return mapOf(
                    JwtConst.USER_CLAIMS to UserClaims::class.java
                )
            }
        }
    }
}

interface ClaimsType {
    fun retrieveClaimsClassType(): Map<String, Class<*>>
}