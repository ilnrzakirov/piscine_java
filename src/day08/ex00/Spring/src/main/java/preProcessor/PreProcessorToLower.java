package preProcessor;

import java.util.Locale;

public class PreProcessorToLower implements PreProcessor{
    @Override
    public String preProcess(String str) {
        return str.toLowerCase();
    }
}
