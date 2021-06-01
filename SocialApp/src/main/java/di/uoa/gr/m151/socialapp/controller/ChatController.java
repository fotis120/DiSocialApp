package di.uoa.gr.m151.socialapp.controller;

import di.uoa.gr.m151.socialapp.DTO.MessageDTO;
import di.uoa.gr.m151.socialapp.DTO.UserDTO;
import di.uoa.gr.m151.socialapp.entity.Page;
import di.uoa.gr.m151.socialapp.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chat/")
public class ChatController {

    @Autowired
    ChatService chatService;


    @PostMapping("/send")
    @PreAuthorize("#message.sender == authentication.name")
    public Boolean sendMessage(@RequestBody MessageDTO message) {
        return chatService.saveAndSendMessage(message);
    }


    @GetMapping("/chatHistory")
    @PreAuthorize("#user == authentication.name")
    public List<MessageDTO> retrieveChat(@RequestParam String user, @RequestParam String friend, @RequestParam(required = false) Integer page) {
        if (page == null ) {
            page = 0;
        }
        return chatService.retrieveChatHistory(user, friend, page);
    }

    @GetMapping("/updateChatHistory")
    @PreAuthorize("#user == authentication.name")
    public List<MessageDTO> updateChat(@RequestParam(required = false) String date, @RequestParam String user, @RequestParam String friend) {
        System.out.println(date);
        SecurityContextHolder.getContext().getAuthentication();
        return chatService.updateChatHistory(user, friend, date);
    }

    @GetMapping("/users")
    public List<UserDTO> retrieveUsers(@RequestParam(required = false) Integer page) {
        page = (page == null ? 0 : page);
        return chatService.findAllUsers(page);
    }



}
