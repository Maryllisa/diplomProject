package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.AccountDTO;
import com.example.diplomproject.model.dto.message.ChatRoomDTO;
import com.example.diplomproject.model.dto.message.MessageDTO;
import com.example.diplomproject.model.dto.message.UserDTO;
import com.example.diplomproject.model.entity.*;
import com.example.diplomproject.repository.ChatMessageRepository;
import com.example.diplomproject.repository.ChatRoomRepository;
import com.example.diplomproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import java.util.*;

@Service
@AllArgsConstructor
public class AccountService {

    private final UserRepository accountRepository;
    private final MailSender mailSender;
    private final ChatMessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    public Map<String, String> getCheckAccount(BindingResult result, AccountDTO accountDTO){
        Map<String, String> checkAccountDto = new HashMap<>();
        result.getFieldErrors().forEach(error ->{
            switch (error.getField()){
                case "surname":{
                    if (accountDTO.getSurname()!=null)
                        checkAccountDto.put(error.getField(), "Некорректный формат фамилии");
                    else checkAccountDto.put(error.getField(), "Поле должно быть заполнено");
                    break;
                }
                case "name":{
                    if (accountDTO.getName()!=null)
                        checkAccountDto.put(error.getField(), "Некорректный формат имени");
                    else checkAccountDto.put(error.getField(), "Поле должно быть заполнено");
                    break;
                }
                case "patronymic":{
                    if (accountDTO.getPatronymic()!=null)
                        checkAccountDto.put(error.getField(), "Некорректный формат отчества");
                    else checkAccountDto.put(error.getField(), "Поле должно быть заполнено");
                    break;
                }
                case "dateBirthday":{
                    if (accountDTO.getDateBirthday()!=null)
                        checkAccountDto.put(error.getField(), "Некорректный формат даты");
                    else checkAccountDto.put(error.getField(), "Поле должно быть заполнено");
                    break;
                }
                case "phone":{
                    if (accountDTO.getPhone()!=null)
                        checkAccountDto.put(error.getField(), "Некорректный формат телефона");
                    else checkAccountDto.put(error.getField(), "Поле должно быть заполнено");
                    break;
                }
                case "email":{
                    if (accountDTO.getEmail()!=null)
                        checkAccountDto.put(error.getField(), "Некорректный формат почты");
                    else checkAccountDto.put(error.getField(), "Поле должно быть заполнено");
                    break;
                }
                case "password":{
                    if (accountDTO.getPassword()!=null)
                        checkAccountDto.put(error.getField(), "Некорректный формат пароля");
                    else checkAccountDto.put(error.getField(), "Поле должно быть заполнено");
                    break;
                }
                case "login":{
                    if (accountDTO.getLogin()!=null)
                        checkAccountDto.put(error.getField(), "Некорректный формат логина");
                    else checkAccountDto.put(error.getField(), "Поле должно быть заполнено");
                    break;
                }
            }
        });
        return checkAccountDto;
    }

    public Map<String, String> checkNewAccount(BindingResult result, AccountDTO accountDTO) {
        Map<String, String> checkAccountDto = new HashMap<>();
        if (!accountRepository.existsByLoginAndEmail(accountDTO.getLogin(),
                accountDTO.getEmail())){
            checkAccountDto = getCheckAccount(result,accountDTO);
        }
        else{
            if (accountRepository.existsByLogin(accountDTO.getLogin())) checkAccountDto.put("login", "Такой логин уже существует");
            if (accountRepository.existsByEmail(accountDTO.getEmail())) checkAccountDto.put("email", "Такая почта уже зарегистрированная");

        }
        return checkAccountDto;

    }

    public Map<String,String> checkLoginAndEmail(AccountDTO account) {
        Map<String, String> checkAccountDto = new HashMap<>();
        if (accountRepository.existsByLogin(account.getLogin())) checkAccountDto.put("login", "Такой логин уже существует");
        if (accountRepository.existsByEmail(account.getEmail())) checkAccountDto.put("email", "Такая почта уже зарегистрированная");
        return checkAccountDto;

    }

    public void addNewAccount(AccountDTO account, String role) {
        Account newAccount = new Account(account.getSurname(),
                            account.getName(),
                account.getPatronymic(),
                account.getDateBirthday(),
                account.getPhone(),
                "",new Photo());
        if (role.equals(Role.ADMIN)) newAccount.setRoles(Role.ADMIN);
        else if (role.equals(Role.MANAGER)) newAccount.setRoles(Role.MANAGER);
        else newAccount.setRoles(Role.CLIENT);
        newAccount.setLogin(account.getLogin());
        newAccount.setEmail(account.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(account.getPassword());
        newAccount.setPassword(encodedPassword);
        newAccount.setActivationCode(UUID.randomUUID().toString());
        String code = newAccount.getActivationCode();
        newAccount.setStatus(Status.OFFLINE);
        accountRepository.save(newAccount);
        if (!StringUtils.isEmpty(account.getEmail())) {
            String message = String.format(
                    "Здравствуйте, %s %s %s! \n" +
                            "Добро пожаловать в СВХ мой товар. Перейдите по ссылке для подтверждение аккаунта: http://localhost:8081/activate/%s",
                    newAccount.getSurname(), newAccount.getName(), newAccount.getPatronymic(),
                    code
            );

            mailSender.send(account.getEmail(), "Activation code", message);
        }
    }
    public boolean activateUser(String code) {
        Account user = accountRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        user.setActive(true);
        accountRepository.save(user);

        return true;
    }

    public UserDTO changeStatusOnline(Account userDetails) {
        Account account = accountRepository.findByLogin(userDetails.getLogin());
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(account.getLogin());
        userDTO.setSurname(account.getSurname());
        userDTO.setName(account.getName());
        userDTO.setPatronymic(account.getPatronymic());
        userDTO.setRole(account.getRoles().toString());

        List<ChatMessage> chatMessage = messageRepository.findAllBySenderIdAndStatus(account, MessageStatus.DELIVERED);
        Set<ChatRoom> chatRoomId = new HashSet<>();
        for(ChatMessage chatMsg: chatMessage) {
            chatRoomId.add(chatMsg.getChatRoom());
        }
        for (ChatRoom chatRoom : chatRoomId){
            ChatRoom chatRoomDB = chatRoomRepository.findById(chatRoom.getIdChatRoom()).orElse(null);
            if (chatRoomDB!=null) {
                ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
                chatRoomDTO.setIdChatRoom(chatRoomDB.getIdChatRoom());
                chatRoomDTO.setLoginUserSender(chatRoomDB.getSender().getLogin());
                chatRoomDTO.setLoginUserRecipient(chatRoomDB.getRecipient().getLogin());
                List<ChatMessage> chatMessageDB = messageRepository.findByChatRoom(chatRoomDB);
                if (chatMessageDB!=null){
                    for (ChatMessage msg : chatMessageDB) {
                        chatRoomDTO.setMessageDTOList(new MessageDTO(chatRoom.getIdChatRoom(),
                                chatRoomDTO.getLoginUserSender(),
                                msg.getSenderId().getSurname() + " " + msg.getSenderId().getName(),
                                msg.getContent(), msg.getTimestamp(),
                                msg.getStatus().toString()));
                    }
                }
                userDTO.setChatRoomDTOS(chatRoomDTO);
            }
        }
        account.setStatus(Status.ONLINE);
        accountRepository.save(account);
        return userDTO;
    }
    public Account changeStatusOffline(Account userDetails) {
        Account account = accountRepository.findByLogin(userDetails.getLogin());
        account.setStatus(Status.OFFLINE);
        return accountRepository.save(account);
    }

    public List<UserDTO> findConnectedUsers() {
        List<Account> accounts = accountRepository.findByStatus(Status.ONLINE);
        List<UserDTO> userDTOList = new ArrayList<>();
        for (Account account: accounts) {
            UserDTO userDTO = new UserDTO();
            userDTO.setLogin(account.getLogin());
            userDTO.setSurname(account.getSurname());
            userDTO.setName(account.getName());
            userDTO.setPatronymic(account.getPatronymic());
            userDTO.setRole(account.getRoles().toString());

            List<ChatMessage> chatMessage = messageRepository.findAllBySenderIdAndStatus(account, MessageStatus.DELIVERED);
            Set<ChatRoom> chatRoomId = new HashSet<>();
            for (ChatMessage chatMsg : chatMessage) {
                chatRoomId.add(chatMsg.getChatRoom());
            }
            for (ChatRoom chatRoom : chatRoomId) {
                ChatRoom chatRoomDB = chatRoomRepository.findById(chatRoom.getIdChatRoom()).orElse(null);
                if (chatRoomDB != null) {
                    ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
                    chatRoomDTO.setIdChatRoom(chatRoomDB.getIdChatRoom());
                    chatRoomDTO.setLoginUserSender(chatRoomDB.getSender().getLogin());
                    chatRoomDTO.setLoginUserRecipient(chatRoomDB.getRecipient().getLogin());
                    List<ChatMessage> chatMessageDB = messageRepository.findByChatRoom(chatRoomDB);
                    if (chatMessageDB != null) {
                        for (ChatMessage msg : chatMessageDB) {
                            chatRoomDTO.setMessageDTOList(new MessageDTO(chatRoom.getIdChatRoom(),
                                    chatRoomDTO.getLoginUserSender(),
                                    msg.getSenderId().getSurname() + " " + msg.getSenderId().getName(),
                                    msg.getContent(), msg.getTimestamp(),
                                    msg.getStatus().toString()));
                        }
                    }
                    userDTO.setChatRoomDTOS(chatRoomDTO);
                }
            }
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    public List<UserDTO> findOfflineUsers() {
        List<Account> accounts = accountRepository.findByStatus(Status.OFFLINE);
        List<UserDTO> userDTOList = new ArrayList<>();
        for (Account account: accounts) {
            UserDTO userDTO = new UserDTO();
            userDTO.setLogin(account.getLogin());
            userDTO.setSurname(account.getSurname());
            userDTO.setName(account.getName());
            userDTO.setPatronymic(account.getPatronymic());
            userDTO.setRole(account.getRoles().toString());

            List<ChatMessage> chatMessage = messageRepository.findAllBySenderIdAndStatus(account, MessageStatus.DELIVERED);
            Set<ChatRoom> chatRoomId = new HashSet<>();
            for (ChatMessage chatMsg : chatMessage) {
                chatRoomId.add(chatMsg.getChatRoom());
            }
            for (ChatRoom chatRoom : chatRoomId) {
                ChatRoom chatRoomDB = chatRoomRepository.findById(chatRoom.getIdChatRoom()).orElse(null);
                if (chatRoomDB != null) {
                    ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
                    chatRoomDTO.setIdChatRoom(chatRoomDB.getIdChatRoom());
                    chatRoomDTO.setLoginUserSender(chatRoomDB.getSender().getLogin());
                    chatRoomDTO.setLoginUserRecipient(chatRoomDB.getRecipient().getLogin());
                    List<ChatMessage> chatMessageDB = messageRepository.findByChatRoom(chatRoomDB);
                    if (chatMessageDB != null) {
                        for (ChatMessage msg : chatMessageDB) {
                            chatRoomDTO.setMessageDTOList(new MessageDTO(chatRoom.getIdChatRoom(),
                                    chatRoomDTO.getLoginUserSender(),
                                    msg.getSenderId().getSurname() + " " + msg.getSenderId().getName(),
                                    msg.getContent(), msg.getTimestamp(),
                                    msg.getStatus().toString()));
                        }
                    }
                    userDTO.setChatRoomDTOS(chatRoomDTO);
                }
            }
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    public List<Account> findUserWhoNotRead(Long id) {
        Account user = accountRepository.getById(id);
        List<Account> userList = new ArrayList<>();
        List<ChatRoom> listChat = chatRoomRepository.findBySenderOrRecipient(user,user);
        for (ChatRoom chat:listChat){
            List<ChatMessage> message = messageRepository.findByChatRoomAndSenderId(chat,
                    user.equals(chat.getSender())? chat.getRecipient() : chat.getSender());
            if(message.stream().map(ChatMessage::getStatus)
                    .filter(MessageStatus.DELIVERED::equals).count()>0){
                userList.add(accountRepository.getById(user.equals(chat.getSender())? chat.getRecipient().getId() : chat.getSender().getId()));

            }
        }
        return userList;
    }

    public void disconnect(Account user) {
        user.setStatus(Status.OFFLINE);
        accountRepository.save(user);
    }

    public UserDTO getAuthUser(String login) {
        Account account = accountRepository.findByLogin(login);
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(account.getLogin());
        userDTO.setSurname(account.getSurname());
        userDTO.setName(account.getName());
        userDTO.setPatronymic(account.getPatronymic());
        userDTO.setRole(account.getRoles().toString());

        List<ChatMessage> chatMessage = messageRepository.findAllBySenderIdAndStatus(account, MessageStatus.DELIVERED);
        Set<ChatRoom> chatRoomId = new HashSet<>();
        for(ChatMessage chatMsg: chatMessage) {
            chatRoomId.add(chatMsg.getChatRoom());
        }
        for (ChatRoom chatRoom : chatRoomId){
            ChatRoom chatRoomDB = chatRoomRepository.findById(chatRoom.getIdChatRoom()).orElse(null);
            if (chatRoomDB!=null) {
                ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
                chatRoomDTO.setIdChatRoom(chatRoomDB.getIdChatRoom());
                chatRoomDTO.setLoginUserSender(chatRoomDB.getSender().getLogin());
                chatRoomDTO.setLoginUserRecipient(chatRoomDB.getRecipient().getLogin());
                List<ChatMessage> chatMessageDB = messageRepository.findByChatRoom(chatRoomDB);
                if (chatMessageDB!=null){
                    for (ChatMessage msg : chatMessageDB) {
                        chatRoomDTO.setMessageDTOList(new MessageDTO(chatRoom.getIdChatRoom(),
                                chatRoomDTO.getLoginUserSender(),
                                msg.getSenderId().getSurname() + " " + msg.getSenderId().getName(),
                                msg.getContent(), msg.getTimestamp(),
                                msg.getStatus().toString()));
                    }
                }
                userDTO.setChatRoomDTOS(chatRoomDTO);
            }
        }
        account.setStatus(Status.ONLINE);
        accountRepository.save(account);
        return userDTO;
    }
}
