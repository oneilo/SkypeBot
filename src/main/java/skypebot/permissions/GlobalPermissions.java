package skypebot.permissions;

import com.google.gson.Gson;
import skypebot.wrapper.BotUser;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kyle on 9/10/2015.
 */
public class GlobalPermissions {
    
    private final Gson gson = new Gson();
    private final File saveFile;
    
    private Map<String, Permission> globalPermissions;
    
    public GlobalPermissions() throws FileNotFoundException {
        saveFile = new File("global-permissions.json");
        load();
    }
    
    private void load() throws FileNotFoundException {
        if (saveFile.exists()) {
            globalPermissions = gson.fromJson(new FileReader(saveFile), HashMap.class);
        } else {
            globalPermissions = new HashMap<>();
        }
    }
    
    public void save() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
        writer.write(gson.toJson(globalPermissions));
        writer.close();
    }
    
    public boolean hasPermission(BotUser user, Permission permission) {
        if (globalPermissions.containsKey(user.getUsername())) {
            Permission permissionLevel = globalPermissions.get(user.getUsername());
            return permissionLevel.getLevel() >= permission.getLevel();
        }
        return false;
    }
    
    public Permission getPermission(BotUser sender) {
        return globalPermissions.get(sender.getUsername());
    }
    
    public void setPermission(BotUser user, Permission permission) {
        globalPermissions.put(user.getUsername(), permission);
    }
}
