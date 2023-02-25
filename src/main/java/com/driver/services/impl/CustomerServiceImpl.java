package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Customer customer=customerRepository2.findById(customerId).get();
		customerRepository2.delete(customer);
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		TripBooking trip=new TripBooking();
		Driver driver=null;
		List<Driver> driverList=driverRepository2.findAll();
		for(Driver drivers:driverList){
			if(drivers.getCab().isAvailable()){
				if((drivers == null) || drivers.getDriverId() > drivers.getDriverId()){
					drivers=drivers;
				}
			}
		}
		if(driver == null) throw new Exception("No Cab Available");

		Customer customer=customerRepository2.findById(customerId).get();
		trip.setDriver(driver);
		trip.setCustomer(customer);
		driver.getCab().setAvailable(false);

		trip.setToLocation(fromLocation);
		trip.setDistanceInKm(distanceInKm);
		trip.setFromLocation(fromLocation);
		int rate=driver.getCab().getPerKmRate();
		trip.setBill(10*distanceInKm);
		trip.setStatus(TripStatus.CONFIRMED);
		customer.getTripBookingList().add(trip);
		customerRepository2.save(customer);
		driver.getTripBooking().add(trip);
		driverRepository2.save(driver);
		return trip;

	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
		tripBooking.setStatus(TripStatus.CANCELED);
		tripBooking.setBill(0);
		tripBooking.getDriver().getCab().setAvailable(true);
		tripBookingRepository2.save(tripBooking);

	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
		tripBooking.setStatus(TripStatus.COMPLETED);
		tripBooking.getDriver().getCab().setAvailable(true);
		int bill=tripBooking.getDriver().getCab().getPerKmRate()*tripBooking.getDistanceInKm();
		tripBooking.setBill(bill);

		tripBookingRepository2.save(tripBooking);
	}
}
