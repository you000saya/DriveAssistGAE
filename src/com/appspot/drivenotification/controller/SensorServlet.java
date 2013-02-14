/****************************************************************//**
* \file 	SensorServlet.java
* \author 	
* \version 	1.0
* \date 	11.2012
*
* \brief 	Receive sensor value POST class.
* \details 	Receive sensor value by POST, store data and make PUSH notification.
********************************************************************/

package com.appspot.drivenotification.controller;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import com.appspot.drivenotification.model.*;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.mortbay.log.Log;
import java.util.logging.Logger;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;

public class SensorServlet extends BaseServlet{
		
	static final String ATTRIBUTE_STATUS = "status";
	static final float lower_limit=(float) 0.0;
	static final float upper_limit=(float) 30.0;
	
	String status = "";
	private static final Logger log = Logger.getLogger(SensorServlet.class.getName());
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		      throws IOException, ServletException {
		//sensorにpostされたデータを扱うサーブレット
		
		String id = req.getParameter("id");
		//String d = req.getParameter("time");
		String tem = req.getParameter("temp");
		
		//Calendar date = Calendar.getInstance();
		Date date = new Date();
		float temperature = Float.parseFloat(tem);
		
		saveValue(id,temperature,date);//データストアにセンサデータを保存
		
		//すべての場合に通知を送るのではなく、温度が基準外になった場合
		if(!isInLimit(temperature)){
			makeNotification(id, temperature);
		}
//		req.setAttribute(HomeServlet.ATTRIBUTE_STATUS, status.toString());
//	    getServletContext().getRequestDispatcher("/sensor").forward(req, resp);
	
	}

	/**
	 * 
	 * @param t Temperature to be judged.
	 * @return boolean The temperature is in the range or not. 
	 * 
	 *\brief Judge the temperature observed is in normal range or not.
	 */
	private boolean isInLimit(float t){
		if(lower_limit<=t && t<upper_limit){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * 
	 * @param id Sensor id.
	 * @param temperature Temperature POSTed by the sensor.
	 * @param date Date time when the server received the sensor value.
	 * 
	 * \brief Save the data. 
	 */
	private void saveValue(String id,float temperature,Date date){
		//データをもとに保存するデータを作成
		SensorValue data = new SensorValue(id,temperature,date);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
	           pm.makePersistent(data);
        } finally {
	           pm.close();
        }
	}
	
	/**
	 * 
	 * @param id Sensor id.
	 * @param temperature Temperature observed.
	 * \brief Make PUSH notification to registered device.
	 */
	private void makeNotification(String id, float temperature){
		List<String> devices = Datastore.getDevices();
		//String status;
		//センサのIDからセンサのGPS情報を取得
		 PersistenceManager pm = PMF.get().getPersistenceManager();
		 Query query = pm.newQuery(SensorGPS.class);
		 query.setFilter("sensorId == paramId");
		 query.declareParameters("String paramId");

		 List<SensorGPS> sensors = (List<SensorGPS>)pm.newQuery(query).execute(id);
		 		 
		 SensorGPS sensor = sensors.get(0);
		 pm.close();
		 log.warning("query="+query+" lat="+sensor.getLatitude()+" lon="+sensor.getLongitude()+" hit count="+sensors.size());
	     float latitude = sensor.getLatitude();
	     float longitude = sensor.getLongitude();
	     
		 Queue queue = QueueFactory.getQueue("gcm");
	      // NOTE: check below is for demonstration purposes; a real application
	      // could always send a multicast, even for just one recipient
	      if (devices.size() == 1) {
	        // send a single message using plain post
	        String device = devices.get(0);
	        queue.add(withUrl("/send")
	        	.param(SendMessageServlet.PARAMETER_DEVICE, device)
	            .param("message", Float.toString(temperature))
	            .param("latitude", Float.toString(latitude))
	            .param("longitude", Float.toString(longitude)));
	        status = "Single message queued for registration id " + device;
	      }
	}
	
}
