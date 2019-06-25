package info.sasinski.entity.enumeration;


public enum Position {
    DEALER("D"), MECHANIC("M"), MANAGER("G");

    String trueName;

    private Position(String trueName) {
        this.trueName = trueName;
    }

    public String getTrueName() {
        return trueName;
    }

}
