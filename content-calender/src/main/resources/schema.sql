CREATE TABLE IF NOT EXISTS Content (
    id INTEGER AUTO_INCREMENT,
    title varchar(255) NOT NULL,
    desc text,
    status VARCHAR(20) NOT NULL,
    content_type VARCHAR(50) NOT NULL,
    date_created TIMESTAMP NOT NULL,
    date_updated TIMESTAMP,
    url VARCHAR(255),
    primary key (id)
    );

INSERT INTO Content(title, desc, status, content_type, date_created, date_updated, url) VALUES
('Sample Content 1', 'This is a sample content description.', 'draft', 'blog', NOW(), NULL, 'http://example.com/sample-content-1'),
('Sample Content 2', 'This is another sample content description.', 'published', 'video', NOW(), NOW(), 'http://example.com/sample-content-2');