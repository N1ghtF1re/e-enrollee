package men.brakh.abiturient.model.abiturient.mapping;

import men.brakh.abiturient.mapping.mapper.CreateDtoMapper;
import men.brakh.abiturient.mapping.mapper.DtoMapper;
import men.brakh.abiturient.mapping.mapper.UpdateDtoMapper;
import men.brakh.abiturient.model.abiturient.Abiturient;
import men.brakh.abiturient.model.abiturient.dto.AbiturientCreateRequest;
import men.brakh.abiturient.model.abiturient.dto.AbiturientDto;
import men.brakh.abiturient.model.abiturient.dto.AbiturientUpdateRequest;
import men.brakh.abiturient.model.abiturient.dto.BaseAbiturientDto;
import org.modelmapper.ModelMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AbiturientDtoMapper implements UpdateDtoMapper<AbiturientUpdateRequest, Abiturient>,
        CreateDtoMapper<AbiturientCreateRequest, Abiturient>, DtoMapper<AbiturientDto, Abiturient> {
    private final ModelMapper modelMapper;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public AbiturientDtoMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;


        modelMapper.typeMap(AbiturientUpdateRequest.class, Abiturient.class).addMappings(mp -> {
            mp.skip(Abiturient::setBirthDate);
        });
        modelMapper.typeMap(AbiturientCreateRequest.class, Abiturient.class).addMappings(mp -> {
            mp.skip(Abiturient::setBirthDate);
        });
        modelMapper.typeMap(AbiturientDto.class, Abiturient.class).addMappings(mp -> {
            mp.skip(Abiturient::setBirthDate);
        });
    }



    @Override
    public Abiturient mapToEntity(final AbiturientCreateRequest createRequest) {
        final Abiturient abiturient = modelMapper.map(createRequest, Abiturient.class);
        return baseMapping(abiturient, createRequest);
    }

    @Override
    public Abiturient mapToEntity(final AbiturientDto dto) {
        final Abiturient abiturient = modelMapper.map(dto, Abiturient.class);
        return baseMapping(abiturient, dto);
    }

    @Override
    public Abiturient mapToEntity(final Abiturient entity, final AbiturientUpdateRequest updateRequest) {
        modelMapper.map(updateRequest, entity);
        return baseMapping(entity, updateRequest);
    }

    private Abiturient baseMapping(final Abiturient abiturient, final BaseAbiturientDto dto) {
        try {
            abiturient.setBirthDate(dateFormat.parse((String) dto.getBirthDate()));
        } catch (ParseException e) {
            abiturient.setBirthDate(null);
        }
        return abiturient;
    }
}
