package com.example.application.data.service;

import com.example.application.data.entity.Book;
import com.example.application.data.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class BookService {

    private BookRepository bookRepository;
    private TagRepository tagRepository;

    @Autowired
    public BookService(BookRepository bookRepository, TagRepository tagRepository) {
        this.bookRepository = bookRepository;
        this.tagRepository = tagRepository;
    }

    public Optional<Book> get(Integer id) {
        return bookRepository.findById(id);
    }

    public Book update(Book entity) {
        return bookRepository.save(entity);
    }

    public void delete(Integer id) {
        bookRepository.deleteById(id);
    }

    public Page<Book> list(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public int count() {
        return (int) bookRepository.count();
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public Book addNewTag(Book book, Tag tag){
        Set<Tag> tagSet = book.getTags();
        tagSet.add(tag);
        book.setTags(tagSet);
        Set<Book> bookSet = tag.getBooks();
        bookSet.add(book);
        tag.setBooks(bookSet);
        tagRepository.save(tag);
        return bookRepository.save(book);
    }
}
