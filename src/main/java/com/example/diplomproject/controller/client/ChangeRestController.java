package com.example.diplomproject.controller.client;

import com.example.diplomproject.message.AnswerMessage;
import com.example.diplomproject.model.dto.IndividualsDTO;
import com.example.diplomproject.model.dto.TruckDTO;
import com.example.diplomproject.service.ApplicationForMarkingService;
import com.example.diplomproject.service.IndividualsService;
import com.example.diplomproject.service.TruckService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/client")
public class ChangeRestController {

    private final IndividualsService individualsService;
    private final TruckService truckService;
    private final ApplicationForMarkingService applicationForMarkingService;

    @GetMapping("/findTruck/{id}")
    public TruckDTO getTruckDTO(@PathVariable("id") Long id,
                                Authentication authentication,
                                Model model) {
        return truckService.getTruck(id);
    }

    @PostMapping("/changeAuto/{idAuto}")
    private ResponseEntity<Map<String, String>> changeAuto(@PathVariable Long idAuto, @ModelAttribute @Valid TruckDTO truckDTO,
                                                           BindingResult result,
                                                           Authentication authentication) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    AnswerMessage.getBadMessage(truckService.check(result, truckDTO)));
        }
        truckService.changeTruck(truckDTO, authentication.getName());
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Авто успешно перерегистрированно"));
    }

    @GetMapping("/findSupplier/{id}")
    public IndividualsDTO getProvider(@PathVariable("id") Long id,
                                      Authentication authentication,
                                      Model model) {
        IndividualsDTO individuals = individualsService.findById(id);
        return individuals;
    }

    @PostMapping("/changeSupplier/{idSup}")
    private ResponseEntity<Map<String, String>> changeCompany(@PathVariable Long idSup, @ModelAttribute @Valid IndividualsDTO individualsDTO,
                                                              BindingResult result,
                                                              Authentication authentication) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    AnswerMessage.getBadMessage(individualsService.check(result, individualsDTO)));
        }
        individualsService.change(individualsDTO, idSup);
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Поставщик успешно изменен"));
    }
    @GetMapping("/deleteZavForMark/{id}")
    private ResponseEntity<Map<String, String>> deleteZavForMark(@PathVariable Long id){
        applicationForMarkingService.deleteApplication(id);
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Заяка успешно отменена"));
    }

}
