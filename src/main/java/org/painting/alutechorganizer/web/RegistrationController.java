package org.painting.alutechorganizer.web;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.painting.alutechorganizer.domain.UserEmployee;
import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.exc.MasterException;
import org.painting.alutechorganizer.exc.WorkerException;
import org.painting.alutechorganizer.service.MasterService;
import org.painting.alutechorganizer.service.WorkerService;
import org.painting.alutechorganizer.service.impl.UserEmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.util.List;

import static org.painting.alutechorganizer.web.MasterController.*;

@RequiredArgsConstructor

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserEmployeeService userService;
    private final WorkerService workerService;
    private final MasterService masterService;

    @GetMapping
    public String getRegistrationForm(Model model) {
        model.addAttribute("userForm", new UserEmployee());
        model.addAttribute("master", new MasterDto());
        return "registration_master_form";
    }

    @PostMapping
    public String registerMaster(@ModelAttribute(name = "userForm") @Valid UserEmployee user,
                                 BindingResult userBindingResult,
                                 @ModelAttribute(name = "master") @Valid MasterDto masterDto,
                                 BindingResult masterBindingResult) {

        if (masterBindingResult.hasErrors() || userBindingResult.hasErrors()) {
            throw new MasterException("The master hasn't been created");
        }
        if (!StringUtils.equals(user.getPassword(), user.getPasswordConfirm())) {
            throw new MasterException("The passwords aren't equals");
        }
        if (!userService.saveUser(user, masterDto)) {
            throw new MasterException("There is a master with such name already");
        }
        return "redirect:/masters/choose_master";
    }

    @GetMapping("/create_worker")
    public ModelAndView getCreatingForm(Model model) {
        List<MasterDto> allMasters = masterService.getAllMasters();
        model.addAttribute("worker", new WorkerDto());
        model.addAttribute("userForm", new UserEmployee());
        return new ModelAndView("create_worker_page", "allMasters", allMasters);
    }

    @PostMapping("/create_worker")
    public String saveWorker(@ModelAttribute(name = "userForm") @Valid UserEmployee user,
                             BindingResult userBindingResult,
                             @ModelAttribute(name = "worker") @Valid WorkerDto worker,
                             BindingResult workerBindingResult) {

        Integer masterId = getMasterId();
        if (workerBindingResult.hasErrors() || userBindingResult.hasErrors()) {
            throw new WorkerException("The worker hasn't been created");
        }
        if (!StringUtils.equals(user.getPassword(), user.getPasswordConfirm())) {
            throw new WorkerException("The passwords aren't equals");
        }
        if (!userService.saveUser(user, worker, masterId)) {
            throw new WorkerException("There is a worker with such name already");
        }
        return "redirect:/workers/all_workers";
    }
}
