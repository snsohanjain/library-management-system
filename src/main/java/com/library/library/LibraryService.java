package com.library.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class LibraryService {
    private final int MAX_BORROWED_BOOKS = 5;
    private final int FINE_PER_DAY = 10;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public boolean borrowBook(int bookId, int userId) {
        Book book = bookRepository.findById(bookId).get();
        User user = userRepository.findById(userId).get();

        if (book.isAvailable() && user.getBorrowedBooks() < MAX_BORROWED_BOOKS) {
            book.setAvailable(false);
            user.setBorrowedBooks(user.getBorrowedBooks() + 1);
            Transaction transaction = new Transaction();
            transaction.setBook(book);
            transaction.setUser(user);
            transaction.setDueDate(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000));
            transactionRepository.save(transaction);
        } else {
            throw new IllegalArgumentException("Book not available or user has reached max borrowed books");
        }



    public void returnBook(int transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).get();
        Book book = transaction.getBook();
        User user = transaction.getUser();

        book.setAvailable(true);
        user.setBorrowedBooks(user.getBorrowedBooks() - 1);

        long diff = new Date().getTime() - transaction.getDueDate().getTime();
        int daysLate = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        if (daysLate > 0) {
            transaction.setFine(daysLate * FINE_PER_DAY);
        }
        transaction.setReturnDate(new Date());
        transactionRepository.save(transaction);


    }
}