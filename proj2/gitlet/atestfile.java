package gitlet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.Locale;
public class atestfile {
    public static void main(String[] args) {

        Date now = new Date();

        // Define the desired format
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.ENGLISH);

        // Convert the date to a formatted string
        String formattedDate = formatter.format(now);

        // Prepend "Date: " to the formatted string
        String message = "Date: " + formattedDate;

        String s = String.format("Date: " + "%1$ta %1$tb %1$te %1$tT %1$tY %1$tz", now);
        // Print the result
        System.out.println(message);
        System.out.println(s);
    }
}

