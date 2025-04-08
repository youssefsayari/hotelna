package tn.esprit.innoxpert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@EnableDiscoveryClient
@SpringBootApplication


public class ReclamationMain {

	public static void main(String[] args) {
		SpringApplication.run(ReclamationMain.class, args);
	}

}
