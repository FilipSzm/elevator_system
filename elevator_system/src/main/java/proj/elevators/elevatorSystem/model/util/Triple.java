package proj.elevators.elevatorSystem.model.util;

/**
 * simple implementation of tuple with 3 elements
 * @param first
 * @param second
 * @param third
 * @param <X> any reference type
 * @param <Y> any reference type
 * @param <Z> any reference type
 */
public record Triple<X, Y, Z>(X first, Y second, Z third) { }
