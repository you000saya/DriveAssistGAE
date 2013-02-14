/****************************************************************//**
* \file 	SensorGPSInitializer.java
* \author 	
* \version 	1.0
* \date 	11.2012
*
* \brief 	Context initializer that loads the sensor GPS from the App Engine datastore.
* \details 	Make a instance to save sensor GPS in datastore.
********************************************************************/



package com.appspot.drivenotification.model;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.appspot.drivenotification.controller.PMF;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class SensorGPSInitializer implements ServletContextListener{

	//public static final String ATTRIBUTE_ACCESS_KEY = "AIzaSyC66TyavENDCq33C7vrSEHDHu7iW3nTsxs";
	
	private static final String ENTITY_KIND = "SensorGPS";
	private static final String ENTITY_KEY = "Sensor";
	private static final String ACCESS_KEY_FIELD_SENSOR_ID = "sensorId";
	private static final String ACCESS_KEY_FIELD_LATITUDE = "latitude";
	private static final String ACCESS_KEY_FIELD_LONGITUDE = "longitude";

	private final Logger logger = Logger.getLogger(getClass().getName());
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
//		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//	    Key key = KeyFactory.createKey(ENTITY_KIND, ENTITY_KEY);
//
//	    Entity entity;
//	    try {
//	      entity = datastore.get(key);
//	    } catch (EntityNotFoundException e) {
//	      entity = new Entity(key);
//	      // NOTE: it's not possible to change entities in the local server, so
//	      // it will be necessary to hardcode the API key below if you are running
//	      // it locally.
//	      entity.setProperty(ACCESS_KEY_FIELD_SENSOR_ID, "set_id_here");
//	      entity.setProperty(ACCESS_KEY_FIELD_LATITUDE,
//	          (float)35.135);
//	      entity.setProperty(ACCESS_KEY_FIELD_LONGITUDE, 
//	    		  (float)135.135);
//	      datastore.put(entity);
////	      logger.severe("Created fake key. Please go to App Engine admin "
////	          + "console, change its value to your API Key (the entity "
////	          + "type is '" + ENTITY_KIND + "' and its field to be changed is '"
////	          + ACCESS_KEY_FIELD + "'), then restart the server!");
//	    }
////	    String accessKey = (String) entity.getProperty(ACCESS_KEY_FIELD_LATITUDE);
////	    event.getServletContext().setAttribute(ATTRIBUTE_ACCESS_KEY, accessKey);
	  
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(SensorGPS.class);
		query.setFilter("sensorId == paramId");
		query.declareParameters("String paramId");

		List<SensorGPS> sensors = (List<SensorGPS>)pm.newQuery(query).execute("sensorid_here");
		
		if(sensors.size()==0){//まだインスタンスが存在していないときのみ生成
		SensorGPS tmpdata = new SensorGPS("sensorid_here", 135, 36);
			try {
		           pm.makePersistent(tmpdata);
	        } finally {
		           pm.close();
	        }
		}
		else{
			pm.close();
		}
	}

}
