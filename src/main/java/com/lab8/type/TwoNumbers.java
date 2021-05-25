package com.lab8.type;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TwoNumbers implements Comparable<TwoNumbers> {
    double x;
    double y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TwoNumbers)) return false;

        TwoNumbers twoNumbers = (TwoNumbers) o;

        if (y != twoNumbers.y) return false;
        if (x != twoNumbers.x) return false;
        return true;
    }

    @Override
    public int hashCode() {
        double result = 1.5;
        result = 31 * result + x;
        result = 31 * result + y;
        return (int)result;
    }

    @Override
    public int compareTo(TwoNumbers twoNumbers) {
            if (x  == twoNumbers.x) {
                if (y == twoNumbers.y) {
                    return 0;
                }
                return Double.compare(y, twoNumbers.y);
            }
        return Double.compare(x, twoNumbers.x);
    }
}
