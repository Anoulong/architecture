package com.mvvm.core.common.utils;

public abstract class LazyLoadedValue<T> {

    private T value;

    protected abstract T load();

    public final T get() {
        if (value == null) {
            value = load();
        }

        return value;
    }

}
