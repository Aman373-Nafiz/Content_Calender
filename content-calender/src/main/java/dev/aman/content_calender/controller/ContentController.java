package dev.aman.content_calender.controller;

import dev.aman.content_calender.model.Content;
import dev.aman.content_calender.repository.ContentCollectionRepository;
import dev.aman.content_calender.repository.ContentRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/content")
@CrossOrigin
public class ContentController {
  private final ContentRepository repository;

  public ContentController(ContentRepository repository) {
    this.repository = repository;
  }

  @GetMapping("")
  List<Content> findAll() {
    return repository.findAll();
  }

  @GetMapping("/{id}")
  public Content findById(@PathVariable Integer id) {
    return repository.findById(id).orElseThrow(
      () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found."));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public void create(@Valid @RequestBody Content c) { repository.save(c);}

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PutMapping("/{id}")
  public void update(@PathVariable Integer id, @RequestBody Content c) {
    Content existing = repository.findById(id).orElseThrow(
      () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found."));
    repository.save(new Content(id, c.title(), c.description(), c.status(),
      c.contentType(), existing.updatedAt(), LocalDateTime.now(), c.url()));
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Integer id){
    repository.deleteById(id);
  }

  @GetMapping("/filter/{keyword}")
  public List<Content>findByTitle(@PathVariable String Keyword){
    return repository.findAllByTitleContains(Keyword);
  }

}
