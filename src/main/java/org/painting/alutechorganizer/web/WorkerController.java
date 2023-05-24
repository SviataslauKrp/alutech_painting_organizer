package org.painting.alutechorganizer.web;

import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.service.MasterService;
import org.painting.alutechorganizer.service.WorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor

@Controller
@RequestMapping("/workers")
public class WorkerController {

    private final WorkerService workerService;
    private final MasterService masterService;

    @PostMapping("/create_worker")
    public String saveWorker(@Valid WorkerDto worker) {
        workerService.saveWorker(worker);
        return "redirect:/workers/all_workers";
    }


    @GetMapping("/create_worker")
    public ModelAndView getCreatingForm(@ModelAttribute("worker") WorkerDto worker) {
        List<MasterDto> allMasters = masterService.getAllMasters();
        return new ModelAndView("create_worker", "allMasters", allMasters);
    }


    @GetMapping("/get_workers_by_master_id")
    public ModelAndView getWorkersByMasterId(@RequestParam(name = "masterId") Integer masterId,
                                             @RequestParam(name = "workplaceId", required = false) Integer workplaceId) {
        List<WorkerDto> workers = workerService.getWorkersByMasterId(masterId);

        if (workplaceId == null) {
            return new ModelAndView("brigade_list", "workers", workers);
        }

        return new ModelAndView("workers_list_with_add", "workers", workers);
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

    @PostMapping("/delete_worker")
    public String deleteWorkerById(@RequestParam(name = "workerId") Integer workerId,
                                   @RequestParam(name = "masterId") Integer masterId) {
        workerService.deleteWorkerById(workerId);
        return String.format("redirect:/workers/get_workers_by_master_id?masterId=%d", masterId);
    }


    @GetMapping("/update_worker")
    public ModelAndView getUpdatePage(@RequestParam(name = "workerId") Integer workerId,
                                      @RequestParam(name = "masterId") Integer masterId) {
        WorkerDto worker = workerService.getWorkerById(workerId);
        List<MasterDto> allMasters = masterService.getAllMasters();
        ModelAndView modelAndView = new ModelAndView("update_worker_page");
        modelAndView.addAllObjects(Map.of("worker", worker,
                "allMasters", allMasters));
        return modelAndView;
    }

    @PostMapping("/update_worker")
    public String updateWorkerById(WorkerDto worker,
                                   @RequestParam(name = "workerId") Integer workerId,
                                   @RequestParam(name = "masterId") Integer masterId) {

        workerService.updateWorker(worker, workerId);
        return String.format("redirect:/workers/get_workers_by_master_id?masterId=%d", masterId);
    }

    @GetMapping("/transfer_worker_to_another_master")
    public ModelAndView getTransferPage(@RequestParam(name = "masterId") Integer masterId) {

        List<WorkerDto> workers = workerService.getWorkersByMasterId(masterId);
        return new ModelAndView("brigade_list_with_transfer", "workers", workers);
    }

    @PostMapping("transfer_worker_to_another_master")
    public String transferWorkerToAnotherMaster(@RequestParam(name = "newMasterId") Integer masterId,
                                                @RequestParam(name = "workerId") Integer workerId) {
        WorkerDto workerById = workerService.getWorkerById(workerId);
        Integer oldMasterId = workerById.getMaster().getId();
        workerService.setNewMasterToWorker(workerId, masterId);
        return String.format("redirect:/workers/get_workers_by_master_id?masterId=%d", oldMasterId);

    }

}
