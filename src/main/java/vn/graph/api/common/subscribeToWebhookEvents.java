//package vn.graph.api.common;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.restfb.FacebookClient;
//import com.restfb.Parameter;
//import com.restfb.json.JsonObject;
//
//public class subscribeToWebhookEvents {
//
//	@Autowired
//	private FacebookClient facebookClient;
//
//	public  subscribeToWebhookEvents() {
//	    String callbackUrl = "https://your-webhook-callback-url.com/webhook";
//	    String verifyToken = "your_verification_token_here";
//
//	    JsonObject result = facebookClient.publish(
//	        "me/subscribed_apps",
//	        JsonObject.class,
//	        Parameter.with("subscribed_fields", "messages,conversations"),
//	        Parameter.with("callback_url", callbackUrl),
//	        Parameter.with("verify_token", verifyToken)
//	    );
//	}
//}
