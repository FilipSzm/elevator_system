package proj.elevators.elevatorSystem.model.util;

/**
 * simple implementation of tuple with 2 elements
 * @param first
 * @param second
 * @param <X> any reference type
 * @param <Y> any reference type
 */
public record Pair<X, Y>(X first, Y second) { }