package com.asap.asapbackend.domain.teacher.application

import com.asap.asapbackend.domain.classroom.domain.service.ClassroomAppender
import com.asap.asapbackend.domain.classroom.domain.service.ClassroomReader
import com.asap.asapbackend.domain.teacher.application.dto.CreateTeacher
import com.asap.asapbackend.domain.teacher.application.dto.LoginTeacher
import com.asap.asapbackend.domain.teacher.domain.service.TeacherAppender
import com.asap.asapbackend.domain.teacher.domain.service.TeacherReader
import com.asap.asapbackend.domain.teacher.domain.service.TeacherValidator
import com.asap.asapbackend.global.jwt.Claims
import com.asap.asapbackend.global.jwt.JwtProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class TeacherService(
    private val passwordEncoder: PasswordEncoder, // TODO : 리팩터링
    private val teacherAppender: TeacherAppender,
    private val teacherValidator: TeacherValidator,
    private val teacherReader: TeacherReader,
    private val classroomReader: ClassroomReader,
    private val classroomAppender: ClassroomAppender,
    private val jwtProvider: JwtProvider
) {

    @Transactional
    fun createTeacher(request: CreateTeacher.Request) {
        val teacher = request.extractTeacher(passwordEncoder::encode).also {
            teacherValidator.validateTeacherCreatable(it.username)
            teacherAppender.appendTeacher(it)
        }
        request.extractClassroom { schoolName, grade, className -> // TODO : 이벤트기반 리팩터링
            classroomReader.findByClassInfoAndSchoolName(grade, className, schoolName)
        }.also { classroomAppender.addTeacher(it, teacher) }
    }

    fun loginTeacher(request: LoginTeacher.Request): LoginTeacher.Response{
        val (username, password) = request.convertLoginInfo(passwordEncoder::encode)
        val teacher = teacherReader.findByUsernameAndPassword(username, password)
        return LoginTeacher.Response(
            accessToken = jwtProvider.generateTeacherAccessToken(Claims.TeacherClaims(teacher.id)),
        )
    }
}