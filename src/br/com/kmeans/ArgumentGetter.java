package br.com.kmeans;

public abstract class ArgumentGetter {

    public static String getArgumentValue(String[] args, String argumentName) {
        for (String arg : args) {
            String[] strings = arg.split("=");
            if (strings.length == 1 && arg.equals(argumentName)) {
                return argumentName;

            } else if (strings.length == 2 && strings[0].equals(argumentName)) {
                return strings[1];
            }
        }
        return null;
    }
}
