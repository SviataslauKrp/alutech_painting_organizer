package org.painting.alutechorganizer.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.exc.MasterException;
import org.painting.alutechorganizer.exc.WorkerException;
import org.painting.alutechorganizer.service.MasterService;
import org.painting.alutechorganizer.service.WorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@Controller
@RequestMapping("/workers")
public class WorkerController {

    private final WorkerService workerService;
    private final MasterService masterService;

    //create
    @PostMapping("/create_worker")
    public String saveWorker(@Valid WorkerDto worker,
                             BindingResult bindingResult,
                             @RequestParam(name = "masterId") Integer masterId) {

        if (bindingResult.hasErrors()) {
            throw new WorkerException("The worker hasn't been created");
        }

        workerService.saveWorker(worker, masterId);
        return "redirect:/workers/all_workers";
    }


    @GetMapping("/create_worker")
    public ModelAndView getCreatingForm(Model model) {
        List<MasterDto> allMasters = masterService.getAllMasters();
        model.addAttribute("worker", new WorkerDto());
        return new ModelAndView("create_worker_page", "allMasters", allMasters);
    }


    //read
    @GetMapping("/get_workers_by_master_id")
    public ModelAndView getWorkersByMasterId(@RequestParam(name = "masterId") Integer masterId,
                                             @RequestParam(name = "workplaceId", required = false) Integer workplaceId) {
        List<WorkerDto> workers = workerService.getWorkersByMasterId(masterId);

        if (workplaceId == null) {
            return new ModelAndView("brigade_list", "workers", workers);
        }
        List<WorkerDto> availableWorkers = workers.stream().filter(WorkerDto::getIsAvailable).collect(Collectors.toList());
        return new ModelAndView("workers_list_with_add", "workers", availableWorkers);
    }

    @GetMapping("/all_workers")
    public ModelAndView getAllWorkers() {

        List<WorkerDto> workers = workerService.getAllWorkers();
        return new ModelAndView("workers_list", "workers", workers);

    }

    @GetMapping(value = "/find_worker")
    public ModelAndView getWorkerBySurname(@RequestParam(name = "foundWorkerSurname") String foundWorkerSurname,
                                           @RequestParam(name = "masterId") Integer masterId) {

        WorkerDto worker = workerService.getWorkerBySurname(foundWorkerSurname);
        return new ModelAndView("brigade_list", "workers", worker);
    }

    //update
    @GetMapping("/update_worker")
    public ModelAndView getUpdatePage(@RequestParam(name = "workerId") Integer workerId,
                                      @RequestParam(name = "masterId") Integer masterId) {
        WorkerDto worker = workerService.getWorkerById(workerId);
        MasterDto masterById = masterService.getMasterById(masterId);

        ModelAndView modelAndView = new ModelAndView("update_worker_page");
        modelAndView.addAllObjects(Map.of("worker", worker,
                "master", masterById));
        return modelAndView;
    }

    @PostMapping("/update_worker")
    public String updateWorkerById(WorkerDto newWorkerVersion,
                                   @RequestParam(name = "workerId") Integer workerId,
                                   @RequestParam(name = "masterId") Integer masterId) {

        workerService.updateWorker(newWorkerVersion, workerId);
        return String.format("redirect:/workers/get_workers_by_master_id?masterId=%d", masterId);
    }

    //delete
    @PostMapping("/delete_worker")
    public String deleteWorkerById(@RequestParam(name = "workerId") Integer workerId,
                                   @RequestParam(name = "masterId") Integer masterId) {
        workerService.deleteWorkerById(workerId);
        return String.format("redirect:/workers/get_workers_by_master_id?masterId=%d", masterId);
    }




    @GetMapping("/transfer_worker_to_another_master")
    public ModelAndView getTransferPage(@RequestParam(name = "masterId") Integer masterId) {

        List<WorkerDto> workers = workerService.getWorkersByMasterId(masterId);
        return new ModelAndView("brigade_list_with_transfer", "workers", workers);
    }

    @PostMapping("/transfer_worker_to_another_master")
    public String transferWorkerToAnotherMaster(@RequestParam(name = "newMasterId") Integer masterId,
                                                @RequestParam(name = "workerId") Integer workerId) {
        WorkerDto workerById = workerService.getWorkerById(workerId);
        Integer oldMasterId = workerById.getMaster().getId();
        workerService.setNewMasterToWorker(workerId, masterId);
        return String.format("redirect:/workers/get_workers_by_master_id?masterId=%d", oldMasterId);

    }

}
