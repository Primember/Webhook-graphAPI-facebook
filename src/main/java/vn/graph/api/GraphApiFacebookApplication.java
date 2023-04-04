package vn.graph.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import vn.graph.api.config.FacebookClient;
//import vn.graph.api.config.JsonObject;

@SpringBootApplication
public class GraphApiFacebookApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphApiFacebookApplication.class, args);
	}

}
