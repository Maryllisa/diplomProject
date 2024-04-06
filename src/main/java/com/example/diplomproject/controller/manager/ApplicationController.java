package com.example.diplomproject.controller.manager;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class ApplicationController {

    @GetMapping("/user/readApplication/{id}")
    public String getApplicationById(Model model, @PathVariable String id){

        // вывод подробный всех документов, относящихся к заявки
        return "Тут пока ничего нет";
    }
}
