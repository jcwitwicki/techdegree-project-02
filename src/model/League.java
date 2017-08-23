package model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class League {

    Team team = new Team();
    private List<Team> allTeams;
    public List<Player> waitingList;
    private ArrayList<Player> allPlayers;
    private int lowHeight;
    private int highHeight;
    private static final int MAX_PLAYERS = 11;

    public League() {
        allTeams = new ArrayList<>();
        waitingList = new ArrayList<>();
    }

    public Team teamCreation(String teamName, String coachName) {
        Team newTeam = new Team(teamName, coachName);
        allTeams.add(newTeam);
        return newTeam;
    }

    public Player playerCreation(String firstName, String lastName, int heightInInches, boolean previousExperience) {
        Player newPlayer = new Player(firstName, lastName, heightInInches, previousExperience);
        waitingList.add(newPlayer);
        return newPlayer;
    }

    public void addPlayerToTeam(Player player, Team teamSelected, Team team) {
        teamSelected.addPlayer(player);
        team.removeFromPlayersAvailable(player);
        System.out.printf(player + " added to " + teamSelected + "%n");
    }

    public void removePlayerFromTeam(Player player, Team teamSelected, Team team) {
        teamSelected.removePlayer(player);
        team.addToPlayersAvailable(player);
        System.out.printf("%n" + player + " removed from " + teamSelected + "%n");
    }

    // Here is a method to add players automatically and fairly, depending on their experience
    public void addPlayersFairly(Team teamSelected, Team team) {
        Player playerSelected;

        int maxExperiencedPlayers = (team.getExperiencedPlayers().size()) / (team.getPlayersAvailable().size() / getMaxPlayers());
        for (int i = 0; i < maxExperiencedPlayers; i++) {
            int random = (int) (Math.random() * team.getExperiencedPlayers().size());
            playerSelected = team.getPlayerByIndex(random, team.getExperiencedPlayers());
            addPlayerToTeam(playerSelected, teamSelected, team);
        }

        int maxInexperiencedPlayers = getMaxPlayers() - (teamSelected.getTeamSquad().size());
        for (int i = 0; i < maxInexperiencedPlayers; i++) {
            int random = (int) (Math.random() * team.getInexperiencedPlayers().size());
            playerSelected = team.getPlayerByIndex(random, team.getInexperiencedPlayers());
            addPlayerToTeam(playerSelected, teamSelected, team);
        }

        System.out.printf("%n" + (maxExperiencedPlayers + maxInexperiencedPlayers) + " players added to " + teamSelected + "%n");
    }

    // Here is a method to add players automatically and randomly, without checking their experience
    // Can easily be used in the addPlayersAutomatically() method from the UserInterface class
    public void addPlayersRandomly(Team teamSelected) {
        Player playerSelected;
        int num = League.getMaxPlayers() - (teamSelected.getTeamSquad().size());
        for (int i = 0; i < num; i++) {
            int random = (int) (Math.random() * team.getPlayersAvailable().size());
            playerSelected = team.getPlayerByIndex(random, team.getPlayersAvailable());
            addPlayerToTeam(playerSelected, teamSelected, team);
        }
        System.out.printf("%n" + num + " players added to " + teamSelected + "%n");
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

    public void printWaitingList() {
        System.out.printf("Waiting list: %n%n");
        List<Player> players = getWaitingList();
        int counter = 1;
        for (Player player : players) {
            System.out.printf("%d.)  %s %n", counter, player);
            counter++;
        }
    }

    public String experienceVsInexperienceV2(Team teamSelected) {
        HashMap<String, Boolean> report = new HashMap<>();

        List<Player> players = teamSelected.getTeamSquad();
        for (Player player : players) {
            report.put(player.getFullName(), player.isPreviousExperience());
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
        System.out.println(getAllPlayers());
        HashMap<String, Integer> report = new HashMap<>();

        ArrayList<Player> players = teamSelected.getTeamSquad();
        for (Player player : players) {
            report.put(player.getFullName(), player.getHeightInInches());
        }

//        Here I chose to use a method which allow to divide into three equal height ranges,
//        depending on the lowest and highest height among all the players:
        List<String> heightLowToInt1 = new ArrayList<>();
        List<String> heightInt1ToInt2 = new ArrayList<>();
        List<String> heightInt2ToHigh = new ArrayList<>();
        lowHeight = lowestHeight();
        highHeight = highestHeight();

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

    public int lowestHeight() {
        List<Integer> heightPlayers = new ArrayList<>();
        int lowHeight = 0;
        for (Player player : getAllPlayers()) {
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
        for (Player player : getAllPlayers()) {
            heightPlayers.add(player.getHeightInInches());
            highHeight = heightPlayers.get(0);
            for (Integer i : heightPlayers) {
                if (i > highHeight) highHeight = i;
            }
        }
        return highHeight;
    }

    public ArrayList<Player> getAllPlayers() {
        allPlayers = new ArrayList<>();
        for (Team team : getAllTeams()) {
            for (Player player : team.getTeamSquad()) {
                allPlayers.add(player);
            }
        }
        return allPlayers;
    }

    public static int getMaxPlayers() {
        return MAX_PLAYERS;
    }

    public List<Team> getAllTeams() {
        return allTeams;
    }

    public List<Player> getWaitingList() {
        return waitingList;
    }

    public List<String> getAllTeamsToString() {
        List<String> teams = new ArrayList<String>();
        for (Team team : allTeams) {
            teams.add(team.getTeamName() + ", coached by " + team.getCoachName());
        }
        return teams;
    }
}