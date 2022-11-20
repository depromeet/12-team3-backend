package com.ahmatda.application.apidocs.util;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;

public interface ApiDocsUtil {

    static OperationRequestPreprocessor getDocumentRequest() {
        return Preprocessors.preprocessRequest(
                Preprocessors.modifyUris()
                        .scheme("https")
                        .host("docs.api.com")
                        .removePort(),
                Preprocessors.prettyPrint()
        );
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        return Preprocessors.preprocessResponse(Preprocessors.prettyPrint());
    }
}
