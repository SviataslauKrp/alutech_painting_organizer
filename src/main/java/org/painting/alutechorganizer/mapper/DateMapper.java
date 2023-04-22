package org.painting.alutechorganizer.mapper;

import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public class DateMapper {

    public LocalDate asLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}
