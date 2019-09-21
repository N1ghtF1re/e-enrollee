package men.brakh.abiturient.model.abiturient.mapping;

import men.brakh.abiturient.model.abiturient.Abiturient;
import men.brakh.abiturient.model.abiturient.dto.AbiturientCreateRequest;
import men.brakh.abiturient.model.abiturient.dto.AbiturientDto;
import men.brakh.abiturient.model.abiturient.dto.AbiturientUpdateRequest;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class AbiturientDtoMapperTest {
    AbiturientDtoMapper dtoMapper;

    @Before
    public void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper
                .getConfiguration()
                .setAmbiguityIgnored(true)
                .setPropertyCondition(Conditions.isNotNull());

        dtoMapper = new AbiturientDtoMapper(modelMapper, simpleDateFormat);
    }

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Test
    public void mapToEntity() throws ParseException {
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

        assertEquals(abiturient, dtoMapper.mapToEntity(abiturientDto));
    }

    @Test
    public void mapToEntity1() throws ParseException {
        Abiturient abiturient = Abiturient.builder()
                .birthDate(simpleDateFormat.parse("19.08.2000"))
                .firstName("Alexander")
                .lastName("Pankratiev")
                .middleName("Sergeevich")
                .build();

        AbiturientCreateRequest abiturientDto = AbiturientCreateRequest.builder()
                .birthDate("19.08.2000")
                .firstName("Alexander")
                .lastName("Pankratiev")
                .middleName("Sergeevich")
                .build();

        assertEquals(abiturient, dtoMapper.mapToEntity(abiturientDto));
    }

    @Test
    public void mapToEntity2() throws ParseException {

        Abiturient abiturient = Abiturient.builder()
                .id(0)
                .birthDate(simpleDateFormat.parse("20.08.2000"))
                .firstName("Alexander")
                .lastName("Pankratiev")
                .middleName("Sergeevich")
                .build();

        AbiturientUpdateRequest abiturientDto = AbiturientUpdateRequest.builder()
                .birthDate("19.08.2000")
                .build();

        Abiturient expected = Abiturient.builder()
                .id(0)
                .birthDate(simpleDateFormat.parse("19.08.2000"))
                .firstName("Alexander")
                .lastName("Pankratiev")
                .middleName("Sergeevich")
                .build();
        assertEquals(expected, dtoMapper.mapToEntity(abiturient, abiturientDto));

    }
}