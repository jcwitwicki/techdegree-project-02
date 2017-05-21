import model.Player;
import model.Players;

import java.io.IOException;

public class LeagueManager {

    public static void main(String[] args) throws IOException {
        Player[] players = Players.load();
        System.out.printf("There are currently %d registered players.", players.length);
        // Your code here!
        UserInterface ui = new UserInterface();
        ui.run();
    }
}