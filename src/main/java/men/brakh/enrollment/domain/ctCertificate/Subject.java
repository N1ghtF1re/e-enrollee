package men.brakh.enrollment.domain.ctCertificate;


import java.util.Arrays;
import java.util.Optional;

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

    public static Subject fromSubjectName(String name) {
        Optional<Subject> foundSubject = Arrays.stream(Subject.values())
                .filter(subject -> subject.getSubjectName().equals(name))
                .findFirst();

        return foundSubject.orElseThrow(() -> new IllegalArgumentException("There is no subject with name " + name));
    }
}
