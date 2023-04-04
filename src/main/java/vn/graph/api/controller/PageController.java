package vn.graph.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
@RestController
@RequestMapping("/api")
public class PageController {

    @GetMapping("/pages/{id}")
    public ResponseEntity<String> getPage(@PathVariable String id,
                                           @RequestParam String accessToken) {
        String url = "https://graph.facebook.com/v16.0/" + id +
                     "?fields=id,name&access_token=" + accessToken;
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);
            JSONObject jsonObject = new JSONObject(response);
            String pageId = jsonObject.getString("id");
            String pageName = jsonObject.getString("name");
            String result = "Page ID: " + pageId + ", Page Name: " + pageName;
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
