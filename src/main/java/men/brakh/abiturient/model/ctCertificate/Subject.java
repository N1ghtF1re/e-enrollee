package men.brakh.abiturient.model.ctCertificate;


public enum Subject {
    HISTORY("History"),
    MATH("Math"),
    PHYSICS("Physics"),
    RUSSIAN_LANG("Russian language"),
    BELORUSSIAN_LANG("Belorussian language"),
    ENGLISH_LANG("English language"),
    CHEMISTRY("Chemistry");

    private final String subjectName;

    Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }
}
