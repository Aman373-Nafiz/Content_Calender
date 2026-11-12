package dev.aman.content_calender.controller;

import dev.aman.content_calender.model.Content;
import dev.aman.content_calender.repository.ContentCollectionRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/content")
@CrossOrigin
public class ContentController {
  private final ContentCollectionRepository contentCollectionRepository;

  public ContentController(ContentCollectionRepository contentCollectionRepository) {
    this.contentCollectionRepository = contentCollectionRepository;
  }

  @GetMapping("")
  List<Content> findAll() {
    return contentCollectionRepository.findAll();
  }

  @GetMapping("/{id}")
  public Content findById(@PathVariable Integer id) {
    return contentCollectionRepository.findById(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public void create(@Valid @RequestBody Content c) { contentCollectionRepository.save(c);}

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PutMapping("/{id}")
  public void update(@PathVariable Integer id, @RequestBody Content c) {
   contentCollectionRepository.update(id,c);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Integer id){
   contentCollectionRepository.deleteById(id);
  }

  @GetMapping("/filter/{keyword}")
  public List<content>findByTitle(@PathVariable String Keyword){
    return contentCollectionRepository.findAllByTitleContains(keyword);
  }

}
