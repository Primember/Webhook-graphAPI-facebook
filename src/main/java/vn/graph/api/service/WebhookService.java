package vn.graph.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vn.graph.api.model.Entry;
import vn.graph.api.model.Event;
import vn.graph.api.model.Messaging;

@Service
public class WebhookService {

	@Value("${facebook.page-id}")
	private String PAGE_ID;
    @Value("${facebook.page-access-token}")
    private String PAGE_ACCESS_TOKEN;

    @Value("${verify.token}")
    private String VERIFY_TOKEN;
    @Value("${graph.api.base.url}")
    private String GRAPH_API;
    @Autowired
    private SendMessageService sendMessageService;

    public void handleWebhookEvent(Event event) {
        if (event == null) {
            return;
        }

        if (event.getObject().equals("page")) {
            for (Entry entry : event.getEntryList()) {
                String pageId = entry.getId();
                System.out.println("" + pageId);
                if (entry.getMessagingList() != null) {
                    for (Messaging messaging : entry.getMessagingList()) {
                        if (messaging.getMessage() != null && messaging.getMessage().getText() != null) {
                            String senderId = messaging.getSender().getId();
                            String messageText = messaging.getMessage().getText();
                            sendMessageService.sendMessageAsPage(senderId, messageText);                        }
                    }
                }
            }
        }
    }

    public ResponseEntity<String> verifyWebhookToken(String mode, String token, String challenge) {
        if (mode != null && token != null && mode.equals("subscribe") && token.equals(VERIFY_TOKEN)) {
            return ResponseEntity.ok(challenge);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    public void processMessage(String accessToken, String payload) {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format("%s/%s/messages?access_token=%s", GRAPH_API,PAGE_ID,accessToken);

        ResponseEntity<String> response = restTemplate.postForEntity(url, payload, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Message sent successfully.");
        } else {
            System.out.println("Failed to send message: " + response.getBody());
        }
    }
    public void subscribeAppToPage() {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format("%s/%s/subscribed_apps?access_token=%s",  GRAPH_API,PAGE_ID, PAGE_ACCESS_TOKEN);

        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Subscribed app to page successfully.");
        } else {
            System.out.println("Failed to subscribe app to page: " + response.getBody());
        }
    }
}