package speiger.src.ic2Fixes.common.energy;

import ic2.api.Direction;
import ic2.api.energy.tile.IEnergyConductor;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.tileentity.TileEntity;

public class EnergyPath
{
	TileEntity target = null;
	Direction targetDirection;
	Set<IEnergyConductor> conductors = new HashSet<IEnergyConductor>();
	
	int minX = 2147483647;
	int minY = 2147483647;
	int minZ = 2147483647;
	int maxX = -2147483648;
	int maxY = -2147483648;
	int maxZ = -2147483648;
	
	double loss = 0.0D;
    int minInsulationEnergyAbsorption = 2147483647;
    int minInsulationBreakdownEnergy = 2147483647;
    int minConductorBreakdownEnergy = 2147483647;
    long totalEnergyConducted = 0L;
}
