package com.bathanh.apibook.persistence.book;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, UUID> {

    @Query(value = "SELECT * " +
            "FROM books " +
            "WHERE title ILIKE CONCAT('%', :title, '%') " +
            "   AND author ILIKE CONCAT('%', :author, '%')", nativeQuery = true)
    Optional<BookEntity> findByTitleAndAuthor(final String title, final String author);

    @Query(value = "SELECT * " +
            "FROM books " +
            "WHERE title ILIKE CONCAT('%', :keyword,'%') " +
            "   OR author ILIKE CONCAT('%', :keyword,'%') " +
            "   OR description ILIKE CONCAT('%', :keyword,'%')", nativeQuery = true)
    List<BookEntity> find(final String keyword);
}