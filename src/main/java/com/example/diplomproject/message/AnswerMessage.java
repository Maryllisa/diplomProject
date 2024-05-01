package com.example.diplomproject.message;

import java.util.HashMap;
import java.util.Map;

public class AnswerMessage {
    public static Map<String, String> getOKMessage(String msg){
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", msg);
        return response;
    }
    public static Map<String, String> getBadMessage(String msg){
        Map<String, String> response = new HashMap<>();
        response.put("status", "bad");
        response.put("message", msg);
        return response;
    }
}
