package Converters;

public class KelvinConverter extends Converter
{
    @Override
    public double convertValue(double celsiusValue)
    {
        setCelsiusValue(celsiusValue);
        return celsiusValue + 273.15;
    }
}

