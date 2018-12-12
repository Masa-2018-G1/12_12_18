package com.sheygam.masa_2018_g1_12_12_18;

public class ErrorDto {
    private String message;
    private int status;
    private String error;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ErrorDto{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", error='" + error + '\'' +
                '}';
    }
}
