package com.asap.asapbackend.domain.child.domain.service

import com.asap.asapbackend.domain.child.domain.model.Child
import com.asap.asapbackend.domain.child.domain.repository.ChildRepository
import com.asap.asapbackend.domain.child.domain.repository.PrimaryChildRepository
import org.springframework.stereotype.Service

@Service
class ChildModifier(
    private val childRepository: ChildRepository,
    private val primaryChildRepository: PrimaryChildRepository
) {
    fun changeInfo(child: Child) {
        childRepository.save(child)
    }

    fun changePrimaryChild(userId: Long, childId: Long) {
        primaryChildRepository.findByUserId(userId)?.let {
            it.changePrimaryChild(childId)
            primaryChildRepository.save(it)
        }
    }
}