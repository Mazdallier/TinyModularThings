package speiger.src.tinymodularthings.common.pipes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import speiger.src.tinymodularthings.common.handler.PipeIconHandler;
import buildcraft.api.core.IIconProvider;
import buildcraft.core.network.TileNetworkData;
import buildcraft.transport.Pipe;
import buildcraft.transport.PipeTransportFluids;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FluidRegstonePipe extends Pipe<PipeTransportFluids>
{
	@TileNetworkData
	public boolean isFull = false;
	
	public FluidRegstonePipe(int itemID)
	{
		super(new PipeTransportFluids(), itemID);
		transport.flowRate = 40;
		transport.travelDelay = 4;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIconProvider getIconProvider()
	{
		return PipeIconHandler.getIcons();
	}
	
	@Override
	public int getIconIndex(ForgeDirection direction)
	{
		if (isFull)
		{
			return 3;
		}
		return 2;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (!this.getWorld().isRemote)
		{
			int i = ((PipeTransportFluids) this.transport).internalTanks[ForgeDirection.UNKNOWN.ordinal()].getAvailable();
			if (i < 230 && isFull)
			{
				isFull = false;
				this.updateNeighbors(true);
			}
			else if (!isFull && !(i < 230))
			{
				isFull = true;
				this.updateNeighbors(true);
			}
		}
		
	}
	
	@Override
	public int isPoweringTo(int side)
	{
		if (isFull)
		{
			return 15;
		}
		return 0;
	}
	
	@Override
	public int isIndirectlyPoweringTo(int l)
	{
		return isPoweringTo(l);
	}

	@Override
	public void readFromNBT(NBTTagCompound data)
	{
		super.readFromNBT(data);
		isFull = data.getBoolean("isFilled");
	}

	@Override
	public void writeToNBT(NBTTagCompound data)
	{
		super.writeToNBT(data);
		data.setBoolean("IsFilled", isFull);
	}
	
	
	
	
	
}
