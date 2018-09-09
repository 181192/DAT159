package dat159.metrics;

public class CBO2 {

	class A {
		B b;
		
		void f() {
			b.c.x = 42;
		}
		void g() {
			C c = b.c;
			c.x = 42;
		}
	}
	
	class B {
		A a1;
		A a2;
		C c;
	}
	
	class C {
		int x;
	}
}
