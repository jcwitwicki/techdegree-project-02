package model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class League {

    Team team = new Team();
    private List<Team> allTeams;
    private int lowHeight;
    private int highHeight;
    private static final int MAX_PLAYERS = 11;

    public League() {
        allTeams = new ArrayList<>();
        lowHeight = team.lowestHeight();
        highHeight = team.highestHeight();
    }

    public Team teamCreation(String teamName, String coachName) {
        Team newTeam = new Team(teamName, coachName);
        allTeams.add(newTeam);
        return newTeam;
    }

    public void addPlayerToTeam(Player player, Team teamSelected, Team team) {
        teamSelected.addPlayer(player);
        team.removeFromPlayersAvailable(player);
        System.out.printf("%n" + player + " added to " + teamSelected + "%n");
    }

    public void removePlayerFromTeam(Player player, Team teamSelected, Team team) {
        teamSelected.removePlayer(player);
        team.addToPlayersAvailable(player);
        System.out.printf("%n" + player + " removed from " + teamSelected + "%n");
    }

    public void printTeamRoster(Team teamSelected) {
        Collections.sort(teamSelected.getTeamSquad());
        List<String> players = teamSelected.getTeamSquadToString();
        int counter = 1;
        for (String player : players) {
            System.out.printf("%d.)  %s %n", counter, player);
            counter++;
        }
    }

    public String experienceVsInexperienceV2(Team teamSelected) {
        HashMap<String, Boolean> report = new HashMap<>();

        List<Player> players = teamSelected.getTeamSquad();
        for (Player player : players) {
            report.put(player.getLastName(), player.isPreviousExperience());
        }
        int experiencedPlayers = 0;
        int inexperiencedPlayers = 0;
        for (Map.Entry<String, Boolean> entry : report.entrySet()) {
            if (entry.getValue().equals(true)) experiencedPlayers++;
            else inexperiencedPlayers++;
        }
        NumberFormat formatter = new DecimalFormat("#0.00");
        double totalPlayers = experiencedPlayers + inexperiencedPlayers;
        String percentageOfExperiencedPlayers = formatter.format((100 / totalPlayers) * experiencedPlayers);
        return experiencedPlayers + " vs. " + inexperiencedPlayers + " - " + percentageOfExperiencedPlayers + "% of experienced players.";
    }

    public void heightReport(Team teamSelected) {
        HashMap<String, Integer> report = new HashMap<>();

        ArrayList<Player> players = teamSelected.getTeamSquad();
        for (Player player : players) {
            report.put(player.getLastName(), player.getHeightInInches());
        }

//        Here I chose to use a method which allow to divide into three equal height ranges,
//        depending on the lowest and highest height among all the players:

        List<String> heightLowToInt1 = new ArrayList<>();
        List<String> heightInt1ToInt2 = new ArrayList<>();
        List<String> heightInt2ToHigh = new ArrayList<>();

        int intermediateHeight1 = ((highHeight - lowHeight) / 3) + lowHeight;
        int intermediateHeight2 = highHeight - ((highHeight - lowHeight) / 3);
        for (HashMap.Entry<String, Integer> entry : report.entrySet()) {
            if (entry.getValue() >= lowHeight && entry.getValue() < intermediateHeight1) {
                heightLowToInt1.add(entry.getKey());
            } else if (entry.getValue() >= intermediateHeight1 && entry.getValue() <= intermediateHeight2) {
                heightInt1ToInt2.add(entry.getKey());
            } else if (entry.getValue() >= intermediateHeight2 && entry.getValue() <= highHeight) {
                heightInt2ToHigh.add(entry.getKey());
            }
        }
        System.out.printf("%nHeight " + lowHeight + "-" + (intermediateHeight1 - 1) + " inches: " + heightLowToInt1);
        System.out.printf("%nHeight " + intermediateHeight1 + "-" + (intermediateHeight2) + " inches: " + heightInt1ToInt2);
        System.out.printf("%nHeight " + (intermediateHeight2 + 1) + "-" + highHeight + " inches: " + heightInt2ToHigh + "%n%n");


//        Here is the method using ranges as per requirement (35-40, 41-46, 47-50 inches):

//        List<String> height35To40 = new ArrayList<>();
//        List<String> height41To46 = new ArrayList<>();
//        List<String> height47To50 = new ArrayList<>();
//
//        for (HashMap.Entry<String,Integer> entry : report.entrySet()) {
//            if (entry.getValue() >= 35 && entry.getValue() < 41) {
//                height35To40.add(entry.getKey());
//            } else if (entry.getValue() >= 41 && entry.getValue() <= 46) {
//                height41To46.add(entry.getKey());
//            } else if (entry.getValue() >= 47 && entry.getValue() <= 50) {
//                height47To50.add(entry.getKey());
//            }
//        }
//        System.out.printf("%nHeight 35-40 inches: " + height35To40);
//        System.out.printf("%nHeight 41-46 inches: " + height41To46);
//        System.out.printf("%nHeight 47-50 inches: " + height47To50 + "%n%n");

    }

    public void extraHeightReport(Team teamSelected) {
        List<Player> players = teamSelected.getTeamSquad();
        for (int i = lowHeight; i < highHeight + 1; i++) {
            int count = 0;
            for (Player player : players) {
                if (player.getHeightInInches() == i) {
                    count++;
                }
            }
            if (count > 0) System.out.println("Height " + i + ": " + count + " player(s)");
        }
    }

    public static int getMaxPlayers() {
        return MAX_PLAYERS;
    }

    public List<Team> getAllTeams() {
        return allTeams;
    }

    public List<String> getAllTeamsToString() {
        List<String> teams = new ArrayList<String>();
        for (Team team : allTeams) {
            teams.add(team.getTeamName() + ", coached by " + team.getCoachName());
        }
        return teams;
    }

}
