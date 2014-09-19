package edu.handong.babgo.util;



public interface Matcher<T, E> {
	
	public E match(T obj);
}
