package men.brakh.enrollment.controller;

import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.model.Dto;
import men.brakh.enrollment.service.EntityReadService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SearchController<T extends Dto> {
    private final SearchHelper<T> searchHelper = new SearchHelper<>();
    private final EntityReadService<T, ?> service;
    private Scanner scanner = new Scanner(System.in);

    private final Class<T> dtoClass;

    public SearchController(final EntityReadService<T, ?> service, final Class<T> dtoClass) {
        this.service = service;
        this.dtoClass = dtoClass;
    }

    public void search() throws BadRequestException {
        final List<T> fullList = service.getAll();

        if (fullList.size() == 0) {
            System.out.println("Three is no data to search :)");
            return;
        }

        System.out.println("Please, enter filter's request in format");
        System.out.println("field1 = value1, field2 = values");
        System.out.println("\n\nAvailable fields: ");
        System.out.println(String.join(", ", searchHelper.availableFields(fullList)));
        final String request = scanner.nextLine().replaceAll(" ", "");
        final String[] maps = request.split(",");
        final Map<String, String> filter = new HashMap<>();

        for (String map : maps) {
            final String[] keyAndValue = map.split("=");
            if (keyAndValue.length != 2) {
                throw new BadRequestException("Invalid request");
            }
            filter.put(keyAndValue[0], keyAndValue[1]);
        }

        if (filter.size() == 0) {
            throw new BadRequestException("Filter is empty");
        }

        System.out.println("Enter field which which will be sort key");
        final String sortKey = scanner.nextLine();


        try {
            List<T> filteredData = searchHelper.search(fullList, filter, dtoClass, sortKey);
            System.out.println("\n\nResuult: \n");

            if (filteredData.size() == 0) {
                System.out.println("Nothing was found :C");
            } else {
                filteredData.forEach(System.out::println);
            }
        } catch (Exception e) {
            throw new BadRequestException("Invalid request");
        }
    }
}
