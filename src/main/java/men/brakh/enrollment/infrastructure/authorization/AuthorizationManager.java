package men.brakh.enrollment.infrastructure.authorization;

import java.util.HashMap;
import java.util.Map;

public class AuthorizationManager {
  private Map<String, Integer> employeesSessions = new HashMap<>();
  private ThreadLocal<Integer> authorizedEmployee = new ThreadLocal<>();

  public void addSession(final String jsessionId, final Integer employeeId) {
    employeesSessions.put(jsessionId, employeeId);
  }

  public Integer getSession(final String jsessionId) {
    return employeesSessions.getOrDefault(jsessionId, null);
  }

  public void authorize(final Integer id) {
    authorizedEmployee.set(id);
  }

  public void unAuthorize() {
    authorizedEmployee.remove();
  }

  public Integer getCurrentEmployeeId() {
    return authorizedEmployee.get();
  }

  public boolean isAuthorized() {
    return authorizedEmployee.get() != null;
  }

}
