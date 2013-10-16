package org.isatools.errorreporter.model;

public class ErrorMessage {
    private ErrorLevel errorLevel;
    private String message;
    private String file = "";

    public ErrorMessage(ErrorLevel errorLevel, String message) {
        this.errorLevel = errorLevel;
        this.message = message;
    }

    public ErrorMessage(ErrorLevel errorLevel, String message, String file) {
        this.errorLevel = errorLevel;
        this.message = message;
        this.file = file;
    }

    public ErrorLevel getErrorLevel() {
        return errorLevel;
    }

    public String getMessage() {
        return message;
    }

    public String getFile() {
        return file;
    }
}
