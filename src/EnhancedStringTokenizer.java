import java.util.StringTokenizer;

/**
 * EnhancedStringTokenizer
 */
public class EnhancedStringTokenizer extends StringTokenizer {
    private String[] tokens;
    private int count;

    public EnhancedStringTokenizer(String line) {
        super(line);
        tokens = new String[countTokens()];
        count = 0;
    }

    public EnhancedStringTokenizer(String line, String delimeter) {
        super(line, delimeter);
        tokens = new String[countTokens()];
        count = 0;
    }

    @Override
    public String nextToken() {
        String token = super.nextToken();
        tokens[count] = token;
        count++;
        return token;
    }

    @Override
    public String nextToken(String delimeter) {
        String token = super.nextToken(delimeter);
        tokens[count] = token;
        count++;
        return token;
    }

    public String[] tokensSoFar() {
        String[] arrayToReturn = new String[count];

        for (int i = 0; i < count; i++) {
            arrayToReturn[i] = tokens[i];
        }

        return arrayToReturn;
    }
}
