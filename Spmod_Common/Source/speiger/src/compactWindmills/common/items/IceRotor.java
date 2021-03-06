package speiger.src.compactWindmills.common.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import speiger.src.api.common.data.nbt.NBTHelper;
import speiger.src.api.common.world.items.IRotorItem;
import speiger.src.api.common.world.tiles.interfaces.IWindmill;
import speiger.src.compactWindmills.CompactWindmills;
import speiger.src.compactWindmills.common.core.CWPreference;
import speiger.src.compactWindmills.common.items.ItemRotor.BasicRotorType;
import speiger.src.spmodapi.common.enums.EnumColor;
import speiger.src.spmodapi.common.enums.EnumColor.SpmodColor;
import speiger.src.spmodapi.common.items.core.SpmodItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class IceRotor extends SpmodItem implements IRotorItem
{
	public static ResourceLocation texture = new ResourceLocation(CWPreference.ModID.toLowerCase()+":textures/renders/rotor.base.ice.png");
	public IceRotor(int par1)
	{
		super(par1);
		this.setMaxDamage(Short.MAX_VALUE);
		this.setMaxStackSize(1);
	}

	@Override
	public void damageRotor(ItemStack par1, int damage, IWindmill windmill)
	{
		par1.setItemDamage(par1.getItemDamage()+damage);
		if(par1.getItemDamage() > par1.getMaxDamage())
		{
			windmill.distroyRotor();
		}
	}
	
	public static ItemStack getRotor(int id)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("Damage", 0);
		ItemStack item = new ItemStack(id, 1, 0);
		return item;
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		if(par2ItemStack != null && par2ItemStack.itemID == Block.ice.blockID)
		{
			return true;
		}
		return super.getIsRepairable(par1ItemStack, par2ItemStack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ResourceLocation getRenderTexture(ItemStack par1)
	{
		return texture;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1)
	{
		return CompactWindmills.rotor.getIconFromDamage(BasicRotorType.IridiumRotor.ordinal());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		return SpmodColor.fromHex(EnumColor.LIGHTBLUE.getAsHex()).mixWith(SpmodColor.fromHex(EnumColor.WHITE.getAsHex())).getHex();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(getRotor(par1));
	}

	@Override
	public void onRotorTick(IWindmill windMill, World world, ItemStack rotor)
	{
		if(world.isRemote || windMill.getFacing() != 0 && windMill.getFacing() != 1)
		{
			if(!world.isRemote)
			{
				windMill.setNewSpeed(0.0F);
			}
			return;
		}
		float currentSpeed = windMill.getActualSpeed();
		if(currentSpeed < 1.0F)
		{
			windMill.setNewSpeed(1.0F);
			damageRotor(rotor, 2, windMill);
			ChunkCoordinates coord = windMill.getChunkCoordinates();
			world.playSoundEffect((double)((float)coord.posX + 0.5F), (double)((float)coord.posY + 0.5F), (double)((float)coord.posZ + 0.5F), "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
		}		
	}
	
	@Override
	public boolean isInfinite(ItemStack par1)
	{
		return false;
	}
	
	@Override
	public IRotorModel getCustomModel(ItemStack par1)
	{
		return null;
	}

	@Override
	public String getName(ItemStack par1)
	{
		return "Ice Rotor";
	}

	@Override
	public boolean hasCustomSpeedMath(IWindmill par1, ItemStack rotor)
	{
		return true;
	}

	@Override
	public RotorWeight getRotorWeight(IWindmill par1, ItemStack par2)
	{
		return RotorWeight.Medium;
	}
	
	
	
}
