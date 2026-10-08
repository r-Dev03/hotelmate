package com.hotelmate.app.repository;

import java.util.List;

import com.hotelmate.app.entity.RoomEntity;
import org.springframework.data.repository.CrudRepository;



public interface RoomRepository extends CrudRepository<RoomEntity, Long> {

	//RoomEntity findById(Long id);
}
