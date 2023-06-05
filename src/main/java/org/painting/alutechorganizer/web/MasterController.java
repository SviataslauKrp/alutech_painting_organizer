package org.painting.alutechorganizer.web;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.painting.alutechorganizer.domain.UserEmployee;
import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.exc.MasterException;
import org.painting.alutechorganizer.exc.WorkerException;
import org.painting.alutechorganizer.service.MasterService;
import org.painting.alutechorganizer.service.impl.UserEmployeeService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor

@Controller
@RequestMapping("/masters")
public class MasterController {

    private final MasterService masterService;
    private final UserEmployeeService userService;


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
        UserEmployee principal = (UserEmployee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ModelAndView modelAndView = new ModelAndView("update_master_page");
        modelAndView.addAllObjects(Map.of("master", master,
                "principal", principal));
        return modelAndView;
    }

    @PostMapping("/update_master")
    public String updateMasterById(@ModelAttribute(name = "master") MasterDto master) {
        Integer masterId = getMasterId();
        masterService.updateMasterById(master, masterId);
        return "redirect:/masters/choose_master";
    }


    //delete
    @GetMapping("/delete_master")
    public String deleteMasterById() {
        Integer masterId = getMasterId();
        masterService.deleteMasterById(masterId);
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }

    static Integer getMasterId() {
        UserEmployee principal = (UserEmployee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getMaster().getId();
    }

}
