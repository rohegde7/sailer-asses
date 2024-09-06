package com.scaler.parking_lot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;

@Entity
public class Gate extends BaseModel{

    private String name;
    @Enumerated(value = EnumType.ORDINAL)
    private GateType type;

    @OneToOne
    private ParkingAttendant parkingAttendant;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GateType getType() {
        return type;
    }

    public void setType(GateType type) {
        this.type = type;
    }

    public ParkingAttendant getParkingAttendant() {
        return parkingAttendant;
    }

    public void setParkingAttendant(ParkingAttendant parkingAttendant) {
        this.parkingAttendant = parkingAttendant;
    }
}
