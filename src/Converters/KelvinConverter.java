package Converters;

    public class KelvinConverter implements Converter {

        @Override
        public double convertValue(double value) {
            return value + 273.15;
        }
    }

