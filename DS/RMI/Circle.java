import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Circle extends Remote {
    public double getArea(int radius) throws RemoteException;
    public double getPerimeter(int radius) throws RemoteException; 
<<<<<<< Updated upstream
    
    public double addition(int num1 , int num2) throws RemoteException;
=======
>>>>>>> Stashed changes
}