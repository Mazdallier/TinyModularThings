package speiger.src.tinymodularthings.common.items.tools;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import speiger.src.api.items.DisplayItem;
import speiger.src.api.items.IItemGui;
import speiger.src.api.language.LanguageRegister;
import speiger.src.api.util.SpmodMod;
import speiger.src.api.util.SpmodModRegistry;
import speiger.src.tinymodularthings.TinyModularThings;
import speiger.src.tinymodularthings.client.gui.items.GuiPotionBag;
import speiger.src.tinymodularthings.common.config.ModObjects.TinyItems;
import speiger.src.tinymodularthings.common.enums.EnumIDs;
import speiger.src.tinymodularthings.common.items.core.TinyItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPotionBag extends TinyItem implements IItemGui
{
	
	
	
	public ItemPotionBag(int par1)
	{
		super(par1);
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.tabFood);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void registerItems(int id, SpmodMod par0)
	{
		if(!SpmodModRegistry.areModsEqual(getMod(), par0))
		{
			return;
		}
		LanguageRegister.getLanguageName(new DisplayItem(id), "potion.bag", par0);
	}
	
	@Override
	public String getDisplayName(ItemStack par1, SpmodMod par0)
	{
		return LanguageRegister.getLanguageName(new DisplayItem(par1.itemID), "potion.bag", par0);
	}

	@Override
	public boolean hasGui(ItemStack par1)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(InventoryPlayer par1, ItemStack par2)
	{
		return new GuiPotionBag(par1, getContainer(par1, par2));
	}

	@Override
	public boolean hasContainer(ItemStack par1)
	{
		return true;
	}

	@Override
	public Container getContainer(InventoryPlayer par1, ItemStack par2)
	{
		return new ContainerPotionBag(par1, new PotionInventory(par1, par2));
	}
	
	
	
	@Override
	public void onUpdate(ItemStack par1, World par2, Entity par3, int par4, boolean par5)
	{
		super.onUpdate(par1, par2, par3, par4, par5);
		if(!par2.isRemote && par3 != null && par3 instanceof EntityPlayer)
		{
			PotionInventory inv = new PotionInventory(((EntityPlayer)par3).inventory, par1);
			inv.onTick(par1);
			inv.onInventoryChanged();
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1, World par2, EntityPlayer par3)
	{
		if(!par2.isRemote)
		{
			if(par3.isSneaking())
			{
				
			}
			else
			{
				par3.openGui(TinyModularThings.instance, EnumIDs.Items.getId(), par2, 0, 0, 0);
			}
		}
		return par1;
	}

	public static ItemStack createEmptyPotionBag(int id)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("ID", ("Bag:"+System.currentTimeMillis()));
		ItemStack stack = new ItemStack(id, 1, 0);
		stack.setTagInfo("Bag", nbt);
		return stack;
	}
	public static boolean delay = false;
	@ForgeSubscribe
	public void onDrop(ItemTossEvent evt)
	{
		if(delay)
		{
			delay = false;
			return;
		}
		ItemStack stack = evt.entityItem.getEntityItem();
		EntityPlayer player = evt.player;
		if(player != null && player.openContainer != null && player.openContainer instanceof ContainerPotionBag)
		{
			if(stack.itemID == TinyItems.potionBag.itemID)
			{
				((ContainerPotionBag)player.openContainer).saveInventory();
				player.closeScreen();
				delay = true;
			}
		}
	}
	
}