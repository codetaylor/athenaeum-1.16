package com.codetaylor.mc.athenaeum.gui.element;

import com.codetaylor.mc.athenaeum.gui.ContainerBase;
import com.codetaylor.mc.athenaeum.gui.GuiContainerBase;
import com.mojang.blaze3d.matrix.MatrixStack;

public class GuiElementTitle
    extends GuiElementBase {

  private final String titleKey;

  public GuiElementTitle(
      GuiContainerBase<? extends ContainerBase> guiBase,
      String titleKey,
      int elementX,
      int elementY
  ) {

    // element width and height don't matter
    super(guiBase, elementX, elementY, 0, 0);
    this.titleKey = titleKey;
  }

  @Override
  public void drawBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
    //
  }

  @Override
  public void drawForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {

    this.guiBase.drawString(matrixStack, this.titleKey, this.elementX, this.elementY);
  }
}
