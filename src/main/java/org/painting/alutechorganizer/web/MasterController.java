package org.painting.alutechorganizer.web;

import lombok.RequiredArgsConstructor;
import org.hibernate.result.UpdateCountOutput;
import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.service.MasterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor

@Controller
@RequestMapping("/masters")
public class MasterController {

    private final MasterService masterService;


    @GetMapping("/create_master")
    public String getCreatingForm(@ModelAttribute(name = "master") MasterDto master) {
        return "create_master_page";
    }


    @PostMapping("/create_master")
    public String saveMaster(@Valid MasterDto master) {
        masterService.saveMaster(master);
        return "redirect:/masters/choose_master";
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

    @GetMapping("/choose_master")
    public ModelAndView getAllMasters(@RequestParam(name = "workerId", required = false) Integer workerId) {
        List<MasterDto> masters = masterService.getAllMasters();

        if (workerId != null) {
            return new ModelAndView("masters_list_for_transfer", "allMasters", masters);
        }
        return new ModelAndView("main_page", "allMasters", masters);

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
