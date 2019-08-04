package com.dynamo.spacex.shared;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TransientDataProvider {

    private ConcurrentMap<Class, UseCase> transientData = new ConcurrentHashMap<>();

    @Inject
    public TransientDataProvider() { }

    public void saveUseCase(UseCase useCase) {
        final Class<? extends UseCase> useCaseClass = useCase.getClass();
        transientData.put(useCaseClass, useCase);
    }

    public <T extends UseCase> T getUseCase(Class<T> useCaseClass) {
        final UseCase removedUseCase = transientData.remove(useCaseClass);
        return useCaseClass.cast(removedUseCase);
    }
}
