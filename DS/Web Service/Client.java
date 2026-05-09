package webservice;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class Client {

    public static void main(String[] args) throws Exception {

        URL url = new URL(
                        "http://localhost:8080/calculator?wsdl");

        QName qname =
                new QName(
                        "http://webservice/",
                        "CalculatorServiceImplService");

        Service service =
                Service.create(url, qname);

        CalculatorService calc =
                service.getPort(CalculatorService.class);

        System.out.println("Addition = "
                + calc.add(10, 5));

        System.out.println("Subtraction = "
                + calc.sub(10, 5));
    }
}