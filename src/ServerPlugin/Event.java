package ServerPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Event implements Listener {

    private main plugin;
    public Event(main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        plugin.data.createPlayer(player);
        player.sendMessage("짜잔");
        ScoreBoard.createBoard(player);
    }

    @EventHandler
    public void onChat(PlayerChatEvent e){
        Player player = e.getPlayer();
        e.setCancelled(true);

        switch (plugin.data.readrank(player.getUniqueId())){
            case "noob":
                Bukkit.broadcastMessage("<"+ChatColor.YELLOW+"뉴비"+" "+ChatColor.WHITE+player.getName()+"> "+e.getMessage());
                break;
            case "people":
                Bukkit.broadcastMessage("<"+ChatColor.GREEN+"평민"+" "+ChatColor.WHITE+player.getName()+"> "+e.getMessage());
                break;
            case "middle-class":
                Bukkit.broadcastMessage("<"+ChatColor.YELLOW+""+ChatColor.BOLD+"중산층 " +ChatColor.WHITE+player.getName()+"> "+e.getMessage());
                break;


        }
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e){

    }


}
