package dev.aman.content_calender.repository;

import dev.aman.content_calender.model.Content;
import dev.aman.content_calender.model.Status;
import dev.aman.content_calender.model.Type;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ContentCollectionRepository {
  private final ArrayList<Content> content = new ArrayList<>();

  public ContentCollectionRepository() {
  }

  public List<Content> findAll() {
    return content;
  }

  public Content findById(Integer id) {
    return content.stream().
      filter(c -> c.id().equals(id)).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content Not Found"));
  }
public boolean existsById(Integer id) {
    return content.stream().anyMatch(c -> c.id().equals(id));
  }
  public void save(Content c) {
    content.add(c);
  }

  public void deleteById(Integer id) {
    if(existsById(id)) {  content.removeIf(c -> c.id().equals(id));}
     else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content Not Found");
    }}

  public void update(Integer id, Content c){
    if(existsById(id)){
      content.removeIf(x -> x.id().equals(id));
      content.add(c);
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id in path and request body do not match");
    }
  }

  @PostConstruct
  //annotation that tells Spring to run a method after a bean is constructed and its dependencies
  // are injected. It is used for initialization or setup tasks. Initialization logic that needs
  // to be executed after the bean is fully constructed and ready for use.
  public void init() {
    content.add(new Content(
      1,
      "My First Blog Post",
      "My first blog post",
      Status.IDEA,
      Type.ARTICLE,
      LocalDateTime.now(),
      null,
      ""
    ));
  }
}
