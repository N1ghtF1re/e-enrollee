package men.brakh.enrollment.domain.interimLists.service;

import men.brakh.enrollment.domain.ctCertificate.CtCertificate;
import men.brakh.enrollment.domain.ctCertificate.Subject;
import men.brakh.enrollment.domain.educationDocument.EducationDocument;
import men.brakh.enrollment.domain.enrollee.Enrollee;
import men.brakh.enrollment.domain.enrollee.mapping.EnrolleeEntityPresenter;
import men.brakh.enrollment.domain.interimLists.dto.InterimListsDto;
import men.brakh.enrollment.domain.specialty.Specialty;
import men.brakh.enrollment.domain.universityApplication.UniversityApplication;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static men.brakh.enrollment.domain.universityApplication.EducationType.FREE_EDUCATION;
import static org.junit.Assert.assertEquals;

public class InterimListsServiceImplTest {

    private EnrolleeEntityPresenter entityPresenter;

    private static int ADDITIONAL = 10;

    @Before
    public void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper
                .getConfiguration()
                .setAmbiguityIgnored(true)
                .setPropertyCondition(Conditions.isNotNull());

        entityPresenter = new EnrolleeEntityPresenter(modelMapper, simpleDateFormat);
    }

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private List<UniversityApplication> generateFirstList() {
        int count = Specialty.EEB.getLimits().get(FREE_EDUCATION) + ADDITIONAL;

        List<UniversityApplication> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(UniversityApplication.builder()
                .specialties(Arrays.asList(Specialty.EEB, Specialty.IPOIT))
                .type(FREE_EDUCATION)
                .certificates(
                        Arrays.asList(CtCertificate.builder()
                                .ctPoints(count - i)
                                .subject(Subject.MATH)
                                .build())
                )
                .enrollee(
                        Enrollee.builder()
                                .id(i)
                                .birthDate(new Date())

                                .build()
                )
                .educationDocument(EducationDocument.builder().averageGrade(0.0).build())
                .build()
            );
        }

        return list;
    }

    @Test
    public void getList() {
        InterimListsServiceImpl interimListsService = new InterimListsServiceImpl(entityPresenter, null);
        InterimListsDto interimListsDto = interimListsService.getList(
             generateFirstList()
        );

        assertEquals(Specialty.EEB.getLimits().get(FREE_EDUCATION).intValue(),
                interimListsDto.getSpecialityInterimLists()
                        .stream()
                        .filter(specialityInterimLists ->
                                specialityInterimLists.getSpeciality().equals(Specialty.EEB.getName())
                                && specialityInterimLists.getApplicationType().equals(FREE_EDUCATION))
                        .findFirst().orElseThrow(RuntimeException::new).getEnrollees().size());

        assertEquals(Specialty.EEB.getLimits().get(FREE_EDUCATION) + ADDITIONAL,
                interimListsDto.getSpecialityInterimLists()
                        .stream()
                        .filter(specialityInterimLists ->
                                specialityInterimLists.getSpeciality().equals(Specialty.EEB.getName())
                                        && specialityInterimLists.getApplicationType().equals(FREE_EDUCATION))
                        .findFirst().orElseThrow(RuntimeException::new).getPassingScore().intValue());

        assertEquals(10,
                interimListsDto.getSpecialityInterimLists()
                        .stream()
                        .filter(specialityInterimLists ->
                                specialityInterimLists.getSpeciality().equals(Specialty.IPOIT.getName())
                                        && specialityInterimLists.getApplicationType().equals(FREE_EDUCATION))
                        .findFirst().orElseThrow(RuntimeException::new).getEnrollees().size());

        assertEquals(0,
                interimListsDto.getSpecialityInterimLists()
                        .stream()
                        .filter(specialityInterimLists ->
                                specialityInterimLists.getSpeciality().equals(Specialty.IPOIT.getName())
                                        && specialityInterimLists.getApplicationType().equals(FREE_EDUCATION))
                        .findFirst().orElseThrow(RuntimeException::new).getPassingScore().intValue());

    }
}