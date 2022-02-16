package ServerPlugin;

import Dungeon.Monster.slime;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.particles.ParticleParamBlock;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.network.protocol.game.PacketPlayOutWorldParticles;
import net.minecraft.server.level.WorldServer;
import org.apache.commons.lang.enums.Enum;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.type.Bed;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.Player;
import Dungeon.*;
import Character.*;

public class Command implements CommandExecutor {

    private main plugin;

    public Command(main plugin){
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String s, String[] args) {
        if(cmd.getName().equalsIgnoreCase("update")){
            if(sender instanceof Player) {
                Player player = (Player) sender;


                        plugin.data.UpdateRank(args[0],player.getUniqueId());
                        player.sendMessage("짠");

                }
            }
        else if(cmd.getName().equalsIgnoreCase("letme")){
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length != 0) {
                    if (args[0].equalsIgnoreCase("Thor")) {
                        Thor.LetMeThor(player);
                    }
                    else if(args[0].equalsIgnoreCase("Resister")){
                        Resister.LetMeResister(player);
                    }
                    else if(args[0].equalsIgnoreCase("open")){
                        GUI.GUI(player);
                    }
                    else if(args[0].equalsIgnoreCase("Boomer")){
                        Boomer.LetMeBoomer(player);
                    }
                    else if(args[0].equalsIgnoreCase("GodBow")){
                        GodBow.LetMeGodBow(player);
                    }
                }
            }
        }
        else if(cmd.getName().equalsIgnoreCase("circle")){
            if(sender instanceof Player) {
                Player player = (Player)sender;


            }
        }



        return false;


    }
    //public void drawCircle(Location loc,float radius){

       // for (double i = 0; i < 50; i+=0.5) {
       //     float x = radius * (float) Math.sin(i);
      //      float z = radius * (float) Math.cos(i);
        //    Location circleLoc = new Location(loc.getWorld(),(float) loc.getX()+x,(float) loc.getY(),(float) loc.getZ()+z,loc.getYaw(),loc.getPitch());
      //      loc.getWorld().getBlockAt(circleLoc).setType(Material.STONE);

     //   }
   // }
}
