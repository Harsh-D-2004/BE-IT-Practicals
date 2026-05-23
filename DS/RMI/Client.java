import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) 
    {
        try 
        {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 4000);
<<<<<<< Updated upstream
            Circle circle = (Circle) registry.lookup("circle");                                                                       
=======
            Circle circle = (Circle) registry.lookup("rmi://localhost:4000/circle");                                                                        
>>>>>>> Stashed changes
            int radius;
            Scanner inp=new Scanner(System.in);
            System.out.print("Enter the radius of the circle: ");
            radius=inp.nextInt();
            inp.close();
            System.out.println("\nThe Area of the circle is "+circle.getArea(radius));
            System.out.println("The Perimeter of the circle is "+circle.getPerimeter(radius));
<<<<<<< Updated upstream
            
            System.out.println("Addition :  "+circle.addition(34 , 45));
=======
>>>>>>> Stashed changes
        } 
        catch (Exception e)
        {
            System.out.println("Client Error: " + e);
        }
    }
}