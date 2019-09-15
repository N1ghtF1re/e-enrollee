package men.brakh.abiturient.model.specialty;

import men.brakh.abiturient.model.ctCertificate.Subject;
import men.brakh.abiturient.model.faculty.Faculty;

import java.util.Arrays;
import java.util.List;


public enum Specialty  {
    POIT("POIT", Faculty.FKSIS, Arrays.asList(Subject.PHYSICS, Subject.BELORUSSIAN_LANG, Subject.RUSSIAN_LANG, Subject.MATH)),
    IPOIT("IPOIT", Faculty.FCP, Arrays.asList(Subject.PHYSICS, Subject.BELORUSSIAN_LANG, Subject.RUSSIAN_LANG, Subject.MATH)),
    EEB("EEB", Faculty.IEF, Arrays.asList(Subject.ENGLISH_LANG, Subject.BELORUSSIAN_LANG, Subject.RUSSIAN_LANG, Subject.MATH));

    private String name;
    private Faculty faculty;
    private List<Subject> allowedSubjects;

    Specialty(final String name, final Faculty faculty, final List<Subject> allowedSubjects) {
        this.name = name;
        this.faculty = faculty;
        this.allowedSubjects = allowedSubjects;
    }

    public String getName() {
        return name;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public List<Subject> getAllowedSubjects() {
        return allowedSubjects;
    }
}