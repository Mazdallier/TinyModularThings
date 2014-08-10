package speiger.src.spmodapi.common.modHelper.BC;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import speiger.src.api.items.InfoStack;
import speiger.src.api.language.LanguageRegister;
import speiger.src.spmodapi.SpmodAPI;
import buildcraft.BuildCraftCore;
import buildcraft.api.gates.IAction;
import buildcraft.api.gates.IActionParameter;
import buildcraft.api.gates.IGate;
import buildcraft.api.gates.IStatementParameter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ActionRandomLoop implements IAction
{
	
	public boolean all = false;
	public boolean randOnly;
	public int delay;
	
	public ActionRandomLoop(boolean par1)
	{
		randOnly = par1;
		delay = 1;
	}
	
	public ActionRandomLoop setAll()
	{
		all = true;
		return this;
	}
	
	public ActionRandomLoop(boolean par1, int ticks)
	{
		randOnly = par1;
		delay = ticks;
	}
	
	public boolean allBlocks()
	{
		return all;
	}
	
	public int getDelay()
	{
		return delay;
	}
	
	public boolean randomOnly()
	{
		return randOnly;
	}

	@Override
	public String getUniqueTag()
	{
		String alls = all ? "all" : "";
		return randOnly ? "rand.rand"+delay+alls : "rand.roundRoubin"+delay+alls;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon()
	{
		return BuildCraftCore.actionLoop.getIcon();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
	}
	
	
	@Override
	public String getDescription()
	{
		if(randOnly)
		{
			return LanguageRegister.getLanguageName(new InfoStack(), "action.random.color.change", SpmodAPI.instance)+" "+delay+" Ticks";
		}
		else
		{
			return LanguageRegister.getLanguageName(new InfoStack(), "action.randRoubin.color.change", SpmodAPI.instance)+" "+delay+" Ticks";
		}
	}

	@Override
	public IAction rotateLeft()
	{
		return this;
	}

	@Override
	public int maxParameters()
	{
		return 0;
	}

	@Override
	public int minParameters()
	{
		return 0;
	}

	@Override
	public IStatementParameter createParameter(int index)
	{
		return null;
	}

	@Override
	public void actionActivate(IGate gate, IActionParameter[] parameters)
	{
		
	}
}