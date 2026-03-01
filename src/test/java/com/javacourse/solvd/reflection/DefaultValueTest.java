package com.javacourse.solvd.reflection;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DefaultValueTest {

    @DefaultValue(value = "3")
    private Integer fieldWithDefault = 10;

    private String fieldWithoutDefault;

    @DefaultValue(value = "47")
    private long primitiveField;

    @DefaultValue(value = "6.47")
    private Double nullableField = null;


    @Test
    @Disabled
    public void testDefaultValueReflection() {
        DefaultValueReflectionUtil util = new DefaultValueReflectionUtil();

        util.initWithDefaults(this);
        testObject(this);

        DefaultValueTest test = util.initWithDefaults(DefaultValueTest.class);
        testObject(test);
    }

    private void testObject(DefaultValueTest test) {
        assertEquals(10, test.fieldWithDefault);
        assertNull(test.fieldWithoutDefault);
        assertEquals(47L, test.primitiveField);
        assertEquals(6.47, test.nullableField, 0.01);
    }
}
