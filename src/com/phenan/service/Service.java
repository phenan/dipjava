package com.phenan.service;

import com.phenan.service_task.ServiceTask;

public interface Service<In, Out> {
    ServiceTask<Out> run(In input);
}
