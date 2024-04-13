package com.example.diplomproject.service;

import com.example.diplomproject.model.entity.GoodTransportDocument;
import com.example.diplomproject.repository.GoodTransportDocumentRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@Service
@AllArgsConstructor
@Slf4j
public class GoodTransportDocumentService {
    private final GoodTransportDocumentRepository goodTransportDocumentRepository;

    @SneakyThrows
    public void addNewGTD(GoodTransportDocument goodTransportDocument, MultipartFile file) {
        log.info("Добавление нового ТТН");
        Base64.Encoder encoder = Base64.getEncoder();
        String encoded = encoder.encodeToString(file.getBytes());
        goodTransportDocument.setSrcTransportDocument(encoded);
    }
}
