package speiger.src.compactWindmills.common.items;

import java.util.HashMap;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import speiger.src.api.items.DisplayStack;
import speiger.src.api.items.IRotorItem;
import speiger.src.api.items.LanguageItem;
import speiger.src.api.language.LanguageRegister;
import speiger.src.api.nbt.NBTHelper;
import speiger.src.api.tiles.IWindmill;
import speiger.src.api.util.MathUtils;
import speiger.src.api.util.SpmodMod;
import speiger.src.api.util.SpmodModRegistry;
import speiger.src.compactWindmills.CompactWindmills;
import speiger.src.compactWindmills.common.core.CWPreference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRotor extends Item implements LanguageItem, IRotorItem
{
	public static HashMap<BasicRotorType, Icon> textures = new HashMap<BasicRotorType, Icon>();
	
	
	public ItemRotor(int par1)
	{
		super(par1);
		this.setHasSubtypes(true);
	}

	@Override
	public boolean ignoreTier(ItemStack par1)
	{
		return false;
	}

	@Override
	public boolean canWorkWithWindmillTier(ItemStack par1, int tier)
	{
		return BasicRotorType.values()[par1.getItemDamage()].matchTier(tier);
	}

	@Override
	public int getTier(ItemStack par1)
	{
		return BasicRotorType.values()[par1.getItemDamage()].getTier();
	}
	
	

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1)
	{
		return textures.get(BasicRotorType.values()[par1]);
	}
	
	

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1)
	{
		for(BasicRotorType type : BasicRotorType.values())
		{
			textures.put(type, par1.registerIcon(CWPreference.ModID.toLowerCase()+":rotors/"+type.getDisplayName()));
		}
	}

	@Override
	public void damageRotor(ItemStack par1, int damage, IWindmill windmill)
	{
		BasicRotorType type = BasicRotorType.values()[par1.getItemDamage()];
		if(!NBTHelper.nbtCheck(par1, "Rotor"))
		{
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("Damage", 0);
			par1.setTagInfo("Rotor", nbt);
		}
		int totalDamage = par1.getTagCompound().getCompoundTag("Rotor").getInteger("Damage");
		if(totalDamage + damage > type.getMaxDamage())
		{
			windmill.distroyRotor();
			return;
		}
		totalDamage += damage;
		par1.getTagCompound().getCompoundTag("Rotor").setInteger("Damage", totalDamage);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ResourceLocation getRenderTexture(ItemStack par1)
	{
		return null;
	}

	@Override
	public void onRotorTick(IWindmill windMill, World world, ItemStack rotor)
	{
		
	}

	@Override
	public float getRotorEfficeny(ItemStack par1)
	{
		return BasicRotorType.values()[par1.getItemDamage()].getEfficeny();
	}

	@Override
	public boolean isInfinite(ItemStack par1)
	{
		return BasicRotorType.values()[par1.getItemDamage()].getMaxDamage() == 0;
	}

	@Override
	public IRotorModel getCustomModel(ItemStack par1, int size)
	{
		return null;
	}
	
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1, EntityPlayer par2EntityPlayer, List par3, boolean par4)
	{
		if(NBTHelper.nbtCheck(par1, "Rotor"))
		{
			BasicRotorType type = BasicRotorType.values()[par1.getItemDamage()];
			int damage = par1.getTagCompound().getCompoundTag("Rotor").getInteger("Damage");
			int damageLeft = type.getMaxDamage() - damage;
			int eff = (int) (type.getEfficeny() * 100);
			if(this.isInfinite(par1))
			{
				par3.add("Stays Infinite");
			}
			else
			{
				par3.add("Time the Rotor will stay at Least: ");
				par3.add(MathUtils.getTicksInTime(damageLeft*64));
			}
			par3.add("Rotor Efficency: "+eff+"%");
			if(Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54))
			{
				par3.add("Damage: "+damage+" / "+type.getMaxDamage());
			}
		}
	}
	
	

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("Damage", 0);
		for(BasicRotorType type : BasicRotorType.values())
		{
			ItemStack stack = new ItemStack(par1, 1, type.ordinal());
			stack.setTagInfo("Rotor", nbt);
			par3List.add(stack);
		}
	}

	@Override
	public String getItemDisplayName(ItemStack par1ItemStack)
	{
		return getDisplayName(par1ItemStack, CompactWindmills.instance);
	}

	@Override
	public String getDisplayName(ItemStack par1, SpmodMod par0)
	{
		return LanguageRegister.getLanguageName(new DisplayStack(par1), BasicRotorType.values()[par1.getItemDamage()].getDisplayName(), par0);
	}

	@Override
	public void registerItems(int id, SpmodMod par0)
	{
		if(!SpmodModRegistry.areModsEqual(par0, CompactWindmills.instance))
		{
			return;
		}
		for(BasicRotorType type : BasicRotorType.values())
		{
			LanguageRegister.getLanguageName(new DisplayStack(new ItemStack(id, 1, type.ordinal())), type.getDisplayName(), par0);
		}
		
	}
	
	@Override
	public boolean isAdvancedRotor(ItemStack par1)
	{
		return false;
	}
	
	public static enum BasicRotorType
	{
		WoodenRotor(2250, 0, 0.5F, "rotor.basic.wood"),
		WoolRotor(562, 0, 0.9F, "rotor.basic.wool"),
//		IronRotor(),
		CarbonRotor(27000, 2, 0.75F, "rotor.basic.carbon"),
		AlloyRotor(6750, 3, 0.9F, "rotor.basic.alloy"),
		IridiumRotor(0, 4, 1.0F, "rotor.basic.iridium");
		
		int maxDamage;
		int tier;
		float eff;
		String displayName;
		
		private BasicRotorType(int par1, int par2, float par3, String par4)
		{
			maxDamage = par1;
			tier = par2;
			eff = par3;
			displayName = par4;
		}
		
		public String getDisplayName()
		{
			return displayName;
		}

		public float getEfficeny()
		{
			return eff;
		}

		public int getMaxDamage()
		{
			return maxDamage;
		}

		public int getTier()
		{
			return tier;
		}

		public boolean matchTier(int Wtier)
		{
			if(Wtier - 1 == tier || Wtier == tier || Wtier + 1 == tier)
			{
				return true;
			}
			return false;
		}
	}


	
}