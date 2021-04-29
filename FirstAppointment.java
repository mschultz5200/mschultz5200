import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FirstAppointment extends Appointment {
	//Data Members for FirstAppointment
	private String cancelledAppointment;
	private String rescheduledAppointment;
	
	
	//No args constructor for FirstAppointment
	public FirstAppointment() {
		super();
		
	}

	//Constructor for FirstAppoinment that calls the super class's constructor
	public FirstAppointment(Patient rn, String re, String vc, String vd) {
		super(rn, re, vc, vd);
		
	}
	//Constructor for FirstAppoinment that calls the super class's constructor
	//Initializes all data members from FirstAppointment
	public FirstAppointment(Patient rn, String re, String vc, String vd, String ca, String ra) {
		super(rn, re, vc, vd);
		cancelledAppointment = ca;
		rescheduledAppointment = ra;
		

	}
	//Getters and Setters
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
	//Calls methods to cancel FirstAppointment of patient p
	public void cancel(Patient p) {
		ArrayList<FirstAppointment> firstAppointments = createFirstAppointments();
		writeAllInOne(firstAppointments, p);
	}
	
	//Reads patientList.txt and firstappointment.txt
	//Creates ArrayList<Patient>patients and ArrayList<Appointment>appointments
	//Adds data from the files to corresponding ArrayLists
	//returns appointments
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
	//Initializes the file firstappointment.txt
	//Calls method to delete the specific appointment from the file
	public void writeAllInOne(ArrayList<FirstAppointment> firstAppointments, Patient p) {
		File f = new File("firstappointment.txt");
		deleteAppointmentFirst(firstAppointments, f, p);
	}
	//Deletes the first Appointment from the text file
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
	
	
	
}
