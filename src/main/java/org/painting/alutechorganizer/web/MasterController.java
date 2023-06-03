package org.painting.alutechorganizer.web;

import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.domain.UserEmployee;
import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.exc.MasterException;
import org.painting.alutechorganizer.service.MasterService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.http.HttpRequest;
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

        if (workerId != null) {
            return new ModelAndView("masters_list_for_transfer", "allMasters", masters);
        }
        return new ModelAndView("main_page", "allMasters", masters);

    }

    //update
    @GetMapping("/update_master")
    public ModelAndView getUpdatePage(@RequestParam(name = "masterId") Integer id) {
        MasterDto master = masterService.getMasterById(id);
        return new ModelAndView("update_master_page", "master", master);
    }

    @PostMapping("/update_master")
    public String updateMasterById(MasterDto master,
                                   @RequestParam(name = "masterId") Integer masterId) {

        masterService.updateMasterById(master, masterId);
        return "redirect:/masters/choose_master";

    }


    //delete
    @GetMapping("/delete_master")
    public String deleteMasterById(@RequestParam(name = "masterId") Integer id) {
        masterService.deleteMasterById(id);
        return "redirect:/masters/choose_master";
    }

    @PostMapping("/add_worker_to_master")
    public void addWorkerToMaster(@RequestParam Integer workerId,
                                  @RequestParam Integer masterId) {
        masterService.addWorkerToMaster(workerId, masterId);
    }

    private Integer getMasterId() {
        UserEmployee principal = (UserEmployee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getMaster().getId();
    }

}
