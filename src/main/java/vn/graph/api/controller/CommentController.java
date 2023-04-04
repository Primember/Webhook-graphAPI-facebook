package vn.graph.api.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CommentController {

    @GetMapping("/api/{id_page}/comments")
    public ResponseEntity<String> getComments(@PathVariable("id_page") String id_page,
                                               @RequestParam("accessToken") String accessToken) {
        String url = "https://graph.facebook.com/v16.0/" + id_page +
                     "/posts?fields=message,from,comments&access_token=" + accessToken;
//        String url = String.format("https://graph.facebook.com/v16.0/%s?fields=%s&access_token=%s", id_page, "posts{comments,from}", accessToken);
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject post = jsonArray.getJSONObject(i);
                String postId = post.getString("id");
                JSONObject from = post.getJSONObject("from");
                sb.append("Post id: " + postId + "\n");
                sb.append("From: " + from.getString("name") + " (ID: " + from.getString("id") + ")\n");
                if (post.has("comments")) {
                    JSONArray comments = post.getJSONObject("comments").getJSONArray("data");
                    for (int j = 0; j < comments.length(); j++) {
                        JSONObject comment = comments.getJSONObject(j);
                        sb.append("Comment id: " + comment.getString("id") + "\n");
                        sb.append("Comment Message: " + comment.getString("message") + "\n");
//                        sb.append("From: " + comment.getJSONObject("from").getString("name") +
//                                  " (ID: " + comment.getJSONObject("from").getString("id") + ")\n");
                        sb.append("Created time: " + comment.getString("created_time") + "\n");
                        sb.append("\n");
                    }
                }
                sb.append("\n");
            }
            String comments = sb.toString();
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("An error occurred: " + e.getMessage(),
                                              HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}