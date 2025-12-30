import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pet {
    private String petId;
    private String petName;
    private String petBreed;
    private int petAge;
    private String ownerName;
    private int contactInfo;
    private LocalDate registrationDate;
    private List<Appointment> appointments;

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public int getPetAge() {
        return petAge;
    }

    public void setPetAge(int petAge) {
        this.petAge = petAge;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(int contactInfo) {
        this.contactInfo = contactInfo;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Pet(String petId, String petName, String petBreed, int petAge, String ownerName, int contactInfo) {
        this.petId = petId;
        this.petName = petName;
        this.petBreed = petBreed;
        this.petAge = petAge;
        this.ownerName = ownerName;
        this.contactInfo = contactInfo;
        this.registrationDate = LocalDate.now();
        this.appointments = new ArrayList<>();
    }

    @Override
    public String toString() {
        return
                "ID:" + petId + "____" +
                "Name:" + petName + "____" +
                "Breed:" + petBreed + "____" +
                "Age:"  + petAge + "____"+
                "Owner:" + ownerName + "____" +
                "Contact:" + contactInfo +"____"+
                "Registration Date:" + registrationDate;
    }
}
