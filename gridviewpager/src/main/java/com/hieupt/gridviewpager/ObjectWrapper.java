package com.hieupt.gridviewpager;

public class ObjectWrapper<T> {

    private T mValue;

    public ObjectWrapper() {
    }

    public ObjectWrapper(T value) {
        mValue = value;
    }

    public T get() {
        return mValue;
    }

    public void set(T value) {
        mValue = value;
    }
}
