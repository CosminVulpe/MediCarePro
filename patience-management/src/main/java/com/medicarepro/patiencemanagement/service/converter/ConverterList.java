package com.medicarepro.patiencemanagement.service.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;

@Converter
public class ConverterList implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = ", ";

    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        return !stringList.isEmpty() ? String.join(SPLIT_CHAR, stringList) : "";
    }

    @Override
    public List<String> convertToEntityAttribute(String string) {
        return !string.isBlank() ? Arrays.asList(string.split(SPLIT_CHAR)) : emptyList();
    }
}
