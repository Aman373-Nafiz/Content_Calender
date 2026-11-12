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

  // Converts ONE row of the ResultSet into ONE Content object.
  // JdbcTemplate calls this method once for every row returned by the query.
  // The argument order must match your Content record:
  // id, title, description, status, contentType, updatedAt, createdAt, url
  private static Content mapRow(ResultSet rs, int rowNum) throws SQLException {
    Timestamp updated = rs.getTimestamp("date_updated"); // can be NULL in the database

    return new Content(
      rs.getInt("id"),
      rs.getString("title"),
      rs.getString("desc"),                                // DB column name from schema.sql
      Status.valueOf(rs.getString("status")),
      Type.valueOf(rs.getString("content_type")),
      updated == null ? null : updated.toLocalDateTime(),  // updatedAt
      rs.getTimestamp("date_created").toLocalDateTime(),   // createdAt
      rs.getString("url")
    );
  }

  // READ: all rows
  public List<Content> getAllContent() {
    String sql = "SELECT * FROM Content";
    return jdbcTemplate.query(sql, ContentJdbcTemplateContentRepository::mapRow);
  }

  // READ: one row by id (empty Optional if not found)
  public Optional<Content> getContentById(Integer id) {
    String sql = "SELECT * FROM Content WHERE id = ?";
    return jdbcTemplate.query(sql, ContentJdbcTemplateContentRepository::mapRow, id)
      .stream()
      .findFirst();
  }

  // CREATE
  public void createContent(Content content) {
    String sql = """
                INSERT INTO Content (title, desc, status, content_type, date_created, url)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
    jdbcTemplate.update(sql,
      content.title(),
      content.description(),
      content.status().name(),
      content.contentType().name(),
      LocalDateTime.now(),
      content.url());
  }

  // UPDATE
  public void updateContent(Content content, Integer id) {
    String sql = """
                UPDATE Content
                SET title = ?, desc = ?, status = ?, content_type = ?, date_updated = ?, url = ?
                WHERE id = ?
                """;
    jdbcTemplate.update(sql,
      content.title(),
      content.description(),
      content.status().name(),
      content.contentType().name(),
      LocalDateTime.now(),
      content.url(),
      id);
  }

  // DELETE
  public void deleteContent(Integer id) {
    String sql = "DELETE FROM Content WHERE id = ?";
    jdbcTemplate.update(sql, id);
  }

  // CHECK: does a row with this id exist?
  public boolean existsById(Integer id) {
    String sql = "SELECT COUNT(*) FROM Content WHERE id = ?";
    Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
    return count != null && count > 0;
  }

  // FILTER: by status
  public List<Content> findByStatus(Status status) {
    String sql = "SELECT * FROM Content WHERE status = ?";
    return jdbcTemplate.query(sql, ContentJdbcTemplateContentRepository::mapRow, status.name());
  }

  // SEARCH: title contains keyword (case-insensitive)
  public List<Content> findByTitleContains(String keyword) {
    String sql = "SELECT * FROM Content WHERE LOWER(title) LIKE ?";
    return jdbcTemplate.query(sql, ContentJdbcTemplateContentRepository::mapRow,
      "%" + keyword.toLowerCase() + "%");
  }
}