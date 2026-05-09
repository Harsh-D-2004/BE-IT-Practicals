import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CircleImpl extends UnicastRemoteObject implements Circle 
{
    private double PI;
    public CircleImpl() throws RemoteException 
    {
        super();
        PI = 22.0 / 7.0;
    }
    @Override
    public double getArea(int radius) {
    	
    	System.out.println("Remote method 'getArea()' invoked");
        System.out.println("Processing started on server...");
        System.out.println("Received radius: " + radius);
        
        double result = PI * radius * radius;
        
        System.out.println("Calculated area: " + result);
        System.out.println("Returning result to client...\n");
        
        return result;
    }
    
    @Override
    public double getPerimeter(int radius) {
    	
    	System.out.println("Remote method 'getPerimeter()' invoked");
        System.out.println("Processing started on server...");
        System.out.println("Received radius: " + radius);
        
        double result = 2 * PI * radius;
        
        System.out.println("Calculated perimeter: " + result);
        System.out.println("Returning result to client...\n");
        
        return result;
    }
    
    @Override
    public double addition(int num1 , int num2) {
    	
    	System.out.println("Remote method 'addition()' invoked");
        System.out.println("Processing started on server...");
        
        double result = num1 + num2;
        
        System.out.println("Calculated result: " + result);
        System.out.println("Returning result to client...\n");
        
        return result;
    }
}