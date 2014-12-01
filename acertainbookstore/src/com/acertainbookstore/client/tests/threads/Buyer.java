package com.acertainbookstore.client.tests.threads;

import java.util.Set;

import com.acertainbookstore.business.BookCopy;
import com.acertainbookstore.business.ConcurrentCertainBookStore;
import com.acertainbookstore.utils.BookStoreException;

public class Buyer implements Runnable {
	
	private Thread runner;
	private ConcurrentCertainBookStore store;
	private int amount;
	private Set<BookCopy> books;
	
	public Buyer(){
		
	}
	
	public Buyer(ConcurrentCertainBookStore store, Set<BookCopy> books, int amount){
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
				//System.out.println("b" + amount);
				amount--;
			}
		}
		catch (BookStoreException e){
			e.printStackTrace();
		}
	}	
}
