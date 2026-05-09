package webservice;

import javax.jws.WebService;

@WebService(endpointInterface = "webservice.CalculatorService")
public class CalculatorServiceImpl implements CalculatorService {

    public int add(int a, int b) {
        return a + b;
    }

    public int sub(int a, int b) {
        return a - b;
    }
}