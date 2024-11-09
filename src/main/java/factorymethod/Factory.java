package factorymethod;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import dataAccess.DataAccess;

public class Factory {

	
	public BLFacade createBLFacade(ConfigXML c) throws MalformedURLException {
			if (c.isBusinessLogicLocal()) {
				DataAccess da = new DataAccess();
				return new BLFacadeImplementation(da);
			} else {
				String serviceName = "http://" + c.getBusinessLogicNode() + ":" + c.getBusinessLogicPort() + 
				"/ws/" + c.getBusinessLogicName() + "?wsdl";

				URL url;

				url = new URL(serviceName);
				// 1st argument refers to wsdl document above
				// 2nd argument is service name, refer to wsdl document above
				QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");

				Service service = Service.create(url, qname);
				return service.getPort(BLFacade.class);
			}
	}

}
