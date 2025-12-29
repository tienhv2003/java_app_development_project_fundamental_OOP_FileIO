import java.time.LocalDateTime;

class AppointmentWithPet {
    private Appointment appointment;
    private Pet pet;

    public AppointmentWithPet(Appointment appointment, Pet pet) {
        this.appointment = appointment;
        this.pet = pet;
    }

    public Appointment getAppointment() { return appointment; }
    public Pet getPet() { return pet; }
    public LocalDateTime getDateTime() { return appointment.getDateTime(); }
}
