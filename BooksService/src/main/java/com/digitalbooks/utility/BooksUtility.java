package com.digitalbooks.utility;

public class BooksUtility {
	
	public  static String nullCheck(Object obj  ) {
		String nullChecked="";
		if(obj!=null) {
			nullChecked=obj.toString();
		}
		
		return nullChecked;
	}

}
