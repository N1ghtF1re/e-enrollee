package men.brakh.enrollment.model.universityApplication;

public enum EducationType {
    FREE_EDUCATION("Education at the expense of the state budget"),
    PAID_EDUCATION("Paid education");

    EducationType(final String description) {
        this.description = description;
    }

    private String description;

    public String getDescription() {
        return description;
    }
}
