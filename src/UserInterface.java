import model.League;
import model.Player;
import model.Players;
import model.Team;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserInterface {

    private static final int MAX_TEAMS = (Players.load().length);
    League league = new League();
    Team team = new Team();
    private Team teamSelected;
    private Player playerSelected;
    private BufferedReader mReader;
    private Map<String, String> mPrompter;

    public UserInterface() {
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mPrompter = new LinkedHashMap<>();

        mPrompter.put("Create", "Create a new team");
        mPrompter.put("Add", "Add player to selected team");
        mPrompter.put("Remove", "Remove player from selected team");
        mPrompter.put("Balance", "League Balance Report");
        mPrompter.put("Height", "Height report for selected team");
        mPrompter.put("Roster", "Print roster for selected team");
        mPrompter.put("Quit", "Exit the program");
    }

    private String promptAction() throws IOException {
        System.out.printf("%nYour options are: %n%n");
        for (Map.Entry<String, String> entry : mPrompter.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.printf("%nWhat do you want to do? : ");
        String choice = mReader.readLine();
        return choice.toLowerCase();
    }

    public void run() {
        String choice = "";
        do {
            try {
                choice = promptAction();

                switch (choice) {
                    case "create":
                        createNewTeam();
                        break;

                    case "add":
                        addPlayerToSelectedTeam();
                        break;

                    case "remove":
                        removePlayerFromSelectedTeam();
                        break;

                    case "balance":
                        leagueBalanceReport();
                        break;

                    case "height":
                        leagueHeightReport();
                        break;

                    case "roster":
                        printRoster();
                        break;

                    case "quit":
                        System.out.println("Thank you for playing");
                        break;

                    default:
                        System.out.println("Unknown choice: " + choice + ". Please try again.");
                }
            } catch (IOException ioe) {
                System.out.println("Problem with input");
                ioe.printStackTrace();
            }
        } while (!choice.equals("quit"));
    }

    private void createNewTeam() throws IOException {
        if (league.getAllTeams().size() < MAX_TEAMS) {
            System.out.printf("%nName of the team: ");
            String teamName = mReader.readLine();
            System.out.printf("Name of the coach: ");
            String coachName = mReader.readLine();
            System.out.printf("Coach " + coachName + " will be in charge of Team " + teamName + "!%n");
            league.teamCreation(teamName, coachName);
        } else {
            System.out.println("Sorry, no more teams allowed. Please choose an other option.");
        }
    }

    private void addPlayerToSelectedTeam() throws IOException {
        if (team.getPlayersAvailableToString().size() == 0) {
            System.out.println("No more available players");
        } else {
            selectTeam();
            if (teamSelected.getTeamSquadToString().size() >= league.getMaxPlayers()) {
                System.out.println("Maximum number of players reached for team " + teamSelected);
                System.out.println("Please remove players or select an other team.");
            } else {
                Collections.sort(team.getPlayersAvailable());
                playerSelected = promptPlayerToAdd(team.getPlayersAvailable());
                league.addPlayerToTeam(playerSelected, teamSelected, team);
            }
        }
    }

    private void removePlayerFromSelectedTeam() throws IOException {
        selectTeam();
        if (teamSelected.getTeamSquadToString().size() == 0) {
            System.out.printf("%n Team " + teamSelected + " is empty");
        } else {
            Collections.sort(teamSelected.getTeamSquad());
            playerSelected = promptPlayerToRemove(teamSelected.getTeamSquad());
            league.removePlayerFromTeam(playerSelected, teamSelected, team);
        }
    }

    private void leagueBalanceReport() {
        System.out.printf("%nLeague Balance Report: Experienced Players vs. Inexperienced Players%n%n");
        Collections.sort(league.getAllTeams());
        List<Team> options = league.getAllTeams();
        for (Team option : options) {
            System.out.printf("%s:  %s %n", option, league.experienceVsInexperienceV2(option));
        }
    }

    private void leagueHeightReport() throws IOException {
        selectTeam();
        System.out.printf("%n" + teamSelected + ": %n");
        league.heightReport(teamSelected);
        league.extraHeightReport(teamSelected);
    }

    private void printRoster() throws IOException {
        selectTeam();
        System.out.printf("%n" + teamSelected + ": %n%n");
        league.printTeamRoster(teamSelected);
    }

    private Team selectTeam() throws IOException {
        if (league.getAllTeams().size() == 0) {
            System.out.printf("%nNo teams available, please create it first: %n");
            createNewTeam();
            selectTeam();
        } else {
            Collections.sort(league.getAllTeams());
            teamSelected = promptTeam(league.getAllTeams());
        }
        return teamSelected;
    }

    private Team promptTeam(List<Team> allTeams) throws IOException {
        System.out.printf("%nAvailable teams: %n%n");
        int index = promptForIndex(league.getAllTeamsToString());
        return allTeams.get(index);
    }

    private Player promptPlayerToAdd(List<Player> allPlayers) throws IOException {
        System.out.printf("%nAvailable players: %n%n");
        int index = promptForIndex(team.getPlayersAvailableToString());
        Collections.sort(allPlayers);
        return allPlayers.get(index);
    }

    private Player promptPlayerToRemove(List<Player> teamSquad) throws IOException {
        System.out.printf("%nAvailable players: %n%n");
        int index = promptForIndex(teamSelected.getTeamSquadToString());
        return teamSquad.get(index);
    }

    private int promptForIndex(List<String> options) throws IOException {
        int counter = 1;
        for (String option : options) {
            System.out.printf("%d.)  %s %n", counter, option);
            counter++;
        }
        int choice = 0;
        do {
            try {
                System.out.printf("%nYour choice:   ");
                String optionAsString = mReader.readLine();
                choice = Integer.parseInt(optionAsString.trim());
            } catch (NumberFormatException nfe) {
                System.out.println("Focus!");
            }
        } while (!(choice > 0 && choice < counter));
        return choice - 1;
    }

}

