package com.example.backend_project.enums;

public enum ErrorCode {
    INVALID_JWT_TOKEN("ERR04002"),
    EXPIRED_JWT_TOKEN("ERR04003"),
    UNSUPPORTED_JWT_TOKEN("ERR04004"),
    INVALID_JWT_COMPACT_HANDLER("ERR04005"),
    UNEXPECTED_JWT_TOKEN_ERROR("ERR04006");
    
    private final String code;
    
    ErrorCode(String code) {
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }
}
