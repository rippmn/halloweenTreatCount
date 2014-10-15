package com.rippmn.halloween.persistence;

import org.springframework.data.repository.CrudRepository;

import com.rippmn.halloween.domain.TrickorTreatEvent;

public interface TrickOrTreatEventRepository extends CrudRepository<TrickorTreatEvent, Long>{

}
