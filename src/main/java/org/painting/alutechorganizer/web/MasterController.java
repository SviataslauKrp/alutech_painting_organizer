package org.painting.alutechorganizer.web;

import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.service.MasterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor

@Controller
@RequestMapping("/masters")
public class MasterController {

    private final MasterService masterService;

    @PostMapping("/create_master")
    public void saveMaster(@Valid MasterDto master) {
        masterService.saveMaster(master);
    }

    @DeleteMapping("/master_page")
    public void deleteMasterById(@RequestParam Integer id) {
        masterService.deleteMasterById(id);
    }

    @GetMapping(value = "/get_master")
    public void getMasterById(@RequestParam Integer id) {
        MasterDto masterById = masterService.getMasterById(id);
    }

    @PutMapping("/master_page")
    public void updateMasterById(MasterDto master, @RequestParam Integer id) {
        masterService.updateMasterById(master, id);
    }

    @GetMapping("/get_all_masters")
    public void getAllMasters() {
        List<MasterDto> masters = masterService.getAllMasters();
        System.out.println();
    }

    @PostMapping("/add_worker_to_master")
    public void addWorkerToMaster(@RequestParam Integer workerId,
                                  @RequestParam Integer masterId) {
        masterService.addWorkerToMaster(workerId, masterId);
    }

    @DeleteMapping("/remove_worker_from_master")
    public void removeWorkerFromMaster(@RequestParam Integer workerId,
                                       @RequestParam Integer masterId) {
        masterService.removeWorkerFromMaster(workerId, masterId);
    }

}
