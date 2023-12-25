package com.mianeko.paymentservice.data.configurations

import org.ktorm.database.Database
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DatabaseConfiguration(
    @Value("\${spring.datasource.url}") val url: String,
    @Value("\${spring.datasource.driver-class-name}") val driverClassName: String,
    @Value("\${spring.datasource.username}") val username: String,
    @Value("\${spring.datasource.password}") val password: String,
) {
    @Bean
    fun provideDatabase(): Database {
        return Database.connect(url, driver = driverClassName, user = username, password = password)
    }
}

