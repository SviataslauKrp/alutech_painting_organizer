package org.painting.alutechorganizer.web;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.painting.alutechorganizer.domain.UserEmployee;
import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.exc.MasterException;
import org.painting.alutechorganizer.service.impl.UserEmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequiredArgsConstructor

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserEmployeeService userService;

    @GetMapping
    public String getRegistrationForm(Model model) {
        model.addAttribute("userForm", new UserEmployee());
        model.addAttribute("master", new MasterDto());
        return "registration_form";
    }

    @PostMapping
    public String registerMaster(@ModelAttribute(name = "userForm") @Valid UserEmployee user,
                                 @ModelAttribute(name = "master") @Valid MasterDto masterDto,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
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

}
