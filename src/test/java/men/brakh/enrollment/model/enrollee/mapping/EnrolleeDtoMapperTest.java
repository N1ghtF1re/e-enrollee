package men.brakh.enrollment.model.enrollee.mapping;

import men.brakh.enrollment.model.enrollee.Enrollee;
import men.brakh.enrollment.model.enrollee.dto.EnrolleeCreateRequest;
import men.brakh.enrollment.model.enrollee.dto.EnrolleeDto;
import men.brakh.enrollment.model.enrollee.dto.EnrolleeUpdateRequest;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class EnrolleeDtoMapperTest {
    EnrolleeDtoMapper dtoMapper;

    @Before
    public void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper
                .getConfiguration()
                .setAmbiguityIgnored(true)
                .setPropertyCondition(Conditions.isNotNull());

        dtoMapper = new EnrolleeDtoMapper(modelMapper, simpleDateFormat);
    }

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Test
    public void mapToEntity() throws ParseException {
        Enrollee enrollee = Enrollee.builder()
                .birthDate(simpleDateFormat.parse("19.08.2000"))
                .firstName("Alexander")
                .lastName("Pankratiev")
                .middleName("Sergeevich")
                .build();

        EnrolleeDto enrolleeDto = EnrolleeDto.builder()
                .birthDate("19.08.2000")
                .firstName("Alexander")
                .lastName("Pankratiev")
                .middleName("Sergeevich")
                .build();

        assertEquals(enrollee, dtoMapper.mapToEntity(enrolleeDto));
    }

    @Test
    public void mapToEntity1() throws ParseException {
        Enrollee enrollee = Enrollee.builder()
                .birthDate(simpleDateFormat.parse("19.08.2000"))
                .firstName("Alexander")
                .lastName("Pankratiev")
                .middleName("Sergeevich")
                .build();

        EnrolleeCreateRequest enrolleeDto = EnrolleeCreateRequest.builder()
                .birthDate("19.08.2000")
                .firstName("Alexander")
                .lastName("Pankratiev")
                .middleName("Sergeevich")
                .build();

        assertEquals(enrollee, dtoMapper.mapToEntity(enrolleeDto));
    }

    @Test
    public void mapToEntity2() throws ParseException {

        Enrollee enrollee = Enrollee.builder()
                .id(0)
                .birthDate(simpleDateFormat.parse("20.08.2000"))
                .firstName("Alexander")
                .lastName("Pankratiev")
                .middleName("Sergeevich")
                .build();

        EnrolleeUpdateRequest enrolleeDto = EnrolleeUpdateRequest.builder()
                .birthDate("19.08.2000")
                .build();

        Enrollee expected = Enrollee.builder()
                .id(0)
                .birthDate(simpleDateFormat.parse("19.08.2000"))
                .firstName("Alexander")
                .lastName("Pankratiev")
                .middleName("Sergeevich")
                .build();
        assertEquals(expected, dtoMapper.mapToEntity(enrollee, enrolleeDto));

    }
}