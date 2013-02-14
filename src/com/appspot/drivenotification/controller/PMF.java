/****************************************************************//**
* \file 	PMF.java
* \author 	Yuka Hasegawa
* \version 	1.0
* \date 	11.2012
*
* \brief 	Access JDO helper class.
* \details 	Make Persistence Manager Instance.
********************************************************************/


package com.appspot.drivenotification.controller;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {
	private static final PersistenceManagerFactory pmfInstance =
	        JDOHelper.getPersistenceManagerFactory("transactions-optional");

	    private PMF() {}

	    public static PersistenceManagerFactory get() {
	        return pmfInstance;
	    }
}
