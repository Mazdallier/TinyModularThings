package speiger.src.spmodapi.common.lib.bc;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

/**
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class InventoryIterator
{
	
	/**
	 * Returns an Iterable object for the specified side of the inventory.
	 * 
	 * @param inv
	 * @param side
	 * @return Iterable
	 */
	public static Iterable<IInvSlot> getIterable(IInventory inv, ForgeDirection side)
	{
		if (inv instanceof ISidedInventory)
		{
			return new InventoryIteratorSided((ISidedInventory) inv, side);
		}
		
		return new InventoryIteratorSimple(inv);
	}
	
	public interface IInvSlot
	{
		
		int getIndex();
		
		boolean canPutStackInSlot(ItemStack stack);
		
		boolean canTakeStackFromSlot(ItemStack stack);
		
		ItemStack decreaseStackInSlot(int size);
		
		ItemStack getStackInSlot();
		
		void setStackInSlot(ItemStack stack);
	}
}
