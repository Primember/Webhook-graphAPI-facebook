package vn.graph.api.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.graph.api.service.WebhookService;

@RestController
public class WebhookController {

    @Value("${facebook.page-access-token}")
    private String PAGE_ACCESS_TOKEN;

    @Value("${verify.token}")
    private String VERIFY_TOKEN;
    @Autowired
    private WebhookService webhookService;

    @GetMapping("/webhook")
    public ResponseEntity<String> getWebhook(@RequestParam("hub.mode") String mode,
                                             @RequestParam("hub.verify_token") String token,
                                             @RequestParam("hub.challenge") String challenge) {

        if (mode != null && token != null) {
            if (mode.equals("subscribe") && token.equals(VERIFY_TOKEN)) {                System.out.println("WEBHOOK_VERIFIED");
                return ResponseEntity.status(HttpStatus.OK).body(challenge);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> postWebhook(@RequestBody String payload) {
        try {
            webhookService.processMessage(PAGE_ACCESS_TOKEN, payload);
            return ResponseEntity.status(HttpStatus.OK).body("EVENT_RECEIVED");
        } catch (Exception e) {
            System.out.println("Error processing message: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}