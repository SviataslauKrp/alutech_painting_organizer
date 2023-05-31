package org.painting.alutechorganizer.web.advice;

import org.painting.alutechorganizer.exc.MasterException;
import org.painting.alutechorganizer.exc.WorkerException;
import org.painting.alutechorganizer.exc.WorkplaceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

@ControllerAdvice
public class GlobalControllerHandler {

    private final static String MASTER_NOT_FOUND_MESSAGE = "The master isn't found";
    private final static String WORKER_NOT_FOUND_MESSAGE = "The worker isn't found";
    private final static String WORKPLACE_NOT_FOUND_MESSAGE = "The workplace isn't found";


    @ExceptionHandler(MasterException.class)
    public ModelAndView handleMasterException(MasterException exception) {
        ModelAndView modelAndView = new ModelAndView("/err/master_exc");
        modelAndView.addObject("error_message", exception.getMessage());

        if (StringUtils.equals(exception.getMessage(), MASTER_NOT_FOUND_MESSAGE)) {
            modelAndView.setStatus(HttpStatus.NOT_FOUND);
        } else {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        }
        return modelAndView;
    }

    @ExceptionHandler(WorkerException.class)
    public ModelAndView handleWorkerException(WorkerException exception) {
        ModelAndView modelAndView = new ModelAndView("/err/worker_exc");
        modelAndView.addObject("error_message", exception.getMessage());

        if (StringUtils.equals(exception.getMessage(), WORKER_NOT_FOUND_MESSAGE)) {
            modelAndView.setStatus(HttpStatus.NOT_FOUND);
        } else {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        }

        return modelAndView;
    }

    @ExceptionHandler(WorkplaceException.class)
    public ModelAndView handleWorkplaceException(WorkplaceException exception) {
        ModelAndView modelAndView = new ModelAndView("/err/workplace_exc");
        modelAndView.addObject("error_message", exception.getMessage());

        if (StringUtils.equals(exception.getMessage(), WORKPLACE_NOT_FOUND_MESSAGE)) {
            modelAndView.setStatus(HttpStatus.NOT_FOUND);
        } else {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        }

        return modelAndView;
    }

}
