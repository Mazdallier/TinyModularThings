package speiger.src.tinymodularthings.common.plugins.BC.core;

import speiger.src.tinymodularthings.common.plugins.BC.triggers.TriggerEnergyFlow;
import speiger.src.tinymodularthings.common.plugins.BC.triggers.TriggerFuel;
import speiger.src.tinymodularthings.common.plugins.BC.triggers.TriggerHasWork;
import speiger.src.tinymodularthings.common.plugins.BC.triggers.TriggerPipeRequestPower;
import speiger.src.tinymodularthings.common.plugins.BC.triggers.TriggerRequestPower;
import speiger.src.tinymodularthings.common.plugins.BC.triggers.TriggerTank;
import buildcraft.api.gates.IAction;
import buildcraft.api.gates.ITrigger;

public class BCVariables
{
	// Triggers
	public static ITrigger hasWork = new TriggerHasWork(true);
	public static ITrigger noWork = new TriggerHasWork(false);
	public static ITrigger needFuel = new TriggerFuel();
	public static ITrigger requestPower = new TriggerRequestPower();
	public static ITrigger pipeRequestPower = new TriggerPipeRequestPower();
	public static ITrigger[] storedFluid = new ITrigger[]{new TriggerTank(0), new TriggerTank(1), new TriggerTank(2)};
	public static ITrigger[] energyFlows = new ITrigger[]{new TriggerEnergyFlow(true), new TriggerEnergyFlow(false)};
	
	
	// Actions
	public static IAction[] changeToSlot = new IAction[64];
	public static IAction[] changeOneTime = new IAction[2];
	public static IAction[] bcFiller = new IAction[2];
}
