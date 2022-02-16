package ServerPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreBoard {
    public static void createBoard(Player p){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("HubScoreboard-1","dummy",
                ChatColor.translateAlternateColorCodes('&',"&6능력자 &bPVP "));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score1 = obj.getScore(ChatColor.WHITE+"=-=-=-=-=-=-=-=");
        score1.setScore(3);
        Score score = obj.getScore("");
        score.setScore(2);
        Score score2 = obj.getScore(ChatColor.WHITE+"접속자 수:"+ChatColor.YELLOW+Bukkit.getOnlinePlayers().size());
        score2.setScore(1);
        Score score3 = obj.getScore(ChatColor.WHITE+"kill Count:"+ChatColor.RED+"미구현");
        score3.setScore(0);
        p.setScoreboard(board);
    }
}
