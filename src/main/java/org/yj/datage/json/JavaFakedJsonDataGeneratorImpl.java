package org.yj.datage.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.StringUtils;
import org.yj.datage.json.exception.JsonGenerationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class JavaFakedJsonDataGeneratorImpl implements DataGenerator {

    public static final String JAVA_FAKER_BASE_PACKAGE = "com.github.javafaker.";

    public static final String OPERATOR_SEPARATOR_PATTERN = "\\.";

    private ObjectMapper objectMapper;

    private Faker faker;

    public JavaFakedJsonDataGeneratorImpl() {
        init();
    }

    void init() {
        objectMapper = new ObjectMapper().findAndRegisterModules()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        faker = Faker.instance();
    }


    /**
     * Generates random data based on definition json
     * @param inputJson definition json
     * @return generated json with data
     */
    @Override
    public String generateJsonString(String inputJson) {
        JsonNode inputPattern = extractJsonNode(inputJson);
        JsonNode outputPattern = processNode(inputPattern);
        try {
            return objectMapper.writeValueAsString(outputPattern);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonNode processNode(JsonNode jsonNode) {
        final JsonNode data;
        if (jsonNode.isNull()) {
            data = objectMapper.nullNode();
        } else if (jsonNode.isObject()) {
            data = objectMapper.createObjectNode();
            jsonNode.fields().forEachRemaining(entry -> ((ObjectNode) data).set(entry.getKey(), processNode(entry.getValue())));
        } else if (jsonNode.isTextual()) {
            String generatorString = jsonNode.asText();
            data = createDataNode(generatorString);
        } else {
            data = objectMapper.nullNode();
        }
        return data;
    }

    private JsonNode createDataNode(String generatorString) {
        String[] generators = generatorString.split(OPERATOR_SEPARATOR_PATTERN);
        if (generators.length > 1) {
            Object generator = faker;
            Class<?> clazz = Faker.class;
            if (!StringUtils.isEmpty(generators[0])) {
                clazz = findClassToCall(generators[0]);
                generator = getGenerator(generators);
            }
            String methodName = generators[1];
            Method method = ClassUtils.extractMethod(clazz, methodName);
            Object generatedValue = ClassUtils.invoke(method, generator);
            return objectMapper.valueToTree(generatedValue);
        }
        return objectMapper.valueToTree(generatorString);

    }

    private Object getGenerator(String[] generators) {
        String operator = findOperatorToCall(generators[0]);
        Method method = ClassUtils.extractMethod(Faker.class, operator);
        try {
            return method.invoke(faker);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Class<?> findClassToCall(String className) {
        try {
            return Class.forName(JAVA_FAKER_BASE_PACKAGE + className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonNode extractJsonNode(String inputJson) {
        try {
            return objectMapper.readTree(inputJson);
        } catch (JsonProcessingException e) {
            throw new JsonGenerationException(e);
        }
    }

    private String findOperatorToCall(String className) {
        if (Objects.equals(className, "DateAndTime")) return "date";
        char[] characters = className.toCharArray();
        characters[0] = Character.toLowerCase(characters[0]);
        return new String(characters);
    }
}
