/****************************************************************//**
* \file 	SensorGPS.java
* \author 	
* \version 	1.0
* \date 	11.2012
*
* \brief 	Class to store sensor GPS in datestore.
* \details 	sensorId, latitude and longitude can be stored.
********************************************************************/



package com.appspot.drivenotification.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class SensorGPS {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
	
	@Persistent
	private String sensorId;
	
	@Persistent
	private float latitude;
	
	@Persistent
	private float longitude;
	
	public float getLatitude(){
		return this.latitude;
	}
	
	public float getLongitude(){
		return this.longitude;
	}
	
	public SensorGPS(String id,float latitude, float longitude){
		this.sensorId=id;
		this.latitude=latitude;
		this.longitude=longitude;
	}
}
