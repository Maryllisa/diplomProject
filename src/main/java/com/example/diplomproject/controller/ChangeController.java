package com.example.diplomproject.controller;

import com.example.diplomproject.model.entity.GoodTransportDocument;
import com.example.diplomproject.service.GoodTransportDocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;
import java.util.Base64;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/client")
public class ChangeController {
    private final GoodTransportDocumentService goodTransportDocumentService;
    @GetMapping("/findTransportDocumentPdf/{id}")
    private ResponseEntity<?> getImageByIDUser(@PathVariable Long id)
    {
        GoodTransportDocument qr = goodTransportDocumentService.getSrcById(id);
        Base64.Decoder decoder = Base64.getDecoder();
        return ResponseEntity.ok()
                .header("fileName", "src")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new ByteArrayInputStream(decoder.decode(qr.getSrcTransportDocument()))));
    }

}
