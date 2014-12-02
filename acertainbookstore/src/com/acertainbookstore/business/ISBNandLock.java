package com.acertainbookstore.business;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ISBNandLock {
	public final int ISBN;
	public final ReentrantReadWriteLock lock;
	
	public ISBNandLock(int ISBN) { 
		this.ISBN = ISBN;
		this.lock = new ReentrantReadWriteLock();
	}
}
