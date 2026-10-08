package com.hotelmate.app.convertor;

import com.hotelmate.app.entity.ReservationEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Project: D387 sample code
 * Package: com.hotelmate.app.convertor
 * <p>
 * User: carolyn.sher
 * Date: 9/16/2022
 * Time: 6:06 PM
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */

public interface ReservationService {
    public ReservationEntity findLast();
    public List<ReservationEntity> findAll();
}
