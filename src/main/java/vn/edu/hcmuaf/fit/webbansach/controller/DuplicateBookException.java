package vn.edu.hcmuaf.fit.webbansach.controller;

public class DuplicateBookException extends RuntimeException {
    private final String code;

    public DuplicateBookException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}