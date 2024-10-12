package com.lolipop.pos.dto;

import org.springframework.data.domain.Page;
import java.util.function.Function;

public class GenericConverter {

    public static <E, D> Page<D> convertToDto(Page<E> entityPage, Function<E, D> dtoConstructor) {
        return entityPage.map(dtoConstructor);
    }
}
