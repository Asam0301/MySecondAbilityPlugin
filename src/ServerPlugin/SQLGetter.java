package ServerPlugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLGetter {
    private main plugin;
    public SQLGetter(main plugin){
        this.plugin = plugin;
    }
    public  void createPlayer(Player player){
        try {
            UUID uuid = player.getUniqueId();
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM User WHERE uuid=?");
            ps.setString(1,uuid.toString());
            ResultSet results = ps.executeQuery();
            results.next();
            if(!exists(uuid)){
                //let's create
                PreparedStatement ps2 = plugin.SQL.getConnection().prepareStatement("INSERT INTO User VALUES(?,?,?,?,?,?);");
                ps2.setString(1,player.getName());
                ps2.setString(2,uuid.toString());
                ps2.setString(3,"noob");
                ps2.setInt(4,0);
                ps2.setInt(5,0);
                ps2.setInt(6,0);
                ps2.executeUpdate();

                Bukkit.getLogger().info("SQL updated");

                return;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean exists(UUID uuid){
        try{
            PreparedStatement ps =plugin.SQL.getConnection().prepareStatement("SELECT * FROM User WHERE uuid=?");
            ps.setString(1,uuid.toString());

            ResultSet results = ps.executeQuery();
            if(results.next()) {
                //player is found
                return true;
            }
            return false;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public String readrank(UUID uuid){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT ranks FROM user WHERE uuid=?");
            ps.setString(1,uuid.toString());
            ResultSet rs = ps.executeQuery();
            String rank = "";
            if(rs.next()) {
                rank = rs.getString("ranks");
                return rank;
            }


        } catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    public void UpdateRank(String rank,UUID uuid){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT ranks FROM user WHERE uuid=?");
            ps.setString(1,uuid.toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                PreparedStatement ps2 = plugin.SQL.getConnection().prepareStatement("UPDATE user SET ranks=? WHERE uuid=?");
                ps2.setString(1,rank.toString());
                ps2.setString(2,uuid.toString());
                ResultSet rs2 = ps.executeQuery();
                ps2.executeUpdate();
                Bukkit.getLogger().info(ps2.toString());
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


}
