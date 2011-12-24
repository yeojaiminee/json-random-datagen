package org.yj.datage.json.exception;

public class JsonGenerationException extends RuntimeException {

    public JsonGenerationException(String message, Throwable e) {
        super(message, e);
    }

    public JsonGenerationException(Throwable e) {
        this("Unable to read json", e);
    }

    public JsonGenerationException() {
        super();
    }
}
