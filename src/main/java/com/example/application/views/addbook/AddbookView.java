package com.example.application.views.addbook;

import com.example.application.data.entity.Book;
import com.example.application.data.service.BookService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.charts.model.Title;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import javax.annotation.security.PermitAll;
import java.util.Arrays;
import java.util.List;

@PageTitle("Add book")
@Route(value = "about", layout = MainLayout.class)
@PermitAll
public class AddbookView extends HorizontalLayout {

    @Autowired
    private BookService bookService;

    private TextField name;
    private TextField author;
    private Text books;
    private Text booksCount;
    private Button sayHello;
    private Crud<Book> table;

    public AddbookView(BookService bookService) {
        this.bookService = bookService;
        String booksTitle = "";
        List<Book> bookList = bookService.list(PageRequest.of(0, 10)).toList();
        for (Book b : bookList) {
            booksTitle += b.getTitle() + " - " + b.getAuthor() + "\n";
        }
        books = new Text(booksTitle);
        booksCount = new Text(String.valueOf(bookService.count()));
        name = new TextField("Title");
        author = new TextField("Author");
        sayHello = new Button("Save");
        sayHello.addClickListener(e -> {
            Book book = new Book();
            book.setTitle(name.getValue());
            book.setAuthor(author.getValue());
            bookService.save(book);
            Notification.show("Added book: " + name.getValue());
            name.setValue("");
            author.setValue("");
        });

        setMargin(true);
        add(books, booksCount);
        setVerticalComponentAlignment(Alignment.END, name, author, sayHello);

        add(name, author, sayHello);
    }

}
