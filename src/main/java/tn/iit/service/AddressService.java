package tn.iit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import tn.iit.entity.Address;
import tn.iit.exceptions.AdressNotFoundException;
import tn.iit.repository.AddressRepository;
import tn.iit.request.CreateAddressRequest;
import tn.iit.response.AddressResponse;

@Service
public class AddressService {

	Logger logger = LoggerFactory.getLogger(AddressService.class);
	
	@Autowired
	AddressRepository addressRepository;
	
	@Value("${server.port}")
	private int serverPort;

	public AddressResponse createAddress(CreateAddressRequest createAddressRequest) {
		
		Address address = new Address();
		address.setStreet(createAddressRequest.getStreet());
		address.setCity(createAddressRequest.getCity());
		
		addressRepository.save(address);
		
		return new AddressResponse(address);
		
	}
	
	public AddressResponse getById (long id) {
		
		logger.info("Inside getById " + id);
		logger.info("Server Port : "+ serverPort);
		Address address = addressRepository.findById(id).get();
		return new AddressResponse(address);
	}
	
}
