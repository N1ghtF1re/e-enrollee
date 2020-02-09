package men.brakh.enrollment.domain.employee.mapping;

import men.brakh.enrollment.application.mapping.mapper.CreateDtoMapper;
import men.brakh.enrollment.application.mapping.mapper.DtoMapper;
import men.brakh.enrollment.application.mapping.mapper.UpdateDtoMapper;
import men.brakh.enrollment.domain.employee.Employee;
import men.brakh.enrollment.domain.employee.Role;
import men.brakh.enrollment.domain.employee.dto.BaseEmployeeDto;
import men.brakh.enrollment.domain.employee.dto.EmployeeDto;
import men.brakh.enrollment.domain.employee.dto.EmployeeRegistrationRequest;
import men.brakh.enrollment.domain.employee.dto.EmployeeUpdateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDtoMapper implements UpdateDtoMapper<EmployeeUpdateRequest, Employee>,
    CreateDtoMapper<EmployeeRegistrationRequest, Employee>, DtoMapper<EmployeeDto, Employee>{

  private final ModelMapper modelMapper;

  public EmployeeDtoMapper(final ModelMapper modelMapper) {
    this.modelMapper = modelMapper;

    modelMapper.typeMap(EmployeeUpdateRequest.class, Employee.class).addMappings(mp -> {
      mp.skip(Employee::setRole);
    });
    modelMapper.typeMap(EmployeeRegistrationRequest.class, Employee.class).addMappings(mp -> {
      mp.skip(Employee::setRole);
    });
    modelMapper.typeMap(EmployeeDto.class, Employee.class).addMappings(mp -> {
      mp.skip(Employee::setRole);
    });
  }

  @Override
  public Employee mapToEntity(final EmployeeDto dto) {
    final Employee employee = modelMapper.map(dto, Employee.class);
    return baseMapping(employee, dto);
  }

  @Override
  public Employee mapToEntity(final Employee entity, final EmployeeUpdateRequest updateRequest) {
    modelMapper.map(updateRequest, entity);

    return baseMapping(entity, updateRequest);
  }

  @Override
  public Employee mapToEntity(final EmployeeRegistrationRequest createRequest) {
    final Employee employee = modelMapper.map(createRequest, Employee.class);
    return baseMapping(employee, createRequest);
  }

  private Employee baseMapping(final Employee employee, final BaseEmployeeDto dto) {
    if (dto.getRole() != null) {
      employee.setRole(Role.valueOf(dto.getRole()));
    }

    return employee;
  }}
