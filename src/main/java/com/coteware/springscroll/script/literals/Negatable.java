package com.coteware.springscroll.script.literals;

public interface Negatable<T> {
    boolean isNegated();
    void negate();
    T getNotNegatedValue();
    T getNegatedValue();

    default T getValue() {
        if (isNegated()) {
            return getNegatedValue();
        } else {
            return getNotNegatedValue();
        }
    }
}
