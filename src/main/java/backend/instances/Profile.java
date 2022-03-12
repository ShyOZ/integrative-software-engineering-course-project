package backend.instances;

public class Profile {
	private int toCheck;
	
	public Profile() {
		
	}

	public Profile(int toCheck) {
		this.toCheck = toCheck;
	}

	public int getToCheck() {
		return toCheck;
	}

	public void setToCheck(int toCheck) {
		this.toCheck = toCheck;
	}

	@Override
	public String toString() {
		return "Profile [toCheck=" + toCheck + "]";
	}
	
	

}
