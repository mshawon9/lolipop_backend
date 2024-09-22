package com.lolipop.pos.service;

import java.text.ParseException;
import java.time.LocalDateTime;

public interface CommonService {
    String generateMessage(String entity, String searchParam);

    LocalDateTime formateDate(String date) throws ParseException;

    boolean isEmptyString(String string);
}
