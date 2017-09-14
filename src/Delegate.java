import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
public class Delegate {

	public static String readUrl(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}
	public static String lowercase(String text){
		char[] chars = text.toCharArray();
		char[] out = new char[chars.length]; 
		int o=0;
	    for (int i = 0; i < chars.length; i++)
	    {
	        char c = chars[i];
	        
	        if (Character.isUpperCase(c))
	        {
	        	c= Character.toLowerCase(c);
	        }
	        /*
	        else if (Character.isLowerCase(c))
	        {
	            chars[i] = Character.toUpperCase(c);
	        }
	        */
	        if (Character.isAlphabetic(c)){
	        	 out[o]=c;
	        	 o++;		 
	        }
	        
	    }
	    String s =new String(out);
	    return s.trim();
	}
}
