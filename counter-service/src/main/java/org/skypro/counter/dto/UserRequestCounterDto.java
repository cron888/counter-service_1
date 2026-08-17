package org.skypro.counter.dto;

public class UserRequestCounterDto {
    private Integer requestCount;

    public UserRequestCounterDto(Integer requestCount) {
        this.requestCount = requestCount;
    }

    public Integer getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(Integer requestCount) {
        this.requestCount = requestCount;
    }
}
