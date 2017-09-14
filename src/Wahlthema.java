import java.util.ArrayList;
import java.util.Arrays;

public class Wahlthema {

public static String auswaehlen (String wahlsystem) {

String result = "";

ArrayList<String> wahlen = new ArrayList<String>();

wahlen.addAll(Arrays.asList("Waehlen", "Wahlprozess", "Wahlablauf"));

if (wahlen.contains(wahlsystem)) {
	result = Wahlsystem.getText();
}

return result;

}

}
