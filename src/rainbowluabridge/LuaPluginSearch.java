/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rainbowluabridge;

import java.io.File;
import java.util.Vector;

/**
 *
 * @author Admin
 */
public class LuaPluginSearch {
    private File plugList = new File(MyPlugin.plugDir);
    private static int MAX_PLUGINS=255;
    
    private Vector plugin = new Vector();//LuaPlugin[] plugin = new LuaPlugin[MAX_PLUGINS];

    public void InitPlugins(){
        System.out.println("Lua path = "+MyPlugin.plugDir);
        if(!plugList.isDirectory() || plugList.exists()){
            plugList.mkdirs();
        }
        
        File[] folderlist = plugList.listFiles();
        
        if(plugList.list().length != 0){
            //int counter = 0;
            //LuaPlugin plg;
            for( File fileName : folderlist){
                if(fileName.isDirectory() && (new File(fileName.getAbsolutePath()+File.separator+"plugin.lua").exists())){
                    plugin.addElement((new LuaPlugin(fileName.getAbsolutePath()+File.separator+"plugin.lua")));
                   // plg = (LuaPlugin)plugin.get(counter);
                   // plg.init(fileName.getAbsolutePath()+File.separator+"plugin.lua");
                   // plg = null;
                   // counter++;
                }
            }
            //counter = 0;
        }else System.out.println(MyPlugin.logPrefix+"no lua plugins found");
    }
    public Vector getPlugins(){
        return plugin;
    }
    public LuaPlugin getPlugin(String pluginName){
        LuaPlugin plg;
        for(int i=0; i<plugin.size();i++){
            plg = (LuaPlugin)plugin.get(i);
            if(plg.getPluginName() == pluginName) 
                    return plg;
        }return null;
    }
    public String[] getPluginsNamesList(){
        String[] list = new String[plugin.size()];
        LuaPlugin plg;
        for(int i=0; i<plugin.size();i++){
                plg = (LuaPlugin)plugin.get(i);
                list[i]=plg.getPluginName();  

        }return list;
    }
    public String getPluginDescription(String pluginName){
        String[] list = new String[plugin.size()];
        LuaPlugin plg;
        for(int i=0; i<plugin.size();i++){
                plg = (LuaPlugin)plugin.get(i);
                if(plg.getPluginName().equalsIgnoreCase(pluginName)){
                    return plg.getPluginDescription();
                }  

        }return "error";
    }
    public void shutdownPlugins(){
        LuaPlugin plg;
            for(int i=0; i<plugin.size();i++){
                    plg = (LuaPlugin)plugin.get(i);  
                    plg.closeScript();
            } 
   }
    public void reloadPlugins(){
        LuaPlugin plg;
            for(int i=0; i<plugin.size();i++){
                    plg = (LuaPlugin)plugin.get(i);  
                    plg.reloadScript();
            } 
   }
    public void reloadPlugin(String pluginName){
        LuaPlugin plg;
            for(int i=0; i<plugin.size();i++){
                    plg = (LuaPlugin)plugin.get(i);  
                    if(plg.getPluginName().equalsIgnoreCase(pluginName)){
                        plg.reloadScript();
                        return;
                    }
            } 
   }
}
