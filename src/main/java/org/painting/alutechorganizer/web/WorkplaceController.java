package org.painting.alutechorganizer.web;

import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.dto.WorkplaceDto;
import org.painting.alutechorganizer.service.WorkplaceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Controller
@RequestMapping("/workplaces")
public class WorkplaceController {

    private final WorkplaceService service;

    @GetMapping("/create_workplace")
    public ModelAndView getCreateFormForWorkplace() {

        return new ModelAndView("create_workplace_page", "workplace", new WorkplaceDto());

    }

    @PostMapping("/create_workplace")
    public String createWorkplace(@Valid WorkplaceDto workplace) {

        service.saveWorkplace(workplace);
        return "redirect:/workplaces/get_all_workplaces";

    }

    @GetMapping("/get_all_workplaces")
    public ModelAndView getAllWorkplaces(@RequestParam(name = "masterId") Integer masterId) {
        List<WorkplaceDto> allWorkplaces = service.getAllWorkplaces();
        ModelAndView modelAndView = new ModelAndView("workplaces_list");
        modelAndView.addAllObjects(Map.of("allWorkplaces", allWorkplaces,
                                          "masterId", masterId));
        return modelAndView;
    }


    @GetMapping(value = "/workplace_page")
    public void getWorkplace(@RequestParam Integer id) {

        WorkplaceDto workplaceById = service.getWorkplaceById(id);

    }

    @DeleteMapping("/workplace_page")
    public void deleteWorkplaceById(@RequestParam Integer id) {
        service.deleteWorkplaceById(id);
    }

    @PutMapping("/workplace_page")
    public void updateWorkplaceById(@Valid WorkplaceDto workplace, @RequestParam Integer id) {
        service.updateWorkplaceById(workplace, id);
    }

    @PostMapping("/add_worker_to_workplace")
    public String addWorkerToWorkplace(@RequestParam(name = "workerId") Integer workerId,
                                       @RequestParam(name = "workplaceId") Integer workplaceId,
                                       @RequestParam(name = "masterId") Integer masterId) {

        service.addWorkerToWorkplace(workerId, workplaceId);
        //не похоже на решение на коленке?
        return String.format("redirect:/workplaces/get_all_workplaces?masterId=%d", masterId);

    }

    @PostMapping("/remove_worker_from_workplace")
    public String removeWorkerFromWorkplace(@RequestParam(name = "workerId") Integer workerId,
                                            @RequestParam(name = "workplaceId") Integer workplaceId,
                                            @RequestParam(name = "masterId") Integer masterId) {

        service.removeWorkerFromWorkplace(workerId, workplaceId);
        return String.format("redirect:/workplaces/get_all_workplaces?masterId=%d", masterId);
    }

}
