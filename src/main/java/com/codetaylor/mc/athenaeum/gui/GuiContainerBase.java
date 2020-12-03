package com.codetaylor.mc.athenaeum.gui;

import com.codetaylor.mc.athenaeum.gui.element.GuiElementBase;
import com.codetaylor.mc.athenaeum.gui.element.IGuiElementClickable;
import com.codetaylor.mc.athenaeum.gui.element.IGuiElementTooltipExtendedProvider;
import com.codetaylor.mc.athenaeum.gui.element.IGuiElementTooltipProvider;
import com.codetaylor.mc.athenaeum.util.KeyHelper;
import com.codetaylor.mc.athenaeum.util.TooltipHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GuiContainerBase<T extends ContainerBase>
    extends ContainerScreen<T> {

  private final List<GuiElementBase> guiElementList;
  private final List<IGuiElementClickable> guiElementClickableList;
  private final List<IGuiElementTooltipProvider> tooltipProviderList;
  private final List<ITextComponent> tooltipTextList;

  protected int scaledWidth;
  protected int scaledHeight;

  public GuiContainerBase(T container, PlayerInventory playerInventory, ITextComponent title, int width, int height) {

    super(container, playerInventory, title);
    this.xSize = width;
    this.ySize = height;
    this.guiElementList = new ArrayList<>();
    this.guiElementClickableList = new ArrayList<>();
    this.tooltipProviderList = new ArrayList<>();
    this.tooltipTextList = new ArrayList<>();
    this.updateScaledResolution();
  }

  private void updateScaledResolution() {

    MainWindow mainWindow = Minecraft.getInstance().getMainWindow();
    this.scaledWidth = mainWindow.getScaledWidth();
    this.scaledHeight = mainWindow.getScaledHeight();
  }

  public FontRenderer getFontRenderer() {

    return this.font;
  }

  public ItemRenderer getItemRender() {

    return this.itemRenderer;
  }

  public void drawItemStack(ItemStack stack, int x, int y, String altText) {

    RenderSystem.translatef(0.0F, 0.0F, 32.0F);
    this.setBlitOffset(200);
    this.itemRenderer.zLevel = 200.0F;
    net.minecraft.client.gui.FontRenderer font = stack.getItem().getFontRenderer(stack);
    if (font == null) {
      font = this.font;
    }
    this.itemRenderer.renderItemAndEffectIntoGUI(stack, x, y);
    this.itemRenderer.renderItemOverlayIntoGUI(font, stack, x, y - (this.draggedStack.isEmpty() ? 0 : 8), altText);
    this.setBlitOffset(0);
    this.itemRenderer.zLevel = 0.0F;
  }

  protected void guiContainerElementAdd(GuiElementBase... elements) {

    for (GuiElementBase element : elements) {
      this.guiElementList.add(element);

      if (element instanceof IGuiElementClickable) {
        this.guiElementClickableList.add((IGuiElementClickable) element);
      }
    }
  }

  public int guiContainerOffsetXGet() {

    return (this.scaledWidth - this.xSize) / 2;
  }

  public int guiContainerOffsetYGet() {

    return (this.scaledHeight - this.ySize) / 2;
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {

    this.updateScaledResolution();

    RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);

    for (GuiElementBase element : this.guiElementList) {
      element.update(partialTicks);

      if (element.elementIsVisible(mouseX, mouseY)) {
        element.drawBackgroundLayer(matrixStack, partialTicks, mouseX, mouseY);
      }
    }
  }

  @Override
  protected void drawGuiContainerForegroundLayer(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY) {

    super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
    this.tooltipProviderList.clear();

    for (GuiElementBase element : this.guiElementList) {

      if (element.elementIsVisible(mouseX, mouseY)) {
        element.drawForegroundLayer(matrixStack, mouseX, mouseY);

        if (element instanceof IGuiElementTooltipProvider
            && element.elementIsMouseInside(mouseX, mouseY)) {

          this.tooltipProviderList.add((IGuiElementTooltipProvider) element);
        }
      }
    }

    for (IGuiElementTooltipProvider element : this.tooltipProviderList) {
      this.tooltipTextList.clear();

      if (element.elementIsVisible(mouseX, mouseY)
          && element.elementIsMouseInside(mouseX, mouseY)) {
        element.tooltipTextGet(this.tooltipTextList);

        if (element instanceof IGuiElementTooltipExtendedProvider) {

          InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT);

          if (KeyHelper.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)
              || KeyHelper.isKeyDown(GLFW.GLFW_KEY_RIGHT_SHIFT)) {
            ((IGuiElementTooltipExtendedProvider) element).tooltipTextExtendedGet(this.tooltipTextList);

          } else {
            this.tooltipTextList.add(TooltipHelper.getTooltipHoldShiftTextComponent());
          }
        }

        GuiUtils.drawHoveringText(
            matrixStack,
            this.tooltipTextList,
            mouseX - this.guiContainerOffsetXGet(),
            mouseY - this.guiContainerOffsetYGet(),
            this.width,
            this.height,
            -1,
            this.font
        );
      }
    }
  }

  /**
   * Test if the 2D point is in a rectangle (relative to the GUI).
   */
  public boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, double pointX, double pointY) {

    int i = this.guiLeft;
    int j = this.guiTop;
    pointX = pointX - i;
    pointY = pointY - j;
    return pointX >= rectX - 1 && pointX < rectX + rectWidth + 1 && pointY >= rectY - 1 && pointY < rectY + rectHeight + 1;
  }

  @Override
  public boolean mouseClicked(double mouseX, double mouseY, int button) {

    boolean result = super.mouseClicked(mouseX, mouseY, button);

    for (IGuiElementClickable element : this.guiElementClickableList) {

      element.mouseClicked(mouseX, mouseY, button);

      if (((GuiElementBase) element).elementIsMouseInside(mouseX, mouseY)
          && ((GuiElementBase) element).elementIsVisible(mouseX, mouseY)) {
        element.elementClicked(mouseX, mouseY, button);
      }
    }

    return result;
  }

  public void drawScaledTexturedModalRectFromIcon(int x, int y, TextureAtlasSprite icon, int width, int height) {

    GuiHelper.drawScaledTexturedModalRectFromIcon(x, y, this.itemRenderer.zLevel, icon, width, height);
  }

  public void drawScaledTexturedModalRectFromIconAnchorBottomLeft(
      int x,
      int y,
      TextureAtlasSprite icon,
      int width,
      int height
  ) {

    GuiHelper.drawScaledTexturedModalRectFromIconAnchorBottomLeft(x, y, this.itemRenderer.zLevel, icon, width, height);
  }

  /**
   * Draws a textured square with an optionally rotated texture.
   *
   * @param x        the x
   * @param y        the y
   * @param textureX the texture x
   * @param textureY the texture y
   * @param size     the size
   * @param rotation (clockwise) 0 = 0 degrees, 1 = 90 degrees, 2 = 180 degrees, 3 = 270 degrees
   */
  public void drawRotatedTexturedModalSquare(int x, int y, int textureX, int textureY, int size, int rotation) {

    GuiHelper.drawRotatedTexturedModalSquare(x, y, this.itemRenderer.zLevel, textureX, textureY, size, rotation);
  }

  public void drawString(MatrixStack matrixStack, String text, int x, int y) {

    this.drawString(matrixStack, text, x, y, Color.WHITE.getRGB());
  }

  public void drawString(MatrixStack matrixStack, String text, int x, int y, int color) {

    this.font.drawString(matrixStack, text, x, y, color);
  }

  public int getGuiLeft() {

    return this.guiLeft;
  }

  public int getGuiTop() {

    return this.guiTop;
  }

}
