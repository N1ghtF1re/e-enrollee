package men.brakh.enrollment.controller;

import men.brakh.enrollment.controller.impl.EnrolleeConsoleCRUDController;
import men.brakh.enrollment.controller.impl.CtCertificateConsoleCRUDController;
import men.brakh.enrollment.controller.impl.EducationDocumentConsoleCRUDController;
import men.brakh.enrollment.controller.impl.UniversityApplicationConsoleCRUDController;
import men.brakh.enrollment.exception.BadRequestException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleController {
    private final Map<String, ConsoleCRUDController> availableControllers = new HashMap<String, ConsoleCRUDController>() {{
        put("enrollee", new EnrolleeConsoleCRUDController());
        put("ct-certificate", new CtCertificateConsoleCRUDController());
        put("education-document", new EducationDocumentConsoleCRUDController());
        put("application", new UniversityApplicationConsoleCRUDController());
    }};

    private Scanner scanner = new Scanner(System.in);

    private void selectMode(String enteredEntity, ConsoleCRUDController crudController) {
        System.out.println("Great. And then, what do you want to do with " + enteredEntity + "?");

        while (true) {
            System.out.println("- create\n- read\n- update\n- delete\n- search\n- back");
            String mode = scanner.nextLine();

            boolean result;

            try {
                switch (mode.toLowerCase()) {
                    case "create":
                        crudController.create();
                        result = true;
                        break;
                    case "read":
                        crudController.showList();
                        result = true;
                        break;
                    case "update":
                        crudController.update();
                        result = true;
                        break;
                    case "delete":
                        crudController.delete();
                        result = true;
                        break;
                    case "search":
                        crudController.search();
                        result = true;
                        break;
                    case "back":
                        return;
                    default:
                        result = false;
                        System.out.println("There is not mode "  + mode + ". Let's start again.");
                        break;
                }
            }  catch (BadRequestException e) {
                System.out.println("Entered incorrect data:\n" + e);
                result = false;
            } catch (NumberFormatException e) {
                System.out.println("Entered incorrect value. (Must be int)");
                result = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                result = false;
            }

            if (result) {
                System.out.println("You're awesome");
            }
        }
    }

    public void receiveMessages() {
        System.out.println("Hello! E-Enrollment welcomes you");
        while (true) {
            System.out.println("Please, select entity which you want to manipulate. Available entities: ");
            availableControllers.forEach(
                    (entityName, controller) -> System.out.println(" - " + entityName)
            );

            System.out.println("\nTo exit, enter 'exit'\n");

            final String enteredEntity = scanner.nextLine();

            if (enteredEntity.toLowerCase().equals("exit")) break;

            ConsoleCRUDController crudController = availableControllers.get(enteredEntity.toLowerCase());

            if (crudController == null) {
                System.out.println("Entered entity isn't exist. Please, try again");
                continue;
            }

            selectMode(enteredEntity, crudController);
        }

        System.out.println("See you soon!");
    }
}
