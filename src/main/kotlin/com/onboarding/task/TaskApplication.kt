package com.onboarding.task

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class TaskApplication

fun main(args: Array<String>) {
	runApplication<TaskApplication>(*args)
}
