package men.brakh.enrollment.model.employee.mapping;

import men.brakh.enrollment.mapping.presenter.EntityPresenter;
import men.brakh.enrollment.model.employee.Employee;
import men.brakh.enrollment.model.employee.dto.EmployeeDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDtoPresenter implements EntityPresenter<Employee, EmployeeDto> {
  private final ModelMapper modelMapper;

  public EmployeeDtoPresenter(final ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Override
  public EmployeeDto mapToDto(final Employee entity, final Class<? extends EmployeeDto> dtoClass) {
    return modelMapper.map(entity, dtoClass);
  }

  @Override
  public List<EmployeeDto> mapListToDto(final List<Employee> entities, final Class<? extends EmployeeDto> dtoClass) {
    return entities
        .stream()
        .map(enrollee -> mapToDto(enrollee, dtoClass))
        .collect(Collectors.toList());
  }
}
