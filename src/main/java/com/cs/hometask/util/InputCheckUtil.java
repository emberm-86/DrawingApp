package com.cs.hometask.util;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InputCheckUtil {
  private static final List<String> VALID_TYPE_CODES = Arrays.asList("C", "R", "L", "B", "U");
  private static final List<Integer> VALID_ARG_NUMBERS = Arrays.asList(3, 5, 5, 4, 1);

  public static Map<Predicate<String>, String> createValidatorMap(
      String[] args, String typeCode, boolean canvasCreated) {

    Map<String, Integer> argNumByTypeCode = createArgNumByTypeCode();

    Map<Predicate<String>, String> argValidatorMap = new LinkedHashMap<>();

    argValidatorMap.put(
        t -> !VALID_TYPE_CODES.contains(t), "You have given invalid type code: " + typeCode);

    argValidatorMap.put(t -> !"C".equals(t) && !canvasCreated, "You have not created the canvas!");

    argValidatorMap.put(
        t -> args.length != argNumByTypeCode.get(t), "You have given input with incorrect length!");

    return argValidatorMap;
  }

  public static Map<String, Integer> createArgNumByTypeCode() {
    return IntStream.range(0, VALID_TYPE_CODES.size())
        .boxed()
        .collect(Collectors.toMap(VALID_TYPE_CODES::get, VALID_ARG_NUMBERS::get));
  }

  public static String getTypeCode(String[] arguments) {
    return arguments[0].toUpperCase(Locale.ROOT);
  }
}
