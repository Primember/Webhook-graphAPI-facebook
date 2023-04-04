package vn.graph.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.graph.api.model.MessageVO;
import vn.graph.api.service.SendMessageService;

@RestController
@RequestMapping("/facebook")
public class SendMessageController {

    private final SendMessageService sendMessageService;

    public SendMessageController(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @PostMapping("/messages")
    public void sendMessage(@RequestBody MessageVO messageVO) {
        sendMessageService.sendMessage(messageVO.getRecipientId(), messageVO.getMessage());
    }
}
