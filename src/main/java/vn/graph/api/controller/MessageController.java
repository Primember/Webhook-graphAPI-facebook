package vn.graph.api.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class MessageController {

    @GetMapping("/messages")
    public ResponseEntity<String> getPageMessages(@PathVariable String id_page
                                                  ) {
    	String accessToken = "EAANUlY20Aj4BAJPeZA9mRGkwqmRZAGRwSGtWA4pZBs86Hmeowybx5i6bENZAULrhd8u8fVznQDEh9vGp3Y441hIurNkx5tFZBedBQe0tVEsvi9U3qCPnCidR4stgzvUk4JiknTgZAgs1BtEYw1tQbi1qTMFzacPhhZCNc26BaGctDC4jZBFIbkRuX5xKPSD9Kc65xScjrHZBR35LEznVsb9w3";
        String url = "https://graph.facebook.com/v16.0/" + id_page +
                     "/conversations?fields=senders,messages{message}&ssaccess_token=" + accessToken;
        try {
	        RestTemplate restTemplate = new RestTemplate();
	        String response = restTemplate.getForObject(url, String.class);
	        JSONObject jsonObject = new JSONObject(response);
	        JSONArray jsonArray = jsonObject.getJSONArray("data");
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < jsonArray.length(); i++) {
	            JSONObject conversation = jsonArray.getJSONObject(i);
	            JSONArray messages = conversation.getJSONObject("messages").getJSONArray("data");
	            for (int j = 0; j < messages.length(); j++) {
	                JSONObject message = messages.getJSONObject(j);
	                String text = message.getString("message");
	                sb.append(text + "\n");
	            }
	        }
	        String messages = sb.toString();
	        return new ResponseEntity<>(messages, HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<String>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
    }

	  
}
