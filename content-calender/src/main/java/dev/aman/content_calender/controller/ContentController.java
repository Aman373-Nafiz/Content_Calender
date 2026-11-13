package dev.aman.content_calender.controller;

import dev.aman.content_calender.model.Content;
import dev.aman.content_calender.model.Status;
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

  @GetMapping
  public List<Content> findAll() {
    return repository.findAll();
  }

  @GetMapping("/{id}")
  public Content findById(@PathVariable Integer id) {
    return repository.findById(id).orElseThrow(
      () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found."));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public void create(@Valid @RequestBody Content c) {
    // save() = INSERT when id is null, UPDATE when id is set.
    repository.save(new Content(null, c.title(), c.description(), c.status(),
      c.contentType(), LocalDateTime.now(), null, c.url()));
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PutMapping("/{id}")
  public void update(@Valid @RequestBody Content c, @PathVariable Integer id) {
    Content existing = repository.findById(id).orElseThrow(
      () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found."));
    // keep dateCreated, set dateUpdated to now
    repository.save(new Content(id, c.title(), c.description(), c.status(),
      c.contentType(), c.updatedAt(), LocalDateTime.now(), c.url()));
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Integer id) {
    repository.deleteById(id);
  }

  @GetMapping("/filter/{keyword}")
  public List<Content> findByTitle(@PathVariable String keyword) {
    return repository.findAllByTitleContains(keyword);
  }

  @GetMapping("/filter/status/{status}")
  public List<Content> listByStatus(@PathVariable String status) {
    Status s;
    try {
      s = Status.valueOf(status.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown status: " + status);
    }
    return repository.listByStatus(s);
  }
}