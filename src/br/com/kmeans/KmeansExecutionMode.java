package br.com.kmeans;

public enum KmeansExecutionMode {
    SERIAL("SERIAL"),
    CONCURRENT("CONCURRENT");

    private String mode;
    KmeansExecutionMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString(){
        return mode;
    }
}
