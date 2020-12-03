package com.codetaylor.mc.athenaeum.gui.element;

import com.codetaylor.mc.athenaeum.gui.GuiContainerBase;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import java.util.List;
import java.util.function.Supplier;

public class GuiElementItemStack
    extends GuiElementBase
    implements IGuiElementTooltipProvider {

  protected final Supplier<ItemStack> itemStackSupplier;
  protected final float alpha;

  public GuiElementItemStack(
      Supplier<ItemStack> itemStackSupplier,
      float alpha,
      GuiContainerBase<? extends Container> guiBase,
      int elementX, int elementY
  ) {

    super(guiBase, elementX, elementY, 16, 16);
    this.itemStackSupplier = itemStackSupplier;
    this.alpha = alpha;
  }

  @Override
  public void drawBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {

    ItemStack stack = this.itemStackSupplier.get();

    if (!stack.isEmpty()) {
      RenderHelper.enableStandardItemLighting();

      this.guiBase.drawItemStack(
          stack,
          this.elementXModifiedGet(),
          this.elementYModifiedGet(),
          null
      );

      RenderSystem.color4f(1, 1, 1, 1);
      RenderHelper.disableStandardItemLighting();
    }
  }

  @Override
  public void drawForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
    //
  }

  @Override
  public List<ITextComponent> tooltipTextGet(List<ITextComponent> tooltip) {

    ItemStack itemStack = this.itemStackSupplier.get();
    Minecraft minecraft = Minecraft.getInstance();

    if (!itemStack.isEmpty()
        && minecraft.player != null
        && minecraft.player.inventory.getItemStack().isEmpty()) {
      ITooltipFlag.TooltipFlags tooltipFlag = minecraft.gameSettings.advancedItemTooltips
          ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL;
      List<ITextComponent> itemStackTooltip = itemStack.getTooltip(minecraft.player, tooltipFlag);
      tooltip.addAll(itemStackTooltip);
    }

    return tooltip;
  }
}
