package Converters;

public class FahrenheitConverter extends Converter {

    @Override
    public double convertValue(double celsiusValue) {

        this.setCelsiusValue(celsiusValue);
        return 1.8 * celsiusValue + 32;
    }
}
