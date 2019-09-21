package men.brakh.abiturient.model.abiturient.mapping;

import men.brakh.abiturient.mapping.presenter.EntityPresenter;
import men.brakh.abiturient.model.abiturient.Abiturient;
import men.brakh.abiturient.model.abiturient.dto.AbiturientDto;
import org.modelmapper.ModelMapper;

import java.text.DateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class AbiturientEntityPresenter implements EntityPresenter<Abiturient, AbiturientDto> {
    private final ModelMapper modelMapper;

    private final DateFormat dateFormat;


    public AbiturientEntityPresenter(final ModelMapper modelMapper,
                                     final DateFormat dateFormat) {
        this.modelMapper = modelMapper;
        this.dateFormat = dateFormat;

        modelMapper.typeMap(Abiturient.class, AbiturientDto.class)
                .addMappings(mapping -> mapping.skip(AbiturientDto::setBirthDate));
    }

    @Override
    public AbiturientDto mapToDto(final Abiturient entity, final Class<? extends AbiturientDto> dtoClass) {
        AbiturientDto abiturientDto = modelMapper.map(entity, AbiturientDto.class);
        abiturientDto.setBirthDate(dateFormat.format(entity.getBirthDate()));
        return abiturientDto;
    }

    @Override
    public List<AbiturientDto> mapListToDto(final List<Abiturient> entities, final Class<? extends AbiturientDto> dtoClass) {
        return entities
                .stream()
                .map(abiturient -> mapToDto(abiturient, dtoClass))
                .collect(Collectors.toList());
    }
}
