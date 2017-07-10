package com.elex.mark.util;

/**
 * Created by sun on 17/3/5.
 */
public class PairUtil<T> {
    private T first;
    private T second;

    public PairUtil() {
    }

    public PairUtil(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public boolean isNotNull() {
        return first != null && second != null;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getSecond() {
        return second;
    }

    public void setSecond(T second) {
        this.second = second;
    }

	@Override
	public String toString() {
		return "{first=" + first + ", second=" + second + "}";
	}
}
