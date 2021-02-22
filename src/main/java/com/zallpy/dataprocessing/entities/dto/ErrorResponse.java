package com.zallpy.dataprocessing.entities.dto;

import java.util.Objects;
import java.util.StringJoiner;

public class ErrorResponse {

    private final String error;

    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ErrorResponse.class.getSimpleName() + "[", "]")
                .add("error='" + error + "'")
                .toString();
    }
}
