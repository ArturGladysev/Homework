package Terminal;

public class AccountIsLockedException extends RuntimeException
{
		public AccountIsLockedException(String message)
		{
				super(message);
		}
}
