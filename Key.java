public class Key {
	
	// data members
	private Patient value;
	private String key;
	
	
	// constructors
	public Key() {
		// TODO Auto-generated constructor stub
	}
	
	public Key(Patient v, String l) {
		value = v;
		key = l;
	}

	
	// getters and setters
	public Patient getValue() {
		return value;
	}

	public void setValue(Patient value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	
}
