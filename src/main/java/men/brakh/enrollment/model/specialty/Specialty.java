package men.brakh.enrollment.model.specialty;

import men.brakh.enrollment.model.ctCertificate.Subject;
import men.brakh.enrollment.model.faculty.Faculty;
import men.brakh.enrollment.model.universityApplication.EducationType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public enum Specialty  {
    POIT("POIT", Faculty.FKSIS,
            Arrays.asList(Subject.PHYSICS, Subject.BELORUSSIAN_LANG, Subject.RUSSIAN_LANG, Subject.MATH),
            new HashMap<EducationType, Integer>(){{
                put(EducationType.FREE_EDUCATION, 50);
                put(EducationType.PAID_EDUCATION, 300);

            }}),
    IPOIT("IPOIT", Faculty.FCP,
            Arrays.asList(Subject.PHYSICS, Subject.BELORUSSIAN_LANG, Subject.RUSSIAN_LANG, Subject.MATH),
            new HashMap<EducationType, Integer>(){{
                put(EducationType.FREE_EDUCATION, 20);
                put(EducationType.PAID_EDUCATION, 40);

            }}),
    EEB("EEB", Faculty.IEF,
            Arrays.asList(Subject.ENGLISH_LANG, Subject.BELORUSSIAN_LANG, Subject.RUSSIAN_LANG, Subject.MATH),
            new HashMap<EducationType, Integer>(){{
                put(EducationType.FREE_EDUCATION, 10);
                put(EducationType.PAID_EDUCATION, 30);

            }});

    private final String name;
    private final Faculty faculty;
    private final List<Subject> allowedSubjects;
    private final Map<EducationType, Integer> limits;


    Specialty(final String name,
              final Faculty faculty,
              final List<Subject> allowedSubjects,
              final Map<EducationType, Integer> limits) {
        this.name = name;
        this.faculty = faculty;
        this.allowedSubjects = allowedSubjects;
        this.limits = limits;
    }

    public Map<EducationType, Integer> getLimits() {
        return limits;
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