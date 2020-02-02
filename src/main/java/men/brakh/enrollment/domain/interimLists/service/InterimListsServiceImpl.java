package men.brakh.enrollment.domain.interimLists.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import men.brakh.enrollment.domain.enrollee.Enrollee;
import men.brakh.enrollment.domain.enrollee.dto.EnrolleeDto;
import men.brakh.enrollment.domain.enrollee.mapping.EnrolleeEntityPresenter;
import men.brakh.enrollment.domain.interimLists.dto.InterimListsDto;
import men.brakh.enrollment.domain.interimLists.dto.SpecialityInterimLists;
import men.brakh.enrollment.domain.specialty.Specialty;
import men.brakh.enrollment.domain.universityApplication.UniversityApplication;
import men.brakh.enrollment.domain.universityApplication.EducationType;
import men.brakh.enrollment.domain.universityApplication.repository.UniversityApplicationRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Class which allows to get interim list.
 *
 * Class has a complicated implementation and I was lazy to fix it. Event don't try to understand it :)
 */
@Component
public class InterimListsServiceImpl implements InterimListsService {

    private final EnrolleeEntityPresenter enrolleeEntityPresenter;
    private final UniversityApplicationRepository universityApplicationRepository;

    public InterimListsServiceImpl(final EnrolleeEntityPresenter enrolleeEntityPresenter,
                                   final UniversityApplicationRepository universityApplicationRepository) {
        this.enrolleeEntityPresenter = enrolleeEntityPresenter;
        this.universityApplicationRepository = universityApplicationRepository;
    }

    @Override
    public InterimListsDto getList() {
        return getList(universityApplicationRepository.findAll());
    }

    @Data
    @AllArgsConstructor
    private static class SpecialityWithApplicationType {
        private final Specialty specialty;
        private final EducationType applicationType;
    }

    @Data
    @AllArgsConstructor
    private static class EnrolleeWitSpecialities {
        private final Integer score;
        private final Enrollee enrollee;
        private final Queue<Specialty> specialties;
    }


    private Comparator<EnrolleeWitSpecialities> getEnrolleeComparator() {
        return Comparator.comparingInt(EnrolleeWitSpecialities::getScore);
    }



    InterimListsDto getList(final List<UniversityApplication> applications) {
        final Map<SpecialityWithApplicationType, PriorityQueue<EnrolleeWitSpecialities>> map
                = initializeMap(applications);

        while (isSpecialitiesIsOverflow(map)) {
            moveExtraEnrollee(map);
        }

        return getInterimListsDto(map);
    }

    private boolean isSpecialitiesIsOverflow(Map<SpecialityWithApplicationType,
            PriorityQueue<EnrolleeWitSpecialities>> map) {
        final AtomicBoolean isOverflow = new AtomicBoolean(false);

        map.forEach((key, queue) -> {
            final int limit = key.getSpecialty().getLimits().get(key.applicationType);
            if (queue.size() > limit) {
                isOverflow.set(true);
            }
        });

        return isOverflow.get();
    }

    private InterimListsDto getInterimListsDto(final Map<SpecialityWithApplicationType,
            PriorityQueue<EnrolleeWitSpecialities>> map) {
        return new InterimListsDto(
                map.entrySet()
                    .stream()
                    .map(entry -> {
                        int limit = entry.getKey().getSpecialty().getLimits().get(entry.getKey().applicationType);
                        boolean isFull = entry.getValue().size() == limit;

                        int passingScore = isFull ? entry.getValue()
                                .stream()
                                .map(EnrolleeWitSpecialities::getScore)
                                .max(Comparator.comparingInt(integer -> integer))
                                .orElse(0) : 0;

                        return SpecialityInterimLists.builder()
                                .applicationType(entry.getKey().getApplicationType())
                                .enrollees(entry.getValue()
                                        .stream()
                                        .map(EnrolleeWitSpecialities::getEnrollee)
                                        .map(enrollee -> enrolleeEntityPresenter.mapToDto(enrollee, EnrolleeDto.class))
                                        .collect(Collectors.toList()))
                                .speciality(entry.getKey().getSpecialty().getName())
                                .passingScore(passingScore)
                                .build();
                        }

                    ).collect(Collectors.toList())
        );
    }

    private void moveExtraEnrollee(final Map<SpecialityWithApplicationType,
            PriorityQueue<EnrolleeWitSpecialities>> map) {
        map.forEach((key, queue) -> {
            final int limit = key.getSpecialty().getLimits().get(key.applicationType);
            while (queue.size() > limit) {
                EnrolleeWitSpecialities enrolleeWitSpecialities = queue.poll();

                final Specialty nextSpeciality = enrolleeWitSpecialities.getSpecialties().poll();

                if (nextSpeciality != null) {
                    map.get(new SpecialityWithApplicationType(nextSpeciality, key.getApplicationType()))
                            .offer(enrolleeWitSpecialities);
                }

            }
        });
    }

    private Map<SpecialityWithApplicationType, PriorityQueue<EnrolleeWitSpecialities>>
        initializeMap(final List<UniversityApplication> applications) {

        final Map<SpecialityWithApplicationType, PriorityQueue<EnrolleeWitSpecialities>> map = new HashMap<>();

        for (Specialty specialty : Specialty.values()) {
            for (EducationType type : EducationType.values()) {
                map.put(new SpecialityWithApplicationType(specialty, type),
                        new PriorityQueue<>(getEnrolleeComparator()));
            }
        }

        applications.forEach(universityApplication -> {
            final Queue<Specialty> specialties = new ArrayDeque<>(universityApplication.getSpecialties());
            final Specialty firstSpeciality = specialties.poll();

            map.get(new SpecialityWithApplicationType(firstSpeciality, universityApplication.getType()))
                    .add(new EnrolleeWitSpecialities(
                            universityApplication.getScores(),
                            universityApplication.getEnrollee(),
                            specialties));
        });
        return map;
    }

}
