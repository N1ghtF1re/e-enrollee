package men.brakh.abiturient.model.ctCertificate.mapping;

import men.brakh.abiturient.mapping.presenter.EntityPresenter;
import men.brakh.abiturient.model.ctCertificate.CtCertificate;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CtCertificateEntityPresenter implements EntityPresenter<CtCertificate, CtCertificateDto> {
    private final ModelMapper modelMapper;

    public CtCertificateEntityPresenter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CtCertificateDto mapToDto(CtCertificate entity, Class<? extends CtCertificateDto> dtoClass) {
        CtCertificateDto ctCertificate = modelMapper.map(entity, dtoClass);

        if (entity.getAbiturient() != null) {
            ctCertificate.setAbiturientId(entity.getAbiturient().getId());
            ctCertificate.setAbiturientName(entity.getAbiturient().getFullName());
        }

        return ctCertificate;
    }

    @Override
    public List<CtCertificateDto> mapListToDto(List<CtCertificate> entities, Class<? extends CtCertificateDto> dtoClass) {
        return entities
                .stream()
                .map(ctCertificate -> mapToDto(ctCertificate, dtoClass))
                .collect(Collectors.toList());

    }
}
