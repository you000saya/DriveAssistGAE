/****************************************************************//**
* \file 	SensorValue.java
* \author 	
* \version 	1.0
* \date 	11.2012
*
* \brief 	Class to store sensor values in datestore.
* \details 	sensorId, temperature and date can be stored.
********************************************************************/

package com.appspot.drivenotification.model;

import java.util.Calendar;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class SensorValue {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
	
	@Persistent
	private String sensorId;
	
	@Persistent
	private float temperature;
	
	@Persistent
	private Date date;
	
	public SensorValue(String id,float temperature,Date date){
		this.sensorId=id;
		this.temperature=temperature;
		this.date=date;
	}

}
