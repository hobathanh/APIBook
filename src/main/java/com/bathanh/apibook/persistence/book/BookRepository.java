package com.bathanh.apibook.persistence.book;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, UUID> {

    @Query("SELECT b FROM BookEntity b WHERE b.title LIKE %:keyword% OR b.author LIKE %:keyword% OR b.description LIKE %:keyword%")
    List<BookEntity> find(final String keyword);
}