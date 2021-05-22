package com.fiedormichal.productOrder.validator.schemaValidator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiedormichal.productOrder.exception.JsonSchemaLoadingFailedException;
import com.fiedormichal.productOrder.exception.JsonValidationFailedException;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsonSchemaValidatingArgumentResolver implements HandlerMethodArgumentResolver {
    private final ObjectMapper objectMapper;
    private final ResourcePatternResolver resourcePatternResolver;
    private final Map<String, JsonSchema> schemaCache;

    public JsonSchemaValidatingArgumentResolver(ObjectMapper objectMapper, ResourcePatternResolver resourcePatternResolver) {
        this.objectMapper = objectMapper;
        this.resourcePatternResolver = resourcePatternResolver;
        this.schemaCache = new HashMap<>();
    }

    public boolean supportsParameter(MethodParameter methodParameter){
        return methodParameter.getParameterAnnotation(ValidJson.class) !=null;
    }

    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        String schemaPath = methodParameter.getParameterAnnotation(ValidJson.class).value();

        JsonSchema schema= getJsonSchema(schemaPath);

        JsonNode json = objectMapper.readTree(getJsonPayLoad(nativeWebRequest));

        Set<ValidationMessage> validationResult = schema.validate(json);

        if(validationResult.isEmpty()){
            return objectMapper.treeToValue(json, methodParameter.getParameterType());
        }
        throw new JsonValidationFailedException(validationResult);
    }

    private String getJsonPayLoad(NativeWebRequest nativeWebRequest) throws IOException{
        HttpServletRequest httpServletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        return StreamUtils.copyToString(httpServletRequest.getInputStream(), StandardCharsets.UTF_8);
    }

    private JsonSchema getJsonSchema(String schemaPath){
        return schemaCache.computeIfAbsent(schemaPath, path->{
             Resource resource = resourcePatternResolver.getResource(path);
             if(!resource.exists()){
                 throw new JsonSchemaLoadingFailedException("Schema does not exist, path" + path);
             }
            JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
             try(InputStream schemaStream = resource.getInputStream()){
                 return schemaFactory.getSchema(schemaStream);
             } catch (Exception e) {
                 e.printStackTrace();
                 throw new JsonSchemaLoadingFailedException("An error occurred while loading JSON Schema, path: " + path + e);
             }
        });
    }


}
