package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

	@Autowired
	private TestEntityManager entityManager;

	private Doctor d1;

	private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;
    
    @Test
    void doctorTest(){
    	d1 = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
    	
        d1.setId(1);
        
        assertThat(d1.getId()).isNotNull();
    }
    
    @Test
    void patientTest(){
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        p1.setId(1);

        assertThat(p1.getId()).isNotNull();
    }
    
    @Test
    void roomTest(){
        r1 = new Room("Dermatology");
        
        assertThat(r1.getRoomName()).isNotNull();
    }
    
    @Test
    void appointmentTest(){
    	d1 = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        r1 = new Room("Dermatology");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        
        LocalDateTime startsAt= LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("20:30 24/04/2023", formatter);

        a1 = new Appointment(p1, d1, r1, startsAt, finishesAt);
        a1.setId(1);
        
        assertThat(a1.getId()).isNotNull();
    }
    

    @Test
    void appointmentOverlapsTestIf1(){
    	d1 = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        r1 = new Room("Dermatology");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        
        LocalDateTime startsAt= LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("20:30 24/04/2023", formatter);

        a1 = new Appointment(p1, d1, r1, startsAt, finishesAt);
        a2 = new Appointment();
        a2.setDoctor(a1.getDoctor());
        a2.setPatient(a1.getPatient());
        a2.setRoom(a1.getRoom());
        a2.setStartsAt(a1.getStartsAt());
        a2.setFinishesAt(a1.getFinishesAt());
        
        assertThat(a1.overlaps(a2)).isEqualTo(true);
    }
    
    @Test
    void appointmentOverlapsTestIf2(){
    	d1 = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        r1 = new Room("Dermatology");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        
        LocalDateTime startsAt= LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("20:30 24/04/2023", formatter);
        LocalDateTime startsAt2= LocalDateTime.parse("20:00 24/04/2023", formatter);
        LocalDateTime finishesAt2 = LocalDateTime.parse("21:00 24/04/2023", formatter);

        a1 = new Appointment(p1, d1, r1, startsAt, finishesAt);
        a2 = new Appointment(p1, d1, r1, startsAt2, finishesAt2);
        
        assertThat(a1.overlaps(a2)).isEqualTo(true);
    }
    
    @Test
    void appointmentOverlapsTestIf3(){
    	d1 = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        r1 = new Room("Dermatology");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        
        LocalDateTime startsAt= LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("20:30 24/04/2023", formatter);
        LocalDateTime startsAt2= LocalDateTime.parse("19:00 24/04/2023", formatter);
        LocalDateTime finishesAt2 = LocalDateTime.parse("20:00 24/04/2023", formatter);

        a1 = new Appointment(p1, d1, r1, startsAt, finishesAt);
        a2 = new Appointment(p1, d1, r1, startsAt2, finishesAt2);
        
        assertThat(a1.overlaps(a2)).isEqualTo(true);
    }
    
    @Test
    void appointmentDontOverlapsTest(){
    	d1 = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        r1 = new Room("Dermatology");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        
        LocalDateTime startsAt= LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("20:30 24/04/2023", formatter);
        LocalDateTime startsAt2= LocalDateTime.parse("15:30 24/04/2023", formatter);
        LocalDateTime finishesAt2 = LocalDateTime.parse("17:30 24/04/2023", formatter);

        a1 = new Appointment(p1, d1, r1, startsAt, finishesAt);
        a2 = new Appointment(p1, d1, r1, startsAt2, finishesAt2);
        
        assertThat(a1.overlaps(a2)).isEqualTo(false);
    }

    /** TODO
     * Implement tests for each Entity class: Doctor, Patient, Room and Appointment.
     * Make sure you are as exhaustive as possible. Coverage is checked ;)
     */
}
