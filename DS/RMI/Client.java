import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) 
    {
        try 
        {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 4000);
            Circle circle = (Circle) registry.lookup("circle");                                                                       
            int radius;
            Scanner inp=new Scanner(System.in);
            System.out.print("Enter the radius of the circle: ");
            radius=inp.nextInt();
            inp.close();
            System.out.println("\nThe Area of the circle is "+circle.getArea(radius));
            System.out.println("The Perimeter of the circle is "+circle.getPerimeter(radius));
            
            System.out.println("Addition :  "+circle.addition(34 , 45));
        } 
        catch (Exception e)
        {
            System.out.println("Client Error: " + e);
        }
    }
}