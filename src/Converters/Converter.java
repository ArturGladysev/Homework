package Converters;

public abstract class Converter
{
		private double celsiusValue;

		public double getCelsiusValue()
		{
				return celsiusValue;
		}

		//Сеттер сделан защищенным, так как недопустимо менять значение темепратуры по Цельсию вне потомков класса и данного пакета
		protected void setCelsiusValue(double celsiusValue)
		{
				this.celsiusValue = celsiusValue;
		}

		public abstract double convertValue(double value);

		public static Converter getInstance(char article)
		{
				article = Character.toUpperCase(article);
				switch(article)
				{
						case 'F':
								return new FahrenheitConverter();
						case 'K':
								return new KelvinConverter();
						default:
								throw new IllegalArgumentException("Wrong article");
				}
		}
}