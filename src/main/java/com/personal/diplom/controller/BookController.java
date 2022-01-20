package com.personal.diplom.controller;

import com.personal.diplom.model.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.personal.diplom.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @RequestMapping(value = "/books/", method = RequestMethod.GET)
    public List<Book> list(){
        System.out.println("in books");
        Iterable<Book> bookIterable = bookRepository.findAll();
        ArrayList<Book> books = new ArrayList<>();
        for(Book book:bookIterable){
            books.add(book);
        }
        return books;
    }
    @RequestMapping(value = "/books/", method = RequestMethod.POST)
    public int add(Book book){
        Book newBook = bookRepository.save(book);
        return newBook.getId();
    }
    @GetMapping("/books/{id}")
    public ResponseEntity get(@PathVariable int id){
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (!optionalBook.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(optionalBook.get(),HttpStatus.OK);
    }
}
