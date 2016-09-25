package com.match.matchEngineRunner;

class Sub {
	Sub(int num) {
		System.out.println(num);
	}
	int var = 10;
	void dothis() {
		System.out.println("Sub");
	}
}
public class Test extends Sub {

	Test(int num) {
		super(num);
		System.out.println("Test Constructor called");
		super.dothis();
		// TODO Auto-generated constructor stub
	}
	static int var = 13;
	public void dothis() {
		System.out.println("Test");
	}
	public static void main(String[] args) {
		Test sub = new Test(12);
		sub.var = 19;
		Test sub1 = new Test(12);
		System.out.println(sub1.var);
	}
}
