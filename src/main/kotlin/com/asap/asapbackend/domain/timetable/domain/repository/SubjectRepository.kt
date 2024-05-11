package com.asap.asapbackend.domain.timetable.domain.repository

import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.timetable.domain.model.Subject
import org.springframework.data.jpa.repository.JpaRepository

interface SubjectRepository : JpaRepository<Subject, Long> {
    fun findByClassroomAndNameAndSemester(classroom: Classroom?, name: String?, semester: String?): Subject?
}