package org.painting.alutechorganizer.web;

import org.painting.alutechorganizer.exc.EmptyBrigadeException;
import org.painting.alutechorganizer.exc.WorkerNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionWorkerController {

    @ExceptionHandler
    public String workerNotFound(WorkerNotFoundException e) {
        return "/errors/worker_not_found";
    }

    @ExceptionHandler
    public String emptyBrigade(EmptyBrigadeException e) {
        return "/errors/empty_brigade";
    }

}
