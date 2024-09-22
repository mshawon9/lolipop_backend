package com.lolipop.pos.configuration;

import org.springframework.util.StringUtils;

public class EntityAlreadyExistException extends RuntimeException {
    public EntityAlreadyExistException(String name, String searchParams) {
        super(EntityAlreadyExistException.generateMessage(name, searchParams));
    }

    private static String generateMessage(String entity, String searchParams) {
        return StringUtils.capitalize(entity) +
                " was found for parameters " +
                searchParams;
    }
}
