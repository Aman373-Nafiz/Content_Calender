package dev.aman.content_calender;

import org.springframework.stereotype.Component;
// @Component is a Spring annotation that marks a class as eligible for automatic
// detection and registration as a Spring-managed bean through component scanning.
// Spring can then create and manage an instance of that class and inject it
// into other beans when needed. By default, the bean name is the class name
// with its first letter in lowercase. Use it when you want Spring to manage
// an ordinary class without manually creating its object using `new`.
// @Component
// public class Message {
//    public static String getMessage() {
//    return "Hello, World!";
//  }
// }
//
// What happens?
// 1. @Component makes UserService eligible for component scanning.
// 2. Spring registers and manages its object as a bean.
// 3. By default, the bean name is "userService".
// 4. Another class can receive this bean through Dependency Injection,
//    without manually creating it using `new Message()`.
@Component
public class Message {
  public static String getMessage() {
    return "Hello, World!";
  }
}
