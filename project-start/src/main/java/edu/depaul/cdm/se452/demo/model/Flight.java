package edu.depaul.cdm.se452.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "flights")
public class Flight implements Serializable {
    private static final long serialVersionUID = 1L;
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "num")
    private String flightNumber;
    
    @ManyToOne
    @JoinColumn(name = "origination_airport", nullable = false)
    @JsonIgnore
    private Airport originationAirport;

    @ManyToOne
    @JoinColumn(name = "destination_airport", nullable = false)    
    @JsonIgnore
    private Airport destinationAirport;

    
    @Column(name="departure_dt")
    private Timestamp departureDateTime;

    private Timestamp arrivalDateTime;
}
