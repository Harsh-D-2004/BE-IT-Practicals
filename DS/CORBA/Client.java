import java.util.Scanner;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import CalculatorApp.Calculator;
import CalculatorApp.CalculatorHelper;

public class Client {

    public static void main(String[] args) {

        try {

            ORB orb = ORB.init(args, null);

            org.omg.CORBA.Object objRef =
                orb.resolve_initial_references("NameService");

            NamingContextExt ncRef =
                NamingContextExtHelper.narrow(objRef);

            Calculator calculator =
                CalculatorHelper.narrow(
                    ncRef.resolve_str("ABC")
                );

            Scanner sc = new Scanner(System.in);

            System.out.println("Welcome to Calculator System");

            while (true) {

                System.out.println("\n1. Add");
                System.out.println("2. Subtract");
                System.out.println("3. Multiply");
                System.out.println("4. Divide");
                System.out.println("5. Exit");

                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();

                if (choice == 5)
                    break;

                System.out.print("Enter first number: ");
                double x = sc.nextDouble();

                System.out.print("Enter second number: ");
                double y = sc.nextDouble();

                switch (choice) {

                    case 1:
                        System.out.println(
                            "Answer = "
                            + calculator.add(x, y)
                        );
                        break;

                    case 2:
                        System.out.println(
                            "Answer = "
                            + calculator.subtract(x, y)
                        );
                        break;

                    case 3:
                        System.out.println(
                            "Answer = "
                            + calculator.multiply(x, y)
                        );
                        break;

                    case 4:
                        System.out.println(
                            "Answer = "
                            + calculator.divide(x, y)
                        );
                        break;

                    default:
                        System.out.println("Invalid choice");
                }
            }

            sc.close();

        }
        catch (Exception e) {

            System.out.println("Client Error: " + e);

            e.printStackTrace(System.out);
        }
    }
}