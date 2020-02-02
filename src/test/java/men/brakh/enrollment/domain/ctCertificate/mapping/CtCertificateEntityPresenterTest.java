package men.brakh.enrollment.domain.ctCertificate.mapping;

import men.brakh.enrollment.domain.enrollee.Enrollee;
import men.brakh.enrollment.domain.ctCertificate.CtCertificate;
import men.brakh.enrollment.domain.ctCertificate.Subject;
import men.brakh.enrollment.domain.ctCertificate.dto.CtCertificateDto;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;

public class CtCertificateEntityPresenterTest {

    private CtCertificateEntityPresenter ctCertificateEntityPresenter;

    @Before
    public void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper
                .getConfiguration()
                .setAmbiguityIgnored(true)
                .setPropertyCondition(Conditions.isNotNull());

        ctCertificateEntityPresenter = new CtCertificateEntityPresenter(modelMapper);
    }


    @Test
    public void mapToDto() {
        Enrollee enrollee = Enrollee.builder()
                .id(1)
                .firstName("Alexander")
                .lastName("Pankratiev")
                .middleName("Sergeevich")
                .build();

        CtCertificate ctCertificate = CtCertificate.builder()
                .certificateIdentifier("111-22-3")
                .enrollee(enrollee)
                .id(1)
                .certificateNumber("1111111")
                .ctPoints(51)
                .subject(Subject.HISTORY)
                .build();

        CtCertificateDto ctCertificateDto = CtCertificateDto.builder()
                .certificateId("111-22-3")
                .id(1)
                .certificateNumber("1111111")
                .enrolleeName("Alexander Sergeevich Pankratiev")
                .enrolleeId(1)
                .ctPoints(51)
                .subject("History")
                .build();

        CtCertificateDto mappedDto = ctCertificateEntityPresenter.mapToDto(ctCertificate, CtCertificateDto.class);

        assertEquals(ctCertificateDto, mappedDto);
        assertEquals(ctCertificateDto.toString(),  mappedDto.toString());

    }
}