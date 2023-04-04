package vn.graph.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
@Configuration
public class FacebookConfig {
    @Value("${facebook.app-id}")
    private String appId;

    @Value("${facebook.app-secret}")
    private String appSecret;

    @Value("${facebook.page-access-token}")
    private String pageToken;

    @Bean
    public FacebookClient facebookClient() {
        return new DefaultFacebookClient(pageToken, appSecret, Version.LATEST);
    }

    @Bean
    public Facebook facebook() {
        return new FacebookTemplate(appId + "|" + appSecret);
    }


}
