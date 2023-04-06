package vn.graph.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.graph.api.config.FacebookProperties;

@Service
public class SendMessageService {
	@Value("${facebook.page-id}")
    private String pageId;

    @Value("${facebook.page-access-token}")
    private String pageAccessToken;

    @Value("${graph.api.base.url}")
    private String graphApiUrl;
    private final RestTemplate restTemplate;
    private final FacebookProperties properties;

    public SendMessageService(RestTemplate restTemplate, FacebookProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    public void sendMessage(String recipientId, String message) {
        String url = String.format("%s/%s/messages?access_token=%s", graphApiUrl, properties.getPageId(), properties.getPageAccessToken());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String json = String.format("{\"recipient\":{\"id\":\"%s\"},\"messaging_type\":\"RESPONSE\",\"message\":{\"text\":\"%s\"}}", recipientId, message);
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        restTemplate.exchange(url, HttpMethod.POST, request, String.class);
    }
    public void sendMessageAsPage(String recipientId, String message) {
        String url = String.format("%s/%s/messages?access_token=%s", graphApiUrl, pageId, pageAccessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String json = String.format(
        	    "{\"recipient\":{\"id\":\"%s\"},\"message\":{\"text\":\"%s\"}}",
        	    recipientId, message
        	);        HttpEntity<String> request = new HttpEntity<>(json, headers);

        restTemplate.exchange(url, HttpMethod.POST, request, String.class);
    }
}

