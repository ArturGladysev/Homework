package Converters;

public interface Converter {
    public double convertValue(double value);

    public static Converter getInstance(String artikul) {
        if (artikul.equals("F")) {
            return new FahrenheitConverter();
        } else if (artikul.equals("K")) {
            return new KelvinConverter();
        } else if (artikul.equals("C")) {
            return new CelsiusConverter();
        }
        return null;
    }
}