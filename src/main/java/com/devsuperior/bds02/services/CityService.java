package com.devsuperior.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {
	
	@Autowired
	CityRepository cityRepository;

	public List<CityDTO> findAll() {
		List<City> cities = cityRepository.findAll(Sort.by("name"));
		
		List<CityDTO> listCityDTO = cities.stream().map(city -> new CityDTO(city)).collect(Collectors.toList());
		
		return listCityDTO;
		
	}
	
	public CityDTO insert(City city) {
		
		City savedCity = cityRepository.save(city);
		
		CityDTO cityDto = new CityDTO(savedCity);
		
		return cityDto;
		
	}

	public void delete(Long id) {
		try {
			cityRepository.deleteById(id);
		}
		catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Id {id} not found");
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integity violation");
		}
		
	}
	
}
