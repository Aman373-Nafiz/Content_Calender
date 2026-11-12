package dev.aman.content_calender;

import dev.aman.content_calender.model.Content;
import dev.aman.content_calender.model.Status;
import dev.aman.content_calender.model.Type;
import dev.aman.content_calender.repository.ContentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  CommandLineRunner commandLineRunner(ContentRepository repository) {
    return args -> {
      // insert some data into the database
      Content content = new Content(null,
        "Hello Chat GPT",
        "All about Chat GPT",
        Status.IDEA,
        Type.VIDEO,
        LocalDateTime.now(),
        null,
        "");

      repository.save(content);
    };
  }
}