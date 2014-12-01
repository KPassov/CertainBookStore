package com.acertainbookstore.client.tests.threads;

import java.util.Set;

import com.acertainbookstore.business.BookCopy;
import com.acertainbookstore.business.ConcurrentCertainBookStore;
import com.acertainbookstore.utils.BookStoreException;
	
public class Inquisitor implements Runnable{

	private Thread runner;
	private ConcurrentCertainBookStore store;
	private int amount;
	private Set<BookCopy> books;
	
	public Inquisitor(){
		
	}
	
	public Inquisitor(ConcurrentCertainBookStore store, Set<BookCopy> books, int amount){
		this.store = store;
		this.books = books;
		this.amount = amount + amount;
		runner = new Thread(this);
		runner.start();
	}
	
	public void run(){
	}	
}
