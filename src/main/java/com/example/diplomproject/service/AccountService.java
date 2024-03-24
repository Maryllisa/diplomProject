package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.AccountDTO;
import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.Photo;
import com.example.diplomproject.model.entity.Role;
import com.example.diplomproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountService {

    private final UserRepository accountRepository;
    private final MailSender mailSender;
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

        accountRepository.save(newAccount);
        if (!StringUtils.isEmpty(account.getEmail())) {
            String message = String.format(
                    "Здравствуйте, %s %s %s! \n" +
                            "Добро пожаловать в СВХ мой товар. Перейдите по ссылке для подтверждение аккаунта: http://localhost:8080/activate/%s",
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
}
