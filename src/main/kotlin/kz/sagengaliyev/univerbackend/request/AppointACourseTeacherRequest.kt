package kz.sagengaliyev.univerbackend.request

import com.fasterxml.jackson.annotation.JsonCreator

data class AppointACourseTeacherRequest(
    val courseId: Long
) {
    constructor() : this(0)
}
