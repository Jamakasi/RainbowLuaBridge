/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rainbowluabridge;

import PluginReference.*;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Admin
 */
public class MyPluginCmd implements MC_Command
{

	@Override
	public String getCommandName()
	{
		return "lua";
	}
	@Override
	public List<String> getAliases()
	{
		return Arrays.asList(new String[]{"rlb"});
	}

	@Override
	public String getHelpLine(MC_Player plr)
	{
	    return ChatColor.GREEN + "/lua" + ChatColor.WHITE + " reload pluginName|info pluginName| plugins" ;
	}

	@Override
	public List<String> getTabCompletionList(MC_Player plr, String[] args)
	{
		
		return null;
	}

	@Override
	public void handleCommand(MC_Player plr, String[] args)
	{
		/*// If no argument, tell them usage...
		if(args.length <= 0)
		{
			plr.sendMessage(getHelpLine(plr));
			return;
		}
		
		String command = args[0];
                String targetPlugin = args[1];
		switch(command){
                    case"reload":{
                        luaPluginReload(plr, targetPlugin);
                        break;
                    }
                    case"info":{
                        luaPluginInfo(plr, targetPlugin);
                        break;
                    }
                    case"plugins":{
                        luaPluginsList(plr);
                        break;
                    }
                    default:{
                        plr.sendMessage(ChatColor.RED+"unknown lua command."+getHelpLine(plr));
                        break;
                    }
                }
                
		//plr.sendMessage(ChatColor.GREEN + "Receiving player head: " + ChatColor.YELLOW + tgtName);
                */
	}

	@Override
	public boolean hasPermissionToUse(MC_Player plr)
	{
		if(plr == null) return true;
		return plr.hasPermission("lua.control");
	}
        
        private void luaPluginReload(MC_Player plr,String pluginName){
            
        }
        /*private void luaPluginsList(MC_Player plr){
            String[] list = MyPlugin.lps.getPluginsList();
                        for(String lplist: list){
                            plr.sendMessage(MyPlugin.logPrefix+lplist);
                        }
        }*/
        private void luaPluginInfo(MC_Player plr,String pluginName){
            
        }
}