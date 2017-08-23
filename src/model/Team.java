package model;

import java.util.*;

public class Team implements Comparable<Team> {

    private String teamName;
    private String coachName;
    private ArrayList<Player> teamSquad;
    private Set<Player> playersAvailable;
    private ArrayList<Player> experiencedPlayers;
    private ArrayList<Player> inexperiencedPlayers;

    public Team() {
        playersAvailable = new HashSet<>(Arrays.asList(Players.load()));
    }

    public Team(String teamName, String coachName) {
        this.teamName = teamName;
        this.coachName = coachName;
        teamSquad = new ArrayList<>();
        }

    public void addPlayer(Player player) {
        teamSquad.add(player);
    }

    public void removePlayer(Player player) {
        teamSquad.remove(player);
    }

    public void addToPlayersAvailable(Player player) {
        playersAvailable.add(player);
    }

    public void removeFromPlayersAvailable(Player player) {
        playersAvailable.remove(player);
    }

    public String toString() {
        return "Team " + teamName;
    }

    public List<String> getPlayersAvailableToString() {
        List<String> players = new ArrayList<>();
        for (Player player : getPlayersAvailable()) {
            players.add(player.toFullString());
        }
        Collections.sort(players);
        return players;
}

    public List<String> getTeamSquadToString() {
        List<String> squad = new ArrayList<>();
        for (Player player : teamSquad) {
            squad.add(player.toFullString());
        }
        return squad;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getCoachName() {
        return coachName;
    }

    public ArrayList<Player> getPlayersAvailable() {
        return new ArrayList<>(playersAvailable);
    }

    public ArrayList<Player> getTeamSquad() {
        return teamSquad;
    }

    public ArrayList<Player> getExperiencedPlayers() {
        experiencedPlayers = new ArrayList<>();
        for (Player player: getPlayersAvailable()) {
            if (player.isPreviousExperience()) {
                experiencedPlayers.add(player);
            }
        }
        return experiencedPlayers;
    }

    public ArrayList<Player> getInexperiencedPlayers() {
        inexperiencedPlayers = new ArrayList<>();
        for (Player player: getPlayersAvailable()) {
            if (!player.isPreviousExperience()) {
                inexperiencedPlayers.add(player);
            }
        }
        return inexperiencedPlayers;
    }

    public Player getPlayerByIndex(int index, ArrayList<Player> list) {
        List<Player> listPlayers = new ArrayList<>(list);
        Player player = listPlayers.get(index);
        return player;
    }

    @Override
    public int compareTo(Team other) {
        return teamName.compareToIgnoreCase(other.getTeamName());
    }
}