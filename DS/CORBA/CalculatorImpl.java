import CalculatorApp.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;

public class CalculatorImpl extends CalculatorPOA {

    @Override
    public double add(double x, double y) {
        return x + y;
    }

    @Override
    public double subtract(double x, double y) {
        return x - y;
    }

    @Override
    public double multiply(double x, double y) {
        return x * y;
    }

    @Override
    public double divide(double x, double y) {

        if (y == 0)
            return 0;

        return x / y;
    }

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
}