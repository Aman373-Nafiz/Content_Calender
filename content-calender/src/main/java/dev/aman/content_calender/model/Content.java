package dev.aman.content_calender.model;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record Content(Integer id,
                      @NotBlank
                      String title,
                      @NotBlank
                      String description,
                      Status status,
                      Type contentType,
                      LocalDateTime updatedAt,
                      LocalDateTime createdAt,
                      String url
                      ) {}
