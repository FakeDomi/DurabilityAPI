package re.domi.durabilityapi.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import re.domi.durabilityapi.IDurability;

@SuppressWarnings({"unused", "deprecation"})
@Mixin(ItemRenderer.class)
public class ItemRendererMixin
{
    @Redirect(
            method = "renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isDamaged()Z"))
    public boolean isDamagedReplacement(ItemStack original, TextRenderer renderer, ItemStack stack, int x, int y, String countLabel)
    {
        if (stack.getItem() instanceof IDurability)
        {
            IDurability item = (IDurability) stack.getItem();

            if (item.hasDurabilityBar(stack))
            {
                RenderSystem.disableDepthTest();
                RenderSystem.disableTexture();
                RenderSystem.disableAlphaTest();
                RenderSystem.disableBlend();
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferBuilder = tessellator.getBuffer();
                int color = item.getDurabilityColor(stack);
                this.renderGuiQuad(bufferBuilder, x + 2, y + 13, 13, 2, 0, 0, 0, 255);
                this.renderGuiQuad(bufferBuilder, x + 2, y + 13, Math.round(13.0F * item.getDurabilityValue(stack)), 1, color >> 16 & 255, color >> 8 & 255, color & 255, 255);
                RenderSystem.enableBlend();
                RenderSystem.enableAlphaTest();
                RenderSystem.enableTexture();
                RenderSystem.enableDepthTest();
            }

            return false;
        }

        return stack.isDamaged();
    }

    @SuppressWarnings("SameParameterValue")
    @Shadow
    private void renderGuiQuad(BufferBuilder buffer, int x, int y, int width, int height, int red, int green, int blue, int alpha)
    {
    }
}