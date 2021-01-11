package Terminal;

public class PinValidator {                 // Класс для валидации пин кода
private boolean overflow = false;
    private boolean valid = false;
   private int count_success = 0;
   private int count_failure = 0;

  public boolean isOverflow () {
      if (overflow==false) {
          return overflow;
      } else {
          count_failure = 0;
          overflow = false;
          return true;
      }
  }

   public boolean isvalid() {
       return valid;
   }

    public void validation(final String key, Character simbol) {
        System.out.println("xx" + count_failure);
        if (!Character.isDigit(simbol)) {
            throw new IllegalArgumentException("The character is not a number, please enter a number");
        }
      if (!simbol.equals(key.charAt(count_success))) {
             count_success = 0;
             ++count_failure;
       if (count_failure == 3) {
    overflow = true;
           System.out.println("!!11");
       }
         if  (overflow == false) {throw new IllegalArgumentException("wrong number");}
        }
        else if (simbol.equals(key.charAt(count_success))) {
            ++count_success;
            if (count_success == key.length()) {
                valid = true;
             return;
            }
        }
    }
}