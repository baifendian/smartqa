package com.bfd.smartqa.etl;

import java.util.HashSet;
import java.util.Set;

public class SetTest {

	public static void main(String[] args) {
		Set<Integer> set = new HashSet<Integer>();
		
		set.add(1);
		set.add(2);
		
		for (int i : set) {
			System.out.println(i);
		}
		
		for (int j : set) {
			System.out.println(j);
		}
	}
}
