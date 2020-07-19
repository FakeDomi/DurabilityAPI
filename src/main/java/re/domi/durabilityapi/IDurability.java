package re.domi.durabilityapi;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public interface IDurability
{
    /**
     * Whether the item renderer should draw a durability bar.
     * @param stack The {@link ItemStack} that is currently being rendered.
     * @return true if there should be a durability bar, false otherwise.
     */
    default boolean hasDurabilityBar(ItemStack stack)
    {
        return stack.isDamaged();
    }

    /**
     * The fill level of the durability bar. 0 means completely empty, 1 means full.
     * @param stack The {@link ItemStack} that is currently being rendered.
     * @return A value between [0.0, 1.0] (inclusive).
     */
    default float getDurabilityValue(ItemStack stack)
    {
        float maxDamage = stack.getMaxDamage();
        return Math.max(0F, Math.min(1F, maxDamage - stack.getDamage() / maxDamage));
    }

    /**
     * The packed RGB value representing the color the durability bar should have.
     * @param stack The {@link ItemStack} that is currently being rendered.
     * @return An integer representing the color to be used.
     */
    default int getDurabilityColor(ItemStack stack)
    {
        return MathHelper.hsvToRgb(this.getDurabilityValue(stack) / 3F, 1F, 1F);
    }
}
