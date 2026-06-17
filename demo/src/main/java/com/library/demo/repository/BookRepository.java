package com.library.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.library.demo.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
