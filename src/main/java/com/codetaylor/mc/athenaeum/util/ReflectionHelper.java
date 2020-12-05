package com.codetaylor.mc.athenaeum.util;

import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public final class ReflectionHelper {

  @Nullable
  public static MethodHandle unreflectGetter(Class<?> referenceClass, String field, Logger logger) {

    try {

      return MethodHandles.lookup().unreflectGetter(referenceClass.getDeclaredField(field));

    } catch (Throwable t) {
      logger.error(String.format("Error unreflecting getter for field: %s", field), t);
    }

    return null;
  }

  @Nullable
  public static MethodHandle findStaticGetter(Class<?> referenceClass, String field, Class<?> typeClass, Logger logger) {

    try {

      return MethodHandles.lookup().findStaticGetter(referenceClass, field, typeClass);

    } catch (Throwable t) {
      logger.error(String.format("Error finding static getter for field: %s", field), t);
    }

    return null;
  }

  public static void inject(Class<?> apiClass, String fieldName, Object value) {

    try {
      Field field = apiClass.getDeclaredField(fieldName);
      field.setAccessible(true);

      Field modifiersField = Field.class.getDeclaredField("modifiers");
      modifiersField.setAccessible(true);
      modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

      field.set(null, value);

    } catch (Exception e) {
      throw new RuntimeException(String.format("Unable to inject [%s] into [%s]", fieldName, apiClass), e);
    }
  }

  private ReflectionHelper() {
    //
  }
}
