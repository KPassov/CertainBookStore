package com.acertainbookstore.client.tests.threads;

import java.util.Set;

import com.acertainbookstore.business.ConcurrentCertainBookStore;
import com.acertainbookstore.business.StockBook;
import com.acertainbookstore.utils.BookStoreException;

public class AddDeleter implements Runnable {
	
	private Thread runner;
	private ConcurrentCertainBookStore store;
	private int amount;
	private Set<Integer> isbns;
	private Set<StockBook> books;
	
	public AddDeleter(){
		
	}
	
	public AddDeleter(ConcurrentCertainBookStore store, Set<Integer> isbns, Set<StockBook> books, int amount){
		this.store = store;
		this.books = books;
		this.amount = amount;
		this.isbns = isbns;
		runner = new Thread(this);
		runner.start();
	}
	
	public void run(){
		try {
			while(amount > 0){
				store.removeBooks(isbns);
				store.addBooks(books);
				amount--;
			}
		}
		catch (BookStoreException e){
			e.printStackTrace();
		}
	}	
}
