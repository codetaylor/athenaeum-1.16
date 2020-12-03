package com.codetaylor.mc.athenaeum.util;

import com.codetaylor.mc.athenaeum.AthenaeumMod;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public final class TooltipHelper {

  public static final String TOOLTIP_COMMON_HOLD_SHIFT = "gui." + AthenaeumMod.MOD_ID + ".tooltip.common.hold_shift";

  public static ITextComponent getTooltipHoldShiftTextComponent() {

    return new TranslationTextComponent(
        TOOLTIP_COMMON_HOLD_SHIFT,
        TextFormatting.DARK_GRAY,
        TextFormatting.AQUA,
        TextFormatting.DARK_GRAY
    );
  }

  public static void addTooltip(List<String> tooltip, String text, int preferredIndex) {

    if (tooltip.size() > preferredIndex) {
      tooltip.add(preferredIndex, text);

    } else {
      tooltip.add(text);
    }
  }

  private TooltipHelper() {
    //
  }
}
