package com.bathanh.apibook.persistence.book;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, UUID> {

    @Query("SELECT b FROM BookEntity b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')) AND LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))")
    Optional<BookEntity> findByTitleAndAuthor(final String title, final String author);

    @Query("SELECT b FROM BookEntity b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword,'%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword,'%')) OR LOWER(b.description) LIKE LOWER(CONCAT('%', :keyword,'%'))")
    List<BookEntity> find(final String keyword);
}