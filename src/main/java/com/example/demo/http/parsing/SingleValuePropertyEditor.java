package com.example.demo.http.parsing;

import lombok.SneakyThrows;

import java.beans.PropertyEditorSupport;

public class SingleValuePropertyEditor<T> extends PropertyEditorSupport {

    private final Class<T> targetType;

    public SingleValuePropertyEditor(Class<T> targetType) {
        this.targetType = targetType;
    }

    @SneakyThrows
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(this.targetType.getDeclaredConstructor(String.class).newInstance(text));
    }
}
