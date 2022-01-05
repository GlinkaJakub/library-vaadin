package com.example.application.views.book;

import com.example.application.data.entity.Book;
import com.example.application.data.entity.Tag;
import com.example.application.data.service.BookService;
import com.example.application.data.service.TagService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import javax.annotation.security.PermitAll;
import java.util.List;

@PageTitle("Add tag to book")
@Route(value = "tagtobook", layout = MainLayout.class)
@PermitAll
public class AddTagToBookView extends HorizontalLayout {

    @Autowired
    private BookService bookService;
    @Autowired
    private TagService tagService;

    private Button sayHello;

    public AddTagToBookView(BookService bookService, TagService tagService) {
        this.bookService = bookService;
        this.tagService = tagService;
        List<Tag> tagList = tagService.list();
        List<Book> bookList = bookService.list(PageRequest.of(0, 10)).toList();
        //tags
        ComboBox.ItemFilter<Tag> filter = (tag, filterString) -> (tag.getName()).toLowerCase().indexOf(filterString.toLowerCase()) > -1;
        ComboBox<Tag> tagComboBox = new ComboBox<>("Tag");
        tagComboBox.setPlaceholder("Select tag");

        tagComboBox.setItems(filter, tagList);
        tagComboBox.setItemLabelGenerator(tag -> tag.getName());

        //books
        ComboBox.ItemFilter<Book> filterBook = (book, filterString) -> (book.getAuthor() + " " + book.getTitle()).toLowerCase().indexOf(filterString.toLowerCase()) > -1;
        ComboBox<Book> bookComboBox = new ComboBox<>("Book");
        bookComboBox.setPlaceholder("Select book");

        bookComboBox.setItems(filterBook, bookList);
        bookComboBox.setItemLabelGenerator(book -> book.getTitle() + " - " + book.getAuthor());

        Text result = new Text("");
        printListOfBooks(bookList, result);

        sayHello = new Button("Save");
        sayHello.addClickListener(e -> {
            Tag tagToAdd = tagComboBox.getValue();
            Book bookToAdd = bookComboBox.getValue();
            bookService.addNewTag(bookToAdd, tagToAdd);
            printListOfBooks(bookList, result);
            tagComboBox.clear();
            bookComboBox.clear();
            //todo cant add other tag to same book without refresh
        });

        setMargin(true);

        add(tagComboBox, bookComboBox, sayHello);
        add(result);
    }

    private void printListOfBooks(List<Book> bookList, Text result) {
        String booksTitle2 = "";
        for (Book b : bookList) {
            booksTitle2 += b.getTitle() + " - " + b.getAuthor() + " ( ";
            for ( Tag t : b.getTags()){
                booksTitle2  += t.getName() + ", ";
            }
            booksTitle2 += " ) ||";
        }
        result.setText(booksTitle2);
    }

}
