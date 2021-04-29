import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Patient {
	//Data Members for Patient
	private String patientName;
	private int patientAge;
	private String patientOccupation;
	private String isHighRisk;
	private String isFirstResponder;
	private String isDesignatedWorker;
	private int ID;
	private ArrayList<Appointment> appointments;


	//No args constructor for Patient
	public Patient() {

	}
	//Constructor for Patient that initializes all data members
	public Patient(String pn, int pa, String po, String ihr, String ifr, String idw, int id) {
		patientName = pn;
		patientAge = pa;
		patientOccupation = po;
		isHighRisk = ihr;
		isFirstResponder = ifr;
		isDesignatedWorker = idw;
		ID = id;
	}
	//Getters and Setters
	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public int getPatientAge() {
		return patientAge;
	}

	public void setPatientAge(int patientAge) {
		this.patientAge = patientAge;
	}

	public String getPatientOccupation() {
		return patientOccupation;
	}

	public void setPatientOccupation(String patientOccupation) {
		this.patientOccupation = patientOccupation;
	}

	public String isHighRisk() {
		return isHighRisk;
	}

	public void setHighRisk(String isHighRisk) {
		this.isHighRisk = isHighRisk;
	}

	public String isFirstResponder() {
		return isFirstResponder;
	}

	public void setFirstResponder(String isFirstResponder) {
		this.isFirstResponder = isFirstResponder;
	}

	public String isDesignatedWorker() {
		return isDesignatedWorker;
	}

	public void setDesignatedWorker(String isDesignatedWorker) {
		this.isDesignatedWorker = isDesignatedWorker;
	}


	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public ArrayList<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(ArrayList<Appointment> appointments) {
		this.appointments = appointments;
	}
	//Calls searchForAppoinments and stores it in appointments
	public void appointment() {
		appointments = searchForAppointments();
	}
	//Prompts user to enter information for First and Second Appointment
	//Calls methods to enter data into newAppointment
	//Write to text file, and add newAppointment to ArrayList Appointments
	private ArrayList<Appointment> searchForAppointments() {
		ArrayList<Appointment> appointments = new ArrayList<>();
		FirstAppointment fAppointment = findFirstAppointment();
		appointments.add(fAppointment);
		SecondAppointment sAppointment = findSecondAppointment();
		appointments.add(sAppointment);
		if (appointments.contains(null)) {
			System.out.println("User is missing information for one or both appointments");
			for (int i = 0; i < appointments.size(); i++) {
				if (appointments.get(i) == null) {
					if (i == 0) {
						System.out.println("Please enter the information for your first appointment.");
						FirstAppointment newAppointment = signUpForFirstAppointment();
						writeFirstAppointment(newAppointment);
						appointments.add(newAppointment);
					} else {
						System.out.println("Please enter the information for your second appointment.");
						SecondAppointment newAppointment = signUpForSecondAppointment();
						writeSecondAppointment(newAppointment);
						appointments.add(newAppointment);
					}
				}
			}
		}
		return appointments;
	}
	//Finds FirstAppoinment using the ID
	//Returns fa which is an instance of FirstAppointment and Appointment
	private FirstAppointment findFirstAppointment() {
		File f = new File("firstappointment.txt");
		try {
			Scanner s = new Scanner(f);
			while (s.hasNextLine()) {
				String line = s.nextLine();
				String[] tokens = line.split("&");
				int id = Integer.parseInt(tokens[0]);
				if (this.ID == id) {
					FirstAppointment fa = new FirstAppointment(this, tokens[1], tokens[2], tokens[3]);
					return fa;
				} else {
					return null;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("No file found.");
			return null;
		}
		return null;
	}
	//Finds SecondAppoinment using the ID
	//Returns sa which is an instance of SecondAppointment and Appointment
	private SecondAppointment findSecondAppointment() {
		File f = new File("secondappointment.txt");
		try {
			Scanner s = new Scanner(f);
			while (s.hasNextLine()) {
				String line = s.nextLine();
				String[] tokens = line.split("&");
				int id = Integer.parseInt(tokens[0]);
				if (this.ID == id) {
					SecondAppointment fa = new SecondAppointment(this, tokens[1], tokens[2], tokens[3]);
					return fa;
				} else {
					return null;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("No file found.");
			return null;
		}
		return null;
	}
	//Stores data from methods called in newFa
	//Which is a instance of FirstAppoinment
	//Returns the new newFa
	private FirstAppointment signUpForFirstAppointment() {
		Scanner s = new Scanner(System.in);
		String email = enterEmail(s);
		String date = enterDate(s);
		String location = enterLocation(s);
		FirstAppointment newFA = new FirstAppointment(this, email, date, location);
		return newFA;
	}
	//Stores data from methods called in newFa
	//Which is a instance of SecondAppoinment
	//Returns the new newFa
	private SecondAppointment signUpForSecondAppointment() {
		Scanner s = new Scanner(System.in);
		String email = enterEmail(s);
		String date = enterDate(s);
		String location = enterLocation(s);
		SecondAppointment newFA = new SecondAppointment(this, email, date, location);
		return newFA;
	}

	//Prompts user to enter email
	//returns input if valid and contains @
	//reprompts if input does not contain @
	private String enterEmail(Scanner s) {
		System.out.println("Please type in your email.");
		String input = s.next();
		if (input.contains("@")) {
			return input;
		} else {
			return enterEmail(s);
		}

	}
	//Prompts user to enter date for vaccination
	private String enterDate(Scanner s) {
		System.out.println("Please type in the date you wish your vaccine to be.");
		String input = s.next();
		return input;
	}
	//Prompts user to enter the name of the vaccination center
	private String enterLocation(Scanner s) {
		System.out.println("Please type in the name of your prefered vaccination center.");
		String input = s.next();
		return input;
	}
	//Uses Appointment a to write to the file firstappointment.txt
	private void writeFirstAppointment(Appointment a) {
		File f = new File("firstappointment.txt");
		try {
			FileWriter fw = new FileWriter(f, true);
			String id = Integer.toString(ID);
			String email = a.getRecipentEmail();
			String location = a.getVaccinationCenter();
			String vaccinationDate = a.getVaccinationDate();
			String line = id + "&" + email + "&" + location + "&" + vaccinationDate + "\n";
			fw.write(line);
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	//Uses Appointment a to write to the file secondappointment.txt
	private void writeSecondAppointment(Appointment a) {
		File f = new File("secondappointment.txt");
		try {
			FileWriter fw = new FileWriter(f, true);
			String id = Integer.toString(ID);
			String email = a.getRecipentEmail();
			String location = a.getVaccinationCenter();
			String vaccinationDate = a.getVaccinationDate();
			String line = id + "&" + email + "&" + location + "&" + vaccinationDate + "\n";
			fw.write(line);
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}




