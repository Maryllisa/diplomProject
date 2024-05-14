package com.example.diplomproject.service;

import com.example.diplomproject.model.entity.*;
import com.example.diplomproject.model.entity.chat.ChatRoom;
import com.example.diplomproject.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MarkForAgencyService {
    private final MarkForAgencyRepository markForAgencyRepository;
    private final UserRepository userRepository;
    private final MarkingInfoRepository markingInfoRepository;
    private final DeliveryProductRepository deliveryProductRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final OtpuskRepository otpuskRepository;
    private final CustomsAgencyRepository customsAgencyRepository;

    public void addNewMarkQuality(Long idMark, MarkForAgency markForAgency, String name) {
        Account account = userRepository.findByLogin(name);
        markForAgency.setClient(account);
        DeliveryProduct deliveryProduct = deliveryProductRepository.getById(idMark);
        deliveryProduct.setMarkForAgency(markForAgencyRepository.save(markForAgency));
        deliveryProductRepository.save(deliveryProduct);
    }
    public void addNewPrinProdQuality(Long idMark, MarkForAgency markForAgency, String name) {
        Account account = userRepository.findByLogin(name);
        markForAgency.setClient(account);
        DeliveryProduct deliveryProduct = deliveryProductRepository.getById(idMark);
        deliveryProduct.setMarkForAgency(markForAgencyRepository.save(markForAgency));
        deliveryProductRepository.save(deliveryProduct);
    }
    public void addOtpusck(Long idMark, MarkForAgency markForAgency, String name) {
        Account account = userRepository.findByLogin(name);
        markForAgency.setClient(account);
        Otpusk otpusk = otpuskRepository.getById(idMark);
        otpusk.setMarkForAgency(markForAgencyRepository.save(markForAgency));
        otpuskRepository.save(otpusk);
    }
    public void addCommunication(String idMark, MarkForAgency markForAgency, String name) {
        Account account = userRepository.findByLogin(name);
        markForAgency.setClient(account);
        ChatRoom chatRoom = chatRoomRepository.getById(idMark);
        chatRoom.setMarkForAgency(markForAgencyRepository.save(markForAgency));
        chatRoomRepository.save(chatRoom);
    }
}
