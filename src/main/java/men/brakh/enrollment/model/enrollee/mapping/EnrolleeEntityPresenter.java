package men.brakh.enrollment.model.enrollee.mapping;

import men.brakh.enrollment.mapping.presenter.EntityPresenter;
import men.brakh.enrollment.model.enrollee.Enrollee;
import men.brakh.enrollment.model.enrollee.dto.EnrolleeDto;
import org.modelmapper.ModelMapper;

import java.text.DateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class EnrolleeEntityPresenter implements EntityPresenter<Enrollee, EnrolleeDto> {
    private final ModelMapper modelMapper;

    private final DateFormat dateFormat;


    public EnrolleeEntityPresenter(final ModelMapper modelMapper,
                                     final DateFormat dateFormat) {
        this.modelMapper = modelMapper;
        this.dateFormat = dateFormat;

        modelMapper.typeMap(Enrollee.class, EnrolleeDto.class)
                .addMappings(mapping -> mapping.skip(EnrolleeDto::setBirthDate));
    }

    @Override
    public EnrolleeDto mapToDto(final Enrollee entity, final Class<? extends EnrolleeDto> dtoClass) {
        EnrolleeDto enrolleeDto = modelMapper.map(entity, EnrolleeDto.class);
        enrolleeDto.setBirthDate(dateFormat.format(entity.getBirthDate()));
        return enrolleeDto;
    }

    @Override
    public List<EnrolleeDto> mapListToDto(final List<Enrollee> entities, final Class<? extends EnrolleeDto> dtoClass) {
        return entities
                .stream()
                .map(enrollee -> mapToDto(enrollee, dtoClass))
                .collect(Collectors.toList());
    }
}
