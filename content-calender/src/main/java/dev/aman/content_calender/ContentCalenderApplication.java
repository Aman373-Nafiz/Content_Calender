package dev.aman.content_calender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ContentCalenderApplication {
  //Main method to run the Spring Boot application
  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(ContentCalenderApplication.class, args);
//    // Method calls this static run method to launch the application
//    // Calls the run method of SpringApplication class, passing the
//    // ContentCalenderApplication class and command-line arguments
//    ConfigurableApplicationContext context = SpringApplication.run(ContentCalenderApplication.class, args);
//    //context.getBeanDefinitionNames();// Retrieves the names of all beans defined in the application context
//    for(String name: context.getBeanDefinitionNames()) {
//      System.out.println("name: " + name+ " -> " + context.getBean(name).getClass().getName());
//    }
    System.out.println("Rest Template Bean: " + context.getBean("restTemplate"));
  }

}
