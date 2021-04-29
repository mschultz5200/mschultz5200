import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SecondAppointment extends Appointment{
	
	//Data Members for SecondAppointment
	private String cancelledAppointment;
	private String rescheduledAppointment;
	
	
	//No Args Constructor
	public SecondAppointment() {
		super();
	}
	//Constructor for SecondAppoinment that calls the super class's constructor
	public SecondAppointment(Patient rn, String re, String vc, String vd) {
		super(rn, re, vc, vd);
	}
	//Constructor for SecondAppointment that initializes all data members
	public SecondAppointment(Patient rn, String re, String vc, String vd, String ca, String ra) {
		super(rn, re, vc, vd);
		cancelledAppointment = ca;
		rescheduledAppointment = ra;
	}
	
	public String getCancelledAppointment() {
		return cancelledAppointment;
	}
	public void setCancelledAppointment(String cancelledAppointment) {
		this.cancelledAppointment = cancelledAppointment;
	}
	public String getRescheduledAppointment() {
		return rescheduledAppointment;
	}
	public void setRescheduledAppointment(String rescheduledAppointment) {
		this.rescheduledAppointment = rescheduledAppointment;
	}
	//Calls methods to cancel SecondAppointment of Patient p
	public void cancel(Patient p) {
		ArrayList<SecondAppointment> secondAppointments = createSecondAppointments();
		writeAllInOne(secondAppointments, p);
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
	//Creates a new patient named value from String Array line and returns it
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
	//Creates a SecondAppointment and returns the appointment
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
	//Initializes the file secondappointment.txt
	//Calls method to delete the specific appointment from the file
	private void writeAllInOne(ArrayList<SecondAppointment> secondAppointments, Patient p) {
		File f1 = new File("secondappointment.txt");
		deleteAppointmentSecond(secondAppointments, f1, p);
	}
	//Deletes the SecondAppoitment from File f
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
	

}
