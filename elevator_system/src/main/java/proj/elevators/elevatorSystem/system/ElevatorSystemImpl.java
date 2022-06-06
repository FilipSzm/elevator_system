package proj.elevators.elevatorSystem.system;

import proj.elevators.elevatorSystem.exception.*;
import proj.elevators.elevatorSystem.model.*;
import proj.elevators.elevatorSystem.model.util.Pair;
import proj.elevators.elevatorSystem.model.util.Triple;

import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Default implementation of {@code ElevatorSystem} interface
 */
public class ElevatorSystemImpl implements ElevatorSystem {

    /**
     * scalar that defines correlation of {@code Target} priorities to {@code Pickup},
     * default value is equal to {@code 8.0}.
     */
    private float targetScalar;

    /**
     * list of all {@code Elevators} in the system.
     */
    final private List<Elevator> elevators;

    /**
     * list of all currently active {@code Pickups} in the system.
     */
    private List<Pickup> pickupList;

    /**
     * default constructor for {@code ElevatorSystemImpl}.
     * @param numberOfElevators number of {@code Elevators} for system to have
     * @throws IncorrectNumberOfElevatorsException if number
     * of {@code Elevators} is out of correct range (1, 16)
     */
    public ElevatorSystemImpl(int numberOfElevators) {
        elevators = initElevators(numberOfElevators);
        pickupList = new ArrayList<>();
        targetScalar = 8F;
    }

    /**
     * two parameter constructor.
     * @param numberOfElevators number of {@code Elevators} for system to have
     * @param targetScalar scalar value for all {@code Target} priorities in the system,
     *                     it gets absolute value of this number because
     *                     {@code targetScalar} cannot be negative
     * @throws IncorrectNumberOfElevatorsException if number
     * of {@code Elevators} is out of correct range (1, 16)
     */
    public ElevatorSystemImpl(int numberOfElevators, float targetScalar) {
        elevators = initElevators(numberOfElevators);
        pickupList = new ArrayList<>();
        targetScalar(targetScalar);
    }

    /**
     * initializator for elevators.
     * @param numberOfElevators number of {@code Elevators} for system to have
     * @return list on newly initialized {@code Elevators} for {@code ElevatorSystem}
     * @throws IncorrectNumberOfElevatorsException if number
     * of {@code Elevators} is out of correct range (1, 16)
     */
    private List<Elevator> initElevators(int numberOfElevators) {
        if (!ValueRange.of(1, 16).isValidIntValue(numberOfElevators))
            throw new IncorrectNumberOfElevatorsException();

        var elevators = new ArrayList<Elevator>(numberOfElevators);
        IntStream.range(0, numberOfElevators)
                .forEachOrdered(i -> elevators.add(new ElevatorImpl(i)));
        return elevators;
    }

    /**
     * adds new {@code pickup} request to {@code ElevatorSystem}.
     * (if there is already {@code Pickup} request for
     * this floor this function only changes its {@code Direction})
     * @param floorNumber param that defines {@code floorNumber} of {@code Pickup} to add
     * @param direction param that defines {@code direction} of {@code Pickup} to add
     * @throws IncorrectPickupDirectionException if {@code direction} is set to {@code NO_DIRECTION}
     */
    @Override
    public void pickup(int floorNumber, Direction direction) {
        if (direction == Direction.NO_DIRECTION)
            throw new IncorrectPickupDirectionException();

        var pickupWithSameFloorNumber = pickupList.stream()
                .filter(p -> p.floorNumber() == floorNumber)
                .findFirst();

        pickupWithSameFloorNumber.ifPresentOrElse(
                pickup -> pickup.direction(direction),
                () -> pickupList.add(new Pickup(floorNumber, direction))
        );
    }

    /**
     * adds new {@code target} request to {@code Elevator} with given {@code id}. (if {@code Elevator}
     * already have {@code Target} request for this floor nothing happens)
     * @param elevatorId {@code id} of {@code Elevator} for which {@code Target} is specified
     * @param floorNumber param that defines {@code floorNumber} of {@code Target} to add
     * @throws NoElevatorWithIdException if there is no {@code Elevator}
     * with given {@code id} in {@code ElevatorSystem}
     */
    @Override
    public void target(int elevatorId, int floorNumber) {
        elevators.stream()
                .filter(e -> e.id() == elevatorId)
                .findFirst()
                .orElseThrow(NoElevatorWithIdException::new)
                .addTarget(new Target(floorNumber));
    }

    /**
     * updates all parameters of {@code Elevator} with given {@code id} with values from given {@code elevator}.
     * @param elevatorId {@code id} of {@code Elevator} to update
     * @param elevator {@code Elevator} containing values to set to for updated {@code Elevator}
     * @throws DuplicateElevatorIdException if there is already {@code Elevator}
     * in the system with id equal to {@code elevator} from parameter
     * @throws NoElevatorWithIdException if there is no {@code Elevator}
     * with given {@code id} in {@code ElevatorSystem}
     */
    @Override
    public void update(int elevatorId, Elevator elevator) {
        if (elevators.stream().anyMatch(e -> e.id() == elevator.id() && e.id() != elevatorId))
            throw new DuplicateElevatorIdException();

        elevators.stream()
                .filter(e -> e.id() == elevatorId)
                .findFirst()
                .orElseThrow(NoElevatorWithIdException::new)
                .update(elevator);
    }

    /**
     * updates position of {@code Elevator} with {@code id} given as parameter.
     * @param elevatorId {@code id} of {@code Elevator} to update
     * @param floorNumber new {@code floorNumber} for updated {@code Elevator}
     * @throws NoElevatorWithIdException if there is no {@code Elevator}
     * with given {@code id} in {@code ElevatorSystem}
     */
    @Override
    public void update(int elevatorId, int floorNumber) {
        elevators.stream()
                .filter(e -> e.id() == elevatorId)
                .findFirst()
                .orElseThrow(NoElevatorWithIdException::new)
                .updatePosition(floorNumber);
    }

    /**
     * performs one step of the simulation:
     * <ul>
     *     <li>Clears all {@code Targets} and {@code Pickups}
     *     that are on the same floor as {@code Elevator}</li>
     *     <li>Moves {@code Elevators} to most optimal
     *     {@code Target} or {@code Pickup}</li>
     *     <li>Clears all {@code Targets} and {@code Pickups}
     *      that are on the same floor as {@code Elevator}</li>
     *      <li>increase time wait time scalar for all {@code Targets}
     *      and {@code Pickups}</li>
     * </ul>
     */
    @Override
    public void step() {
        elevators.forEach(Elevator::dropOff);
        pickupList = updatePickupList();

        var notMovedToPickups = new ArrayList<>(pickupList);
        var notMovedElevators = new ArrayList<>(elevators);

        while (!notMovedToPickups.isEmpty() && !notMovedElevators.isEmpty()) {
            var pair = pickupElevatorPairWithHighestPriority(notMovedToPickups, notMovedElevators);

            if (pair.second().moveUsingPickupOrNot(pair.first(), targetScalar))
                notMovedToPickups.remove(pair.first());

            notMovedElevators.remove(pair.second());
        }
        notMovedElevators.forEach(e -> e.move(targetScalar));

        elevators.forEach(Elevator::dropOff);
        pickupList = updatePickupList();

        elevators.forEach(Elevator::increaseWaitTimeScalar);
        pickupList.forEach(Pickup::increaseWaitTimeScalar);
    }

    /**
     * utility function to find the highest priority {@code Pickup}-{@code Elevator} pair.
     * @param pickupList list of {@code Pickups} to search in
     * @param elevators list of {@code Elevators} to search in
     * @return best {@code Pickup}-{@code Elevator} pair
     */
    private Pair<Pickup, Elevator> pickupElevatorPairWithHighestPriority(List<Pickup> pickupList, List<Elevator> elevators) {
        Triple<Pickup, Elevator, Float> bestTriple = pickupList.stream()
                .map(p -> tripleWithBestElevator(p, elevators))
                .max(Comparator.comparing(Triple::third))
                .orElseThrow(EmptyCollectionException::new);

        return new Pair<>(bestTriple.first(), bestTriple.second());
    }

    /**
     * utility function to find the highest priority {@code Elevator} for {@code Pickup}.
     * @param p {@code Pickup} to calculate priority to
     * @param elevators list of {@code Elevators} to search in
     * @return {@code Triple} consisting of {@code Pickup}, {@code Elevator}
     * with the highest priority and that priority
     */
    private Triple<Pickup, Elevator, Float> tripleWithBestElevator(Pickup p, List<Elevator> elevators) {
        var tripleList = elevators.stream()
                .map(e -> new Triple<>(p, e, p.calculatePriority(e))).toList();

        var highestPriority = tripleList.stream()
                .map(Triple::third)
                .max(Float::compareTo)
                .orElseThrow(EmptyCollectionException::new);

        var elementsWithHighestPriority = tripleList.stream()
                .filter(e -> Objects.equals(e.third(), highestPriority)).toList();

        for (var triple : elementsWithHighestPriority) {
            if (Direction.areDirectionsSame(triple.first().direction(), triple.second().direction()))
                return triple;
        }
        return elementsWithHighestPriority.stream().findFirst().orElseThrow(EmptyCollectionException::new);
    }

    /**
     * utility function that removes all {@code Pickups} that are on the same floor as {@code Elevator}.
     * @return filtered list of {@code Pickups}
     */
    private List<Pickup> updatePickupList() {
        var pickupList = new ArrayList<Pickup>();
        for (var pickup : this.pickupList) {
            elevators.stream()
                    .filter(e -> e.floorNumber() == pickup.floorNumber())
                    .findFirst()
                    .ifPresentOrElse(
                            e -> e.pickUpIfSameFloor(pickup),
                            () -> pickupList.add(pickup)
                    );
        }
        return pickupList;
    }

    /**
     * @return list of statuses of all {@code Elevators} in {@code ElevatorSystem}
     */
    @Override
    public List<ElevatorStatus> status() {
        return elevators.stream()
                .map(Elevator::status)
                .toList();
    }

    /**
     * {@code pickupList} getter.
     * @return {@code pickupList}
     */
    @Override
    public List<Pickup> pickupList() {
        return pickupList;
    }

    /**
     * {@code elevators} getter.
     * @return {@code elevators}
     */
    @Override
    public List<Elevator> elevators() {
        return elevators;
    }

    /**
     * {@code targetScalar} setter.
     * @param targetScalar scalar value for all {@code Target} priorities in the system,
     * it gets absolute value of this number because
     * {@code targetScalar} cannot be negative
     */
    @Override
    public void targetScalar(float targetScalar) {
        this.targetScalar = Math.abs(targetScalar);
    }
}
