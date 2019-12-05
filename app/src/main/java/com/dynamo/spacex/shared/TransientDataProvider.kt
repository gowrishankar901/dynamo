package com.dynamo.spacex.shared

import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransientDataProvider @Inject constructor() {
    private val transientData = ConcurrentHashMap<Class<out UseCase>, UseCase>()

    fun saveUseCase(useCase: UseCase) {
        val useCaseClass: Class<out UseCase> = useCase.javaClass
        transientData[useCaseClass] = useCase
    }

    fun getUseCase(useCaseClass: Class<out UseCase>): UseCase? {
        return transientData.remove(useCaseClass)
    }

    fun containsUseCase(useCaseClass: Class<out UseCase>): Boolean {
        return transientData.containsKey(useCaseClass)
    }
}