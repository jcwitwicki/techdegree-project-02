package model;

import java.util.*;

public class Team implements Comparable<Team> {

    private String teamName;
    private String coachName;
    private ArrayList<Player> teamSquad;
    private Set<Player> playersAvailable;

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

    public int lowestHeight() {
        List<Integer> heightPlayers = new ArrayList<>();
        int lowHeight = 0;
        for (Player player : getPlayersAvailable()) {
            heightPlayers.add(player.getHeightInInches());
            lowHeight = heightPlayers.get(0);
            for (Integer i : heightPlayers) {
                if (i < lowHeight) lowHeight = i;
            }
        }
        return lowHeight;
    }

    public int highestHeight() {
        List<Integer> heightPlayers = new ArrayList<>();
        int highHeight = 0;
        for (Player player : getPlayersAvailable()) {
            heightPlayers.add(player.getHeightInInches());
            highHeight = heightPlayers.get(0);
            for (Integer i : heightPlayers) {
                if (i > highHeight) highHeight = i;
            }
        }
        return highHeight;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getCoachName() {
        return coachName;
    }

    public List<Player> getPlayersAvailable() {
        return new ArrayList<>(playersAvailable);
    }

    public ArrayList<Player> getTeamSquad() {
        return teamSquad;
    }

    @Override
    public int compareTo(Team other) {
        return teamName.compareToIgnoreCase(other.getTeamName());
    }
}
