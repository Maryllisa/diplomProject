package com.example.diplomproject.service;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import com.example.diplomproject.model.dto.SearchData;
import com.example.diplomproject.model.dto.message.ChatRoomDTO;
import com.example.diplomproject.model.dto.message.MessageDTO;
import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.CustomsAgency;
import com.example.diplomproject.model.entity.MarkForAgency;
import com.example.diplomproject.model.entity.Otpusk;
import com.example.diplomproject.model.entity.chat.ChatMessage;
import com.example.diplomproject.model.entity.chat.ChatRoom;
import com.example.diplomproject.model.entity.enumStatus.MessageStatus;
import com.example.diplomproject.model.entity.enumStatus.TypeEvaluation;
import com.example.diplomproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final EntityManager entityManager;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository messageRepository;
    private final CustomsAgencyRepository customsAgencyRepository;
    private final MarkForAgencyRepository markForAgencyRepository;
    public ChatRoomDTO findChatMessages(String senderLogin, String recipientLogin) {
        Account sender = userRepository.findByLogin(senderLogin);
        Account recipient = userRepository.findByLogin(recipientLogin);
        if (sender==null && recipient==null) return null;
        String chatId = senderLogin + " " + recipientLogin;
        ChatRoom chatRoom = chatRoomRepository.findById(chatId).orElse(null);
        if (chatRoom==null){
            chatId = recipientLogin + " " + senderLogin;
            chatRoom = chatRoomRepository.findById(chatId).orElse(null);
            if (chatRoom==null) {
                chatRoom = new ChatRoom(chatId,
                        sender,
                        recipient,
                        null, null);
                chatRoom = chatRoomRepository.save(chatRoom);
                List<CustomsAgency> customsAgencyList = customsAgencyRepository.findAllByAccount(sender);
                if (sender!=null){
                    CustomsAgency customsAgency = customsAgencyList.get(customsAgencyList.size()-1);
                    MarkForAgency markForAgency = new MarkForAgency();
                    markForAgency.setCustomsAgency(customsAgency);
                    markForAgency.setTypeEvaluation(TypeEvaluation.comunicationQuality);
                    markForAgency.setClient(sender);
                    customsAgency.setListMarkForAgency(markForAgencyRepository.save(markForAgency));
                    customsAgency.setChatRoom(chatRoom);
                    customsAgencyRepository.save(customsAgency);
                }
                else {
                    CustomsAgency customsAgency = customsAgencyList.get(customsAgencyList.size()-1);
                    MarkForAgency markForAgency = new MarkForAgency();
                    markForAgency.setCustomsAgency(customsAgency);
                    markForAgency.setTypeEvaluation(TypeEvaluation.comunicationQuality);
                    markForAgency.setClient(recipient);
                    customsAgency.setListMarkForAgency(markForAgencyRepository.save(markForAgency));
                    customsAgency.setChatRoom(chatRoom);
                    customsAgencyRepository.save(customsAgency);
                }
            }
        }
        ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
        chatRoomDTO.setIdChatRoom(chatRoom.getIdChatRoom());
        chatRoomDTO.setLoginUserSender(chatRoom.getSender().getLogin());
        chatRoomDTO.setLoginUserRecipient(chatRoom.getRecipient().getLogin());
        if (chatRoom.getChatMessageList()!=null) {
            List<ChatMessage> messageList = messageRepository.findByChatRoom(chatRoom);
            for (ChatMessage msg : messageList) {
                chatRoomDTO.setMessageDTOList(new MessageDTO(chatRoom.getIdChatRoom(),
                        chatRoomDTO.getLoginUserSender(),
                        msg.getSenderId().getSurname() + " " + msg.getSenderId().getName(),
                        msg.getContent(), msg.getTimestamp(),
                        msg.getStatus().toString()));
                msg.setStatus(MessageStatus.RECEIVED);
                messageRepository.save(msg);
            }
        }
        return chatRoomDTO;
    }

    public List<ChatRoomDTO> findChatMessagesSender(String senderLogin) {
        Account sender = userRepository.findByLogin(senderLogin);
        if (sender==null ) return null;
        List<ChatRoom> chatRoomList = chatRoomRepository.findAllByIdChatRoomLike(senderLogin);
        if (chatRoomList==null){
                return null;
        }
        List<ChatRoomDTO> chatRoomDTOList = new ArrayList<>();
        for(ChatRoom chatRoom :chatRoomList){
            ChatRoomDTO chatRoomDTO =new ChatRoomDTO();
            chatRoomDTO.setIdChatRoom(chatRoom.getSender().getLogin() + " " + chatRoom.getRecipient().getLogin());
            chatRoomDTO.setLoginUserSender(chatRoom.getSender().getLogin());
            chatRoomDTO.setLoginUserRecipient(chatRoom.getRecipient().getLogin());
            List<ChatMessage> messageList = messageRepository.findByChatRoom(chatRoom);
            for (ChatMessage msg : messageList) {
                chatRoomDTO.setMessageDTOList(new MessageDTO(chatRoom.getIdChatRoom(),
                        chatRoomDTO.getLoginUserSender(),
                        msg.getSenderId().getSurname() + " " + msg.getSenderId().getName(),
                        msg.getContent(), msg.getTimestamp(),
                        msg.getStatus().toString()));
            }
            chatRoomDTOList.add(chatRoomDTO);
        }
        return chatRoomDTOList;
    }

    public List<ChatRoom> getAllByAccount(String name) {
        return chatRoomRepository.findBySenderOrRecipient(userRepository.findByLogin(name), userRepository.findByLogin(name));
    }

    public List<ChatRoom> getAllByAccount(String name, SearchData searchData) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ChatRoom> query = builder.createQuery(ChatRoom.class);
        Root<ChatRoom> root = query.from(ChatRoom.class);
        query.select(root);

        List<Order> orders = new ArrayList<>();

        if (searchData.getSortCriteria() != null && !searchData.getSortCriteria().isEmpty()) {
            if (searchData.getHowSort().equals("asc")) {
                switch (searchData.getSortCriteria()) {
                    case "idMarkingInfo":
                        orders.add(builder.asc(root.get("markForAgency").get("idMarkForAgency")));
                        break;
                    case "markForAgency.evaluation":
                        orders.add(builder.asc(root.get("markForAgency").get("evaluation")));
                        break;
                }
            } else {
                switch (searchData.getSortCriteria()) {
                    case "idMarkingInfo":
                        orders.add(builder.desc(root.get("markForAgency").get("idMarkForAgency")));
                        break;
                    case "markForAgency.evaluation":
                        orders.add(builder.desc(root.get("markForAgency").get("evaluation")));
                        break;
                }
            }
        }

        if (!orders.isEmpty()) {
            query.orderBy(orders);
        }

        List<Predicate> predicates = new ArrayList<>();

        if (searchData.getSearchQuery() != null && !searchData.getSearchQuery().isEmpty()) {
            switch (searchData.getSearchParam()) {
                case "idMarkingInfo":
                    predicates.add(builder.like(root.get("markForAgency").get("idMarkForAgency"), searchData.getSearchQuery()));
                    break;
                case "markForAgency.evaluation":
                    predicates.add(builder.like(root.get("markForAgency").get("evaluation"), searchData.getSearchQuery()));
                    break;
            }
        }

        Predicate searchPredicate = builder.and(predicates.toArray(new Predicate[0]));
        query.where(searchPredicate);
        predicates.add(builder.equal(root.get("account"), userRepository.findByLogin(name)));
        query.where(searchPredicate);
        TypedQuery<ChatRoom> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
