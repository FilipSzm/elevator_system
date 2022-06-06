package proj.elevator.system.model.param;

/**
 * wrapper object for json conversion purposes.
 * @param numberOfElevators
 * @param targetScalar
 */
public record InitParam(int numberOfElevators, float targetScalar) { }
