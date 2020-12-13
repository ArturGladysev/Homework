package Converters;

public class FahrenheitConverter implements Converter {

    @Override
    public double convertValue(double value) {
        return 1.8 * value + 32;
    }
}
