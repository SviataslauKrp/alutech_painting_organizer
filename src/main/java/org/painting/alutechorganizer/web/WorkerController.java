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
    public String saveWorker(@Valid @ModelAttribute(name = "worker") WorkerDto worker) {
        workerService.saveWorker(worker);
        return "redirect:/workers/all_workers";
    }


    @GetMapping("/create_worker")
    public ModelAndView getCreateForm() {
        ModelAndView modelAndView = new ModelAndView("create_worker");
        List<MasterDto> allMasters = masterService.getAllMasters();
        modelAndView.addAllObjects(Map.of("worker", new WorkerDto(),
                "allMasters", allMasters));
        return modelAndView;
    }

    @GetMapping(value = "/personal_page")
    public ModelAndView getWorkerById(@RequestParam Integer id) {

        WorkerDto worker = workerService.getWorkerById(id);
        return new ModelAndView("personal_page", "worker", worker);

    }

    @DeleteMapping("/personal_page")
    public void deleteWorkerById(@RequestParam Integer id) {
        workerService.deleteWorkerById(id);
    }

    @GetMapping("/all_workers")
    public ModelAndView getAllWorkers() {

        List<WorkerDto> workers = workerService.getAllWorkers();
        return new ModelAndView("workers_list", "workers", workers);

    }

    @PostMapping(value = "/all_workers", params = "workplaceId")
    public ModelAndView getAllWorkersWithAdd() {

        List<WorkerDto> workers = workerService.getAllWorkers();
        return new ModelAndView("workers_list_with_add", "workers", workers);

    }

    @GetMapping("/update_worker")
    public ModelAndView getUpdate(@RequestParam(name = "workerId") Integer id) {
        WorkerDto worker = workerService.getWorkerById(id);
        Integer masterId = worker.getMaster().getId();
        List<MasterDto> allMasters = masterService.getAllMasters();
        ModelAndView modelAndView = new ModelAndView("update_page");
        modelAndView.addAllObjects(Map.of("worker", worker,
                "allMasters", allMasters,
                "masterId", masterId));
        return modelAndView;
    }

    @PostMapping("/update_worker")
    public String updateWorkerById(WorkerDto worker,
                                   @RequestParam(name = "workerId") Integer workerId,
                                   @RequestParam(name = "masterId") Integer masterId) {
        workerService.updateWorker(worker, workerId);
        //чувствую, что решение "на коленке"
        return String.format("redirect:/workers/get_workers_by_master_id?masterId=%d", masterId);
    }

    @GetMapping(value = "/get_workers_by_master_id", params = "masterId")
    public ModelAndView getWorkersByMasterId(@RequestParam(name = "masterId") Integer masterId) {

        List<WorkerDto> workers = workerService.getWorkersByMasterId(masterId);
        return new ModelAndView("brigade_list", "workers", workers);

    }

    @GetMapping(value = "/get_workers_by_master_id", params = {"masterId", "workplaceId"})
    public ModelAndView getWorkersByMasterIdWithAdd(@RequestParam(name = "masterId") Integer masterId) {
        //как работать со скрытыми параметрами??
        List<WorkerDto> workers = workerService.getWorkersByMasterId(masterId);
        ModelAndView modelAndView = new ModelAndView("workers_list_with_add");
        modelAndView.addAllObjects(Map.of("workers", workers,
                                          "masterId", masterId));
        return modelAndView;
    }

    @GetMapping("/transfer_worker_to_another_master")
    public ModelAndView getTransferPage(@RequestParam(name = "masterId") Integer masterId) {

        List<WorkerDto> workers = workerService.getWorkersByMasterId(masterId);
        ModelAndView modelAndView = new ModelAndView("brigade_list_with_transfer");
        modelAndView.addAllObjects(Map.of("workers", workers));
        return modelAndView;
    }

    @PostMapping("transfer_worker_to_another_master")
    public String transferWorkerToAnotherMaster(@RequestParam(name = "newMasterId") Integer masterId,
                                                @RequestParam(name = "workerId") Integer workerId) {
        WorkerDto workerById = workerService.getWorkerById(workerId);
        Integer oldMasterId = workerById.getMaster().getId();
        workerService.setNewMasterToWorker(workerId, masterId);
        return String.format("redirect:/workers/get_workers_by_master_id?masterId=%d", oldMasterId);

    }

    @GetMapping("/list_masters_for_transfer")
    public ModelAndView getListMastersForTransfer(@RequestParam(name = "workerId") Integer workerId) {

        List<MasterDto> allMasters = masterService.getAllMasters();
        ModelAndView modelAndView = new ModelAndView("masters_list_for_transfer");
        modelAndView.addAllObjects(Map.of("allMasters", allMasters,
                                          "workerId", workerId));
        return modelAndView;

    }

}
