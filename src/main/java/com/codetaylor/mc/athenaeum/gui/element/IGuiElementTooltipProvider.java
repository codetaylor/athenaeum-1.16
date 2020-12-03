package com.codetaylor.mc.athenaeum.gui.element;

import net.minecraft.util.text.ITextComponent;

import java.util.List;

public interface IGuiElementTooltipProvider
    extends
    IGuiElement {

  List<ITextComponent> tooltipTextGet(List<ITextComponent> tooltip);

}
