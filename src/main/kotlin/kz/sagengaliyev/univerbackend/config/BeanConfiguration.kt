package kz.sagengaliyev.univerbackend.config

import kz.sagengaliyev.univerbackend.exception.CanNotEnrollStudentToCourse
import kz.sagengaliyev.univerbackend.facade.impl.*
import kz.sagengaliyev.univerbackend.repository.*
import kz.sagengaliyev.univerbackend.service.*
import kz.sagengaliyev.univerbackend.service.impl.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfiguration{

    @Bean
    fun studentService(
        studentRepository: StudentRepository
    ): StudentService = StudentServiceImpl(
        studentRepository = studentRepository
    )

    @Bean
    fun universityService(
        universityRepository: UniversityRepository
    ) : UniversityService = UniversityServiceImpl(
        universityRepository = universityRepository
    )

    @Bean
    fun studentFacade(
        studentService: StudentService,
        universityService: UniversityService,
        courseService: CourseService
    ) : StudentFacadeImpl = StudentFacadeImpl(
        studentService = studentService,
        universityService = universityService,
        courseService = courseService
    )

    @Bean
    fun universityFacade(
        universityService: UniversityService
    ) : UniversityFacadeImpl = UniversityFacadeImpl(
        universityService = universityService
    )

    @Bean
    fun courseService(
        courseRepository: CourseRepository
    ) : CourseServiceImpl = CourseServiceImpl(
        courseRepository
    )

    @Bean
    fun teacherService(
        teacherRepository: TeacherRepository
    ) : TeacherServiceImpl = TeacherServiceImpl(
        teacherRepository
    )

    @Bean
    fun courseFacade(
        courseService: CourseService,
        studentService: StudentService,
        teacherService: TeacherService
    ) : CourseFacadeImpl = CourseFacadeImpl(
        courseService = courseService,
        studentService = studentService,
        teacherService = teacherService
    )

    @Bean
    fun teacherFacade(
        teacherService: TeacherService,
        courseService: CourseService
    ) : TeacherFacadeImpl = TeacherFacadeImpl(
        teacherService, courseService
    )

    @Bean
    fun fileStorageService(
        fileEntityRepository: FileEntityRepository
    ) : FileStorageServiceImpl = FileStorageServiceImpl(fileEntityRepository)

    @Bean
    fun fileStorageFacade(
        fileStorageService: FileStorageService
    ) : FileStorageFacadeImpl = FileStorageFacadeImpl(fileStorageService)

    @Bean
    fun assignmentService(
        assignmentRepository: AssignmentRepository
    ) : AssignmentServiceImpl = AssignmentServiceImpl(assignmentRepository)

    @Bean
    fun assignmentFacade(
        assignmentService: AssignmentService,
        fileStorageService: FileStorageService
    ) : AssignmentFacadeImpl = AssignmentFacadeImpl(assignmentService, fileStorageService)

    //second bean for example for another profile
//    @Bean
//    fun studentService(
//        studentRepository: StudentRepository
//    ): StudentService = StudentServiceImpl(
//        studentRepository = studentRepository
//    )
}