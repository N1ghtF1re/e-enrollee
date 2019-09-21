package men.brakh.enrollment.model.enrollee.mapping;

import men.brakh.enrollment.model.enrollee.Enrollee;
import men.brakh.enrollment.model.enrollee.dto.EnrolleeDto;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class EnrolleeEntityPresenterTest {

    private EnrolleeEntityPresenter entityPresenter;

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


    @Test
    public void mapToDto() throws ParseException {
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

        assertEquals(enrolleeDto, entityPresenter.mapToDto(enrollee, EnrolleeDto.class));
    }
}