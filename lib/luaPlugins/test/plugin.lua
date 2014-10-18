pluginName = "examplePlugin"
pluginDescription = "example plugin description"
pluginEvents = "OnConsoleInput"
debugMode=false

function OnConsoleInput(cmd, ei)
	--if(ei:isCancelled) return
	--end
	--ei:isCancelled = true   .." ei:"..ei:isCancelled
	print("cmd is="..cmd:toString())
end