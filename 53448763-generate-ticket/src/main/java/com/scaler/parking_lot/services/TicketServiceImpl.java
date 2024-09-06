package com.scaler.parking_lot.services;

import com.scaler.parking_lot.exceptions.InvalidGateException;
import com.scaler.parking_lot.exceptions.InvalidParkingLotException;
import com.scaler.parking_lot.exceptions.ParkingSpotNotAvailableException;
import com.scaler.parking_lot.models.*;
import com.scaler.parking_lot.respositories.GateRepository;
import com.scaler.parking_lot.respositories.ParkingLotRepository;
import com.scaler.parking_lot.respositories.TicketRepository;
import com.scaler.parking_lot.respositories.VehicleRepository;
import com.scaler.parking_lot.strategies.assignment.SpotAssignmentStrategyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private GateRepository gateRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public Ticket generateTicket(int gateId, String registrationNumber, String vehicleType)
            throws InvalidGateException, InvalidParkingLotException, ParkingSpotNotAvailableException {


        Gate gate = gateRepository.findById(gateId).orElseThrow(() -> new InvalidGateException("No gate"));
        ParkingLot parkingLot = parkingLotRepository.findAll().get(0);
        ParkingSpot parkingSpot;

        SpotAssignmentStrategyImpl spotAssignmentStrategy = new SpotAssignmentStrategyImpl();
        Optional<ParkingSpot> optionalParkingSpot = spotAssignmentStrategy.assignSpot(parkingLot, VehicleType.valueOf(vehicleType));

//        outerLoop:
//        for (ParkingFloor floor : parkingLot.getParkingFloors()) {
//            for (ParkingSpot spot : floor.getSpots()) {
//                if (spot.getSupportedVehicleType().toString().equalsIgnoreCase(vehicleType)) {
//                    parkingSpot = spot;
//                    break outerLoop;
//                }
//            }
//        }

        if (optionalParkingSpot.isEmpty()) throw new ParkingSpotNotAvailableException("No parking spot");
        else parkingSpot = optionalParkingSpot.get();

        Vehicle vehicle;
        Optional<Vehicle> optionalVehicle = vehicleRepository.getVehicleByRegistrationNumber(registrationNumber);

        if(optionalVehicle.isPresent()) {
            vehicle = optionalVehicle.get();
        } else {
            vehicle = new Vehicle();
            vehicle.setVehicleType(VehicleType.valueOf(vehicleType));
            vehicle.setRegistrationNumber(registrationNumber);
            vehicleRepository.save(vehicle);
        }


        Ticket ticket = new Ticket();
        ticket.setEntryTime(new Date());
        ticket.setGate(gate);
        ticket.setParkingAttendant(gate.getParkingAttendant());
        ticket.setVehicle(vehicle);
        ticket.setParkingSpot(parkingSpot);

        return ticketRepository.save(ticket);
    }
}
