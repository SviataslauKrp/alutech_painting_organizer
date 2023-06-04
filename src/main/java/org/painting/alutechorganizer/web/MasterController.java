package org.painting.alutechorganizer.web;

import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.domain.UserEmployee;
import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.service.MasterService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor

@Controller
@RequestMapping("/masters")
public class MasterController {

    private final MasterService masterService;


    //create
//    @GetMapping("/create_master")
//    public String getCreatingForm(@ModelAttribute(name = "master") MasterDto master) {
//        return "create_master_page";
//    }


//    @PostMapping("/create_master")
//    public String saveMaster(@Valid MasterDto master,
//                             BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            throw new MasterException("The master hasn't been created");
//        }
//        masterService.saveMaster(master);
//        return "redirect:/masters/choose_master";
//
//    }

    //read

    @GetMapping("/choose_master")
    public ModelAndView getAllMasters(@RequestParam(name = "workerId", required = false) Integer workerId) {

        List<MasterDto> masters = masterService.getAllMasters();
        Integer masterId = getMasterId();
        MasterDto master = masters.stream().filter(masterDto -> masterDto.getId().equals(masterId)).toList().get(0);

        if (workerId != null) {
            return new ModelAndView("masters_list_for_transfer", "allMasters", masters);
        }

        return new ModelAndView("main_page", "master", master);

    }

    //update
    @GetMapping("/update_master")
    public ModelAndView getUpdatePage() {
        Integer masterId = getMasterId();
        MasterDto master = masterService.getMasterById(masterId);
        return new ModelAndView("update_master_page", "master", master);
    }

    @PostMapping("/update_master")
    public String updateMasterById(MasterDto master) {
        Integer masterId = getMasterId();
        masterService.updateMasterById(master, masterId);
        return "redirect:/masters/choose_master";
    }


    //delete
    @GetMapping("/delete_master")
    public String deleteMasterById() {
        Integer masterId = getMasterId();
        masterService.deleteMasterById(masterId);
        return "redirect:/login";
    }

//    @PostMapping("/add_worker_to_master")
//    public void addWorkerToMaster(@RequestParam Integer workerId,
//                                  @RequestParam Integer masterId) {
//        masterService.addWorkerToMaster(workerId, masterId);
//    }

    static Integer getMasterId() {
        UserEmployee principal = (UserEmployee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getMaster().getId();
    }

}
