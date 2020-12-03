package com.codetaylor.mc.athenaeum.gui.element;

import net.minecraft.util.text.ITextComponent;

import java.util.List;

public interface IGuiElementTooltipExtendedProvider
    extends IGuiElementTooltipProvider {

  List<ITextComponent> tooltipTextExtendedGet(List<ITextComponent> tooltip);

}
