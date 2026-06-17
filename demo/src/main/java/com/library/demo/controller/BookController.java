package com.library.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.library.demo.service.BookService;
import com.library.demo.service.S3Service;
import com.library.demo.entity.Book;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private S3Service s3Service;

    @PostMapping
    public Book uploadBook(@RequestParam String title,
                           @RequestParam String author,
                           @RequestParam int totalPages,
                           @RequestParam(required = false) MultipartFile coverImage,
                           @RequestParam(required = false) MultipartFile pdfFile) throws IOException {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setTotalPages(totalPages);

        if (coverImage != null && !coverImage.isEmpty()) {
            String coverImageUrl = s3Service.uploadFile(coverImage);
            book.setCoverImageUrl(coverImageUrl);
        }
        if (pdfFile != null && !pdfFile.isEmpty()) {
            String pdfFileUrl = s3Service.uploadFile(pdfFile);
            book.setPdfFileUrl(pdfFileUrl);
        }

        return bookService.save(book);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }
}
