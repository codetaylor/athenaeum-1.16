package com.codetaylor.mc.athenaeum.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public final class GuiHelper {

  public static final ResourceLocation GALACTIC_ALT_FONT = new ResourceLocation("minecraft", "alt");
  public static final Style GALACTIC_STYLE = Style.EMPTY.setFontId(GALACTIC_ALT_FONT);

  public static IFormattableTextComponent asGalactic(IFormattableTextComponent textComponent) {

    return textComponent.mergeStyle(GALACTIC_STYLE);
  }

  public static void drawModalRectWithCustomSizedTexture(int x, int y, int z, float u, float v, int width, int height, float textureWidth, float textureHeight) {

    float f = 1.0F / textureWidth;
    float f1 = 1.0F / textureHeight;
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferbuilder = tessellator.getBuffer();
    bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
    bufferbuilder.pos(x, y + height, z).tex(u * f, (v + (float) height) * f1).endVertex();
    bufferbuilder.pos(x + width, y + height, z).tex((u + (float) width) * f, (v + (float) height) * f1).endVertex();
    bufferbuilder.pos(x + width, y, z).tex((u + (float) width) * f, v * f1).endVertex();
    bufferbuilder.pos(x, y, z).tex(u * f, v * f1).endVertex();
    tessellator.draw();
  }

  public static void drawStringOutlined(
      MatrixStack matrixStack,
      ITextComponent textComponent,
      int x,
      int y,
      FontRenderer fontRenderer,
      int textShadowColor
  ) {

    GuiHelper.drawStringOutlined(matrixStack, textComponent, x, y, fontRenderer, textShadowColor, false);
  }

  public static void drawStringOutlined(
      MatrixStack matrixStack,
      ITextComponent textComponent,
      int x,
      int y,
      FontRenderer fontRenderer,
      int textShadowColor,
      boolean dropShadow
  ) {

    if (dropShadow) {
      fontRenderer.func_243246_a(matrixStack, textComponent, x + 0, y + 1, textShadowColor);
      fontRenderer.func_243246_a(matrixStack, textComponent, x + 1, y + 1, textShadowColor);
      fontRenderer.func_243246_a(matrixStack, textComponent, x + 1, y - 1, textShadowColor);
      fontRenderer.func_243246_a(matrixStack, textComponent, x + 1, y + 0, textShadowColor);

      fontRenderer.func_243246_a(matrixStack, textComponent, x - 0, y - 1, textShadowColor);
      fontRenderer.func_243246_a(matrixStack, textComponent, x - 1, y - 1, textShadowColor);
      fontRenderer.func_243246_a(matrixStack, textComponent, x - 1, y + 1, textShadowColor);
      fontRenderer.func_243246_a(matrixStack, textComponent, x - 1, y - 0, textShadowColor);

      fontRenderer.func_243246_a(matrixStack, textComponent, x, y, Color.BLACK.getRGB());

    } else {
      fontRenderer.func_243248_b(matrixStack, textComponent, x + 0, y + 1, textShadowColor);
      fontRenderer.func_243248_b(matrixStack, textComponent, x + 1, y + 1, textShadowColor);
      fontRenderer.func_243248_b(matrixStack, textComponent, x + 1, y - 1, textShadowColor);
      fontRenderer.func_243248_b(matrixStack, textComponent, x + 1, y + 0, textShadowColor);

      fontRenderer.func_243248_b(matrixStack, textComponent, x - 0, y - 1, textShadowColor);
      fontRenderer.func_243248_b(matrixStack, textComponent, x - 1, y - 1, textShadowColor);
      fontRenderer.func_243248_b(matrixStack, textComponent, x - 1, y + 1, textShadowColor);
      fontRenderer.func_243248_b(matrixStack, textComponent, x - 1, y - 0, textShadowColor);

      fontRenderer.func_243248_b(matrixStack, textComponent, x, y, Color.BLACK.getRGB());
    }
  }

  public static void drawTexturedRect(
      Minecraft minecraft,
      ResourceLocation texture,
      int x,
      int y,
      int width,
      int height,
      int zLevel,
      float u0,
      float v0,
      float u1,
      float v1
  ) {

    TextureManager renderEngine = minecraft.getTextureManager();
    renderEngine.bindTexture(texture);

    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferbuilder = tessellator.getBuffer();
    bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
    bufferbuilder
        .pos(x, (y + height), zLevel)
        .tex(u0, v1)
        .endVertex();
    bufferbuilder
        .pos((x + width), (y + height), zLevel)
        .tex(u1, v1)
        .endVertex();
    bufferbuilder
        .pos((x + width), y, zLevel)
        .tex(u1, v0)
        .endVertex();
    bufferbuilder
        .pos(x, y, zLevel)
        .tex(u0, v0)
        .endVertex();
    tessellator.draw();
  }

  public static void drawColoredRect(
      BufferBuilder renderer,
      int x,
      int y,
      int width,
      int height,
      int red,
      int green,
      int blue,
      int alpha
  ) {

    renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
    renderer.pos(x + 0, y + 0, 0.0D).color(red, green, blue, alpha).endVertex();
    renderer.pos(x + 0, y + height, 0.0D).color(red, green, blue, alpha).endVertex();
    renderer.pos(x + width, y + height, 0.0D).color(red, green, blue, alpha).endVertex();
    renderer.pos(x + width, y + 0, 0.0D).color(red, green, blue, alpha).endVertex();
    Tessellator.getInstance().draw();
  }

  public static void drawVerticalScaledTexturedModalRectFromIconAnchorBottomLeft(
      int x,
      int y,
      float z,
      TextureAtlasSprite icon,
      int width,
      int height
  ) {

    // TODO: this only handles tiling vertically, need to implement horizontal tiling as well

    if (icon == null) {
      return;
    }

    int iconHeight = icon.getHeight();
    int iconWidth = icon.getWidth();

    float minU = icon.getMinU();
    float maxU = icon.getMaxU();
    float minV = icon.getMinV();
    float maxV = icon.getMaxV();

    BufferBuilder buffer = Tessellator.getInstance().getBuffer();
    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

    int sections = height / iconHeight;

    for (int i = 0; i < sections; i++) {
      buffer.pos(x, y + height - (i * iconHeight), z)
          .tex(minU, maxV)
          .endVertex();

      buffer.pos(x + width, y + height - (i * iconHeight), z)
          .tex(minU + (maxU - minU) * width / (float) iconWidth, maxV)
          .endVertex();

      buffer.pos(x + width, y + height - ((i + 1) * iconHeight), z)
          .tex(minU + (maxU - minU) * width / (float) iconWidth, maxV - (maxV - minV))
          .endVertex();

      buffer.pos(x, y + height - ((i + 1) * iconHeight), z)
          .tex(minU, maxV - (maxV - minV))
          .endVertex();
    }

    int remainder = height - sections * iconHeight;

    if (remainder > 0) {

      buffer.pos(x, y + height - (sections * iconHeight), z)
          .tex(minU, maxV)
          .endVertex();

      buffer.pos(x + width, y + height - (sections * iconHeight), z)
          .tex(minU + (maxU - minU) * width / (float) iconWidth, maxV)
          .endVertex();

      buffer.pos(x + width, y + height - (sections * iconHeight + remainder), z)
          .tex(minU + (maxU - minU) * width / (float) iconWidth, maxV - (maxV - minV) * remainder / (float) iconHeight)
          .endVertex();

      buffer.pos(x, y + height - (sections * iconHeight + remainder), z)
          .tex(minU, maxV - (maxV - minV) * remainder / (float) iconHeight)
          .endVertex();
    }

    /*buffer.pos(x, y + height, z).tex(minU, maxV).endVertex();

    buffer.pos(x + width, y + height, z).tex(minU + (maxU - minU) * width / 16F, maxV).endVertex();

    buffer.pos(x + width, y, z)
        .tex(minU + (maxU - minU) * width / 16F, maxV - (maxV - minV) * height / 16F)
        .endVertex();

    buffer.pos(x, y, z).tex(minU, maxV - (maxV - minV) * height / 16F).endVertex();*/

    Tessellator.getInstance().draw();

  }

  public static void drawScaledTexturedModalRectFromIconAnchorBottomLeft(
      int x,
      int y,
      float z,
      TextureAtlasSprite icon,
      int width,
      int height
  ) {

//    double scaledTime = (double) Minecraft.getMinecraft().world.getTotalWorldTime() * 0.05;
//    height += (Math.sin(scaledTime) * 0.5 + 0.5) * 32;
//    width += (Math.sin(scaledTime) * 0.5 + 0.5) * 32;

    if (icon == null) {
      return;
    }

    int iconHeight = icon.getHeight();
    int iconWidth = icon.getWidth();

    float minU = icon.getMinU();
    float maxU = icon.getMaxU();
    float minV = icon.getMinV();
    float maxV = icon.getMaxV();

    int verticalSections = height / iconHeight + 1;
    int horizontalSections = width / iconWidth + 1;

    BufferBuilder buffer = Tessellator.getInstance().getBuffer();
    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

    for (int i = 0; i < verticalSections; i++) {
      for (int j = 0; j < horizontalSections; j++) {

        int px1 = x + (j * iconWidth);
        int px2 = x + Math.min((j + 1) * iconWidth, width);
        int py1 = y + height - (i * iconHeight);
        int py2 = y + height - Math.min((i + 1) * iconHeight, height);

        float tu2 = minU + (maxU - minU) * (((j + 1) * iconWidth > width)
            ? (width - (j * iconWidth)) / (float) iconWidth : 1);
        float tv2 = maxV - (maxV - minV) * (((i + 1) * iconHeight > height)
            ? (height - (i * iconHeight)) / (float) iconHeight : 1);

        buffer.pos(px1, py1, z).tex(minU, maxV).endVertex();
        buffer.pos(px2, py1, z).tex(tu2, maxV).endVertex();
        buffer.pos(px2, py2, z).tex(tu2, tv2).endVertex();
        buffer.pos(px1, py2, z).tex(minU, tv2).endVertex();
      }
    }

    Tessellator.getInstance().draw();
  }

  public static int getFluidHeight(int fluidAmount, int fluidCapacity, int displayHeight) {

    float fluidHeightScalar = GuiHelper.getFluidHeightScalar(fluidAmount, fluidCapacity, displayHeight);
    int elementHeightModified = (int) (fluidHeightScalar * displayHeight);
    return Math.max(0, Math.min(elementHeightModified, displayHeight));
  }

  public static float getFluidHeightScalar(int fluidAmount, int fluidCapacity, int displayHeight) {

    if (fluidAmount > 0) {
      return Math.max((float) fluidAmount / (float) fluidCapacity, 1 / (float) displayHeight);

    } else {
      return 0;
    }
  }

  public static int getFluidY(int fluidAmount, int fluidCapacity, int displayHeight, int offsetY) {

    float fluidHeightScalar = GuiHelper.getFluidHeightScalar(fluidAmount, fluidCapacity, displayHeight);
    int elementHeightModified = (int) (fluidHeightScalar * displayHeight);
    return displayHeight - Math.max(0, Math.min(elementHeightModified, displayHeight)) + offsetY;
  }

  // https://github.com/TheCBProject/CoFHLib/blob/master/src/main/java/cofh/lib/gui/GuiBase.java
  public static void drawScaledTexturedModalRectFromIcon(
      int x,
      int y,
      float z,
      TextureAtlasSprite icon,
      int width,
      int height
  ) {

    if (icon == null) {
      return;
    }

    int iconHeight = icon.getHeight();
    int iconWidth = icon.getWidth();

    float minU = icon.getMinU();
    float maxU = icon.getMaxU();
    float minV = icon.getMinV();
    float maxV = icon.getMaxV();

    BufferBuilder buffer = Tessellator.getInstance().getBuffer();
    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
    buffer
        .pos(x, y + height, z)
        .tex(minU, minV + (maxV - minV) * height / (float) iconHeight)
        .endVertex();
    buffer
        .pos(x + width, y + height, z)
        .tex(minU + (maxU - minU) * width / (float) iconWidth, minV + (maxV - minV) * height / (float) iconHeight)
        .endVertex();
    buffer
        .pos(x + width, y, z)
        .tex(minU + (maxU - minU) * width / (float) iconWidth, minV)
        .endVertex();
    buffer
        .pos(x, y, z)
        .tex(minU, minV)
        .endVertex();
    Tessellator.getInstance().draw();

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
  public static void drawRotatedTexturedModalSquare(
      int x,
      int y,
      float z,
      int textureX,
      int textureY,
      int size,
      int rotation
  ) {

    // TODO: these magic numbers tho...

    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder vertexbuffer = tessellator.getBuffer();
    vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);

    if (rotation == 1) {

      vertexbuffer
          .pos(x, y + size, z)
          .tex((textureX + size) * 0.00390625F, (textureY + size) * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x + size, (y + size), z)
          .tex((textureX + size) * 0.00390625F, textureY * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x + size, y, z)
          .tex(textureX * 0.00390625F, textureY * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x, y, z)
          .tex(textureX * 0.00390625F, (textureY + size) * 0.00390625F)
          .endVertex();

    } else if (rotation == 2) {

      vertexbuffer
          .pos(x, y + size, z)
          .tex((textureX + size) * 0.00390625F, textureY * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x + size, (y + size), z)
          .tex(textureX * 0.00390625F, textureY * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x + size, y, z)
          .tex(textureX * 0.00390625F, (textureY + size) * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x, y, z)
          .tex((textureX + size) * 0.00390625F, (textureY + size) * 0.00390625F)
          .endVertex();

    } else if (rotation == 3) {

      vertexbuffer
          .pos(x, y + size, z)
          .tex(textureX * 0.00390625F, textureY * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x + size, (y + size), z)
          .tex(textureX * 0.00390625F, (textureY + size) * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x + size, y, z)
          .tex((textureX + size) * 0.00390625F, (textureY + size) * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x, y, z)
          .tex((textureX + size) * 0.00390625F, textureY * 0.00390625F)
          .endVertex();

    } else { // rotation 0 is default

      vertexbuffer
          .pos(x, y + size, z)
          .tex(textureX * 0.00390625F, (textureY + size) * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x + size, (y + size), z)
          .tex((textureX + size) * 0.00390625F, (textureY + size) * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x + size, y, z)
          .tex((textureX + size) * 0.00390625F, textureY * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x, y, z)
          .tex(textureX * 0.00390625F, textureY * 0.00390625F)
          .endVertex();

    }

    tessellator.draw();

  }

  private GuiHelper() {
    //
  }

}
