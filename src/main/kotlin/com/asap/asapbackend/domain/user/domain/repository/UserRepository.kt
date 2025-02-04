package com.asap.asapbackend.domain.user.domain.repository

import com.asap.asapbackend.domain.user.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun findBySocialInfo_SocialId(socialId: String): User?

    fun existsBySocialInfo_SocialId(socialId: String): Boolean
}