package structure;

public enum Language {
	FR("FR", "FRENCH"), EN("EN", "ENGLISH");

    private final String key;
    private final String value;

    Language(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    
    public String getValue() {
        return value;
    }
    
    public String getLowerCaseValue() {
        return (value.substring(0, 1) + value.substring(1, value.length()).toLowerCase());
    }
}
