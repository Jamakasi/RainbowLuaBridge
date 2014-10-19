package rainbowluabridge;
/**
 * @author Jamakasi
 */
import PluginReference.*;
import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class MyPlugin extends PluginBase{

    public static MC_Server server = null;
    
    public static String logPrefix = "RLB: ";
    public static String plugDir = (new File("").getAbsolutePath())+File.separator+"luaPlugins"+File.separator;
    
    public static LuaPluginSearch lps;
    public Vector plugins;
    LuaPlugin plg;
    public void onStartup(MC_Server argServer)
	{
		System.out.println("======= Rainbow Lua Bridge --- Startup! =======");
                server = argServer;
                    lps = new LuaPluginSearch();
                    lps.InitPlugins();
                server.registerCommand(new MyPluginCmd());
                plugins = lps.getPlugins();
	}
	
    public void onShutdown()
	{
		System.out.println("======= Rainbow Lua Bridge --- Shutdown! =======");
                lps.shutdownPlugins();
	}
    //Called whenever a permission check occurs.
    public void	onServerFullyLoaded(){
        
    }
    /*
    * About plugin
    */
    public PluginInfo getPluginInfo() 
	{ 
		PluginInfo info = new PluginInfo();
		info.description = "Rainbow Lua Bridge plugin by Jamakasi";
		info.eventSortOrder = -2345.67f; // call me much earlier than default
		return info;
	}
    
    
    /*
    * Game events
    */
    public void onConsoleInput(String cmd, MC_EventInfo ei) 
	{
            for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.OnConsoleInput(cmd,  ei);
            } 
	}
    /*
    * Called when a player interacts with an Armor Stand
    */
    public void onAttemptArmorStandInteract(MC_Player plr, MC_Entity entStand, MC_ArmorStandActionType actionType, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptArmorStandInteract(plr, entStand, actionType,  ei);
            } 
    }
    /*
    * Called when a player attempts to attack an entity
    */
    public void onAttemptAttackEntity(MC_Player plr, MC_Entity ent, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptAttackEntity(plr, ent, ei);
            } 
    }
    /*
    * Called when a player attempts a block break.
    */
    public void onAttemptBlockBreak(MC_Player plr, MC_Location loc, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptBlockBreak(plr, loc, ei);
            } 
    }
    /*
    * Called when a block flows (i.e.
    */
    public void onAttemptBlockFlow(MC_Location loc, MC_Block blk, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptBlockFlow(loc, blk, ei);
            } 
    }
    /*
    * Called when a book is about to change.
    */
    public void onAttemptBookChange(MC_Player plr, List<String> bookContent, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptBookChange(plr, bookContent, ei);
            } 
    }
    /*
    * Called when a farmland is about to get trampled
    */
    public void onAttemptCropTrample(MC_Entity ent, MC_Location loc, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptCropTrample(ent, loc, ei);
            } 
    }
    
    public void	onAttemptDamageHangingEntity(MC_Player plr, MC_Location loc, MC_HangingEntityType entType, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptDamageHangingEntity(plr, loc, entType, ei);
            } 
    }
    //Called when a player attempts to damage a Painting or Item Frame
    public void	onAttemptEntityDamage(MC_Entity ent, MC_DamageType dmgType, double amt, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptEntityDamage(ent, dmgType, amt, ei);
            } 
    }
    //Called when an entity is about to take damage.
    public void	onAttemptEntityInteract(MC_Player plr, MC_Entity ent, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptEntityInteract(plr, ent, ei);
            } 
    }
    //Called when a player interacts with an entity
    public void	onAttemptEntitySpawn(MC_Entity ent, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptEntitySpawn(ent, ei);
            } 
    }
    //Called when an entity is about to spawn.
    public boolean onAttemptExplodeSpecific(MC_Entity ent, List<MC_Location> locs){
       
        return true;
    }
    //Called when an explosion occurs but you can selectively cancel individual blocks.
    public void	onAttemptExplosion(MC_Location loc, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptExplosion(loc,  ei);
            } 
    }
    //Called when an explosion occurs but not for individual blocks.
    public void	onAttemptHopperReceivingItem(MC_Location loc, MC_ItemStack is, boolean isMinecartHopper, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptHopperReceivingItem(loc, is, isMinecartHopper, ei);
            } 
    }
    //Called when a hopper is about to receive an item.
    public void	onAttemptItemDrop(MC_Player plr, MC_ItemStack is, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptItemDrop(plr, is, ei);
            } 
    }
    //Called when a player attempts to drop an item
    public void	onAttemptItemFrameInteract(MC_Player plr, MC_Location loc, MC_ItemFrameActionType actionType, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptItemFrameInteract(plr, loc, actionType, ei);
            } 
    }
    //Called when a player attempts to interact with an Item Frame
    public void	onAttemptItemPickup(MC_Player plr, MC_ItemStack is, boolean isXpOrb, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptItemPickup(plr, is, isXpOrb, ei);
            } 
    }
    //Called when a player attempts to pickup an item
    public void	onAttemptItemUse(MC_Player plr, MC_ItemStack is, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptItemUse(plr, is, ei);
            } 
    }
    //Called when a player attempts to use an item, even into the air or not a valid item use.
    public void	onAttemptPistonAction(MC_Location loc, MC_DirectionNESWUD dir, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptPistonAction(loc, dir, ei);
            } 
    }
    //Called when a piston fires
    public void	onAttemptPlaceOrInteract(MC_Player plr, MC_Location loc, MC_EventInfo ei, MC_DirectionNESWUD dir){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptPlaceOrInteract(plr, loc, ei, dir);
            } 
    }
    //Called when a player attempts to either place or interact with a block
    public void	onAttemptPlayerChangeDimension(MC_Player plr, int newDimension, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptPlayerChangeDimension(plr, newDimension, ei);
            } 
    }
    //Called when a player changes dimension (Nether, TheEnd, etc)
    public void	onAttemptPlayerMove(MC_Player plr, MC_Location locFrom, MC_Location locTo, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptPlayerMove(plr, locFrom, locTo, ei);
            } 
    }
    //Called when a player moves.
    public void	onAttemptPlayerTeleport(MC_Player plr, MC_Location loc, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptPlayerTeleport(plr, loc, ei);
            } 
    }
    //Called when a player teleport is occurring.
    public void	onAttemptPotionEffect(MC_Player plr, MC_PotionEffectType potionType, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onAttemptPotionEffect(plr, potionType, ei);
            } 
    }
    //Called when a player is receiving a potion effect (from potion or beacon etc).
    public void	onBlockBroke(MC_Player plr, MC_Location loc, int blockKey){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onBlockBroke(plr, loc, blockKey);
            } 
    }
    //Called after a block was broken.
    public void	onContainerClosed(MC_Player plr, MC_ContainerType containerType){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onContainerClosed(plr, containerType);
            } 
    }
    //Called when any container closes, even player inventory
    public void	onContainerOpen(MC_Player plr, List<MC_ItemStack> items, String internalClassName){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onContainerOpen(plr, items, internalClassName);
            } 
    }
    //Called when most containers is opened.
    public void	onFallComplete(MC_Entity ent, float fallDistance, MC_Location loc, boolean isWaterLanding){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onFallComplete(ent, fallDistance, loc, isWaterLanding);
            } 
    }
    //Called when a player lands from a fall or jump.
    public void	onGenerateWorldColumn(int x, int z, MC_GeneratedColumn data){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onGenerateWorldColumn(x, z, data);
            } 
    }
    //Called when a new column of terrain needs generating in default world.
    public void	onInteracted(MC_Player plr, MC_Location loc, MC_ItemStack isHandItem){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onInteracted(plr, loc, isHandItem);
            } 
    }
    //Called after an Interact (right-click) on something happened.
    public void	onItemPlaced(MC_Player plr, MC_Location loc, MC_ItemStack isHandItem, MC_Location locPlacedAgainst, MC_DirectionNESWUD dir){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onItemPlaced(plr, loc, isHandItem, locPlacedAgainst, dir);
            } 
    }
    //Called after an item is placed.
    public void	onNonPlayerEntityDeath(MC_Entity entVictim, MC_Entity entKiller, MC_DamageType dmgType){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onNonPlayerEntityDeath(entVictim, entKiller, dmgType);
            } 
    }
    //Called when a non-player entity dies
    public void	onPacketSoundEffect(MC_Player plr, String soundName, MC_Location loc, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onPacketSoundEffect(plr, soundName, loc, ei);
            } 
    }
    //Called when a player is about to receive a sound effect from server.
    public void	onPlayerDeath(MC_Player plrVictim, MC_Player plrKiller, MC_DamageType dmgType, String deathMsg){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onPlayerDeath(plrVictim, plrKiller, dmgType, deathMsg);
            } 
    }
    //Called after a player death
    public void	onPlayerInput(MC_Player plr, String msg, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onPlayerInput(plr, msg, ei);
            } 
    }
    //Called for all player input.
    public  void onPlayerJoin(MC_Player plr){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onPlayerJoin(plr);
            } 
    }
    //Called after a player is joined and is able to interact and receive messages
    public void	onPlayerLogin(String playerName, UUID uuid, String ip){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onPlayerLogin(playerName, uuid, ip);
            } 
    }
    //Called when a player logs in.
    public void	onPlayerLogout(String playerName, UUID uuid){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onPlayerLogout(playerName, uuid);
            } 
    }
    //Called when a player logs out
    public void	onPlayerRespawn(MC_Player plr){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onPlayerRespawn(plr);
            } 
    }
    //Called when a player respawns after death
    public Boolean onRequestPermission(String playerKey, String permission){
        return true;
    }
    
    //Called when server is shutting down
    public void	onSignChanged(MC_Player plr, MC_Sign sign, MC_Location loc){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onSignChanged(plr, sign, loc);
            } 
    }
    //Called after a sign update occurs
    public void	onSignChanging(MC_Player plr, MC_Sign sign, MC_Location loc, List<String> newLines, MC_EventInfo ei){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onSignChanging(plr, sign, loc, newLines, ei);
            } 
    }
    //Called when a plugin is loaded.
    public void	onTick(int tickNumber){
        for(int i=0; i<plugins.size();i++){
                    plg = (LuaPlugin)plugins.get(i);  
                    plg.onTick(tickNumber);
            } 
    }
    //Called every tick (1/20th of a second).
}
