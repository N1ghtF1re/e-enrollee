package men.brakh.enrollment.model.enrollee.mapping;

import men.brakh.enrollment.mapping.mapper.CreateDtoMapper;
import men.brakh.enrollment.mapping.mapper.DtoMapper;
import men.brakh.enrollment.mapping.mapper.UpdateDtoMapper;
import men.brakh.enrollment.model.enrollee.Enrollee;
import men.brakh.enrollment.model.enrollee.dto.EnrolleeCreateRequest;
import men.brakh.enrollment.model.enrollee.dto.EnrolleeDto;
import men.brakh.enrollment.model.enrollee.dto.EnrolleeUpdateRequest;
import men.brakh.enrollment.model.enrollee.dto.BaseEnrolleeDto;
import org.modelmapper.ModelMapper;

import java.text.DateFormat;
import java.text.ParseException;

public class EnrolleeDtoMapper implements UpdateDtoMapper<EnrolleeUpdateRequest, Enrollee>,
        CreateDtoMapper<EnrolleeCreateRequest, Enrollee>, DtoMapper<EnrolleeDto, Enrollee> {
    private final ModelMapper modelMapper;

    private final DateFormat dateFormat;

    public EnrolleeDtoMapper(final ModelMapper modelMapper,
                               final DateFormat dateFormat) {
        this.modelMapper = modelMapper;
        this.dateFormat = dateFormat;


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
        try {
            if (dto.getBirthDate() != null)
                enrollee.setBirthDate(dateFormat.parse((String) dto.getBirthDate()));
        } catch (ParseException e) {
            enrollee.setBirthDate(null);
        }
        return enrollee;
    }
}
