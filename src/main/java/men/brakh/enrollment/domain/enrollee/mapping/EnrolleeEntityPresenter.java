package men.brakh.enrollment.domain.enrollee.mapping;

import java.util.List;
import java.util.stream.Collectors;
import men.brakh.enrollment.domain.enrollee.Enrollee;
import men.brakh.enrollment.domain.enrollee.dto.EnrolleeDto;
import men.brakh.enrollment.application.mapping.presenter.EntityPresenter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EnrolleeEntityPresenter implements EntityPresenter<Enrollee, EnrolleeDto> {
    private final ModelMapper modelMapper;


    public EnrolleeEntityPresenter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.typeMap(Enrollee.class, EnrolleeDto.class)
                .addMappings(mapping -> mapping.skip(EnrolleeDto::setBirthDate));
    }

    @Override
    public EnrolleeDto mapToDto(final Enrollee entity, final Class<? extends EnrolleeDto> dtoClass) {
        EnrolleeDto enrolleeDto = modelMapper.map(entity, dtoClass);
        enrolleeDto.setBirthDate(entity.getBirthDate());
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
