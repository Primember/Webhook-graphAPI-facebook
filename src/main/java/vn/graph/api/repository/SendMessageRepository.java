package vn.graph.api.repository;

import org.springframework.stereotype.Repository;

import vn.graph.api.config.FacebookProperties;

@Repository
public class SendMessageRepository {

    private final FacebookProperties properties;

    public SendMessageRepository(FacebookProperties properties) {
        this.properties = properties;
    }

    public void saveAccessToken(String accessToken) {
        properties.setPageAccessToken(accessToken);
    }
}
