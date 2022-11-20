package com.ahmatda.application.apidocs.util;

import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.snippet.Attributes;

import java.util.List;

public interface DocumentConstraintsGenerator {

    String CONSTRAINTS_KEY = "constraints";

    static Attributes.Attribute getConstraintsAttribute(Class<?> clazz, String propertyName) {
        List<String> nameDescription = new ConstraintDescriptions(clazz).descriptionsForProperty(propertyName);
        return Attributes.key(CONSTRAINTS_KEY).value(nameDescription);
    }
}
