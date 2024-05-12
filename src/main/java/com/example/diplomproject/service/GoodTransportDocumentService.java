package com.example.diplomproject.service;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.GoodTransportDocument;
import com.example.diplomproject.repository.GoodTransportDocumentRepository;
import com.example.diplomproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class GoodTransportDocumentService {
    private final GoodTransportDocumentRepository goodTransportDocumentRepository;
    private final UserRepository userRepository;

    @SneakyThrows
    public void addNewGTD(GoodTransportDocument goodTransportDocument, MultipartFile file, String login) {
        log.info("Добавление нового ТТН");
        Base64.Encoder encoder = Base64.getEncoder();
        String encoded = encoder.encodeToString(file.getBytes());
        goodTransportDocument.setSrcTransportDocument(encoded);
        goodTransportDocument.setAccount(userRepository.findByLogin(login));
        goodTransportDocumentRepository.save(goodTransportDocument);
    }

    public List<GoodTransportDocument> getAllByAccaount(String name) {
        Account account = userRepository.findByLogin(name);
        return goodTransportDocumentRepository.findAllByAccount(account);
    }

    public GoodTransportDocument getSrcById(Long id) {
        return goodTransportDocumentRepository.getById(id);
    }
}
