package com.onboarding.task.service

import com.onboarding.task.repository.UserRepository
import com.onboarding.task.service.impl.JwtServiceImpl
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class JwtServiceImplTest @Autowired constructor(
    val jwtServiceImpl: JwtServiceImpl,
    val userRepository: UserRepository,
    val em: EntityManager
) {
}