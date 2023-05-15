package com.devsuperior.bds02.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;


@Service
public class EventService {
	
	@Autowired
	EventRepository eventRepository;

	@Transactional
	public EventDTO update(Long id, EventDTO event) {
		try {

			Event entity = eventRepository.getOne(id);
			this.copyDtoToEntity(event, entity);
			entity = eventRepository.save(entity);
			return new EventDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found" + id);
		}
	}
	
	private void copyDtoToEntity(EventDTO eventDto, Event entity) {
		entity.setName(eventDto.getName());
		entity.setDate(eventDto.getDate());
		entity.setUrl(eventDto.getUrl());
		entity.setCity(new City(eventDto.getCityId(), "Goiânia"));

	}
		
}
