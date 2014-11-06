package speiger.src.spmodapi.common.items.crafting;

import net.minecraft.item.ItemStack;
import speiger.src.spmodapi.common.config.ModObjects.APIUtils;
import speiger.src.spmodapi.common.items.core.SpmodItem;

public class RedstoneCable extends SpmodItem
{
	
	public RedstoneCable(int par1)
	{
		super(par1);
		this.setCreativeTab(APIUtils.tabCrafing);
	}

	@Override
	public String getName(ItemStack par1)
	{
		return "Redstone Cable";
	}

	
}
