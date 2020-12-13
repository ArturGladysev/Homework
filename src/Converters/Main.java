package Converters;

public class Main {
    public static void main(String[] args) {


    Converter kelvinConverter = Converter.getInstance("K");
    Converter fahrenheitConverter = Converter.getInstance("F");
    Converter celsiusConverter = Converter.getInstance("C");
 double temp = 100;
    System.out.println(celsiusConverter.convertValue(temp) + "C");
        System.out.println(fahrenheitConverter.convertValue(temp) + "F");
        System.out.println(kelvinConverter.convertValue(temp) + "K");
    }
}
