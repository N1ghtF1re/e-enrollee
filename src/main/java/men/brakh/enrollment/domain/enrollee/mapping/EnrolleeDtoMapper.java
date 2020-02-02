package men.brakh.enrollment.domain.enrollee.mapping;

import men.brakh.enrollment.domain.enrollee.Enrollee;
import men.brakh.enrollment.domain.enrollee.dto.BaseEnrolleeDto;
import men.brakh.enrollment.domain.enrollee.dto.EnrolleeCreateRequest;
import men.brakh.enrollment.domain.enrollee.dto.EnrolleeDto;
import men.brakh.enrollment.domain.enrollee.dto.EnrolleeUpdateRequest;
import men.brakh.enrollment.application.mapping.mapper.CreateDtoMapper;
import men.brakh.enrollment.application.mapping.mapper.DtoMapper;
import men.brakh.enrollment.application.mapping.mapper.UpdateDtoMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EnrolleeDtoMapper implements UpdateDtoMapper<EnrolleeUpdateRequest, Enrollee>,
        CreateDtoMapper<EnrolleeCreateRequest, Enrollee>, DtoMapper<EnrolleeDto, Enrollee> {
    private final ModelMapper modelMapper;


    public EnrolleeDtoMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;


        modelMapper.typeMap(EnrolleeUpdateRequest.class, Enrollee.class).addMappings(mp -> {
            mp.skip(Enrollee::setBirthDate);
        });
        modelMapper.typeMap(EnrolleeCreateRequest.class, Enrollee.class).addMappings(mp -> {
            mp.skip(Enrollee::setBirthDate);
        });
        modelMapper.typeMap(EnrolleeDto.class, Enrollee.class).addMappings(mp -> {
            mp.skip(Enrollee::setBirthDate);
        });
    }



    @Override
    public Enrollee mapToEntity(final EnrolleeCreateRequest createRequest) {
        final Enrollee enrollee = modelMapper.map(createRequest, Enrollee.class);
        return baseMapping(enrollee, createRequest);
    }

    @Override
    public Enrollee mapToEntity(final EnrolleeDto dto) {
        final Enrollee enrollee = modelMapper.map(dto, Enrollee.class);
        return baseMapping(enrollee, dto);
    }

    @Override
    public Enrollee mapToEntity(final Enrollee entity, final EnrolleeUpdateRequest updateRequest) {
        modelMapper.map(updateRequest, entity);
        return baseMapping(entity, updateRequest);
    }

    private Enrollee baseMapping(final Enrollee enrollee, final BaseEnrolleeDto dto) {

        return enrollee;
    }
}
