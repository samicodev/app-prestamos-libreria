package com.samicodev.service.impl;

import com.samicodev.exception.*;
import com.samicodev.model.Book;
import com.samicodev.service.IBookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements IBookService {

    private final List<Book> books = new ArrayList<>();

    @Override
    public void registerBook(Book book)  {
        // Verificar si ya existe un libro con el mismo ISBN
        boolean isDuplicateIsbn = books.stream()
                .anyMatch(existingBook -> existingBook.getIsbn().equals(book.getIsbn()));

        // Si hay un duplicado, lanzar excepción
        if (isDuplicateIsbn) {
            //throw new DuplicateStudentException("Book with ISBN " + book.getIsbn() + " already exists");
            throw new DuplicateBookException(
                    ErrorMessages.DUPLICATE_BOOK.formatMessage(book.getTitle(), book.getIsbn())
            );
        }

        // Si no hay duplicados, agregar el libro
        books.add(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return books;
    }

    @Override
    public Optional<Book> findBookByISBN(String isbn)  {

//        return books.stream()
//                .filter(book -> book.getIsbn(isbn))
//                .findFirst()
//                //.orElseThrow(() -> new BookNotFoundException("Book with ISBN " + isbn + " not found"));
//                .orElseThrow(() -> new BookNotFoundException(ExceptionMessages.BOOK_NOT_FOUND.formatMessage(isbn)
//                ));

        Optional<Book> optionalBook = books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst();

        if (optionalBook.isEmpty()) {
            //throw new BookNotFoundException("Book not found with ISBN: " + isbn);
            throw new BookNotFoundException(ErrorMessages.BOOK_NOT_FOUND.formatMessage(isbn));
        }

        return optionalBook;
    }


    //other alternativa
    /*public void registerBook(Book book) {
        validateDuplicateISBN(book.getIsbn());
        books.add(book);
    }

    public List<Book> listBooks() {
        return new ArrayList<>(books);
    }

    public Book findBookByISBN(String isbn) {
        return findBookByISBNInternal(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ISBN: " + isbn));
    }

    private Optional<Book> findBookByISBNInternal(String isbn) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst();
    }

    private void validateDuplicateISBN(String isbn) {
        if (findBookByISBNInternal(isbn).isPresent()) {
            throw new DuplicateBookException("Book with ISBN " + isbn + " already exists.");
        }
    }*/

}
