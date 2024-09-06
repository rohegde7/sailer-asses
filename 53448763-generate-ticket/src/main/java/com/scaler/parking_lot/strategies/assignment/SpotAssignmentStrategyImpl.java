package com.scaler.parking_lot.strategies.assignment;

import com.scaler.parking_lot.models.ParkingFloor;
import com.scaler.parking_lot.models.ParkingLot;
import com.scaler.parking_lot.models.ParkingSpot;
import com.scaler.parking_lot.models.VehicleType;

import java.util.Optional;

public class SpotAssignmentStrategyImpl implements SpotAssignmentStrategy{

    @Override
    public Optional<ParkingSpot> assignSpot(ParkingLot parkingLot, VehicleType vehicleType) {
        for (ParkingFloor floor : parkingLot.getParkingFloors()) {
            for (ParkingSpot spot : floor.getSpots()) {
                if (spot.getSupportedVehicleType() == vehicleType) {
                    return Optional.of(spot);
                }
            }
        }

        return Optional.empty();
    }
}
