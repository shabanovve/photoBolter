package ru.photoBolter.exception;

public class NoConfigException extends Exception {
    public NoConfigException(Throwable cause) {
        super(cause);
    }

    public NoConfigException(String s) {
        super(s);
    }
}
