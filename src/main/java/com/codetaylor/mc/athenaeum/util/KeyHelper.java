package com.codetaylor.mc.athenaeum.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import org.lwjgl.glfw.GLFW;

public final class KeyHelper {

  public static boolean isKeyBindingDown(KeyBinding keyBinding) {

    if (keyBinding.isInvalid()) {
      return false;
    }

    boolean isDown = false;

    switch (keyBinding.getKey().getType()) {

      case KEYSYM:
        isDown = KeyHelper.isKeyDown(keyBinding.getKey().getKeyCode());
        break;

      case MOUSE:
        isDown = GLFW.glfwGetMouseButton(Minecraft.getInstance().getMainWindow().getHandle(), keyBinding.getKey().getKeyCode()) == GLFW.GLFW_PRESS;
        break;
    }

    return isDown
        && keyBinding.getKeyConflictContext().isActive()
        && keyBinding.getKeyModifier().isActive(keyBinding.getKeyConflictContext());
  }

  public static boolean isKeyDown(int keyCode) {

    return InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), keyCode);
  }

  private KeyHelper() {
    //
  }
}
