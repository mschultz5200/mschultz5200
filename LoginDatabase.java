import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Exception;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class LoginDatabase {

	// data members
	private static LoginDatabase l;
	private ArrayList<Key> database;

	//constructor
	private LoginDatabase() {
		
	}
	
	
	//method used to call the class
	public static LoginDatabase createDatabase() {
		if (l == null) {
			l = new LoginDatabase();
		}
		l.setDatabase();
		return l;
	}	
	
	
	//getters and setters
	public ArrayList<Key> getDatabase() {
		return database;
	}
	
	public void setDatabase(ArrayList<Key> database) {
		this.database = database;
	}
	
	private void setDatabase() {
		database = intializeDatabase();
	}
	
	// ------------------------------------------------------
	
	
	// the main method that prompts the user to login or sign up
	public Patient login() {
		int choice = loginOrSignUp();
		if (choice == 1) {
			Patient s = validateLogin(this.database);
			if (s == null) {
				return login();
			} else {
				return s;
			}
		} else {
			Patient user = signUp();
			return user;
		}
	}
	
	// method that ask the user for their input to sign up or log in
	private int loginOrSignUp() {
		System.out.println("Welcome! Please enter 1 if you want to login to your account or 2 if you would like to create an account.");
		Scanner s = new Scanner(System.in);
		if (!s.hasNextInt()) {
			System.out.println("Enter either 1 or 2");
			return loginOrSignUp();
		} else {
			int input = s.nextInt();
			if (input == 1 || input == 2) {
				return input;
			} else {
				System.out.println("Enter either 1 or 2");
				return loginOrSignUp();
			}
		}
	}
	
	private Patient validateLogin(ArrayList<Key> database) {
		try {
			String inputedLogin = enterLogin();
			int index = 0;
			boolean found = false;
			while (index < database.size() && found == false) {
				if (database.get(index).getKey().equals(inputedLogin)) {
					found = true;
					return database.get(index).getValue();
				}
				index++;
			}
			return null;
		} catch (Exception e) {
			return login();
		}
	}
		
	
	// ------------------------------------------------------
	
	// prompts the user to enter their user name and password
	private String enterLogin() {
		Scanner s = new Scanner(System.in);
		String userName = enterUsername(s);
		String password = enterPassword(s);
		return userName + " " + password;
	}
	
	// generates the username
	private String enterUsername(Scanner s) {
		System.out.println("Please type in your user name.");
		String input = s.next();
		return input;
	}
	
	//generates the password
	private String enterPassword(Scanner s) {
		System.out.println("Please type in your password.");
		String input = s.next();
		return input;
	}
	
	// ------------------------------------------------------
	
	// this generates the database from reading a file
	private ArrayList<Key> intializeDatabase() {
		File f = new File("patientList.txt");
		ArrayList<Key> database = new ArrayList<>();
		try {
			Scanner s = new Scanner(f);
			while (s.hasNextLine()) {
				Key key = compareDatabases(s);
				if (key != null) {
					database.add(key);
				}
			}
			return database;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return database;
		}
	}
	
	// splits a line
	private String[] split(String line) {
		if (!line.contains("&")) {
			return null;
		} else {
			String[] login = line.split("&");
			return login;		
		}
		
	}
	
	// this checks to see that each instance of key is true so that it can be added to the database
	private Key compareDatabases(Scanner s) {
		try {
			String line = s.nextLine(); 
			String[] tokens = split(line);
			if (validateLine(tokens) == true) {
				Key newKey = establishNewKey(tokens);
				return newKey;
			} else {
				return null;
			}
			
		} catch (Exception e) {
			return null;
		}
	}
	
	// helper method that validates the age, the risk, and if the patient is on the front lines
	private boolean validateLine(String[] line) {
		if (validateAge(line) == false || validateHighRisk(line) == false || validateFirstResponder(line) == false) {
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateAge(String[] line) {
		try {
			int age = Integer.parseInt(line[2]);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private boolean validateHighRisk(String[] line) {
		if (line[4].equals("yes") || line[4].equals("no")) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean validateFirstResponder(String[] line) {
		if (line[5].equals("yes") || line[5].equals("no")) {
			return true;
		} else {
			return false;
		}
	}
	
	// creates a new key that will be used for the database
	private Key establishNewKey(String[] line) {
		String login = line[0];
		String name = line[1];
		int age = Integer.parseInt(line[2]);
		String occupation = line[3];
		String isHighRisk = line[4];
		String isFirstResponder = line[5];
		String isDesignatedWorker = line[6];
		int id = Integer.parseInt(line[7]);
		Patient value = new Patient(name, age, occupation, isHighRisk, isFirstResponder, isDesignatedWorker, id);
		Key key = new Key(value, login);
		return key;
	}
	
	// ------------------------------------------------------
	
	
	//this is how the patient will sign up for the program
	private Patient signUp() {
		String login = enterLogin();
		boolean found = validateNewLogin(login);
		if (found == true) {
			System.out.println("Username already taken.");
			return signUp();
		} else {
			Patient newUser = createAllInOne(login);
			return newUser;
		}
	}
	
	// method that reads through an arraylist to try and find a match in terms of login data
	private boolean validateNewLogin(String login) {
		boolean found = false;
		String username = login.split(" ")[0];
		int index = 0;
		while (index < this.database.size() && found == false) {
			String existingUsername = this.database.get(index).getKey().split(" ")[0];		
			if (existingUsername.equals(username)) {
				return true;
			} else {
				return false;
			}
		} 
		return false;
	}
	
	// helper method that creates a patient and write to a file
	private Patient createAllInOne(String login) {
		Patient newUser = userInput();
		addUserToLoginList(login, newUser);
		return newUser;
	}
	
	
	// helper method that generates the data for the Patient class
	private Patient userInput() {
		Random rng = new Random();
		int up = 999999;
		int id = rng.nextInt(up);
		String name = nameInput();
		System.out.println("Please insert how old you are.");
		int age = ageInput();
		String occupation = occupationInput();
		System.out.println("Please enter if you are high risk.");
		String risk = riskInput();
		System.out.println("Please enter if you are a first responder.");
		String firstResponder = riskInput();
		System.out.println("Please enter if you are a designated worker.");
		String worker = riskInput();
		Patient u = new Patient(name, age, occupation, risk, firstResponder, worker, id);
		return u;
	}
	
	// user input for the patient's name
	private String nameInput() {
		System.out.println("Please insert your first and last name.");
		Scanner s = new Scanner(System.in);
		String name = s.nextLine();;
		return name;
	}
	
	// user input for the patient's age
	private int ageInput() {
		Scanner s = new Scanner(System.in);
		if (!s.hasNextInt()) {
			System.out.println("Please enter a number.");
			return ageInput();
		} else {
			int input = s.nextInt();
			return input;
		}
	}
	
	// user input to see the users occupation
	private String occupationInput() {
		System.out.println("Please enter your occupation.");
		Scanner s = new Scanner(System.in);
		String occupation = s.nextLine();
		return occupation;
	}
	
	// user input to see the patients risk
	private String riskInput() {
		Scanner sc = new Scanner(System.in);
		String risk = sc.nextLine();
		if (risk.equals("yes") || risk.equals("no")) {
			return risk;
		} else {
			System.out.println("Please insert yes or no");
			return riskInput();
		}
	}
	
	// this adds the user to the login database 
	private void addUserToLoginList(String login, Patient user) {
		Key newKey = new Key(user, login);
		this.database.add(newKey);
		updateFile(newKey);
	}
	
	
	// updates patient list with the new user
	private void updateFile(Key k) {
		File f = new File("patientList.txt");
		try {
			FileWriter fw = new FileWriter(f, true);
			String login = k.getKey();
			String name = k.getValue().getPatientName();
			String age = Integer.toString(k.getValue().getPatientAge());
			String occupation = k.getValue().getPatientOccupation();
			String risk = k.getValue().isHighRisk();
			String firstResponder = k.getValue().isDesignatedWorker();
			String worker = k.getValue().isDesignatedWorker();
			String id = Integer.toString(k.getValue().getID());
			String line = login + "&" + name + "&" + age + "&" + occupation + "&" + risk + "&" + firstResponder + "&"  + worker + "&" + id + "\n";
			fw.write(line);
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
