package webservice;

import javax.xml.ws.Endpoint;

public class Server {

    public static void main(String[] args) {

        Endpoint.publish(
                "http://localhost:8080/calculator",
                new CalculatorServiceImpl());

        System.out.println("Web Service Started...");
    }
}