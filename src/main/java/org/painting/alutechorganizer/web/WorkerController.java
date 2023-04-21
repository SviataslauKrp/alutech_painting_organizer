package org.painting.alutechorganizer.web;

import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.dto.*;
import org.painting.alutechorganizer.service.WorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@RequiredArgsConstructor

@Controller
@RequestMapping("/")
public class WorkerController {

    private final WorkerService service;

    @PostMapping("/create_worker")
    public void createWorker(@RequestParam(name = "name") String name,
                               @RequestParam(name = "surname") String surname,
                               @RequestParam(name = "startWorking") Date startWorking,
                               @RequestParam(name = "employeeNumber") Integer employeeNumber,
                               @RequestParam(name = "profession") String profession) {

        WorkerDto worker = switch (profession) {
            case "corrector" -> new CorrectorDto(name, surname, startWorking, employeeNumber);
            case "operator" -> new OperatorDto(name, surname, startWorking, employeeNumber);
            case "seniorWorker" -> new SeniorWorkerDto(name, surname, startWorking, employeeNumber);
            default -> new StackerEntityDto(name, surname, startWorking, employeeNumber);
        };

        service.saveWorker(worker);

    }

    @GetMapping("/create_worker")
    public String hello() {
        return "test.html";
    }

}
