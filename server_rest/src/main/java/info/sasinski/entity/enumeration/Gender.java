package info.sasinski.entity.enumeration;

public enum Gender {
    W("Female"), M("Male");


    String trueName;

    private Gender(String trueName) {
        this.trueName = trueName;
    }

    public String getTrueName() {
        return trueName;
    }


}
