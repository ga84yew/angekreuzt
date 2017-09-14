import java.util.ArrayList;
import java.util.Arrays;

public class Wahlthema {

public static String auswaehlen (String wahlsystem) {

String result = "";

ArrayList<String> wahlen = new ArrayList<String>();

wahlen.addAll(Arrays.asList("waehlen", "wahlprozess", "wahlablauf"));

if (wahlen.contains(wahlsystem.toLowerCase())) {
	result = Wahlsystem.getText();

}

return result;

}

}
