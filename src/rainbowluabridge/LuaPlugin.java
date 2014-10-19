package rainbowluabridge;

import PluginReference.*;
import java.io.File;
import java.util.List;
import java.util.UUID;
import org.keplerproject.luajava.LuaObject;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

/*
 * @author Jamakasi
 */


public class LuaPlugin{
    private final LuaState Lstate;
    
    private final String pluginName;
    private final String pluginDescription;
    private final String[] pluginEvents;
    private final String luaFile;
    private final String pathToLuaFile;
    private boolean debug = false;
    
    public void setDebug(boolean status){
        this.debug = status;
    }
    public boolean isDebug(){
        return this.debug;
    }
    LuaPlugin(final String luaFileName){
        luaFile = luaFileName.substring(luaFileName.lastIndexOf(File.separator), luaFileName.length());
        pathToLuaFile = luaFileName;
        Lstate = LuaStateFactory.newLuaState();
        Lstate.openLibs();
        int err =Lstate.LdoFile(luaFileName);
        isLuaErr(err, Lstate);
        //if(!isLuaErr(err)){
                //pluginName = luaGetFuncAsString("getPluginName");
                //pluginDescription = luaGetFuncAsString("getPluginDescription");
                pluginName = luaGetVarAsString("pluginName");
                pluginDescription = luaGetVarAsString("pluginDescription");
                pluginEvents =luaGetVarAsString("pluginEvents").split(",");
                debug = luaGetVarAsBool("debugMode");
                log("loaded plugin: "+pluginName);
        //}
    }

    public void closeScript(){
        log(MyPlugin.logPrefix+"unloaded plugin: "+pluginName);
        Lstate.close();
    }
    /*public void reloadScript(){
        closeScript();
        init(pathToLuaFile);
    }*/
    public String getPluginName(){
        return pluginName;
    }
    public String getPluginDescription(){
        return pluginDescription;
    }
    
    
    private void log(String message){
        System.out.println(MyPlugin.logPrefix+message);
    }
    private boolean isLuaErr(int err, LuaState ls){
        if(err != 0 || this.isDebug())
        {
          switch (err)
          {
            case 1 :{
              log(pluginName+ "Runtime error. " + ls.toString(-1));
              return true;}

            case 2 :{
              log(pluginName+" File not found or error in lua function: " + ls.toString(-1));
              return true;}

            case 3 :{
              log(pluginName+" Syntax error. " + ls.toString(-1));
              return true;}

            case 4 :{
              log(pluginName+" Memory error. " + ls.toString(-1));
              return true;}

            default :{
              log(pluginName+" Error. " + ls.toString(-1));
              return true;}
          }
        }
        return false;
    }
    private String luaGetFuncAsString(String funcName){
        Lstate.getGlobal(funcName);
        int error = Lstate.pcall(0, 1, 0); //0 =все ништяк
        LuaObject lret = Lstate.getLuaObject(1);
            //isLuaErr(error);
        Lstate.pop(1);
        return lret.getString();
    }
    private String luaGetVarAsString(String varible){
            String result = Lstate.getLuaObject(varible).getString();
            if(result.equals(null)){
                log("error get global varible:"+varible+"set result to error");
                result = "error";
            }
        return result;
    }
    private boolean luaGetVarAsBool(String varible){
        return Lstate.getLuaObject(varible).getBoolean();
    }
    private boolean eventAvailable(String eventName){
        for (String ev : pluginEvents) 
        {  
            //log("checkEvent: "+ev);
            if(ev.equalsIgnoreCase(eventName)){
                return true;
            }else return false;
            /*switch (ev.toLowerCase()){
                     case"onAttemptArmorStandInteract":{return true;}
                     case"onAttemptAttackEntity":{return true;}
                     case"onAttemptBlockBreak":{return true;}
                     case"onAttemptBlockFlow":{return true;}
                     case"onAttemptBlockPlace":{return true;}
                     case"onAttemptBookChange":{return true;}
                     case"onAttemptCropTrample":{return true;}
                     case"onAttemptDamageHangingEntity":{return true;}
                     case"onAttemptEntityDamage":{return true;}
                     case"onAttemptEntityInteract":{return true;}
                     case"onAttemptEntitySpawn":{return true;}
                     case"onAttemptExplodeSpecific":{return true;}
                     case"onAttemptExplosion":{return true;}
                     case"onAttemptHopperReceivingItem":{return true;}
                     case"onAttemptItemDrop":{return true;}
                     case"onAttemptItemFrameInteract":{return true;}
                     case"onAttemptItemPickup":{return true;}
                     case"onAttemptItemUse":{return true;}
                     case"onAttemptPistonAction":{return true;}
                     case"onAttemptPlaceOrInteract":{return true;}
                     case"onAttemptPlayerChangeDimension":{return true;}
                     case"onAttemptPlayerMove":{return true;}
                     case"onAttemptPlayerTeleport":{return true;}
                     case"onAttemptPotionEffect":{return true;}
                     case"onBlockBroke":{return true;}
                     case"onConsoleInput":{log("checkEvent: "+ev);return true;}
                     case"onContainerClosed":{return true;}
                     case"onContainerOpen":{return true;}
                     case"onFallComplete":{return true;}
                     case"onGenerateWorldColumn":{return true;}
                     case"onInteracted":{return true;}
                     case"onItemCrafted":{return true;}
                     case"onItemPlaced":{return true;}
                     case"onNonPlayerEntityDeath":{return true;}
                     case"onPacketSoundEffect":{return true;}
                     case"onPlayerBedEnter":{return true;}
                     case"onPlayerBedLeave":{return true;}
                     case"onPlayerDeath":{return true;}
                     case"onPlayerInput":{return true;}
                     case"onPlayerJoin":{return true;}
                     case"onPlayerLogin":{return true;}
                     case"onPlayerLogout":{return true;}
                     case"onPlayerRespawn":{return true;}
                     case"onRequestPermission":{return true;}
                     case"onServerFullyLoaded":{return true;}
                     case"onShutdown":{return true;}
                     case"onSignChanged":{return true;}
                     case"onSignChanging":{return true;}
                     case"onStartup":{return true;}
                     case"onTick":{return true;}
                     default:{log("checkEvent: "+ev);return false;}
            }*/
        }
        return false;
    }
   /* private void luaGetFuncVoidEvent(String funcName,String cmd, MC_EventInfo ei){
        Lstate.getGlobal(funcName);
        Lstate.pushJavaObject(cmd);
        
        Lstate.pushJavaObject(ei);
        int error = Lstate.pcall(2, 0, 0); //0 =все ништяк
            luaErr(error);
        Lstate.pop(1);
    }*/
    
    /*
    Event proxy
    */
    public void OnConsoleInput(String cmd, MC_EventInfo ei){
        if(eventAvailable("OnConsoleInput")){
            Lstate.getGlobal("OnConsoleInput");
            Lstate.pushJavaObject(cmd);
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(2, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate);
            Lstate.pop(1);
            
        }
        
    }
    /*
    * Called when a player interacts with an Armor Stand
    */
    public void onAttemptArmorStandInteract(MC_Player plr, MC_Entity entStand, MC_ArmorStandActionType actionType, MC_EventInfo ei){
        if(eventAvailable("onAttemptArmorStandInteract")){
            Lstate.getGlobal("onAttemptArmorStandInteract");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(entStand);
            Lstate.pushJavaObject(actionType);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(4, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate);
            Lstate.pop(1);
        }    
    }
    /*
    * Called when a player attempts to attack an entity
    */
    public void onAttemptAttackEntity(MC_Player plr, MC_Entity ent, MC_EventInfo ei){
        if(eventAvailable("onAttemptAttackEntity")){
            Lstate.getGlobal("onAttemptAttackEntity");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(ent);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(3, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate);
            Lstate.pop(1);
        }
    }
    /*
    * Called when a player attempts a block break.
    */
    public void onAttemptBlockBreak(MC_Player plr, MC_Location loc, MC_EventInfo ei){
        if(eventAvailable("onAttemptBlockBreak")){
            Lstate.getGlobal("onAttemptBlockBreak");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(loc);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(3, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
            
        }
    }
    /*
    * Called when a block flows (i.e.
    */
    public void onAttemptBlockFlow(MC_Location loc, MC_Block blk, MC_EventInfo ei){
        if(eventAvailable("onAttemptBlockFlow")){
            Lstate.getGlobal("onAttemptBlockFlow");
            Lstate.pushJavaObject(loc);
            Lstate.pushJavaObject(blk);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(3, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    /*
    * Called when a book is about to change.
    */
    public void onAttemptBookChange(MC_Player plr, List<String> bookContent, MC_EventInfo ei){
        if(eventAvailable("onAttemptBookChange")){
            Lstate.getGlobal("onAttemptBookChange");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(bookContent);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(3, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    /*
    * Called when a farmland is about to get trampled
    */
    public void onAttemptCropTrample(MC_Entity ent, MC_Location loc, MC_EventInfo ei){
        if(eventAvailable("onAttemptCropTrample")){
            Lstate.getGlobal("onAttemptCropTrample");
            Lstate.pushJavaObject(ent);
            Lstate.pushJavaObject(loc);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(3, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    
    public void	onAttemptDamageHangingEntity(MC_Player plr, MC_Location loc, MC_HangingEntityType entType, MC_EventInfo ei){
        if(eventAvailable("onAttemptDamageHangingEntity")){
            Lstate.getGlobal("onAttemptDamageHangingEntity");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(loc);
            Lstate.pushJavaObject(entType);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(4, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when a player attempts to damage a Painting or Item Frame
    public void	onAttemptEntityDamage(MC_Entity ent, MC_DamageType dmgType, double amt, MC_EventInfo ei){
        if(eventAvailable("onAttemptEntityDamage")){
            Lstate.getGlobal("onAttemptEntityDamage");
            Lstate.pushJavaObject(ent);
            Lstate.pushJavaObject(dmgType);
            Lstate.pushJavaObject(amt);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(4, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when an entity is about to take damage.
    public void	onAttemptEntityInteract(MC_Player plr, MC_Entity ent, MC_EventInfo ei){
        if(eventAvailable("onAttemptEntityInteract")){
            Lstate.getGlobal("onAttemptEntityInteract");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(ent);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(3, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when a player interacts with an entity
    public void	onAttemptEntitySpawn(MC_Entity ent, MC_EventInfo ei){
        if(eventAvailable("onAttemptEntitySpawn")){
            Lstate.getGlobal("onAttemptEntitySpawn");
            Lstate.pushJavaObject(ent);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(2, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when an entity is about to spawn.
    public boolean onAttemptExplodeSpecific(MC_Entity ent, List<MC_Location> locs){
       
        return true;
    }
    //Called when an explosion occurs but you can selectively cancel individual blocks.
    public void	onAttemptExplosion(MC_Location loc, MC_EventInfo ei){
        if(eventAvailable("onAttemptExplosion")){
            Lstate.getGlobal("onAttemptExplosion");
            Lstate.pushJavaObject(loc);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(2, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when an explosion occurs but not for individual blocks.
    public void	onAttemptHopperReceivingItem(MC_Location loc, MC_ItemStack is, boolean isMinecartHopper, MC_EventInfo ei){
        if(eventAvailable("onAttemptHopperReceivingItem")){
            Lstate.getGlobal("onAttemptHopperReceivingItem");
            Lstate.pushJavaObject(loc);
            Lstate.pushJavaObject(is);
            Lstate.pushJavaObject(isMinecartHopper);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(4, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when a hopper is about to receive an item.
    public void	onAttemptItemDrop(MC_Player plr, MC_ItemStack is, MC_EventInfo ei){
        if(eventAvailable("onAttemptItemDrop")){
            Lstate.getGlobal("onAttemptItemDrop");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(is);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(3, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when a player attempts to drop an item
    public void	onAttemptItemFrameInteract(MC_Player plr, MC_Location loc, MC_ItemFrameActionType actionType, MC_EventInfo ei){
        if(eventAvailable("onAttemptItemFrameInteract")){
            Lstate.getGlobal("onAttemptItemFrameInteract");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(loc);
            Lstate.pushJavaObject(actionType);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(4, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when a player attempts to interact with an Item Frame
    public void	onAttemptItemPickup(MC_Player plr, MC_ItemStack is, boolean isXpOrb, MC_EventInfo ei){
        if(eventAvailable("onAttemptItemPickup")){
            Lstate.getGlobal("onAttemptItemPickup");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(is);
            Lstate.pushJavaObject(isXpOrb);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(4, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when a player attempts to pickup an item
    public void	onAttemptItemUse(MC_Player plr, MC_ItemStack is, MC_EventInfo ei){
        if(eventAvailable("onAttemptItemUse")){
            Lstate.getGlobal("onAttemptItemUse");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(is);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(3, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when a player attempts to use an item, even into the air or not a valid item use.
    public void	onAttemptPistonAction(MC_Location loc, MC_DirectionNESWUD dir, MC_EventInfo ei){
        if(eventAvailable("onAttemptPistonAction")){
            Lstate.getGlobal("onAttemptPistonAction");
            Lstate.pushJavaObject(loc);
            Lstate.pushJavaObject(dir);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(3, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when a piston fires
    public void	onAttemptPlaceOrInteract(MC_Player plr, MC_Location loc, MC_EventInfo ei, MC_DirectionNESWUD dir){
        if(eventAvailable("onAttemptPlaceOrInteract")){
            Lstate.getGlobal("onAttemptPlaceOrInteract");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(loc);
            
            Lstate.pushJavaObject(ei);
            Lstate.pushJavaObject(dir);
            int error = Lstate.pcall(4, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when a player attempts to either place or interact with a block
    public void	onAttemptPlayerChangeDimension(MC_Player plr, int newDimension, MC_EventInfo ei){
        if(eventAvailable("onAttemptPlayerChangeDimension")){
            Lstate.getGlobal("onAttemptPlayerChangeDimension");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(newDimension);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(3, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when a player changes dimension (Nether, TheEnd, etc)
    public void	onAttemptPlayerMove(MC_Player plr, MC_Location locFrom, MC_Location locTo, MC_EventInfo ei){
        if(eventAvailable("onAttemptPlayerMove")){
            Lstate.getGlobal("onAttemptPlayerMove");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(locFrom);
            Lstate.pushJavaObject(locTo);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(4, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when a player moves.
    public void	onAttemptPlayerTeleport(MC_Player plr, MC_Location loc, MC_EventInfo ei){
        if(eventAvailable("onAttemptPlayerTeleport")){
            Lstate.getGlobal("onAttemptPlayerTeleport");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(loc);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(3, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when a player teleport is occurring.
    public void	onAttemptPotionEffect(MC_Player plr, MC_PotionEffectType potionType, MC_EventInfo ei){
        if(eventAvailable("onAttemptPotionEffect")){
            Lstate.getGlobal("onAttemptPotionEffect");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(potionType);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(3, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when a player is receiving a potion effect (from potion or beacon etc).
    public void	onBlockBroke(MC_Player plr, MC_Location loc, int blockKey){
        if(eventAvailable("onBlockBroke")){
            Lstate.getGlobal("onBlockBroke");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(loc);
            Lstate.pushJavaObject(blockKey);
            int error = Lstate.pcall(3, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called after a block was broken.
    public void	onContainerClosed(MC_Player plr, MC_ContainerType containerType){
        if(eventAvailable("onContainerClosed")){
            Lstate.getGlobal("onContainerClosed");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(containerType);
            int error = Lstate.pcall(2, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when any container closes, even player inventory
    public void	onContainerOpen(MC_Player plr, List<MC_ItemStack> items, String internalClassName){
        if(eventAvailable("onContainerOpen")){
            Lstate.getGlobal("onContainerOpen");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(items);
            Lstate.pushJavaObject(internalClassName);
            int error = Lstate.pcall(3, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when most containers is opened.
    public void	onFallComplete(MC_Entity ent, float fallDistance, MC_Location loc, boolean isWaterLanding){
        if(eventAvailable("onFallComplete")){
            Lstate.getGlobal("onFallComplete");
            Lstate.pushJavaObject(ent);
            Lstate.pushJavaObject(fallDistance);
            Lstate.pushJavaObject(loc);
            Lstate.pushJavaObject(isWaterLanding);
            int error = Lstate.pcall(4, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when a player lands from a fall or jump.
    public void	onGenerateWorldColumn(int x, int z, MC_GeneratedColumn data){
        if(eventAvailable("onGenerateWorldColumn")){
            Lstate.getGlobal("onGenerateWorldColumn");
            Lstate.pushJavaObject(x);
            Lstate.pushJavaObject(z);
            Lstate.pushJavaObject(data);
            int error = Lstate.pcall(3, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when a new column of terrain needs generating in default world.
    public void	onInteracted(MC_Player plr, MC_Location loc, MC_ItemStack isHandItem){
        if(eventAvailable("onInteracted")){
            Lstate.getGlobal("onInteracted");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(loc);
            Lstate.pushJavaObject(isHandItem);
            int error = Lstate.pcall(3, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called after an Interact (right-click) on something happened.
    public void	onItemPlaced(MC_Player plr, MC_Location loc, MC_ItemStack isHandItem, MC_Location locPlacedAgainst, MC_DirectionNESWUD dir){
        if(eventAvailable("onItemPlaced")){
            Lstate.getGlobal("onItemPlaced");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(loc);
            Lstate.pushJavaObject(isHandItem);
            Lstate.pushJavaObject(locPlacedAgainst);
            Lstate.pushJavaObject(dir);
            int error = Lstate.pcall(5, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called after an item is placed.
    public void	onNonPlayerEntityDeath(MC_Entity entVictim, MC_Entity entKiller, MC_DamageType dmgType){
        if(eventAvailable("onNonPlayerEntityDeath")){
            Lstate.getGlobal("onNonPlayerEntityDeath");
            Lstate.pushJavaObject(entVictim);
            Lstate.pushJavaObject(entKiller);
            Lstate.pushJavaObject(dmgType);
            int error = Lstate.pcall(3, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when a non-player entity dies
    public void	onPacketSoundEffect(MC_Player plr, String soundName, MC_Location loc, MC_EventInfo ei){
        if(eventAvailable("onPacketSoundEffect")){
            Lstate.getGlobal("onPacketSoundEffect");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(soundName);
            Lstate.pushJavaObject(loc);
             
             Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(4, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when a player is about to receive a sound effect from server.
    public void	onPlayerDeath(MC_Player plrVictim, MC_Player plrKiller, MC_DamageType dmgType, String deathMsg){
        if(eventAvailable("onPlayerDeath")){
            Lstate.getGlobal("onPlayerDeath");
            Lstate.pushJavaObject(plrVictim);
            Lstate.pushJavaObject(plrKiller);
            Lstate.pushJavaObject(dmgType);
             Lstate.pushJavaObject(deathMsg);
            int error = Lstate.pcall(4, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called after a player death
    public void	onPlayerInput(MC_Player plr, String msg, MC_EventInfo ei){
        if(eventAvailable("onPlayerInput")){
            Lstate.getGlobal("onPlayerInput");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(msg);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(3, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called for all player input.
    public  void onPlayerJoin(MC_Player plr){
            if(eventAvailable("onPlayerJoin")){
            Lstate.getGlobal("onPlayerJoin");
            Lstate.pushJavaObject(plr);
            int error = Lstate.pcall(1, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called after a player is joined and is able to interact and receive messages
    public void	onPlayerLogin(String playerName, UUID uuid, String ip){
        if(eventAvailable("onPlayerLogin")){
            Lstate.getGlobal("onPlayerLogin");
            Lstate.pushJavaObject(playerName);
            Lstate.pushJavaObject(uuid);
            Lstate.pushJavaObject(ip);
            int error = Lstate.pcall(3, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when a player logs in.
    public void	onPlayerLogout(String playerName, UUID uuid){
        if(eventAvailable("onPlayerLogout")){
            Lstate.getGlobal("onPlayerLogout");
            Lstate.pushJavaObject(playerName);
            Lstate.pushJavaObject(uuid);
            int error = Lstate.pcall(2, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when a player logs out
    public void	onPlayerRespawn(MC_Player plr){
        if(eventAvailable("onPlayerRespawn")){
            Lstate.getGlobal("onPlayerRespawn");
            Lstate.pushJavaObject(plr);
            int error = Lstate.pcall(1, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when a player respawns after death
    public Boolean onRequestPermission(String playerKey, String permission){
        return true;
    }
    
    //Called when server is shutting down
    public void	onSignChanged(MC_Player plr, MC_Sign sign, MC_Location loc){
        if(eventAvailable("onSignChanged")){
            Lstate.getGlobal("onSignChanged");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(sign);
            Lstate.pushJavaObject(loc);
            int error = Lstate.pcall(3, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called after a sign update occurs
    public void	onSignChanging(MC_Player plr, MC_Sign sign, MC_Location loc, List<String> newLines, MC_EventInfo ei){
        if(eventAvailable("onSignChanging")){
            Lstate.getGlobal("onSignChanging");
            Lstate.pushJavaObject(plr);
            Lstate.pushJavaObject(sign);
            Lstate.pushJavaObject(loc);
            Lstate.pushJavaObject(newLines);
            
            Lstate.pushJavaObject(ei);
            int error = Lstate.pcall(5, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called when a plugin is loaded.
    public void	onTick(int tickNumber){
        if(eventAvailable("onTick")){
            Lstate.getGlobal("onTick");
            Lstate.pushJavaObject(tickNumber);
            int error = Lstate.pcall(1, 0, 0); //0 =все ништяк
            isLuaErr(error, Lstate); 
            Lstate.pop(1);
        }
    }
    //Called every tick (1/20th of a second).
}
