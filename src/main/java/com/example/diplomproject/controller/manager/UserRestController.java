package com.example.diplomproject.controller.manager;

import com.example.diplomproject.model.dto.MarkingInfoDTO;
import com.example.diplomproject.model.entity.MarkingInfo;
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
public class UserRestController {
    private final MarkingInfoService markingInfoService;
    @PostMapping("/user/registration/marking")
    public ResponseEntity<String> addNewMarking(@ModelAttribute MarkingInfoDTO markingInfoDTO,
                                                Model model){
        markingInfoService.addNewMarking(markingInfoDTO);
        return ResponseEntity.ok("Добавлено");
    }
    @GetMapping("/user/qr/{id}")
    private ResponseEntity<?> getImageByIDUser(@PathVariable String id)
    {
        MarkingInfo qr = markingInfoService.findById(Integer.parseInt(id));
        Base64.Decoder decoder = Base64.getDecoder();
        return ResponseEntity.ok()
                .header("fileName", qr.getOriginalFileName())
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(Long.parseLong(qr.getSize()))
                .body(new InputStreamResource(new ByteArrayInputStream(decoder.decode(qr.getSrcCode()))));
    }
    @GetMapping("/client/qr/{id}")
    private ResponseEntity<?> getImageByIDClient(@PathVariable String id)
    {
        MarkingInfo qr = markingInfoService.findById(Integer.parseInt(id));
        Base64.Decoder decoder = Base64.getDecoder();
        return ResponseEntity.ok()
                .header("fileName", qr.getOriginalFileName())
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(Long.parseLong(qr.getSize()))
                .body(new InputStreamResource(new ByteArrayInputStream(decoder.decode(qr.getSrcCode()))));
    }
}
