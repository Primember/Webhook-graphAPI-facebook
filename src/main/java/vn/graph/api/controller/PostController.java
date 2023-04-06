package vn.graph.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
@RestController
@RequestMapping("/api")
public class PostController {
	@Value("${graph.api.base.url}")
	private String GRAPH_URL;

    @GetMapping("/{id_page}/posts")

    public ResponseEntity<String> getPagePosts(@PathVariable("id_page") String id_page,
                                               @RequestParam("accessToken") String accessToken) {
        String url = GRAPH_URL + id_page +
                     "/posts?access_token=" + accessToken;
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject post = jsonArray.getJSONObject(i);
                String id = post.getString("id");
                sb.append("ID: " + id + "\n");
                String created_time = post.getString("created_time");
                String message;
                if (post.has("message")) {
                    message = post.getString("message");
                    sb.append("message: " + message + "\n");
                } else {
                    message = post.getString("story");
                    sb.append("story: " + message + "\n");
                }
                
                sb.append("created_time: " + created_time + "\n");
                sb.append("\n");
            }
            String posts = sb.toString();
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
