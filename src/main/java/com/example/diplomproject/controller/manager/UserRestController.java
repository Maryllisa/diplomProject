package com.example.diplomproject.controller.manager;

import com.example.diplomproject.model.dto.MarkingInfoDTO;
import com.example.diplomproject.model.entity.ApplicationForStorage;
import com.example.diplomproject.model.entity.MarkingInfo;
import com.example.diplomproject.model.entity.enumStatus.StatusApplication;
import com.example.diplomproject.model.entity.marking.StatusMarkingApplication;
import com.example.diplomproject.service.ApplicationForMarkingService;
import com.example.diplomproject.service.ApplicationForStorageService;
import com.example.diplomproject.service.ApplicationService;
import com.example.diplomproject.service.MarkingInfoService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.Base64;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserRestController {
    private final MarkingInfoService markingInfoService;
    private final ApplicationForStorageService applicationForStorageService;
    private ApplicationForMarkingService applicationForMarking;

    @PostMapping("/registration/marking")
    public ResponseEntity<String> addNewMarking(@ModelAttribute MarkingInfoDTO markingInfoDTO,
                                                Model model){
        markingInfoService.addNewMarking(markingInfoDTO);
        return ResponseEntity.ok("Добавлено");
    }
    @GetMapping("/qr/{id}")
    private ResponseEntity<?> getImageByIDUser(@PathVariable Long id)
    {
        MarkingInfo qr = markingInfoService.findById(id);
        Base64.Decoder decoder = Base64.getDecoder();
        return ResponseEntity.ok()
                .header("fileName", qr.getOriginalFileName())
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(Long.parseLong(qr.getSize()))
                .body(new InputStreamResource(new ByteArrayInputStream(decoder.decode(qr.getSrcCode()))));
    }
//    @GetMapping("/client/qr/{id}")
//    private ResponseEntity<?> getImageByIDClient(@PathVariable String id)
//    {
//        MarkingInfo qr = markingInfoService.findById(Integer.parseInt(id));
//        Base64.Decoder decoder = Base64.getDecoder();
//        return ResponseEntity.ok()
//                .header("fileName", qr.getOriginalFileName())
//                .contentType(MediaType.IMAGE_PNG)
//                .contentLength(Long.parseLong(qr.getSize()))
//                .body(new InputStreamResource(new ByteArrayInputStream(decoder.decode(qr.getSrcCode()))));
//    }

    @GetMapping("/productsInfo/{idProduct}")
    private ResponseEntity<MarkingInfoDTO> getMarkInfo(@PathVariable Long idProduct){
        return ResponseEntity.ok(markingInfoService.findById(idProduct).build());
    }
    @GetMapping("/findApplication/{id}")
    private ApplicationForStorage getMarkInfos(@PathVariable Long id){
        return applicationForStorageService.findById(id);
    }
    @GetMapping("" +
            "/changeStatusMark/{idMark}")
    public ResponseEntity<String> changeStatusMark(@PathVariable Long idMark){
        return ResponseEntity.ok(applicationForMarking.changeStatus(idMark, StatusMarkingApplication.CANCELED));
    }
}
