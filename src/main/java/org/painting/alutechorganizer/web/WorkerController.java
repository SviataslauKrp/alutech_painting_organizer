package org.painting.alutechorganizer.web;

import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.dto.*;
import org.painting.alutechorganizer.service.WorkerService;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequiredArgsConstructor

@Controller
@RequestMapping("/")
@Validated
public class WorkerController {

    private final WorkerService service;

    @PostMapping("/create_worker")
    public String createWorker(@RequestParam(name = "name") @NotBlank String name,
                               @RequestParam(name = "surname") @NotBlank String surname,
                               @RequestParam(name = "startWorking") @NotNull String startWorking,
                               @RequestParam(name = "employeeNumber") @NotNull Integer employeeNumber,
                               @RequestParam(name = "profession") @NotBlank String profession) {

        WorkerDto worker = switch (profession) {
            case "corrector" -> new CorrectorDto(name, surname, startWorking, employeeNumber);
            case "operator" -> new OperatorDto(name, surname, startWorking, employeeNumber);
            case "seniorWorker" -> new SeniorWorkerDto(name, surname, startWorking, employeeNumber);
            default -> new StackerEntityDto(name, surname, startWorking, employeeNumber);
        };

        service.saveWorker(worker);
        return "test";

    }

    @GetMapping("/get_all_workers")
    public ModelAndView getAllWorkers() {
        List<WorkerDto> allWorkers = service.getAllWorkers();
        ModelAndView modelAndView = new ModelAndView("all_workers");
        modelAndView.addObject("allWorkers", allWorkers);
        return modelAndView;
    }

    @GetMapping("/create_worker")
    public String hello() {
        return "test";
    }

    @GetMapping("/personal_page")
    public ModelAndView getWorkerById(@RequestParam(name = "id") @NotNull Integer id) {

        WorkerDto workerById = service.getWorkerById(id);
        ModelAndView modelAndView = new ModelAndView("personal_page");
        modelAndView.addObject("worker", workerById);
        return modelAndView;
    }

    @DeleteMapping("/personal_page")
    public void deleteWorkerById(@RequestParam(name = "id") @NotNull Integer id) {

        service.deleteWorkerById(id);

    }

    @PutMapping("/personal_page")
    public void updateWorkerById(@RequestParam(name = "id") @NotNull Integer id,
                                 @RequestParam(name = "name") @DefaultValue(value = "name") String name) {

        service.updateWorkerById(id);

    }

}
