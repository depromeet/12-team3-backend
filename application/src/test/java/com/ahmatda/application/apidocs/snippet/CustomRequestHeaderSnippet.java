package com.ahmatda.application.apidocs.snippet;

import org.springframework.restdocs.headers.AbstractHeadersSnippet;
import org.springframework.restdocs.headers.HeaderDescriptor;
import org.springframework.restdocs.operation.Operation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomRequestHeaderSnippet extends AbstractHeadersSnippet {

    public static CustomRequestHeaderSnippet customRequestHeaders(String type, Map<String, Object> attributes, HeaderDescriptor... descriptors) {
        return new CustomRequestHeaderSnippet(type, Arrays.asList(descriptors), attributes);
    }

    private CustomRequestHeaderSnippet(String type, List<HeaderDescriptor> descriptors, Map<String, Object> attributes) {
        super(type, descriptors, attributes);
    }

    @Override
    protected Set<String> extractActualHeaders(Operation operation) {
        return operation.getRequest().getHeaders().keySet();
    }
}
