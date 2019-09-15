package men.brakh.abiturient.model.abiturient.mapping;

import men.brakh.abiturient.model.abiturient.Abiturient;
import men.brakh.abiturient.model.abiturient.dto.AbiturientDto;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class AbiturientEntityPresenterTest {

    private AbiturientEntityPresenter entityPresenter;

    @Before
    public void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper
                .getConfiguration()
                .setAmbiguityIgnored(true)
                .setPropertyCondition(Conditions.isNotNull());

        entityPresenter = new AbiturientEntityPresenter(modelMapper);
    }

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");


    @Test
    public void mapToDto() throws ParseException {
        Abiturient abiturient = Abiturient.builder()
                .birthDate(simpleDateFormat.parse("19.08.2000"))
                .firstName("Alexander")
                .lastName("Pankratiev")
                .middleName("Sergeevich")
                .build();

        AbiturientDto abiturientDto = AbiturientDto.builder()
                .birthDate("19.08.2000")
                .firstName("Alexander")
                .lastName("Pankratiev")
                .middleName("Sergeevich")
                .build();

        assertEquals(abiturientDto, entityPresenter.mapToDto(abiturient, AbiturientDto.class));
    }
}