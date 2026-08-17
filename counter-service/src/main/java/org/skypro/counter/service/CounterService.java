package org.skypro.counter.service;

import org.skypro.counter.domain.UserRequestCounter;
import org.springframework.stereotype.Service;

@Service
public class CounterService {

    private final UserRequestCounter counter;

    public CounterService(UserRequestCounter counter) {
        this.counter = counter;
    }

    public void increment() {
        counter.setRequestCount(counter.getRequestCount()+1);
    }

    public Integer getCount() {
        return counter.getRequestCount();
    }
}
