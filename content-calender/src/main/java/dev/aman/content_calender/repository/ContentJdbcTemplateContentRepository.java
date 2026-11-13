package dev.aman.content_calender.repository;

import dev.aman.content_calender.model.Content;
import dev.aman.content_calender.model.Status;
import dev.aman.content_calender.model.Type;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ContentJdbcTemplateContentRepository {

  private final JdbcTemplate jdbcTemplate;

  public ContentJdbcTemplateContentRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  // Argument order must match the record:
  // id, title, desc, status, contentType, dateCreated, dateUpdated, url
  private static Content mapRow(ResultSet rs, int rowNum) throws SQLException {
    Timestamp updated = rs.getTimestamp("date_updated");   // can be NULL
    return new Content(
      rs.getInt("id"),
      rs.getString("title"),
      rs.getString("description"),
      Status.valueOf(rs.getString("status")),
      Type.valueOf(rs.getString("content_type")),
      rs.getTimestamp("date_created").toLocalDateTime(),
      updated == null ? null : updated.toLocalDateTime(),
      rs.getString("url")
    );
  }

  public List<Content> getAllContent() {
    return jdbcTemplate.query("SELECT * FROM Content",
      ContentJdbcTemplateContentRepository::mapRow);
  }

  public Optional<Content> getContentById(Integer id) {
    return jdbcTemplate.query("SELECT * FROM Content WHERE id = ?",
        ContentJdbcTemplateContentRepository::mapRow, id)
      .stream().findFirst();
  }

  public void createContent(Content content) {
    String sql = """
      INSERT INTO Content (title, description, status, content_type, date_created, url)
      VALUES (?, ?, ?, ?, ?, ?)
      """;
    jdbcTemplate.update(sql,
      content.title(), content.description(),
      content.status().name(), content.contentType().name(),
      LocalDateTime.now(), content.url());
  }

  public void updateContent(Content content, Integer id) {
    String sql = """
      UPDATE Content
      SET title = ?, description = ?, status = ?, content_type = ?, date_updated = ?, url = ?
      WHERE id = ?
      """;
    jdbcTemplate.update(sql,
      content.title(), content.description(),
      content.status().name(), content.contentType().name(),
      LocalDateTime.now(), content.url(), id);
  }

  public void deleteContent(Integer id) {
    jdbcTemplate.update("DELETE FROM Content WHERE id = ?", id);
  }

  public boolean existsById(Integer id) {
    Integer count = jdbcTemplate.queryForObject(
      "SELECT COUNT(*) FROM Content WHERE id = ?", Integer.class, id);
    return count != null && count > 0;
  }

  public List<Content> findByStatus(Status status) {
    return jdbcTemplate.query("SELECT * FROM Content WHERE status = ?",
      ContentJdbcTemplateContentRepository::mapRow, status.name());
  }

  public List<Content> findByTitleContains(String keyword) {
    return jdbcTemplate.query("SELECT * FROM Content WHERE LOWER(title) LIKE ?",
      ContentJdbcTemplateContentRepository::mapRow, "%" + keyword.toLowerCase() + "%");
  }
}