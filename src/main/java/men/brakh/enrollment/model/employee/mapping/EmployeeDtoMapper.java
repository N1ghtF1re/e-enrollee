package men.brakh.enrollment.model.employee.mapping;

import men.brakh.enrollment.mapping.mapper.CreateDtoMapper;
import men.brakh.enrollment.mapping.mapper.DtoMapper;
import men.brakh.enrollment.mapping.mapper.UpdateDtoMapper;
import men.brakh.enrollment.model.employee.Employee;
import men.brakh.enrollment.model.employee.Role;
import men.brakh.enrollment.model.employee.dto.BaseEmployeeDto;
import men.brakh.enrollment.model.employee.dto.EmployeeDto;
import men.brakh.enrollment.model.employee.dto.EmployeeRegistrationRequest;
import men.brakh.enrollment.model.employee.dto.EmployeeUpdateReuqest;
import org.modelmapper.ModelMapper;

public class EmployeeDtoMapper implements UpdateDtoMapper<EmployeeUpdateReuqest, Employee>,
    CreateDtoMapper<EmployeeRegistrationRequest, Employee>, DtoMapper<EmployeeDto, Employee>{

  private final ModelMapper modelMapper;

  public EmployeeDtoMapper(final ModelMapper modelMapper) {
    this.modelMapper = modelMapper;

    modelMapper.typeMap(EmployeeUpdateReuqest.class, Employee.class).addMappings(mp -> {
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
  public Employee mapToEntity(final Employee entity, final EmployeeUpdateReuqest updateRequest) {
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
