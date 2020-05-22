package com.majida.mbook.repository;

import com.majida.mbook.entity.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @Query(value = "SELECT * FROM Book b WHERE b.author LIKE CONCAT('%',:author,'%')", nativeQuery = true)
    List<Book> findByAuthorLike(@Param("author") String author);

    @Query(value = "SELECT * FROM Book b WHERE b.title LIKE CONCAT('%',:keyword,'%') OR b.author LIKE CONCAT('%',:keyword,'%') OR b.description LIKE CONCAT('%',:keyword,'%')", nativeQuery = true)
    List<Book> findByKeywordLike(@Param("keyword") String keyword);
}