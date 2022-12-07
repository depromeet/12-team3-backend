package com.depromeet.ahmatda.user.token;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

class UserTokenDeSerializer extends JsonDeserializer<UserToken> {

    @Override
    public UserToken deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        return new UserToken(p.getText());
    }
}
