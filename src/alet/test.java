package alet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

import alet.sets.Set;

class A{
	int val;
	public A(int val) {
		this.val = val;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof A) {
			A new_name = (A) obj;
			return val == new_name.val;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(val);
	}
}

class B{
	int x;
	public B(int x) {
		this.x = x;
	}
	
	@Override
	public boolean equals(Object obj) {
		return true;
	}
	
}


public class test {

	public static void main(String[] args) {
		String s1 = "A";
		String s2 = "B";
		ArrayList<String> a1 = new ArrayList<String>();

		ArrayList<String> a2 = new ArrayList<String>();
		
		a1.add(s1);
		a1.add(s2);
		a2.add(s1);
		a2.add(s2);
		
		System.out.println(a1.equals(a2));
		ArrayList<ArrayList<String>> aa = new ArrayList<ArrayList<String>>();
		aa.add(a1);
		System.out.println(aa.contains(a2));
	}
}

