package ru.liga.entity;

public class Truck {
    private static final int MAX_HEIGHT = 6;
    private static final int MAX_WIDTH = 6;

    // TODO Удалить за ненадобностью
//    private static final String CARCASE_TEMPLATE =
//    """
//    +      +
//    +      +
//    +      +
//    +      +
//    +      +
//    +%s    +
//    ++++++++
//    """;

    private final String value;

    public Truck(String value) {
        if (value.length() > MAX_WIDTH) {
            throw new IllegalArgumentException(
                    String.format("Посылка %s не соответствует ширине грузовика: %s", value, MAX_WIDTH));
        }
        this.value = value;
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();
        stringBuilder.append("+      +\n");
        stringBuilder.append("+      +\n");
        stringBuilder.append("+      +\n");
        stringBuilder.append("+      +\n");
        stringBuilder.append("+      +\n");
        stringBuilder.append("+");
        stringBuilder.append(value);
        stringBuilder.append(" ".repeat(MAX_HEIGHT - value.length()));
        stringBuilder.append("+\n");
        stringBuilder.append("++++++++\n\n");

        return stringBuilder.toString();
    }
}
