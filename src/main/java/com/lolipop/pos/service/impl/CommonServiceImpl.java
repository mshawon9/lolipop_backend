package com.lolipop.pos.service.impl;

import com.lolipop.pos.service.CommonService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class CommonServiceImpl implements CommonService {
    @Override
    public String generateMessage(String entity, String searchParam) {
        return StringUtils.capitalize(entity) +
                " was not found for parameter - " +
                searchParam;
    }
    @Override
    public LocalDateTime formateDate(String date) throws ParseException {

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date parsedDate = inputFormat.parse(date.replace("/", "-"));

        return LocalDateTime.parse(outputFormat.format(parsedDate));
    }

    public LocalDateTime convertDateTimeToPST(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        ZonedDateTime orderedAtZoned = date.atZone(ZoneId.of("UTC")); // Convert LocalDateTime to ZonedDateTime in UTC
        ZonedDateTime orderedAtPST = orderedAtZoned.withZoneSameInstant(ZoneId.of("America/Los_Angeles")); // Convert to PST

        return orderedAtPST.toLocalDateTime();
    }
    @Override
    public boolean isEmptyString(String string) {
        return string == null || string.isEmpty();
    }
}
