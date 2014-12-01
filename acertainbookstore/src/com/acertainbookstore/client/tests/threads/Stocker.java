package com.acertainbookstore.client.tests.threads;

import java.util.Set;

import com.acertainbookstore.business.BookCopy;
import com.acertainbookstore.business.ConcurrentCertainBookStore;
import com.acertainbookstore.utils.BookStoreException;

public class Stocker implements Runnable{

	private Thread runner;
	private ConcurrentCertainBookStore store;
	private int amount;
	private Set<BookCopy> books;
	
	public Stocker(ConcurrentCertainBookStore store, Set<BookCopy> books, int amount){
		this.store = store;
		this.books = books;
		this.amount = amount;
		runner = new Thread(this);
		runner.start();
	}

	public void run(){
		try {
			while(amount > 0){
				store.addCopies(books);
				//System.out.println("s" + amount);
				amount--;
			}
		}
		catch (BookStoreException e){
			e.printStackTrace();
		}
	}	
}
