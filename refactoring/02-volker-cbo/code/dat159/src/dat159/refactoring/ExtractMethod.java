package dat159.refactoring;

public class ExtractMethod {

	int fields_are_easy = 1;
	
	void simple() {
		fields_are_easy = fields_are_easy+1;
		
		System.out.println("The output is:");
		System.out.println(fields_are_easy);
	}

	int getF() { return fields_are_easy; }
	
	void stillSimple() {
		fields_are_easy = fields_are_easy+1;
		
		System.out.println("The output is:");
		System.out.println(getF());
	}

	void localVariable() {
		
		System.out.println("The output is:");
		int temp = fields_are_easy+1;
		System.out.println(temp);
		
		// Avoid dead code above:
		System.out.println(temp);
	}
	
	void localVariableWithParams() {
		
		int base = 1;
		int offset = 42;

		System.out.println("The output is:");
		int temp = fields_are_easy+base*offset;
		System.out.println(temp);
		
		// Avoid dead code above:
		System.out.println(temp);
	}

	void nope() {
		
		System.out.println("The output is:");
		int temp = fields_are_easy+1;
		int temp2 = fields_are_easy+1;
		System.out.println(getF());
		
		// Avoid dead code above:
		System.out.println(temp);
		System.out.println(temp2);
	}
}
