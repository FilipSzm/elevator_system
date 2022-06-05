package proj.elevators.elevatorSystem.model;

import proj.elevators.elevatorSystem.exception.DuplicateElevatorIdException;
import proj.elevators.elevatorSystem.exception.IncorrectPickupDirectionException;
import proj.elevators.elevatorSystem.exception.NoElevatorWithIdException;

import java.util.List;

/**
 * interface for {@code ElevatorSystem}.
 */
public interface ElevatorSystem {

    /**
     * adds new {@code pickup} request to {@code ElevatorSystem}.
     * @param floorNumber param that defines {@code floorNumber} of {@code Pickup} to add
     * @param direction param that defines {@code direction} of {@code Pickup} to add
     * @throws IncorrectPickupDirectionException if {@code direction} is set to {@code NO_DIRECTION}
     */
    void pickup(int floorNumber, Direction direction);

    /**
     * adds new {@code target} request to {@code Elevator} with given {@code id}.
     * @param elevatorId {@code id} of {@code Elevator} for which {@code Target} is specified
     * @param floorNumber param that defines {@code floorNumber} of {@code Target} to add
     * @throws NoElevatorWithIdException if there is no {@code Elevator}
     * with given {@code id} in {@code ElevatorSystem}
     */
    void target(int elevatorId, int floorNumber);

    /**
     * updates all parameters of {@code Elevator} with given {@code id} with values from given {@code elevator}.
     * @param elevatorId {@code id} of {@code Elevator} to update
     * @param elevator {@code Elevator} containing values to set to for updated {@code Elevator}
     * @throws DuplicateElevatorIdException if there is already {@code Elevator}
     * in the system with id equal to {@code elevator} from parameter
     * @throws NoElevatorWithIdException if there is no {@code Elevator}
     * with given {@code id} in {@code ElevatorSystem}
     */
    void update(int elevatorId, Elevator elevator);

    /**
     * updates position of {@code Elevator} with {@code id} given as parameter.
     * @param elevatorId {@code id} of {@code Elevator} to update
     * @param floorNumber new {@code floorNumber} for updated {@code Elevator}
     * @throws NoElevatorWithIdException if there is no {@code Elevator}
     * with given {@code id} in {@code ElevatorSystem}
     */
    void update(int elevatorId, int floorNumber);

    /**
     * performs one step of the simulation:
     * <ul>
     *     <li>Clears all {@code Targets} and {@code Pickups}
     *     that are on the same floor as {@code Elevator}</li>
     *     <li>Moves {@code Elevators} to most optimal
     *     {@code Target} of {@code Pickup}</li>
     *     <li>Clears all {@code Targets} and {@code Pickups}
     *      that are on the same floor as {@code Elevator}</li>
     * </ul>
     */
    void step();

    /**
     * @return list of statuses of all {@code Elevators} in {@code ElevatorSystem}
     */
    List<ElevatorStatus> status();

    /**
     * {@code pickupList} getter.
     * @return {@code pickupList}
     */
    List<Pickup> pickupList();

    /**
     * {@code elevators} getter.
     * @return {@code elevators}
     */
    List<Elevator> elevators();

    /**
     * {@code targetScalar} setter.
     * @param targetScalar scalar for {@code Target} priority
     */
    void targetScalar(float targetScalar);
}
