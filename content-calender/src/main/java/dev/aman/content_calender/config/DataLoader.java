package dev.aman.content_calender.config;

import dev.aman.content_calender.model.Content;
import dev.aman.content_calender.repository.ContentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.io.InputStream;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

  private final ContentRepository repository;
  private final JsonMapper jsonMapper;

  public DataLoader(ContentRepository repository, JsonMapper jsonMapper) {
    this.repository = repository;
    this.jsonMapper = jsonMapper;
  }

  @Override
  public void run(String... args) throws Exception {
    if (repository.count() == 0) {
      try (InputStream in = new ClassPathResource("data/content.json").getInputStream()) {
        List<Content> items = jsonMapper.readValue(in, new TypeReference<List<Content>>() {});
        repository.saveAll(items);
        log.info("DataLoader: inserted {} content items from content.json", items.size());
      }
    }
  }
}