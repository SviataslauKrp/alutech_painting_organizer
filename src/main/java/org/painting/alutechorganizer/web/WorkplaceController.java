package org.painting.alutechorganizer.web;

import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.dto.WorkplaceDto;
import org.painting.alutechorganizer.exc.WorkplaceException;
import org.painting.alutechorganizer.service.WorkplaceService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;


@RequiredArgsConstructor
@Controller
@RequestMapping("/workplaces")
public class WorkplaceController {

    private final WorkplaceService workplaceService;

    @GetMapping("/create_workplace")
    public ModelAndView getCreateFormForWorkplace(@ModelAttribute(name = "workplace") WorkplaceDto workplace,
                                                  @RequestParam(name = "masterId") Integer masterId) {

        return new ModelAndView("create_workplace_page");

    }

    @PostMapping("/create_workplace")
    public String createWorkplace(@Valid WorkplaceDto workplace,
                                  BindingResult bindingResult,
                                  @RequestParam(name = "masterId") Integer masterId) {
        if (bindingResult.hasErrors()) {
            throw new WorkplaceException("The workplace hasn't been created");
        }
        workplaceService.saveWorkplace(workplace, masterId);
        return String.format("redirect:/workplaces/get_all_workplaces?masterId=%d", masterId);

    }

    @GetMapping("/get_all_workplaces")
    public ModelAndView getWorkplacesByMasterId(@RequestParam(name = "masterId") Integer masterId) {
        List<WorkplaceDto> workplaces = workplaceService.getWorkplacesByMasterId(masterId);
        return new ModelAndView("workplaces_list", "allWorkplaces", workplaces);
    }


    @GetMapping(value = "/workplace_page")
    public void getWorkplace(@RequestParam Integer id) {

        WorkplaceDto workplaceById = workplaceService.getWorkplaceById(id);

    }

    @PostMapping("/delete_workplace")
    public String deleteWorkplaceById(@RequestParam(name = "workplaceId") Integer workplaceId,
                                      @RequestParam(name = "masterId") Integer masterId) {
        workplaceService.deleteWorkplaceById(workplaceId);
        return String.format("redirect:/workplaces/get_all_workplaces?masterId=%d", masterId);
    }

    //update
    @GetMapping("/update_workplace")
    public ModelAndView getUpdatePage(@RequestParam(name = "workplaceId") Integer workplaceId,
                                      @RequestParam(name = "masterId") Integer masterId) {

        WorkplaceDto workplace = workplaceService.getWorkplaceById(workplaceId);
        return new ModelAndView("update_workplace_page", "workplace", workplace);

    }

    @PostMapping("/update_workplace")
    public String updateWorkplaceById(WorkplaceDto workplace,
                                      @RequestParam(name = "workplaceId") Integer workplaceId,
                                      @RequestParam(name = "masterId") Integer masterId) {

        workplaceService.updateWorkplaceById(workplace, workplaceId);
        return String.format("redirect:/workplaces/get_all_workplaces?masterId=%d", masterId);

    }

    @PostMapping("/add_worker_to_workplace")
    public String addWorkerToWorkplace(@RequestParam(name = "workerId") Integer workerId,
                                       @RequestParam(name = "workplaceId") Integer workplaceId,
                                       @RequestParam(name = "masterId") Integer masterId) {

        workplaceService.addWorkerToWorkplace(workerId, workplaceId);
        return String.format("redirect:/workplaces/get_all_workplaces?masterId=%d", masterId);

    }

    @PostMapping("/remove_worker_from_workplace")
    public String removeWorkerFromWorkplace(@RequestParam(name = "workerId") Integer workerId,
                                            @RequestParam(name = "workplaceId") Integer workplaceId,
                                            @RequestParam(name = "masterId") Integer masterId) {

        workplaceService.removeWorkerFromWorkplace(workerId, workplaceId);
        return String.format("redirect:/workplaces/get_all_workplaces?masterId=%d", masterId);
    }

}
