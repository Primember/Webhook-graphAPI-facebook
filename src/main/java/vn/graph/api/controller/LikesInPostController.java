package vn.graph.api.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class LikesInPostController {
	@Value("${graph.api.base.url}")
	private String GRAPH_URL;

    @GetMapping("/posts/{id}/likes")
    public ResponseEntity<Integer> getLikesInPost(@PathVariable String id,
                                                  @RequestParam String accessToken) {
        String url = GRAPH_URL + id +
                     "/likes?summary=true&access_token=" + accessToken;
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);
            JSONObject jsonObject = new JSONObject(response);
            int likes = jsonObject.getJSONObject("summary").getInt("total_count");
            return new ResponseEntity<>(likes, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
