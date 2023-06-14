package org.painting.alutechorganizer.web;

import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.domain.UserEmployee;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.dto.WorkplaceDto;
import org.painting.alutechorganizer.exc.MasterException;
import org.painting.alutechorganizer.exc.WorkplaceException;
import org.painting.alutechorganizer.service.WorkplaceService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.painting.alutechorganizer.web.MasterController.getMasterId;


@RequiredArgsConstructor
@Controller
@RequestMapping("/workplaces")
public class WorkplaceController {

    private final WorkplaceService workplaceService;

    @GetMapping("/create_workplace")
    public ModelAndView getCreateFormForWorkplace() {
        return new ModelAndView("create_workplace_page", "workplace", new WorkplaceDto());
    }

    @PostMapping("/create_workplace")
    public String createWorkplace(@Valid WorkplaceDto workplace,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new WorkplaceException("The workplace hasn't been created");
        }
        Integer masterId = getMasterId();
        workplaceService.saveWorkplace(workplace, masterId);
        return "redirect:/workplaces/get_all_workplaces";
    }

    @GetMapping("/get_all_workplaces")
    public ModelAndView getWorkplacesByMasterId() {

        Integer masterId;

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_WORKER"))) {
            UserEmployee principal = (UserEmployee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            WorkerEntity worker = principal.getWorker();
            masterId = worker.getMaster().getId();
            List<WorkplaceDto> workplacesByMasterId = workplaceService.getWorkplacesByMasterId(masterId);
            ModelAndView modelAndView = new ModelAndView("workplaces_list_for_worker", "principal", worker);
            modelAndView.addObject("allWorkplaces", workplacesByMasterId);
            return modelAndView;
        }
        masterId = getMasterId();
        List<WorkplaceDto> workplaces = workplaceService.getWorkplacesByMasterId(masterId);
        return new ModelAndView("workplaces_list", "allWorkplaces", workplaces);
    }

    @PostMapping("/delete_workplace")
    public String deleteWorkplaceById(@RequestParam(name = "workplaceId") Integer workplaceId) {
        workplaceService.deleteWorkplaceById(workplaceId);
        return "redirect:/workplaces/get_all_workplaces";
    }

    //update
    @GetMapping("/update_workplace")
    public ModelAndView getUpdatePage(@RequestParam(name = "workplaceId") Integer workplaceId) {
        Integer masterId = getMasterId();
        WorkplaceDto workplace = workplaceService.getWorkplaceById(workplaceId);
        if (!Objects.equals(workplace.getMaster().getId(), masterId)) {
            throw new MasterException("cheating");
        }
        return new ModelAndView("update_workplace_page", "workplace", workplace);
    }

    @PostMapping("/update_workplace")
    public String updateWorkplaceById(WorkplaceDto workplace,
                                      @RequestParam(name = "workplaceId") Integer workplaceId) {
        workplaceService.updateWorkplaceById(workplace, workplaceId);
        return "redirect:/workplaces/get_all_workplaces";
    }

    @PostMapping("/add_worker_to_workplace")
    public String addWorkerToWorkplace(@RequestParam(name = "workerId") Integer workerId,
                                       @RequestParam(name = "workplaceId") Integer workplaceId) {

        workplaceService.addWorkerToWorkplace(workerId, workplaceId);
        return "redirect:/workplaces/get_all_workplaces";
    }

    @PostMapping("/remove_worker_from_workplace")
    public String removeWorkerFromWorkplace(@RequestParam(name = "workerId") Integer workerId,
                                            @RequestParam(name = "workplaceId") Integer workplaceId) {

        workplaceService.removeWorkerFromWorkplace(workerId, workplaceId);
        return "redirect:/workplaces/get_all_workplaces";
    }

}
