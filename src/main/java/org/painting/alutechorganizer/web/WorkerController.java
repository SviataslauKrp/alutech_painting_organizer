package org.painting.alutechorganizer.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.domain.MasterEntity;
import org.painting.alutechorganizer.domain.UserEmployee;
import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.dto.WorkplaceDto;
import org.painting.alutechorganizer.exc.MasterException;
import org.painting.alutechorganizer.exc.WorkerException;
import org.painting.alutechorganizer.service.MasterService;
import org.painting.alutechorganizer.service.WorkerService;
import org.painting.alutechorganizer.service.WorkplaceService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.painting.alutechorganizer.web.MasterController.*;

@RequiredArgsConstructor

@Controller
@RequestMapping("/workers")
public class WorkerController {

    private final WorkerService workerService;
    private final MasterService masterService;
    private final WorkplaceService workplaceService;

    //read
    @GetMapping("/get_workers_by_master_id")
    public ModelAndView getWorkersByMasterId(@RequestParam(name = "workplaceId", required = false) Integer workplaceId) {

        UserEmployee principal = (UserEmployee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MasterEntity master = principal.getMaster();
        Integer masterId = master.getId();
        List<WorkerDto> workers = workerService.getWorkersByMasterId(masterId);

        if (workplaceId == null) {
            return new ModelAndView("brigade_list", "workers", workers);
        }
        WorkplaceDto workplaceById = workplaceService.getWorkplaceById(workplaceId);
        if (!Objects.equals(workplaceById.getMaster().getId(), masterId)) {
            throw new MasterException("Cheating");
        }
        List<WorkerDto> availableWorkers = workers.stream().filter(WorkerDto::getIsAvailable).collect(Collectors.toList());
        return new ModelAndView("workers_list_with_add", "workers", availableWorkers);
    }

    @GetMapping("/all_workers")
    public ModelAndView getAllWorkers() {

        List<WorkerDto> workers = workerService.getAllWorkers();
        return new ModelAndView("workers_list", "workers", workers);

    }

    @GetMapping("/find_worker")
    public ModelAndView getWorkerBySurnameAndMasterId(@RequestParam(name = "foundWorkerSurname") String foundWorkerSurname) {
        Integer masterId = getMasterId();
        List<WorkerDto> worker = workerService.getWorkerBySurnameAndMasterId(foundWorkerSurname, masterId);
        return new ModelAndView("brigade_list", "workers", worker);
    }

    //update
    @GetMapping("/update_worker")
    public ModelAndView getUpdatePage(@RequestParam(name = "workerId") Integer workerId) {
        Integer masterId = getMasterId();
        WorkerDto worker = workerService.getWorkerById(workerId);
        MasterDto masterById = masterService.getMasterById(masterId);

        ModelAndView modelAndView = new ModelAndView("update_worker_page");
        modelAndView.addAllObjects(Map.of("worker", worker,
                "master", masterById));
        return modelAndView;
    }

    @PostMapping("/update_worker")
    public String updateWorkerById(WorkerDto newWorkerVersion,
                                   @RequestParam(name = "workerId") Integer workerId) {
        workerService.updateWorker(newWorkerVersion, workerId);
        return "redirect:/workers/get_workers_by_master_id";
    }

    //delete
    @PostMapping("/delete_worker")
    public String deleteWorkerById(@RequestParam(name = "workerId") Integer workerId) {
        workerService.deleteWorkerById(workerId);
        return "redirect:/workers/get_workers_by_master_id";
    }




    @GetMapping("/transfer_worker_to_another_master")
    public ModelAndView getTransferPage() {
        Integer masterId = getMasterId();
        List<WorkerDto> workers = workerService.getWorkersByMasterId(masterId);
        return new ModelAndView("brigade_list_with_transfer", "workers", workers);
    }

    @PostMapping("/transfer_worker_to_another_master")
    public String transferWorkerToAnotherMaster(@RequestParam(name = "newMasterId") Integer masterId,
                                                @RequestParam(name = "workerId") Integer workerId) {
        workerService.setNewMasterToWorker(workerId, masterId);
        return "redirect:/workers/get_workers_by_master_id";
    }

}
