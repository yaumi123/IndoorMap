package com.tq.indoormap.location;

import java.util.Comparator;

/**
 * Created by niantuo on 2017/4/8.
 */

public class ValueComparator implements Comparator<ValueComparator.Value> {

    @Override
    public int compare(Value o1, Value o2) {
        if (o1.getValue() < o2.getValue())
            return -1;
        if (o1.getValue() > o2.getValue())
            return 1;
        return 0;
    }

    public interface Value {

        int getValue();
    }

}
