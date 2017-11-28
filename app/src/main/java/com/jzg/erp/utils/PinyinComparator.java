package com.jzg.erp.utils;

import com.jzg.erp.model.CustomerInfo;

import java.util.Comparator;


/**
 * 
 * @author Mr.Z
 */
public class PinyinComparator implements Comparator<CustomerInfo> {

	public int compare(CustomerInfo o1, CustomerInfo o2) {
//		if(o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
//			return -1;
//		} else if(o1.getSortLetters().equals("#") || o2.getSortLetters().equals("@")) {
//			return 1;
//		} else {
//			return o1.getSortLetters().compareTo(o2.getSortLetters());
//		}
		return 1;
	}

}
