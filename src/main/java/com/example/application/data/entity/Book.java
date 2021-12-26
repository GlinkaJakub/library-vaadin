package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Book extends AbstractEntity {

    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @ManyToMany
    @JoinTable(
        name = "book_tag",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    @ManyToOne
    private User borrower;

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getAuthor() {return author;}
    public void setAuthor(String author) {this.author = author;}
}
