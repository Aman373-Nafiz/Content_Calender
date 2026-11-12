package dev.aman.content_calender.repository;

import dev.aman.content_calender.model.Content;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;


public interface ContentRepository extends ListCrudRepository<Content, Integer> {
  List<Content> findAllByTitleContains(String title);
}
