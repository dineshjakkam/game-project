package comp102x.project.task;

public class CharValidator {
	
	public boolean validateChar(char c) {
		boolean status=false;
		// Please write your code after this line
		if((c>64 && c<91) || (c>96 && c<123) || (c>47 && c<58))
		status=true;
		return status; // This line should be modified or removed after finising the implementation of this method.
	}

}
