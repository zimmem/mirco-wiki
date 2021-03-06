package com.zimmem.velocity.reference;

import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.velocity.app.event.ReferenceInsertionEventHandler;

public class DateReference implements ReferenceInsertionEventHandler {

    private static final FastDateFormat dateFormat = FastDateFormat.getInstance(System.getProperty("velocity.dateformat.default"));

    @Override
    public Object referenceInsert(String reference, Object value) {

        if (value == null) return null;

        if (value instanceof Date) {
            return dateFormat.format(value);
        }
        return value;
    }

}
