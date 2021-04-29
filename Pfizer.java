import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Exception;
import java.io.FileWriter;
import java.io.IOException;

public class Pfizer {
	//Data Members for Pfizer
	private String recipientName;
	private int recipientAge;
	//No args Constructor for Pfizer
	public Pfizer() {
		
	}
	//Constructor for Pfizer that initializes all data members
	public Pfizer(String rn, int ra) {
		recipientName = rn;
		recipientAge = ra;
		
		
	}
	//Getters and Setters for Pfizer
	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public int getRecipientAge() {
		return recipientAge;
	}

	public void setRecipientAge(int recipientAge) {
		this.recipientAge = recipientAge;
	}
	//Calls methods to vaccinate patient p
	public void vaccinate(Patient p) {
		ArrayList<FirstAppointment> firstAppointments = createFirstAppointments();
		ArrayList<SecondAppointment> secondAppointments = createSecondAppointments();
		writeAllInOne(firstAppointments, secondAppointments, p);
	}
	

	//Reads patientList.txt and firstappointment.txt
	//Creates ArrayList<Patient>patients and ArrayList<Appointment>appointments
	//Adds data from the files to corresponding ArrayLists
	//Returns appointments
	private ArrayList<FirstAppointment> createFirstAppointments() {
		ArrayList<Patient> patients = new ArrayList<>();
		ArrayList<FirstAppointment> appointments = new ArrayList<>();
		File f = new File("patientList.txt");
		File f1 = new File("firstappointment.txt");
		try {
			Scanner s = new Scanner(f);
			Scanner s1 = new Scanner(f1);
			while (s.hasNextLine()) {
				String line = s.nextLine();
				String[] tokens = line.split("&");
				Patient add = establishNewPatient(tokens);
				patients.add(add);
			}
			while (s1.hasNextLine() ) {
				String line = s1.nextLine();
				String[] tokens = line.split("&");
				FirstAppointment newA = createFirstAppointment(tokens, patients);
				if (newA != null) {
					appointments.add(newA);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
		return appointments;
	}
	//Reads patientList.txt and secondappointment.txt
	//Creates ArrayList<Patient>patients and ArrayList<Appointment>appointments
	//Adds data from the files to corresponding ArrayLists
	//returns appointments
	private ArrayList<SecondAppointment> createSecondAppointments() {
		ArrayList<Patient> patients = new ArrayList<>();
		ArrayList<SecondAppointment> appointments = new ArrayList<>();
		File f = new File("patientList.txt");
		File f1 = new File("secondappointment.txt");
		try {
			Scanner s = new Scanner(f);
			Scanner s1 = new Scanner(f1);
			while (s.hasNextLine()) {
				String line = s.nextLine();
				String[] tokens = line.split("&");
				Patient add = establishNewPatient(tokens);
				patients.add(add);
			}
			while (s1.hasNextLine() ) {
				String line = s1.nextLine();
				String[] tokens = line.split("&");
				SecondAppointment newA = createSecondAppointment(tokens, patients);
				if (newA != null) {
					appointments.add(newA);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
		return appointments;
	}
	//Creates a new value for a patient from String Array line
	//Returns Value
	private Patient establishNewPatient(String[] line) {
		String name = line[1];
		int age = Integer.parseInt(line[2]);
		String occupation = line[3];
		String isHighRisk = line[4];
		String isFirstResponder = line[5];
		String isDesignatedWorker = line[6];
		int id = Integer.parseInt(line[7]);
		Patient value = new Patient(name, age, occupation, isHighRisk, isFirstResponder, isDesignatedWorker, id);
		return value;
	}
	//Creates a firstAppointment and returns the appointment
	private FirstAppointment createFirstAppointment(String[] line, ArrayList<Patient> patients) {
		int id = Integer.parseInt(line[0]);
		for (int i = 0; i < patients.size(); i++) {
			if (id == patients.get(i).getID()) {
				FirstAppointment appointment = new FirstAppointment(patients.get(i), line[1], line[2], line[3]);
				return appointment;
			}
		}
		System.out.println("No patient Found.");
		return null;
	}
	//Reads data from line and returns a new SecondAppointment with the data from line
	private SecondAppointment createSecondAppointment(String[] line, ArrayList<Patient> patients) {
		int id = Integer.parseInt(line[0]);
		for (int i = 0; i < patients.size(); i++) {
			if (id == patients.get(i).getID()) {
				SecondAppointment appointment = new SecondAppointment(patients.get(i), line[1], line[2], line[3]);
				return appointment;
			}
		}
		System.out.println("No patient Found.");
		return null;
	}
	//Initializes files f, f1, f2, and f3
	//Calls methods to delete from firstappointment.txt and secondappointment.txt
	//Calls methods to add to from firstvaccine.txt and secondvaccine.txt
	private void writeAllInOne(ArrayList<FirstAppointment> firstAppointments, ArrayList<SecondAppointment> secondAppointments, Patient p) {
		File f = new File("firstappointment.txt");
		File f1 = new File("secondappointment.txt");
		File f2 = new File("firstvaccine.txt");
		File f3 = new File("secondvaccine.txt");
		deleteAppointmentFirst(firstAppointments, f, p);
		addVaccinationFirst(f2, p);
		deleteAppointmentSecond(secondAppointments, f1, p);
		addVaccinationSecond(f3, p);
	}
	//Deletes the FirstAppointment from the File f
	private void deleteAppointmentFirst(ArrayList<FirstAppointment> appointment, File f, Patient p) {
		boolean found = false;
		int index = 0;
		while (found == false && index < appointment.size()) {
			if (appointment.get(index).getRecipientName().getID() == p.getID()) {
				appointment.remove(index);
				found = true;
			} else {
				index++;
			}
		}
		try {
			FileWriter fw = new FileWriter(f, false);
			for (int i = 0; i < appointment.size(); i++) {
				String id = Integer.toString(appointment.get(i).getRecipientName().getID());
				String email = appointment.get(i).getRecipentEmail();
				String location = appointment.get(i).getVaccinationCenter();
				String date = appointment.get(i).getVaccinationDate();
				String line = id + "&" + email + "&" + location + "&" + date + "\n";
				fw.write(line);
				fw.flush();
			}
		} catch (IOException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
	}
	//Deletes the SecondAppointment from the File f
	private void deleteAppointmentSecond(ArrayList<SecondAppointment> appointment, File f, Patient p) {
		boolean found = false;
		int index = 0;
		while (found == false && index < appointment.size()) {
			if (appointment.get(index).getRecipientName().getID() == p.getID()) {
				appointment.remove(index);
				found = true;
			} else {
				index++;
			}
		}
		try {
			FileWriter fw = new FileWriter(f, false);
			for (int i = 0; i < appointment.size(); i++) {
				String id = Integer.toString(appointment.get(i).getRecipientName().getID());
				String email = appointment.get(i).getRecipentEmail();
				String location = appointment.get(i).getVaccinationCenter();
				String date = appointment.get(i).getVaccinationDate();
				String line = id + "&" + email + "&" + location + "&" + date + "\n";
				fw.write(line);
				fw.flush();
			}
		} catch (IOException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
	}
	//Writes ID to firstvaccine.txt
	private void addVaccinationFirst(File f, Patient p) {
		try {
			FileWriter fw = new FileWriter(f, true);
			String line = p.getID() + "\n";
			fw.write(line);
			fw.flush();
		} catch (IOException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
	}
	//Writes ID to secondvaccine.txt
	private void addVaccinationSecond(File f, Patient p) {
		try {
			FileWriter fw = new FileWriter(f, true);
			String line = p.getID() + "\n";
			fw.write(line);
			fw.flush();
		} catch (IOException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
	}
	

}
