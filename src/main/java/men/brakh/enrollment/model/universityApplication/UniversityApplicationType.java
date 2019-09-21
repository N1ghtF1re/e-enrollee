package men.brakh.enrollment.model.universityApplication;

public enum UniversityApplicationType {
    FREE_EDUCATIONS_APPLICATION("Application for education at the expense of the state budget"),
    PAID_EDUCATIONS_APPLICATION("Paid education's application");

    UniversityApplicationType(final String description) {
        this.description = description;
    }

    private String description;

    public String getDescription() {
        return description;
    }
}
