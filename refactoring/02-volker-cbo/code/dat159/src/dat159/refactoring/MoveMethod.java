package dat159.refactoring;

public class MoveMethod {

	class Project {
		Person manager;
		Person[] participants;
		void shouldntreallyBeHere() {
			System.out.println(manager.id);
			System.out.println(manager.name);
		}
	}

	class Person {
		int id;
		String name;
		boolean participate(Project p) {
			for(int i=0; i<p.participants.length; i++) {
				if (p.participants[i].id == id) return(true);
			}
			return(false);
		}   
	}
}
