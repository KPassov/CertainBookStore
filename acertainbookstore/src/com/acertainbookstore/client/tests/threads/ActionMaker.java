package com.acertainbookstore.client.tests.threads;

import java.util.Set;

import com.acertainbookstore.business.BookCopy;
import com.acertainbookstore.business.ConcurrentCertainBookStore;
import com.acertainbookstore.utils.BookStoreException;

public class ActionMaker implements Runnable{
	private Thread runner;
	private ConcurrentCertainBookStore store;
	private int amount;
	private Set<BookCopy> books;
	
	public ActionMaker(){
		
	}
	
	public ActionMaker(ConcurrentCertainBookStore store, Set<BookCopy> books, int amount, boolean run){
		this.store = store;
		this.books = books;
		this.amount = amount;
		runner = new Thread(this);
		if (run)
			runner.start();
	}
	
	public ActionMaker(ConcurrentCertainBookStore store, Set<BookCopy> books, int amount){
		this.store = store;
		this.books = books;
		this.amount = amount;
		runner = new Thread(this);
		runner.start();
	}

	public void run(){
		try {
			while(amount > 0){
				store.buyBooks(books);
				store.addCopies(books);
				amount--;
			}
		}
		catch (BookStoreException e){
			e.printStackTrace();
		}
	}	
}