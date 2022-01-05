package com.example.application.data.service;


import com.example.application.data.entity.Book;
import com.example.application.data.entity.Tag;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    BookRepository bookRepository;
    @Mock
    TagRepository tagRepository;

    BookService bookService;

    @Before
    public void setUp() {
        this.bookService = new BookService(bookRepository, tagRepository);
    }

    @Test
    public void get() {
        //given
        Book newBook = new Book();
        newBook.setId(1);
        newBook.setAuthor("Zuzanna Glinka");
        newBook.setTitle("W swiecie");
        when(bookRepository.findById(1)).thenReturn(Optional.of(newBook));
        //when
        Book testedBook = bookService.get(1).orElseThrow();
        //then
        Assertions.assertEquals("Zuzanna Glinka", testedBook.getAuthor());
    }

    @Test
    public void addNewTag() {
        //given
        Tag tag = new Tag();
        tag.setName("Romance");
        Book newBook = new Book();
        newBook.setId(1);
        newBook.setAuthor("Zuzanna Glinka");
        newBook.setTitle("W swiecie");

        Tag tag2 = tag;
        Book book2 = newBook;
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);
        book2.setTags(tags);
        Set<Book> books = new HashSet<>();
        books.add(newBook);
        tag2.setBooks(books);
        when(bookRepository.save(newBook)).thenReturn(book2);
        when(tagRepository.save(tag)).thenReturn(tag2);
        //when
        Book testedBook = bookService.addNewTag(newBook, tag);
        //then
        Assertions.assertEquals(tags, testedBook.getTags());
        Assertions.assertEquals("Zuzanna Glinka", testedBook.getAuthor());
    }
}
