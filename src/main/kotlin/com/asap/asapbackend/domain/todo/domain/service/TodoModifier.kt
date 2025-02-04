package com.asap.asapbackend.domain.todo.domain.service

import com.asap.asapbackend.domain.todo.domain.repository.TodoRepository
import org.springframework.stereotype.Service

@Service
class TodoModifier(
    private val todoRepository: TodoRepository,
){

    fun changeTodoStatusBtUserIdAndTodoId(userId: Long, todoId: Long){
        todoRepository.findByUserIdAndTodoId(userId, todoId)?.let {
            it.changeStatus()
            todoRepository.save(it)
        }
    }
}