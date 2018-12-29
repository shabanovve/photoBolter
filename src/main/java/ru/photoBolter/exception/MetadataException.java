package ru.photoBolter.exception;

public class MetadataException extends RuntimeException{
    public MetadataException(Throwable cause) {
        super(cause);
    }

    public MetadataException(String string) {
        super(string);
    }
}
