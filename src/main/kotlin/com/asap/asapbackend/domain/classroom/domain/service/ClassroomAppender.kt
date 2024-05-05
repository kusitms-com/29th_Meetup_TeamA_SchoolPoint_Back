package com.asap.asapbackend.domain.classroom.domain.service

import com.asap.asapbackend.batch.classroom.dto.ClassroomResponse
import com.asap.asapbackend.domain.child.domain.model.Child
import com.asap.asapbackend.domain.classroom.domain.model.ChildClassroom
import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.classroom.domain.model.Grade
import com.asap.asapbackend.domain.classroom.domain.model.Year
import com.asap.asapbackend.domain.classroom.domain.repository.ChildClassRoomRepository
import com.asap.asapbackend.domain.classroom.domain.repository.ClassroomRepository
import org.springframework.stereotype.Service

@Service
class ClassroomAppender(
    private val classroomRepository: ClassroomRepository,
    private val childClassRoomRepository: ChildClassRoomRepository
) {

    fun addChild(classroom: Classroom, student: Child): Long {
        return childClassRoomRepository.save(
            ChildClassroom(
                classroom = classroom,
                student = student
            )
        ).id
    }
    fun addClassroom(classrooms: List<ClassroomResponse>) {
        for (classroom in classrooms) {
            if(classroomRepository.findBySchoolAndGradeAndClassNumber(classroom.school, Grade.convert(classroom.grade),classroom.classNumber)==null){
                val newClassroom = Classroom(
                        grade = classroom.grade,
                        classNumber = classroom.classNumber,
                        year = Year(),
                        school = classroom.school
                )
                classroomRepository.save(newClassroom)
            }
        }
    }
}