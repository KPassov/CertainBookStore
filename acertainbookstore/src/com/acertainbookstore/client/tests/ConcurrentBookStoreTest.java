package com.acertainbookstore.client.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.acertainbookstore.business.BookCopy;
import com.acertainbookstore.business.ConcurrentCertainBookStore;
import com.acertainbookstore.business.ImmutableStockBook;
import com.acertainbookstore.business.StockBook;
import com.acertainbookstore.client.tests.threads.ActionMaker;
import com.acertainbookstore.client.tests.threads.Buyer;
import com.acertainbookstore.client.tests.threads.Inquisitor;
import com.acertainbookstore.client.tests.threads.Stocker;
import com.acertainbookstore.utils.BookStoreException;

public class ConcurrentBookStoreTest {

	private static final Integer TEST_ISBN1 = 30345653;
    private static final Integer TEST_ISBN2 = 30345651;
    private static final Integer TEST_ISBN3 = 30345654;
    private static final Integer TEST_ISBN4 = 30345655;

	List<Runnable> threads;
	ConcurrentCertainBookStore store;	
	
	@Before
	public void setUpBefore() throws BookStoreException{
		threads = new ArrayList<Runnable>();
		store = new ConcurrentCertainBookStore();
		store.addBooks(getDefaultBook());
	}
	
	@After
	public void cleanAfter(){
//		for(Runnable thread : threads){
//			thread.interrupt();
//		}
//		threads = null;
	}

	public static Set<StockBook> getDefaultBook() {
        Set<StockBook> books = new HashSet<StockBook>(); 
        books.add(new ImmutableStockBook(TEST_ISBN1, "Harry Potter and JUnit",
                        "JK Unit", (float) 10, 2500, 0, 0, 0, false));
        books.add(new ImmutableStockBook(TEST_ISBN2, "Does and donts of teamwork",
                        "Kasper skriver tests. De virker ikke", (float) 10, 2500, 0, 0, 0, false));
        books.add(new ImmutableStockBook(TEST_ISBN3, "Kasper og NB på java eventyr",
                		"Eventyrsgruppen", (float) 10, 2500, 0, 0, 0, false));
        books.add(new ImmutableStockBook(TEST_ISBN4, "YOLO, livet på gaden",
        				"mormor", 	       (float) 10, 2500, 0, 0, 0, false));
        return books;
	}

	
	@Test
	public void testOne() throws InterruptedException, BookStoreException{
//		Set<StockBook> bookss = new HashSet<StockBook>();
//		bookss.add(new ImmutableStockBook(TEST_ISBN1, "Harry Potter and JUnit",
//                "JK Unit", (float) 10, 1, 0, 0, 0, false));
		Set<BookCopy> books = new HashSet<BookCopy>();
	    books.add(new BookCopy(TEST_ISBN1, 4));
	    books.add(new BookCopy(TEST_ISBN2, 3));
	    books.add(new BookCopy(TEST_ISBN3, 2));
	    books.add(new BookCopy(TEST_ISBN4, 5));
		threads.add(new Stocker(store, books, 500));		
		threads.add(new Buyer(store, books, 500));
		
		Thread.sleep(1000);
		for(StockBook book : store.getBooks()){
			if(book.getNumCopies() != 2500){
				System.out.println(book.getTitle() + " failed test 1 with " + book.getNumCopies() + " books");
				assertTrue(false);
			}
		}
	}
	
	@Test
	public void testTwo(){
		Set<BookCopy> books = new HashSet<BookCopy>();
		books.add(new BookCopy(TEST_ISBN1, 3));
		books.add(new BookCopy(TEST_ISBN2, 3));
		books.add(new BookCopy(TEST_ISBN3, 3));
		books.add(new BookCopy(TEST_ISBN4, 3));
		threads.add(new ActionMaker(store, books, 500));
		for(int i = 1000; i > 0; i--){
			System.out.println("Itteration " + i);
			assertTrue(store.getBooks().get(0).getNumCopies() == store.getBooks().get(1).getNumCopies());
			assertTrue(store.getBooks().get(1).getNumCopies() == store.getBooks().get(2).getNumCopies());
			assertTrue(store.getBooks().get(2).getNumCopies() == store.getBooks().get(3).getNumCopies());
		}
	}
}
