package ServerPlugin;


import Dungeon.Monster.SpawnStick;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import Dungeon.*;
import Character.*;
import java.sql.SQLException;


public class main extends JavaPlugin implements Listener {

    public mysql SQL;
    public SQLGetter data;
    private Command command;






    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("The_War_of_God System Enabled");
        this.SQL = new mysql();
        this.data = new SQLGetter(this);
        try {
            SQL.connect();
        } catch (ClassNotFoundException | SQLException e) {
            //e.printStackTrace();
            Bukkit.getLogger().info("DB is not connected");
        }

        if (SQL.isConnected()) {
            Bukkit.getLogger().info("DB is connected");
        }

        getServer().getPluginManager().registerEvents(new Event(this), this);
        getServer().getPluginManager().registerEvents(new Thor(this),this);
        getServer().getPluginManager().registerEvents(new GUI(),this);
        getServer().getPluginManager().registerEvents(new Resister(this),this);
        getServer().getPluginManager().registerEvents(new Boomer(),this);
        getServer().getPluginManager().registerEvents(new GodBow(this),this);
        getCommand("update").setExecutor(new Command(this));
        getCommand("letme").setExecutor(new Command(this));
        getCommand("circle").setExecutor(new Command(this));

        ItemLoader.LoadItems();

        DropItem.loadItems();



    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("The_War_of_God System Disabled");
        SQL.disconnect();
    }







}
